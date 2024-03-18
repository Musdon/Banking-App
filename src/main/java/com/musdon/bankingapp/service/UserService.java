package com.musdon.bankingapp.service;

import com.musdon.bankingapp.entity.User;
import com.musdon.bankingapp.payload.AccountCreationDto;
import com.musdon.bankingapp.payload.CustomResponse;
import com.musdon.bankingapp.payload.TransactionRequest;
import com.musdon.bankingapp.payload.UserDto;
import com.musdon.bankingapp.repository.UserRepository;
import com.musdon.bankingapp.util.AccountUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AccountUtils accountUtils;

    public CustomResponse createAccount(AccountCreationDto accountCreationDto){
        log.info("User details: {}", accountCreationDto);
        Optional<User> existingUser = userRepository.findByEmail(accountCreationDto.getEmail());
        log.info("does user exist? {}", existingUser.isPresent());
        if (existingUser.isPresent()){
            return CustomResponse.builder()
                    .error(true)
                    .message("User with this email exists")
                    .build();
        }
        User user = User.builder()
                .firstName(accountCreationDto.getFirstName())
                .lastName(accountCreationDto.getLastName())
                .otherName(accountCreationDto.getOtherName())
                .accountNumber(accountUtils.generateAccountNumber())
                .email(accountCreationDto.getEmail())
                .accountBalance(BigDecimal.ZERO)
                .build();
        userRepository.save(user);

        return CustomResponse.builder()
                .error(false)
                .message("SUCCESS")
                .userDetails(accountUtils.mapUserToUserDto(user))
                .build();
    }

    public CustomResponse fetchAllUsers(){
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtos = userList.stream().map(accountUtils::mapUserToUserDto).toList();
        return CustomResponse.builder()
                .error(false)
                .message("SUCCESS")
                .users(userDtos)
                .build();
    }

    public CustomResponse fetchSingleUser(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            return CustomResponse.builder()
                    .error(true)
                    .message("User with id " + id + " not found")
                    .build();
        }
        User foundUser = user.get();
        return CustomResponse.builder()
                .error(false)
                .message("SUCCESS")
                .userDetails(accountUtils.mapUserToUserDto(foundUser))
                .build();
    }

    public CustomResponse updateUser(AccountCreationDto accountCreationDto, Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            return CustomResponse.builder()
                    .error(true)
                    .message("User with id " + id + " not found")
                    .build();
        }
        User userToUpdate = user.get();
        if (accountCreationDto.getFirstName() != null){
            userToUpdate.setFirstName(accountCreationDto.getFirstName());
        }
        if (accountCreationDto.getLastName() != null){
            userToUpdate.setLastName(accountCreationDto.getLastName());
        }
        if (accountCreationDto.getOtherName() != null){
            userToUpdate.setOtherName(accountCreationDto.getOtherName());
        }
        userToUpdate = userRepository.save(userToUpdate);
        return CustomResponse.builder()
                .error(false)
                .message("SUCCESS")
                .userDetails(accountUtils.mapUserToUserDto(userToUpdate))
                .build();
    }

    public CustomResponse deleteUser(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            return CustomResponse.builder()
                    .error(true)
                    .message("User with id " + id + " not found")
                    .build();
        }
        userRepository.delete(user.get());
        return CustomResponse.builder()
                .error(false)
                .message("User deleted!")
                .build();
    }

    public CustomResponse balanceEnquiry(String accountNumber){
        Optional<User> user = userRepository.findByAccountNumber(accountNumber);
        if (user.isEmpty()){
            return CustomResponse.builder()
                    .error(true)
                    .message("User not found")
                    .build();
        }
        return CustomResponse.builder()
                .error(false)
                .message("Success")
                .accountBalance(user.get().getAccountBalance())
                .build();
    }

    public CustomResponse deposit(TransactionRequest depositRequest){
        //check if the account number exist
        //return message if not
        //update balance
        Optional<User> user = userRepository.findByAccountNumber(depositRequest.getAccountNumber());
        if (user.isEmpty()){
            return CustomResponse.builder()
                    .error(true)
                    .message("Account not found")
                    .build();
        }
        User transactingUser = user.get();
        transactingUser.setAccountBalance(transactingUser.getAccountBalance().add(BigDecimal.valueOf(depositRequest.getAmount())));
        transactingUser = userRepository.save(transactingUser);
        return CustomResponse.builder()
                .error(false)
                .message("SUCCESS")
                .userDetails(accountUtils.mapUserToUserDto(transactingUser))
                .build();

    }

    public CustomResponse withdraw(TransactionRequest withdrawalRequest){
        Optional<User> user = userRepository.findByAccountNumber(withdrawalRequest.getAccountNumber());
        if (user.isEmpty()){
            return CustomResponse.builder()
                    .error(true)
                    .message("Account not found")
                    .build();
        }
        User transactingUser = user.get();
        if (transactingUser.getAccountBalance().compareTo(BigDecimal.valueOf(withdrawalRequest.getAmount())) < 0){
            return CustomResponse.builder()
                    .error(true)
                    .message("Insufficient Balance")
                    .build();
        }
        transactingUser.setAccountBalance(transactingUser.getAccountBalance().subtract(BigDecimal.valueOf(withdrawalRequest.getAmount())));
        userRepository.save(transactingUser);
        return CustomResponse.builder()
                .error(false)
                .message("SUCCESS")
                .userDetails(accountUtils.mapUserToUserDto(transactingUser))
                .build();
    }
}
