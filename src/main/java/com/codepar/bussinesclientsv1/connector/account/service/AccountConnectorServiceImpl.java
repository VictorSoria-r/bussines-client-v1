package com.codepar.bussinesclientsv1.connector.account.service;

import com.codepar.bussinesclientsv1.connector.HttpClient;
import com.codepar.bussinesclientsv1.connector.account.apiconnect.ApiAccountClient;
import com.codepar.bussinesclientsv1.connector.account.model.AccountState;
import com.codepar.bussinesclientsv1.core.exception.CoderPadException;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class AccountConnectorServiceImpl implements AccountConnectorService {

    private final HttpClient httpClient;
    private ApiAccountClient apiAccountClient;

    @Override
    public Flowable<AccountState> getStateAccountByIdentificationClient(String identification,
                                                                          String startDate,
                                                                          String endDate) {
        apiAccountClient = httpClient.createClient(ApiAccountClient.class);
        return apiAccountClient.getStateAccount(
                identification,startDate,endDate)
                .doOnError(error -> {
                    System.out.println("el error " + error);
                })
                .flatMapIterable(list -> list)
                .doOnNext(accountState -> System.out.println("received: " + accountState));

    }
}
