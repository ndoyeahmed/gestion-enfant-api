package com.gestion.stock.services.admin;

import com.gestion.stock.entities.admin.Profil;
import com.gestion.stock.entities.admin.ProfilUtilisateur;
import com.gestion.stock.entities.admin.Utilisateur;
import com.gestion.stock.repositories.admin.ProfilRepository;
import com.gestion.stock.repositories.admin.ProfilUtilisateurRepository;
import com.gestion.stock.repositories.admin.UtilisateurRepository;
import com.gestion.stock.utils.Utils;
import com.gestion.stock.web.exceptions.BadRequestException;
import com.gestion.stock.web.exceptions.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Log
@Transactional
public class UtilisateurService {

    private UtilisateurRepository utilisateurRepository;
    private BCryptPasswordEncoder encoder;
    private ProfilRepository profilRepository;
    private ProfilUtilisateurRepository profilUtilisateurRepository;
    private MailService mailService;

    @Autowired
    public UtilisateurService(UtilisateurRepository utilisateurRepository,
                              ProfilRepository profilRepository,
                              MailService mailService,
                              BCryptPasswordEncoder encoder,
                              ProfilUtilisateurRepository profilUtilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.encoder = encoder;
        this.profilRepository = profilRepository;
        this.profilUtilisateurRepository = profilUtilisateurRepository;
        this.mailService = mailService;
    }

    public Utilisateur connectedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String login = "";
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            login = user.getUsername();
        }
        if (authentication.getPrincipal() instanceof String)
            login = (String) authentication.getPrincipal();
        return utilisateurRepository
                .connexion(login, true)
                .map(user -> user)
                .orElse(null);
    }

    public Utilisateur addUtilisateur(Utilisateur utilisateur, String profil) {
        Profil profil1 = profilRepository.findByLibelle(profil).orElseThrow(() -> new EntityNotFoundException("profil not found"));
        String password = UUID.randomUUID().toString();
        utilisateur.setPassword(encoder.encode(password));
        utilisateur.setLogin(Utils.getLogin(utilisateur.getNom().trim(), utilisateur.getPrenom().trim()));
        utilisateur.setStatut(true);
        utilisateur.setArchive(false);
        ProfilUtilisateur profilUtilisateur = new ProfilUtilisateur();
        utilisateurRepository.save(utilisateur);
        profilUtilisateur.setUtilisateur(utilisateur);
        profilUtilisateur.setProfil(profil1);
        profilUtilisateur.setDate(Timestamp.valueOf(LocalDateTime.now()));
        profilUtilisateurRepository.save(profilUtilisateur);

        // send email
        try {
            String subject = "Création de compte";
            String body = "Bienvenue " + utilisateur.getPrenom() + " " + utilisateur.getNom() + ",<br/>Veuillez recevoir ci-dessous votre login et votre mot de passe pour " +
                    "vous connectez à la plateforme. <br/>" +
                    "<strong>Login : </strong>" + utilisateur.getLogin()
                    + "<br/><strong>Mot de passe : </strong>" + password + "";
            mailService.send(utilisateur.getEmail(), subject, body);
        } catch (Exception e) {
            log.severe(e.getLocalizedMessage());
        }
        return utilisateur;
    }

    public List<Utilisateur> findAllByArchiveFalseAndStatutTrue(boolean archive) {
        return utilisateurRepository.findAllByArchive(archive).orElse(new ArrayList<>());
    }

    public List<Profil> findAllProfil() {
        return profilRepository.findAll();
    }

    public Utilisateur findUserByIdAndArchiveFalseAndStatutTrue(Long id) {
        return utilisateurRepository.findByIdAndArchiveFalseAndStatutTrue(id).orElse(null);
    }

    public Utilisateur updateUtilisateur(Map<String, String> body, Long id) {
        Utilisateur utilisateur = findUserByIdAndArchiveFalseAndStatutTrue(id);
        ProfilUtilisateur profilUtilisateur = profilUtilisateurRepository
                .findByUtilisateurAndProfil(utilisateur, utilisateur.getProfilUtilisateurs().get(0).getProfil())
                .orElseThrow(() -> new EntityNotFoundException("profil user not found"));
        Profil profil = profilRepository.findByLibelle(body.get("profil") != null ? body.get("profil") : utilisateur.getProfilUtilisateurs().get(0).getProfil().getLibelle())
                .orElseThrow(() -> new EntityNotFoundException("profil not found"));
        utilisateur.setAdresse(body.get("adresse") != null && !body.get("adresse").trim().equals("") ? body.get("adresse") : utilisateur.getAdresse());
        utilisateur.setNom(body.get("nom") != null && !body.get("nom").trim().equals("") ? body.get("nom") : utilisateur.getNom());
        utilisateur.setPrenom(body.get("prenom") != null && !body.get("prenom").trim().equals("") ? body.get("prenom") : utilisateur.getPrenom());
        utilisateur.setEmail(body.get("email") != null && !body.get("email").trim().equals("") ? body.get("email") : utilisateur.getEmail());
        utilisateur.setTelephone(body.get("telephone") != null && !body.get("telephone").trim().equals("") ? body.get("telephone") : utilisateur.getTelephone());
        utilisateur.setPhoto(body.get("photo"));

        // update profil user
        profilUtilisateur.setUtilisateur(utilisateur);
        profilUtilisateur.setProfil(profil);
        profilUtilisateurRepository.save(profilUtilisateur);

        // update user
        utilisateurRepository.save(utilisateur);

        return utilisateur;
    }

    public Utilisateur updateStatutUser(Map<String, String> body, Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("user not found"));

        if (body.get("archive") != null) {
            utilisateur.setArchive(Boolean.parseBoolean(body.get("archive")));
        }
        if (body.get("statut") != null) {
            utilisateur.setStatut(Boolean.parseBoolean(body.get("statut")));
        }

        // update user status
        utilisateurRepository.save(utilisateur);

        return utilisateur;
    }

    public Utilisateur resetUserPassword(Map<String, String> body) {
        Utilisateur utilisateur = connectedUser();
        utilisateur.setPasswordChange(true);
        if (body.get("password").equals(body.get("confirmPassword"))) {
            utilisateur.setPassword(encoder.encode(body.get("password")));
        } else throw new BadRequestException("password not match");

        utilisateurRepository.save(utilisateur);
        return utilisateur;
    }
}
