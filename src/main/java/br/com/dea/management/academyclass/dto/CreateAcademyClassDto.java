package br.com.dea.management.academyclass.dto;

import br.com.dea.management.academyclass.ClassType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateAcademyClassDto {

    @NotNull(message = "startDate could not be null")
    private LocalDate startDate;

    @NotNull(message = "endDate could not be null")
    private LocalDate endDate;

    @NotNull(message = "classType could not be null")
    private ClassType classType;

    @NotNull(message = "instructorId could not be null")
    private Long instructorId;

}
