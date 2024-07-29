package com.codepar.bussinesclientsv1.web.service;

import com.codepar.bussinesclientsv1.connector.account.model.AccountState;
import com.codepar.bussinesclientsv1.web.dto.ClientResponse;
import com.codepar.bussinesclientsv1.web.dto.CreateClientResponse;
import io.reactivex.rxjava3.core.*;

public interface ClientApplicationService {

    Maybe<ClientResponse> getClientById(Long id);

    Single<CreateClientResponse> saveClient(ClientResponse client);

    Single<ClientResponse> updateInfoClient(Long id, ClientResponse client);

    Completable deleteClient(Long id);

    Flowable<AccountState> getStateAccount(String identification,
                                             String startDate,
                                             String endDate);
}
