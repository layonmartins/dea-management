package br.com.dea.management.academyclass.controller;

import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.academyclass.dto.AcademyClassDto;
import br.com.dea.management.academyclass.dto.CreateAcademyClassDto;
import br.com.dea.management.academyclass.dto.UpdateAcademyClassDto;
import br.com.dea.management.academyclass.service.AcademyClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Tag(name = "AcademyClass", description = "The AcademyClass API")
public class AcademyClassController {

    @Autowired
    AcademyClassService academyClassService;

    @Operation(summary = "Load the list of academyClass paginated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Page or Page Size params not valid"),
            @ApiResponse(responseCode = "500", description = "Error fetching academyClass list"),
    })
    @GetMapping("/academy-class")
    public Page<AcademyClassDto> getAcademyClass(@RequestParam(required = true) Integer page,
                                                 @RequestParam(required = true) Integer pageSize) {

        log.info(String.format("Fetching academyClass : page : %s : pageSize", page, pageSize));

        Page<AcademyClass> academyClassPaged = this.academyClassService.findAllAcademyClassPaginated(page, pageSize);
        Page<AcademyClassDto> academyClasses = academyClassPaged.map(academyClass -> AcademyClassDto.fromAcademyClass(academyClass));

        log.info(String.format("AcademyClass loaded successfully : AcademyClass : %s : pageSize", academyClasses.getContent().size()));

        return academyClasses;

    }

    @Operation(summary = "Load the academyClass by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "AcademyClass Id invalid"),
            @ApiResponse(responseCode = "404", description = "AcademyClass Not found"),
            @ApiResponse(responseCode = "500", description = "Error fetching academyClass list"),
    })
    @GetMapping("/academy-class/{id}")
    public AcademyClassDto getAcademyClass(@PathVariable Long id) {

        log.info(String.format("Fetching academyClass by id : Id : %s", id));

        AcademyClassDto academyClass = AcademyClassDto.fromAcademyClass(this.academyClassService.findAcademyClassById(id));

        log.info(String.format("AcademyClass loaded successfully : AcademyClass : %s", academyClass));

        return academyClass;

    }


    @Operation(summary = "Create a new AcademyClass")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfull operation"),
            @ApiResponse(responseCode = "400", description = "Payload not valid"),
            @ApiResponse(responseCode = "500", description = "Error creating AcademyClass")
    })
    @PostMapping("/academy-class")
    public void createAcademyClass(@Valid @RequestBody CreateAcademyClassDto createAcademyClassDto) {
        log.info(String.format("Creating AcademyClass: Payload : %s", createAcademyClassDto));

        AcademyClass academyClass = academyClassService.createAcademyClass(createAcademyClassDto);

        log.info(String.format("AcademyClass created: Payload : %s", createAcademyClassDto));

    }

    @Operation(summary = "Update AcademyClass")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Payload not valid"),
            @ApiResponse(responseCode = "400", description = "AcademyClass not found"),
            @ApiResponse(responseCode = "500", description = "Error creating AcademyClass")
    })
    @PutMapping("/academy-class/{academyClassId}")
    public void updateAcademyClass(@PathVariable Long academyClassId, @Valid @RequestBody UpdateAcademyClassDto updateAcademyClassDto) {
        log.info(String.format("Update AcademyClass: Payload : %s", updateAcademyClassDto));

        AcademyClass academyClass = academyClassService.updateAcademyClass(academyClassId, updateAcademyClassDto);

        log.info(String.format("AcademyClass updated: Payload : %s", updateAcademyClassDto));
    }

    @Operation(summary = "Delete a AcademyClass")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Payload not valid"),
            @ApiResponse(responseCode = "400", description = "AcademyClass not found"),
            @ApiResponse(responseCode = "500", description = "Error creating AcademyClass")
    })
    @DeleteMapping("/academy-class/{academyClassId}")
    public void deleteAcademyClass(@PathVariable Long academyClassId) {
        log.info(String.format("Deleting AcademyClass: Payload : %s", academyClassId));

        academyClassService.deleteAcademyClass(academyClassId);

        log.info(String.format("AcademyClass deleted: Payload : %s", academyClassId));
    }

}
