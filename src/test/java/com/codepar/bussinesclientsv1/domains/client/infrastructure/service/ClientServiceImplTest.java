package com.codepar.bussinesclientsv1.domains.client.infrastructure.service;

import com.codepar.bussinesclientsv1.domains.client.infrastructure.dao.entity.ClientEntity;
import com.codepar.bussinesclientsv1.domains.client.infrastructure.dao.repository.ClientRepositoryJpa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientRepositoryJpa clientRepositoryJpa;

    private ClientEntity client;

    void buildInitClientEntity() {
        client = new ClientEntity();
        client.setIdentification("47657979");
        client.setName("Hola mundo");
        client.setPhoneNumber("12345");
        client.setStatus(false);
    }

    private ClientEntity getClientPostCreateDatabase() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1L);
        clientEntity.setIdentification("47657979");
        clientEntity.setName("Hola mundo");
        clientEntity.setPhoneNumber("12345");
        clientEntity.setStatus(true);
        return  clientEntity;
    }
    @Test
    @DisplayName("return id when save client is success")
    void returnIdWhenSaveClientSuccess() {
        buildInitClientEntity();
        when(clientRepositoryJpa.save(any())).thenReturn(getClientPostCreateDatabase());

        Long id = clientService.saveClient(client);

        assertEquals(1L,id);
    }
}