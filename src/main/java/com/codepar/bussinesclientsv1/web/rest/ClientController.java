package com.codepar.bussinesclientsv1.web.rest;

import com.codepar.bussinesclientsv1.connector.account.model.AccountState;
import com.codepar.bussinesclientsv1.web.dto.ClientResponse;
import com.codepar.bussinesclientsv1.web.dto.CreateClientResponse;
import com.codepar.bussinesclientsv1.web.service.ClientApplicationService;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/client")
public class ClientController {

    private final ClientApplicationService clientApplicationService;

    @GetMapping("/{id}")
    public Single<ResponseEntity<ClientResponse>> getClientById(
            @PathVariable Long id) {
        return clientApplicationService.getClientById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build())
                .doOnError(System.out::println);
    }

    @PostMapping(value = "/create", consumes =  MediaType.APPLICATION_JSON_VALUE)
    public Single<ResponseEntity<CreateClientResponse>> createClient(
            @RequestBody ClientResponse request) {
        return clientApplicationService.saveClient(request)
                .map(clientResponse -> {
                    URI location = URI.create("api/client/"+ clientResponse.getIdClient());
                    return ResponseEntity.created(location)
                            .body(clientResponse);
                });
    }

    @PutMapping("/{id}")
    public Single<ResponseEntity<ClientResponse>> updateClient(
            @PathVariable Long id, @RequestBody ClientResponse clientResponse) {
        return clientApplicationService.updateInfoClient(id,clientResponse)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Completable deleteClient(
            @PathVariable Long id) {
        return clientApplicationService.deleteClient(id)
                .andThen(Completable.fromAction(
                        () -> ResponseEntity.noContent().build()));
    }


    @GetMapping(value = "account/state",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flowable<AccountState> getStateAccount(@RequestParam String identification,
                                                  @RequestParam String startDate,
                                                  @RequestParam String endDate) {
        return clientApplicationService.getStateAccount(identification,startDate,endDate)
                .doOnError(error -> {
                    System.out.println("en el error" + error);
                });
    }
}
