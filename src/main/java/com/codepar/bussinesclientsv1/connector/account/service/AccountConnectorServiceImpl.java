package com.codepar.bussinesclientsv1.connector.account.service;

import com.codepar.bussinesclientsv1.connector.config.HttpClient;
import com.codepar.bussinesclientsv1.connector.account.apiconnect.ApiAccountClient;
import com.codepar.bussinesclientsv1.connector.account.model.AccountState;
import com.codepar.bussinesclientsv1.connector.config.HttpStreamingTransformer;
import com.codepar.bussinesclientsv1.core.exception.ApiClientException;
import com.codepar.bussinesclientsv1.core.exception.CoderPadException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.rxjava3.circuitbreaker.operator.CircuitBreakerOperator;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class AccountConnectorServiceImpl implements AccountConnectorService {

    private final HttpClient httpClient;
    private ApiAccountClient apiAccountClient;

    private final CircuitBreaker circuitBreaker;

    @Override
    public Flowable<AccountState> getStateAccountByIdentificationClient(String identification,
                                                                          String startDate,
                                                                          String endDate) {
        apiAccountClient = httpClient.createClient(ApiAccountClient.class);
        return apiAccountClient.getStateAccount(
                identification,startDate,endDate)
                .compose(CircuitBreakerOperator.of(circuitBreaker))
                .compose(HttpStreamingTransformer.of(AccountState.class))
                .toFlowable(BackpressureStrategy.BUFFER)
                .onErrorResumeNext(error -> {
                    if (error instanceof ApiClientException ioException) {
                        return Flowable.error(CoderPadException.builder()
                                 .status(HttpStatus.CONFLICT)
                                 .shortMessage(ioException.getMessage())
                                 .build());
                    } else {
                        return Flowable.error(error);
                    }
                })
                .doOnNext(accountState -> System.out.println("received: " + accountState));

    }
}
