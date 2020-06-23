package com.gestion.stock.web.controllers.gestionenfants;

import com.gestion.stock.entities.admin.Utilisateur;
import com.gestion.stock.entities.gestionenfants.*;
import com.gestion.stock.services.admin.UtilisateurService;
import com.gestion.stock.services.gestionenfants.InscriptionService;
import com.gestion.stock.web.exceptions.BadRequestException;
import com.gestion.stock.web.exceptions.EntityNotFoundException;
import com.gestion.stock.web.utils.Utilitaire;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gestion/enfants/")
@Log
public class InscriptionController {

    private final String ENTITY_CANNOT_BE_NULL = "entity cannot be null";
    private final String CODE_REQUIRED = "code required";
    private final String LIBELLE_REQUIRED = "libelle required";
    private final String INTERNAL_SERVER_ERROR = "Erreur 500";

    private InscriptionService inscriptionService;
    private UtilisateurService utilisateurService;
    private Utilitaire utilitaire;

    @Autowired
    public InscriptionController(InscriptionService inscriptionService,
                                 UtilisateurService utilisateurService,
                                 Utilitaire utilitaire) {
        this.inscriptionService = inscriptionService;
        this.utilisateurService = utilisateurService;
        this.utilitaire = utilitaire;
    }

    // type document endpoint

