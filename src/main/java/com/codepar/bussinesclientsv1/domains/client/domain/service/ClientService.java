package com.codepar.bussinesclientsv1.domains.client.domain.service;

import com.codepar.bussinesclientsv1.domains.client.infrastructure.dao.entity.ClientEntity;
import java.util.Optional;

public interface ClientService {

    ClientEntity getClientById(Long id);
    ClientEntity findByIdentification(String identification);
    Long saveClient(ClientEntity client);
    ClientEntity updateClient(ClientEntity client);
    Boolean deleteClient(Long id);

}
