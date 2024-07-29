package com.codepar.bussinesclientsv1.domains.client.infrastructure.service;

import com.codepar.bussinesclientsv1.domains.client.domain.service.ClientService;
import com.codepar.bussinesclientsv1.domains.client.infrastructure.dao.entity.ClientEntity;
import com.codepar.bussinesclientsv1.domains.client.infrastructure.dao.repository.ClientRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepositoryJpa clientRepositoryJpa;


    @Transactional(readOnly = true)
    @Override
    public ClientEntity getClientById(Long id) {
        return clientRepositoryJpa.findById(id)
                .orElse(null);
    }
    @Transactional(readOnly = true)
    @Override
    public ClientEntity findByIdentification(String identification) {
        return clientRepositoryJpa.findByIdentification(identification)
                .orElse(null);
    }

    @Transactional
    @Override
    public Long saveClient(ClientEntity client) {
        return clientRepositoryJpa.save(client).getId();
    }

    @Transactional
    @Override
    public ClientEntity updateClient(ClientEntity client) {
        return clientRepositoryJpa.save(client);
    }

    @Transactional
    @Override
    public Boolean deleteClient(Long id) {
        return clientRepositoryJpa.deleteClientById(id) > 0;
    }
}
