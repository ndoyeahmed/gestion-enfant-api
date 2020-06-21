package com.gestion.stock.repositories.sales;

import com.gestion.stock.entities.sales.TypeClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeClientRepository extends JpaRepository<TypeClient, Long> {
}
