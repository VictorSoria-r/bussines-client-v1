package com.codepar.bussinesclientsv1.connector.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.rxjava3.core.*;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import org.reactivestreams.Publisher;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import static io.reactivex.rxjava3.core.BackpressureStrategy.*;

@FunctionalInterface
public interface HttpStreamingTransformer<T> extends ObservableTransformer<ResponseBody,T>, FlowableTransformer<ResponseBody,T> {
    ObjectMapper mapper = new Jackson2ObjectMapperBuilder().createXmlMapper(false).build();
    
    Class<T> getTarget();

    static<E> HttpStreamingTransformer<E> of (Class<E> tdClass) {
        return () -> tdClass;
    }

    default Publisher<T> apply(Flowable<ResponseBody> source) {
        return source.flatMap((body) -> {
            return Flowable.create((emitter) -> {
                this.fetchStreamingResponse(body,emitter);
            }, BUFFER);
            });
    }

    default ObservableSource<T> apply(Observable<ResponseBody> source) {
        return source.flatMap((body) -> {
            return Observable.create((emitter) -> {
                this.fetchStreamingResponse(body,emitter);
            });
        });
    }


    private void fetchStreamingResponse(ResponseBody body, Emitter<T> emmiter) {
        try {
            BufferedSource is = body.source();
            try {
                while (true) {
                    if (is.exhausted()) {
                        emmiter.onComplete();
                        break;
                    }
                    String data = is.readUtf8Line();
                    emmiter.onNext(mapper.readValue(data,this.getTarget()));
                }
            } catch (Exception e) {
                if (is != null) {
                    try {
                        is.close();
                    } catch (Exception exception) {
                        e.addSuppressed(exception);
                    }
                }
                throw e;
            }
            if (is != null) {
                is.close();
            }

        } catch (Exception e) {
            emmiter.onError(e);
        }
    }
    
}
