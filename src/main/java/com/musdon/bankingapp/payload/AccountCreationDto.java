package com.musdon.bankingapp.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountCreationDto {
    @NotNull(message = "firstName may not be null")
    private String firstName;
    @NotNull(message = "last name may not be null")
    private String lastName;
    private String otherName;
    @Email
    private String email;
}
