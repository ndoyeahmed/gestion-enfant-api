package com.gestion.stock.repositories.sales;

import com.gestion.stock.entities.sales.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
