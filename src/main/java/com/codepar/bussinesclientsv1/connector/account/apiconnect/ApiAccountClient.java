package com.codepar.bussinesclientsv1.connector.account.apiconnect;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

@CircuitBreaker(name = "bs-clients-v1")
public interface ApiAccountClient {

    @Headers({
            "Accept: application/stream+json"
    })
    @Streaming
    @GET(value = "/api/account/state")
    Observable<ResponseBody> getStateAccount(@Query("identificationClient") String identification,
                                             @Query("startDate") String startDate,
                                             @Query("endDate") String endDate);
}
