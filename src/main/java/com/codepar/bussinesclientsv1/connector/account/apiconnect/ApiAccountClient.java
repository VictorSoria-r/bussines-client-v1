package com.codepar.bussinesclientsv1.connector.account.apiconnect;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import com.codepar.bussinesclientsv1.connector.account.model.AccountState;
import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

import java.util.List;

@CircuitBreaker(name = "bs-clients-v1")
public interface ApiAccountClient {

    @Streaming
    @GET("/api/account/state")
    Flowable<List<AccountState>> getStateAccount(@Query("identification") String identification,
                                                 @Query("startDate") String startDate,
                                                 @Query("endDate") String endDate);
}
