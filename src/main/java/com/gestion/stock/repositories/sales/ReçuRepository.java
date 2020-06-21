package com.gestion.stock.repositories.sales;

import com.gestion.stock.entities.sales.Reçu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReçuRepository extends JpaRepository<Reçu, Long> {
}
