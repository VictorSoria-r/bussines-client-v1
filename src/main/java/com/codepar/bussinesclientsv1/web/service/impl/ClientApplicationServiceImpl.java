package com.codepar.bussinesclientsv1.web.service.impl;

import com.codepar.bussinesclientsv1.connector.account.model.AccountState;
import com.codepar.bussinesclientsv1.connector.account.service.AccountConnectorService;
import com.codepar.bussinesclientsv1.core.exception.CoderPadException;
import com.codepar.bussinesclientsv1.domains.client.domain.service.ClientService;
import com.codepar.bussinesclientsv1.domains.client.infrastructure.dao.entity.ClientEntity;
import com.codepar.bussinesclientsv1.web.dto.ClientResponse;
import com.codepar.bussinesclientsv1.web.dto.CreateClientResponse;
import com.codepar.bussinesclientsv1.web.service.ClientApplicationService;
import io.reactivex.rxjava3.core.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientApplicationServiceImpl implements ClientApplicationService {

    private final ClientService clientService;
    private final AccountConnectorService accountConnectorService;
    @Override
    public Maybe<ClientResponse> getClientById(Long id) {
        return Maybe.fromCallable(() ->
                        clientService.getClientById(id))
                .switchIfEmpty(Maybe.empty())
                .map(clientEntity -> ClientResponse.builder()
                        .name(clientEntity.getName())
                        .address(clientEntity.getAddress())
                        .identification(clientEntity.getIdentification())
                        .phoneNumber(clientEntity.getPhoneNumber())
                        .password(clientEntity.getPassword())
                        .status(clientEntity.isStatus() ? "ACTIVE" : "INACTIVE")
                        .build());
    }

    @Override
    public Single<CreateClientResponse> saveClient(ClientResponse client) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName(client.getName());
        clientEntity.setPassword(client.getPassword());
        clientEntity.setAddress(client.getAddress());
        clientEntity.setIdentification(client.getIdentification());
        clientEntity.setPhoneNumber(client.getPhoneNumber());
        clientEntity.setStatus(Boolean.TRUE);
        return Maybe.fromCallable(() ->
                clientService.findByIdentification(client.getIdentification()))
                .flatMap(entity -> Maybe.error(CoderPadException.builder()
                                .shortMessage("Cliente con numero de identificación ya registrado")
                                .status(HttpStatus.CONFLICT)
                        .build()))
                .switchIfEmpty(Maybe.fromCallable(()->
                        clientService.saveClient(clientEntity)))
                .map(id-> CreateClientResponse.builder()
                        .idClient((Long) id)
                        .build())
                .toSingle()
                .doOnError(error-> Maybe.error(error));
    }

    @Override
    public Single<ClientResponse> updateInfoClient(Long id,ClientResponse client) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(id);
        clientEntity.setName(client.getName());
        clientEntity.setPassword(client.getPassword());
        clientEntity.setAddress(client.getAddress());
        clientEntity.setIdentification(client.getIdentification());
        clientEntity.setPhoneNumber(client.getPhoneNumber());
        clientEntity.setStatus((client.getStatus().equals("ACTIVE")));
        return Maybe.fromCallable(() ->
                clientService.getClientById(id))
                .switchIfEmpty(Single.error(CoderPadException.builder()
                        .shortMessage("El usuario no se encuentra registrado")
                        .status(HttpStatus.CONFLICT)
                        .build()))
                .map(entity -> clientService.updateClient(clientEntity))
                .map(entity -> ClientResponse.builder()
                        .name(entity.getName())
                        .address(entity.getAddress())
                        .phoneNumber(entity.getPhoneNumber())
                        .identification(entity.getIdentification())
                        .password(entity.getPassword())
                        .status(clientEntity.isStatus() ? "ACTIVE" : "INACTIVE")
                        .build());
    }

    @Override
    public Completable deleteClient(Long id) {
        return Maybe.fromCallable(() ->
                        clientService.getClientById(id))
                .switchIfEmpty(Single.error(CoderPadException.builder()
                        .shortMessage("El usuario no se encuentra registrado")
                        .status(HttpStatus.CONFLICT)
                        .build()))
                .flatMapCompletable(entity -> Completable.fromCallable(
                        () -> clientService.deleteClient(id)))
                .doOnError(Completable::error);
    }

    @Override
    public Flowable<AccountState> getStateAccount(String identification, String startDate, String endDate) {
        return accountConnectorService.getStateAccountByIdentificationClient(identification,
                startDate,endDate);
    }
}
