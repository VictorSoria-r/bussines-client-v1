package com.codepar.bussinesclientsv1.connector.config;

import com.codepar.bussinesclientsv1.connector.config.ErrorInterceptor;
import com.codepar.bussinesclientsv1.connector.config.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.LocalDate;

@Service
public class HttpClient {
    public <T> T createClient(Class<T> client) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new ErrorInterceptor());
        Gson gson = new GsonBuilder()
                .setLenient()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8091")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit.create(client);
    }
}
