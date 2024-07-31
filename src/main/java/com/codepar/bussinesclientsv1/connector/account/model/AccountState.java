package com.codepar.bussinesclientsv1.connector.account.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class AccountState implements Serializable {
    private String clientName;
    private String accountNumber;
    private String typeAccount;
    private List<MovementState> movementStateList;


    @Data
    @Builder
    public static class MovementState {
        @JsonFormat()
        private LocalDate transactionDate;
        private BigDecimal initialBalance;
        private BigDecimal movementAmount;
        private BigDecimal availableBalance;
    }
}
