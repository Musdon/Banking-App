package com.musdon.bankingapp.controller;

import com.musdon.bankingapp.payload.AccountCreationDto;
import com.musdon.bankingapp.payload.CustomResponse;
import com.musdon.bankingapp.payload.TransactionRequest;
import com.musdon.bankingapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/createAccount")
    public CustomResponse createAccount(@RequestBody AccountCreationDto accountCreationDto){
        return userService.createAccount(accountCreationDto);
    }

    @GetMapping("/allUsers")
    public CustomResponse fetchAllUsers(){
        return userService.fetchAllUsers();
    }

    @GetMapping("{id}")
    public CustomResponse fetchSingleUser(@PathVariable Long id){
        return userService.fetchSingleUser(id);
    }

    @PutMapping("{id}")
    public CustomResponse updateUser(@RequestBody AccountCreationDto accountCreationDto, @PathVariable Long id){
        return userService.updateUser(accountCreationDto, id);
    }

    @DeleteMapping("{id}")
    public CustomResponse deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

    @GetMapping("balanceEnquiry")
    public CustomResponse balanceEnquiry(@RequestParam String accountNumber){
        return userService.balanceEnquiry(accountNumber);
    }

    @PostMapping("deposit")
    public CustomResponse deposit(@RequestBody TransactionRequest transactionRequest){
        return userService.deposit(transactionRequest);
    }

    @PostMapping("withdraw")
    public CustomResponse withdraw(@RequestBody TransactionRequest transactionRequest){
        return userService.withdraw(transactionRequest);
    }
}