    @PostMapping("type-document")
    public ResponseEntity<?> addTypeDocument(@RequestBody TypeDocument typeDocument) {
        try {
            if (typeDocument == null) throw new BadRequestException(ENTITY_CANNOT_BE_NULL);
            if (typeDocument.getLibelle() == null || typeDocument.getLibelle().trim().equals(""))
                throw new BadRequestException(LIBELLE_REQUIRED);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(inscriptionService.addTypeDocument(typeDocument));
        } catch (Exception e) {
            log.severe(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("type-document/{id}")
    public ResponseEntity<?> typeDocumentById(@PathVariable Long id) {
        if (id == null || id == 0) throw new BadRequestException("incorrect id");

        TypeDocument typeDocument = inscriptionService.findTypeDocumentById(id);
        if (typeDocument == null) throw new EntityNotFoundException("typeDocument not found");

        return ResponseEntity.ok(typeDocument);
    }

    @GetMapping("type-document/archive/{archive}")
    public List<TypeDocument> typeDocumentList(@PathVariable Boolean archive) {
        return inscriptionService.findAllTypeDocument(archive);
    }

    @PutMapping("type-document/archive/{id}")
    public ResponseEntity<?> archiveOrRestoreTypeDocument(@PathVariable Long id, @RequestBody Map<String, String> body) {
        TypeDocument typeDocument = inscriptionService.findTypeDocumentById(id);
        if (typeDocument == null) throw new EntityNotFoundException("typeDocument not found");

        typeDocument.setArchive(Boolean.parseBoolean(body.get("archive")));

        return ResponseEntity.ok(inscriptionService.updateTypeDocument(typeDocument));
    }

    @PutMapping("type-document/{id}")
    public ResponseEntity<?> updateTypeDocument(@RequestBody TypeDocument typeDocument, @PathVariable Long id) {
        if (id == null || id == 0) throw new BadRequestException("incorrect id");

        TypeDocument typeDocumentOrigin = inscriptionService.findTypeDocumentById(id);
        if (typeDocumentOrigin == null) throw new EntityNotFoundException("typeDocument not found");

        if (typeDocument == null) throw new BadRequestException(ENTITY_CANNOT_BE_NULL);

        typeDocumentOrigin.setLibelle(typeDocument.getLibelle());

        return ResponseEntity.ok(inscriptionService.updateTypeDocument(typeDocumentOrigin));
    }

    // end type document endpoint

    // site endpoint

    @PutMapping("sites/archive/{id}")
    public MappingJacksonValue archiveOrRestoreSite(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Site site = inscriptionService.findSiteById(id);
        if (site == null) throw new EntityNotFoundException("site not found");

        site.setArchive(Boolean.parseBoolean(body.get("archive")));

        return utilitaire.getFilter(inscriptionService.updateSite(site), "passwordFilter", "password");
    }

    @PostMapping("sites")
    public MappingJacksonValue addSites(@RequestBody Site site) {

        if (site == null) throw new BadRequestException(ENTITY_CANNOT_BE_NULL);
        if (site.getLibelle() == null || site.getLibelle().trim().equals(""))
            throw new BadRequestException(LIBELLE_REQUIRED);
        if (site.getUtilisateur() == null || site.getUtilisateur().getId() == null)
            throw new BadRequestException("utilisateur required");

        return utilitaire.getFilter(inscriptionService.addSite(site), "passwordFilter", "password");
    }

    @GetMapping("sites/archive/{archive}")
    public List<Site> siteList(@PathVariable Boolean archive) {
        return inscriptionService.findAllSite(archive);
    }

    @GetMapping("sites/id/{id}")
    public ResponseEntity<?> siteById(@PathVariable Long id) {
        if (id == null || id == 0) throw new BadRequestException("incorrect id");

        Site site = inscriptionService.findSiteById(id);
        if (site == null) throw new EntityNotFoundException("site not found");

        return ResponseEntity.ok(site);
    }

    @GetMapping("sites/code/{code}")
    public ResponseEntity<?> siteByCode(@PathVariable String code) {
        if (code == null || code.trim().equals("")) throw new BadRequestException("incorrect code");

        Site site = inscriptionService.findSiteByCode(code);
        if (site == null) throw new EntityNotFoundException("site not found");

        return ResponseEntity.ok(site);
    }

    @GetMapping("sites/utilisateur/{userId}")
    public ResponseEntity<?> siteByUtilisateur(@PathVariable Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("incorrect userId");

        Utilisateur utilisateur = utilisateurService.findUserByIdAndArchiveFalseAndStatutTrue(userId);
        if (utilisateur == null) throw new EntityNotFoundException("user not found");

        return ResponseEntity.ok(inscriptionService.findAllSiteByUtilisateur(utilisateur, false));
    }

    @GetMapping("sites/connected-user/archive/{archive}")
    public MappingJacksonValue siteByConnectedUtilisateur(@PathVariable Boolean archive) {
        String[] admin = {"ADMIN"};
        if (utilisateurService.hasProfile(admin)) {
            List<Site> sites = inscriptionService.findAllSite(archive);
            return utilitaire.getFilter(sites, "passwordFilter", "password");
        } else {
            List<Site> sites = inscriptionService.findAllSiteByUtilisateur(utilisateurService.connectedUser(), archive);
            return utilitaire.getFilter(sites, "passwordFilter", "password");
        }

    }

    // end site endpoint

    // document endpoint

    @GetMapping("documents")
    public List<Document> documentList() {
        return inscriptionService.findAllDocument();
    }

    @GetMapping("documents/dossier/{dossierId}")
    public List<Document> documentListByDossier(@PathVariable Long dossierId) {
        if (dossierId == null || dossierId == 0) throw new BadRequestException("incorrect dossierId");

        Dossier dossier = inscriptionService.findDossierById(dossierId);
        if (dossier == null) throw new EntityNotFoundException("dossier not found");

        return inscriptionService.findAllDocumentByDossier(dossier);
    }

    @GetMapping("documents/type-document/{typeDocId}")
    public List<Document> documentListByTypeDocument(@PathVariable Long typeDocId) {
        if (typeDocId == null || typeDocId == 0) throw new BadRequestException("incorrect typeDocId");

        TypeDocument typeDocument = inscriptionService.findTypeDocumentById(typeDocId);
        if (typeDocument == null) throw new EntityNotFoundException("typeDocument not found");

        return inscriptionService.findAllDocumentByTypeDocument(typeDocument);
    }

    @GetMapping("documents/id/{id}")
    public ResponseEntity<?> documentById(@PathVariable Long id) {
        if (id == null || id == 0) throw new BadRequestException("incorrect id");

        Document document = inscriptionService.findDocumentById(id);
        if (document == null) throw new EntityNotFoundException("document not found");

        return ResponseEntity.ok(document);
    }

    @GetMapping("documents/code/{code}")
    public ResponseEntity<?> documentByCode(@PathVariable String code) {
        if (code == null || code.trim().equals("")) throw new BadRequestException("incorrect code");

        Document document = inscriptionService.findDocumentByCode(code);
        if (document == null) throw new EntityNotFoundException("document not found");

        return ResponseEntity.ok(document);
    }

    // end document endpoint

    // dossier endpoint

    @CrossOrigin
    @GetMapping("dossiers/archive-utilisateur/{archive}")
    public ResponseEntity<?> dossierListByArchiveAndUtilisateur(@PathVariable Boolean archive) {
        try {
            String[] admin = {"ADMIN"};
            if (utilisateurService.hasProfile(admin)) {
                return ResponseEntity.ok(utilitaire.getFilter(inscriptionService.findAllDossierByArchive(archive), "passwordFilter", "password"));
            } else {
                return ResponseEntity.ok(utilitaire.getFilter(inscriptionService
                                .findAllDossierByArchiveAndSiteUtilisateur(archive, utilisateurService.connectedUser())
                        , "passwordFilter", "password"));
            }
        } catch (Exception e) {
            log.severe(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }

    @GetMapping("dossiers/id/{id}")
    public ResponseEntity<?> dossierById(@PathVariable Long id) {
        if (id == null || id == 0) throw new BadRequestException("incorrect id");

        Dossier dossier = inscriptionService.findDossierById(id);
        if (dossier == null) throw new EntityNotFoundException("dossier not found");

        return ResponseEntity.ok(dossier);
    }

    @GetMapping("dossiers/code/{code}")
    public ResponseEntity<?> dossiersByCode(@PathVariable String code) {
        if (code == null || code.trim().equals("")) throw new BadRequestException("incorrect code");

        Dossier dossier = inscriptionService.findDossierByCode(code);
        if (dossier == null) throw new EntityNotFoundException("dossier not found");

        return ResponseEntity.ok(dossier);
    }

    // end dossier endpoint

    // enfant endpoint

    @GetMapping("enfants/id/{id}")
    public ResponseEntity<?> enfantsById(@PathVariable Long id) {
        if (id == null || id == 0) throw new BadRequestException("incorrect id");

        Enfant enfant = inscriptionService.findEnfantById(id);
        if (enfant == null) throw new EntityNotFoundException("enfant not found");

        return ResponseEntity.ok(enfant);
    }

    @GetMapping("enfants/matricule/{matricule}")
    public ResponseEntity<?> enfantsByCode(@PathVariable String matricule) {
        if (matricule == null || matricule.trim().equals(""))
            throw new BadRequestException("incorrect matricule");

        Enfant enfant = inscriptionService.findEnfantByMatricule(matricule);
        if (enfant == null) throw new EntityNotFoundException("enfant not found");

        return ResponseEntity.ok(enfant);
    }

    // end enfant endpoint

    // inscription endpoint
    @PostMapping("inscription")
    public ResponseEntity<?> inscription(@RequestBody Dossier dossier) {
        try {
            if (dossier == null) throw new BadRequestException(ENTITY_CANNOT_BE_NULL);
            if (dossier.getEnfant() == null) throw new BadRequestException(ENTITY_CANNOT_BE_NULL);
            if (dossier.getEnfant().getPrenom() == null || dossier.getEnfant().getPrenom().trim().equals(""))
                throw new BadRequestException(ENTITY_CANNOT_BE_NULL);
            if (dossier.getDocuments() == null || dossier.getDocuments().isEmpty())
                throw new BadRequestException("document required");

            return ResponseEntity.status(HttpStatus.CREATED).body(utilitaire.getFilter(inscriptionService.inscription(dossier)
                    , "passwordFilter", "password"));
        } catch (Exception e) {
            log.severe(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }
    // end inscription endpoint


}
