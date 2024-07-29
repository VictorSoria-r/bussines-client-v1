package com.codepar.bussinesclientsv1.web.dto;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;

@Data
@Builder
public class ClientResponse implements Serializable {

    private String name;
    private String address;
    private String identification;
    private String password;
    private String phoneNumber;
    private String status;
}
