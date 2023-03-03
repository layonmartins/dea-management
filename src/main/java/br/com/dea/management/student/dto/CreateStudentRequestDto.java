package br.com.dea.management.student.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateStudentRequestDto {

    @NotNull(message = "Name could not be null")
    @NotEmpty(message = "Name could not be empty")
    // @Min(value = 2, message = "Name should not be less than 4") bug
    // @Max(value = 50, message = "Name should not be more than 50 characters") bug
    private String name;

    @NotNull(message = "Email could not be null")
    @NotEmpty(message = "Email could not be empty")
    @Email(message = "Email passed is not valid!")
    private String email;

    @NotNull(message = "Linkedin could not be null")
    @NotEmpty(message = "Linkedin could not be empty")
    private String linkedin;

    @NotNull(message = "University could not be null")
    @NotEmpty(message = "University could not be empty")
    private String university;

    @NotNull(message = "Graduation could not be null")
    @NotEmpty(message = "Graduation could not be empty")
    private String graduation;

    @NotNull(message = "FinishDate could not be null")
    @FutureOrPresent(message = "FinishDate should be in the future or present")
    private LocalDate finishDate;

    @NotNull(message = "Password could not be null")
    @NotEmpty(message = "Password could not be empty")
    @Size(min = 4, max = 8, message = "Password must be between 4 and 8 characters")
    private String password;

}
