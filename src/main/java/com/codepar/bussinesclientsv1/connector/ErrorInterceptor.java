package com.codepar.bussinesclientsv1.connector;

import com.codepar.bussinesclientsv1.core.advice.ErrorResponse;
import com.codepar.bussinesclientsv1.core.exception.CoderPadException;
import com.google.gson.Gson;
import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class ErrorInterceptor implements Interceptor {

    private final Gson gson = new Gson();
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        if (!response.isSuccessful()) {
            if (response.code() == 409) {
                ResponseBody responseBody = response.body();
                if (responseBody!=null) {
                    ErrorResponse errorResponse = gson.fromJson(responseBody.string(), ErrorResponse.class);
                    throw new IOException(errorResponse.getMessage());
                }
            }
            throw new IOException(response.message());
        }
        return response;
    }
}
