package com.musdon.bankingapp.util;

import com.musdon.bankingapp.entity.User;
import com.musdon.bankingapp.payload.ErrorResponse;
import com.musdon.bankingapp.payload.UserDto;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class AccountUtils {

    public String generateAccountNumber(){
        StringBuilder accountNumber = new StringBuilder();
        Random random = new Random();
        int count = 0;
        while(count < 10){
            accountNumber.append(random.nextInt(10));
            count++;
        }
        return accountNumber.toString();
    }

    public UserDto mapUserToUserDto(User user){
        return UserDto.builder()
                .accountBalance(user.getAccountBalance())
                .email(user.getEmail())
                .accountNumber(user.getAccountNumber())
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .otherName(user.getOtherName())
                .build();
    }


}
