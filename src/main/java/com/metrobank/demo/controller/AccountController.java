package com.metrobank.demo.controller;

import com.metrobank.demo.dto.AccountDTO;
import com.metrobank.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    @Autowired
    AccountService accountService;
    @PostMapping
    public ResponseEntity<Object> createAccount(@RequestBody AccountDTO accountDTO){

        return accountService.createAccount(accountDTO);
    }

    @GetMapping("/{customerNumber}")
    public ResponseEntity<Object> findByCustomerNumber(@PathVariable String customerNumber){
        return accountService.findCustomerByCustomerNumber(customerNumber);
    }
}
