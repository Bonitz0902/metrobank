package com.metrobank.demo.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAccountResponse {
    private String customerAccountId;
    private int transactionStatusCode;
    private String transactionStatusDescription;
}
