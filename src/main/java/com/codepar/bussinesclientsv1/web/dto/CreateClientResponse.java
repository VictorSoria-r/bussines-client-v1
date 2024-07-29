package com.codepar.bussinesclientsv1.web.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CreateClientResponse implements Serializable {
    private Long idClient;
}
