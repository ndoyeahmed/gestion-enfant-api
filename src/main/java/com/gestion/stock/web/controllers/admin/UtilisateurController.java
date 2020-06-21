package com.gestion.stock.web.controllers.admin;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.gestion.stock.entities.admin.Utilisateur;
import com.gestion.stock.security.TokenProvider;
import com.gestion.stock.services.admin.UtilisateurService;
import com.gestion.stock.web.exceptions.BadRequestException;
import com.gestion.stock.web.exceptions.EntityNotFoundException;
import com.gestion.stock.web.pojo.JWTToken;
import com.gestion.stock.web.pojo.LoginModel;
import com.gestion.stock.web.utils.Utilitaire;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@Log
@RequestMapping("/api")
public class UtilisateurController {

    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;
    private UtilisateurService utilisateurService;
    private Utilitaire utilitaire;

    @Autowired
    public UtilisateurController(AuthenticationManager authenticationManager, TokenProvider tokenProvider,
                                 UtilisateurService utilisateurService, Utilitaire utilitaire) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.utilisateurService = utilisateurService;
        this.utilitaire = utilitaire;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginModel loginModel, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginModel.getLogin(), loginModel.getPassword());

        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Map<String, String> jwt = tokenProvider.createToken(authentication);
            response.addHeader(TokenProvider.HEADER, "Bearer " + jwt);
            return ResponseEntity.ok(new JWTToken(jwt.get("id_token"), Long.valueOf(jwt.get("expires_at"))));
        } catch (Exception e) {
            log.severe(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("AuthenticationException", e.getLocalizedMessage()));
        }
    }

    @GetMapping("/connected-user")
    public MappingJacksonValue connectedUser() {
        Utilisateur utilisateur = utilisateurService.connectedUser();

        return utilitaire.getFilter(utilisateur, "passwordFilter", "password");
    }

    @PostMapping("/utilisateurs")
    public MappingJacksonValue saveUser(@RequestBody Map<String, String> body) {
        try {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(body.get("nom"));
            utilisateur.setPrenom(body.get("prenom"));
            utilisateur.setTelephone(body.get("telephone"));
            utilisateur.setEmail(body.get("email"));
            utilisateur.setAdresse(body.get("adresse"));
            utilisateur.setPhoto(body.get("photo"));

            Utilisateur utilisateurs = utilisateurService.addUtilisateur(utilisateur, body.get("profil"));

            return utilitaire.getFilter(utilisateurs, "passwordFilter", "password");

        } catch (EntityNotFoundException e) {
            log.severe(e.getMessage());
            throw e;
        }
    }

    @PostMapping("/reset-password")
    public MappingJacksonValue resetUserPassword(@RequestBody Map<String, String> body) {
        try {
            Utilisateur utilisateurs = utilisateurService.resetUserPassword(body);

            return utilitaire.getFilter(utilisateurs, "passwordFilter", "password");

        } catch (BadRequestException e) {
            log.severe(e.getMessage());
            throw e;
        }
    }

    @GetMapping("/utilisateurs/{archive}")
    public MappingJacksonValue allUsers(@PathVariable boolean archive) {
        List<Utilisateur> utilisateurs = utilisateurService.findAllByArchiveFalseAndStatutTrue(archive);

        return utilitaire.getFilter(utilisateurs, "passwordFilter", "password");
    }

    @GetMapping("/profils")
    public ResponseEntity<?> allProfil() {
        return ResponseEntity.ok(utilisateurService.findAllProfil());
    }

    @GetMapping("/utilisateurs-by-id/{id}")
    public MappingJacksonValue findByIdAndArchiveFalseAndStatutTrue(@PathVariable Long id) {
        try {
            return utilitaire.getFilter(utilisateurService.findUserByIdAndArchiveFalseAndStatutTrue(id), "passwordFilter", "password");
        } catch (EntityNotFoundException e) {
            log.severe(e.getMessage());
            throw e;
        }
    }

    @PutMapping("/utilisateurs/{id}")
    public MappingJacksonValue updateUser(@RequestBody Map<String, String> body, @PathVariable Long id) {
        try {
            return utilitaire.getFilter(utilisateurService.updateUtilisateur(body, id), "passwordFilter", "password");
        } catch (EntityNotFoundException e) {
            log.severe(e.getMessage());
            throw e;
        }
    }

    @PutMapping("/utilisateurs-status/{id}")
    public MappingJacksonValue updateUserStatus(@RequestBody Map<String, String> body, @PathVariable Long id) {
        try {
            return utilitaire.getFilter(utilisateurService.updateStatutUser(body, id), "passwordFilter", "password");
        } catch (EntityNotFoundException e) {
            log.severe(e.getMessage());
            throw e;
        }
    }
}
