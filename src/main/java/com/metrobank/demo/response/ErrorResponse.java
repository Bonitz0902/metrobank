package com.metrobank.demo.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private int transactionStatusCode;
    private String transactionStatusDescription;
}
