package com.codepar.bussinesclientsv1.connector.account.service;

import com.codepar.bussinesclientsv1.connector.account.model.AccountState;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

import java.time.LocalDate;

public interface AccountConnectorService {

    Flowable<AccountState> getStateAccountByIdentificationClient(String identification,
                                                                   String startDate, String endDate);
}
