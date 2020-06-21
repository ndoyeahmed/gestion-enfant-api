package com.gestion.stock.repositories.gestionenfants;

import com.gestion.stock.entities.gestionenfants.Document;
import com.gestion.stock.entities.gestionenfants.Dossier;
import com.gestion.stock.entities.gestionenfants.TypeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    Optional<Document> findByCode(String code);

    Optional<List<Document>> findAllByDossier(Dossier dossier);

    Optional<List<Document>> findAllByTypeDocument(TypeDocument typeDocument);
}
