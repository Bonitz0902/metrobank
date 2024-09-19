package com.metrobank.demo.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoundCustomerResponse {

    String customerNumber;
    String customerName;
    String customerMobile;
    String customerEmail;
    String address1;
    String address2;
    Saving saving;
    int transactionStatusCode;
    String transactionStatusDescription;


    @Data
    public static class Saving{
        String accountNumber;
        String accountType;
        String availableBalance ;

        public Saving(){
            this.accountNumber = "Test AccountNumber";
            this.accountType = "Test AccountType";
            this.availableBalance = "Test Available Balance";
        }
    }


}
