package br.com.dea.management.employee.dto;

import br.com.dea.management.employee.EmployeeType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateEmployeeRequestDto {

    @NotNull(message = "Name could not be null")
    private String name;

    @NotNull(message = "Email could not be null")
    @Email(message = "Email passed is not valid!")
    private String email;

    private String linkedin;

    private EmployeeType employeeType;

    private Long position;

    @NotNull(message = "Password could not be null")
    @NotEmpty(message = "Password could not be empty")
    @Size(min = 4, max = 8, message = "Password must be between 4 and 8 characters")
    private String password;
}
