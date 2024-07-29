package com.codepar.bussinesclientsv1.domains.client.infrastructure.dao.repository;

import com.codepar.bussinesclientsv1.domains.client.infrastructure.dao.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepositoryJpa extends JpaRepository<ClientEntity,Long> {
    Optional<ClientEntity> findByIdentification(String identification);

    @Modifying
    @Query(value ="update CLIENT set STATUS = false " +
            "where CLIENT_ID = :id", nativeQuery = true)
    int deleteClientById(@Param("id") Long id);
}
