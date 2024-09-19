package com.metrobank.demo.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    String customerName;
    String customerMobile;
    String customerEmail;
    String address1;
    String address2;

    public enum AccountType{
        Y("Y", "Y"),
        S("S", "Saving"),
        C("C", "Checking");

        private final String code;
        private final String description;

        AccountType(String code, String description){
            this.code = code;
            this.description = description;
        }

        public String getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

    }
}
