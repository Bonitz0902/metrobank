package com.metrobank.demo.service;

import com.metrobank.demo.dto.AccountDTO;
import com.metrobank.demo.entity.Account;
import com.metrobank.demo.repository.AccountRepository;
import com.metrobank.demo.response.CreateAccountResponse;
import com.metrobank.demo.response.ErrorResponse;
import com.metrobank.demo.response.FoundCustomerResponse;
import com.metrobank.demo.response.FoundCustomerResponse.Saving;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public ResponseEntity<Object> createAccount(AccountDTO accountDTO){
        Account accountToSave = buildAccountFromDto(accountDTO);
        Account account = accountRepository.save(accountToSave);

        if(checkEmptyProperties(accountDTO).isEmpty()){
            CreateAccountResponse createAccountResponse = CreateAccountResponse.builder()
                    .customerAccountId(account.getId())
                    .transactionStatusCode(HttpStatus.CREATED.value())
                    .transactionStatusDescription("Customer account created")
                    .build();
            return new ResponseEntity<>(createAccountResponse, HttpStatus.CREATED);
        }

        String errorDescription = buildErrorDescription(accountDTO);

        ErrorResponse errorResponse = buildErrorResponse(errorDescription,HttpStatus.BAD_REQUEST.value() );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> findCustomerByCustomerNumber(String customerNumber) {
        Account account = accountRepository.findFirstById(customerNumber);

        if(account != null){
            FoundCustomerResponse foundCustomerResponse = FoundCustomerResponse.builder()
                    .customerNumber(account.getId())
                    .customerName(account.getCustomerName())
                    .customerMobile(account.getCustomerMobile())
                    .customerEmail(account.getCustomerEmail())
                    .address1(account.getAddress1())
                    .address2(account.getAddress2())
                    .saving(new Saving())
                    .transactionStatusCode(HttpStatus.FOUND.value())
                    .transactionStatusDescription("Customer Account Found")
                    .build();

            return new ResponseEntity<>(foundCustomerResponse, HttpStatus.FOUND);
        }

        ErrorResponse errorResponse = buildErrorResponse("Customer not found", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }



    private String buildErrorDescription(AccountDTO accountDTO) {
        List<String> emptyProperties = checkEmptyProperties(accountDTO);

        if(emptyProperties.size() == 1){
            return String.join("", emptyProperties) + " is required field";
        }else if(emptyProperties.size() > 1){
            return String.join(", ", emptyProperties) + " are required fields";
        } else return null;

    }

    private List<String> checkEmptyProperties(AccountDTO accountDTO) {
        List<String> emptyProperties = new ArrayList<>();
        for(Field field : AccountDTO.class.getDeclaredFields()){
            field.setAccessible(true);

            try{
                Object value = field.get(accountDTO);
                if(value == null || value == ""){
                    emptyProperties.add(field.getName());
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }

        return emptyProperties;
    }

    private Account buildAccountFromDto(AccountDTO accountDTO) {
        return Account.builder()
                .customerName(accountDTO.getCustomerName())
                .customerMobile(accountDTO.getCustomerMobile())
                .customerEmail(accountDTO.getCustomerEmail())
                .address1(accountDTO.getAddress1())
                .address2(accountDTO.getAddress2())
                .build();
    }



    private static ErrorResponse buildErrorResponse(String errorDescription, int statusCode) {
        ErrorResponse errorResponse =  ErrorResponse.builder()
                .transactionStatusCode(statusCode)
                .transactionStatusDescription(errorDescription)
                .build();
        return errorResponse;
    }
}
