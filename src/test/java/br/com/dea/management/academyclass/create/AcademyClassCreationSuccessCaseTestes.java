package br.com.dea.management.academyclass.create;

import br.com.dea.management.AcademyTestUtils;
import br.com.dea.management.academyclass.ClassType;
import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.academyclass.repository.AcademyClassRepository;
import br.com.dea.management.employee.EmployeeTestUtils;
import br.com.dea.management.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test-mysql")
public class AcademyClassCreationSuccessCaseTestes {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AcademyClassRepository academyClassRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AcademyTestUtils academyClassTestUtils;

    @Autowired
    private EmployeeTestUtils employeeTestUtils;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    void whenRequestingAcademyClassCreationWithAValidPayload_thenCreateAcademyClassSuccessfully() throws Exception {

        // Preparing for the Test
        this.academyClassRepository.deleteAll();
        this.employeeRepository.deleteAll();

        // created a valid employee to be the instructor
        this.employeeTestUtils.createFakeEmployees(1);
        Long instructorId = this.employeeRepository.findAll().get(0).getId();

        // created a valid payload passing a valid instructorId
        String payload = "{" +
                "\"startDate\": \"2022-01-01\", " +
                "\"endDate\": \"2023-01-01\", " +
                "\"classType\" : \"DEVELOPER\", " +
                "\"instructor\" : " + instructorId +
                "}";

        // perform the post operation and check if it has success
        mockMvc.perform(post("/academy-class")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isOk());

        // check if the inserted AcademyClass is valid
        AcademyClass academyClass = this.academyClassRepository.findAll().get(0);
        assertThat(academyClass.getStartDate()).isEqualTo("2022-01-01");
        assertThat(academyClass.getEndDate()).isEqualTo("2023-01-01");
        assertThat(academyClass.getClassType()).isEqualTo(ClassType.DEVELOPER);
        assertThat(academyClass.getInstructor().getId()).isEqualTo(instructorId);
    }
}
