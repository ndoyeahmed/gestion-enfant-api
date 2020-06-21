package com.gestion.stock.services.gestionenfants;

import com.gestion.stock.entities.admin.Utilisateur;
import com.gestion.stock.entities.gestionenfants.*;
import com.gestion.stock.repositories.admin.UtilisateurRepository;
import com.gestion.stock.repositories.gestionenfants.*;
import com.gestion.stock.utils.Utils;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Log
public class InscriptionService {
    private DossierRepository dossierRepository;
    private DocumentRepository documentRepository;
    private EnfantRepository enfantRepository;
    private SiteRepository siteRepository;
    private TypeDocumentRepository typeDocumentRepository;
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    public InscriptionService(DossierRepository dossierRepository, DocumentRepository documentRepository,
                              EnfantRepository enfantRepository, SiteRepository siteRepository,
                              TypeDocumentRepository typeDocumentRepository) {
        this.dossierRepository = dossierRepository;
        this.documentRepository = documentRepository;
        this.enfantRepository = enfantRepository;
        this.siteRepository = siteRepository;
        this.typeDocumentRepository = typeDocumentRepository;
    }

    @Autowired
    public void setUtilisateurRepository(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }
// begin type document operation

    public TypeDocument addTypeDocument(TypeDocument typeDocument) {
        try {
            typeDocument.setArchive(false);
            typeDocumentRepository.save(typeDocument);
            return typeDocument;
        } catch (Exception e) {
            log.severe(e.getLocalizedMessage());
            throw e;
        }
    }

    public TypeDocument updateTypeDocument(TypeDocument typeDocument) {
        try {
            typeDocumentRepository.save(typeDocument);
            return typeDocument;
        } catch (Exception e) {
            log.severe(e.getLocalizedMessage());
            throw e;
        }
    }

    public List<TypeDocument> findAllTypeDocument(Boolean archive) {
        return typeDocumentRepository.findAllByArchive(archive)
                .orElse(new ArrayList<>());
    }

    public TypeDocument findTypeDocumentById(Long id) {
        return typeDocumentRepository.findById(id).orElse(null);
    }

    // end type document operation

    // begin site operation

    public Site addSite(Site site) {
        try {
            site.setCode(Utils.createCode(site.getLibelle()));
            site.setArchive(false);
            siteRepository.save(site);
            return site;
        } catch (Exception e) {
            log.severe(e.getLocalizedMessage());
            throw e;
        }
    }

    public Site findSiteById(Long id) {
        return siteRepository.findById(id).orElse(null);
    }

    public Site findSiteByCode(String code) {
        return siteRepository.findByCode(code).orElse(null);
    }

    public List<Site> findAllSite(Boolean archive) {
        return siteRepository.findAllByArchive(archive).orElse(new ArrayList<>());
    }

    public List<Site> findAllSiteByUtilisateur(Utilisateur utilisateur) {
        return siteRepository.findAllByUtilisateurAndArchiveFalse(utilisateur)
                .orElse(new ArrayList<>());
    }

    // end site operation

    // begin document operation

    public Document addDocument(Document document) {
        try {
            document.setCode(Utils.createCode(document.getLibelle()));
            documentRepository.save(document);
            return document;
        } catch (Exception e) {
            log.severe(e.getLocalizedMessage());
            throw e;
        }
    }

    public Document findDocumentById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }

    public Document findDocumentByCode(String code) {
        return documentRepository.findByCode(code).orElse(null);
    }

    public List<Document> findAllDocument() {
        return documentRepository.findAll();
    }

    public List<Document> findAllDocumentByDossier(Dossier dossier) {
        return documentRepository.findAllByDossier(dossier).orElse(new ArrayList<>());
    }

    public List<Document> findAllDocumentByTypeDocument(TypeDocument typeDocument) {
        return documentRepository.findAllByTypeDocument(typeDocument).orElse(new ArrayList<>());
    }

    // end document operation

    // begin dossier operation

    public Dossier findDossierById(Long id) {
        return dossierRepository.findById(id).orElse(null);
    }

    public Dossier findDossierByCode(String code) {
        return dossierRepository.findByCode(code).orElse(null);
    }

    // end dossier operation

    // begin enfant operation

    public Enfant findEnfantById(Long id) {
        return enfantRepository.findById(id).orElse(null);
    }

    public Enfant findEnfantByMatricule(String matricule) {
        return enfantRepository.findByMatricule(matricule).orElse(null);
    }

    // end enfant operation

    // inscription enfant

    public Dossier inscription(Dossier dossier) {
        try {
            Enfant enfant = dossier.getEnfant();
            List<Document> documents = dossier.getDocuments();
            enfant.setMatricule(Utils.generateMatricule(enfant.getNom(), enfant.getPrenom(), enfant.getDateNaissance()));
            enfantRepository.save(enfant);
            dossier.setEnfant(enfant);
            dossier.setLibelle(Utils.generateMatricule
                    ("Dossier", enfant.getPrenom(),
                            Timestamp.valueOf(LocalDateTime.now())));
            dossier.setCode(Utils.createCode(dossier.getLibelle()));
            dossier.setDocuments(null);
            dossierRepository.save(dossier);
            documents.forEach(d -> {
                d.setDossier(dossier);
                documentRepository.save(d);
            });

            return dossier;
        } catch (Exception e) {
            log.severe(e.getLocalizedMessage());
            throw e;
        }
    }

    // end inscription

    public void addDefaultSite() {
        List<Site> sites = siteRepository.findAll();
        if (sites.isEmpty()) {
            Site site = new Site();
            site.setArchive(false);
            site.setLibelle("Liberté 6");
            site.setCode(Utils.createCode(site.getLibelle()));
            site.setUtilisateur(utilisateurRepository.findById(1L).orElse(null));

            Site site2 = new Site();
            site2.setArchive(false);
            site2.setLibelle("Sacré coeur 3");
            site2.setCode(Utils.createCode(site2.getLibelle()));
            site2.setUtilisateur(utilisateurRepository.findById(1L).orElse(null));

            siteRepository.save(site);
            siteRepository.save(site2);
        }
    }

    public void addDefaultTypeDocument() {
        List<TypeDocument> typeDocuments = typeDocumentRepository.findAll();
        if (typeDocuments.isEmpty()) {
            TypeDocument typeDocument = new TypeDocument();
            typeDocument.setArchive(false);
            typeDocument.setLibelle("Carte nationale d'identité");

            TypeDocument typeDocument2 = new TypeDocument();
            typeDocument2.setArchive(false);
            typeDocument2.setLibelle("Extrait de naissance");

            typeDocumentRepository.save(typeDocument);
            typeDocumentRepository.save(typeDocument2);
        }
    }
}
