package br.com.dea.management.academyclass.update;

import br.com.dea.management.AcademyTestUtils;
import br.com.dea.management.academyclass.ClassType;
import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.academyclass.get.AcademyClassGetAllTests;
import br.com.dea.management.academyclass.repository.AcademyClassRepository;
import br.com.dea.management.employee.EmployeeTestUtils;
import br.com.dea.management.employee.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test-mysql")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class AcademyClassUpdateSuccessCaseTestes {

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

    @BeforeEach
    void beforeEach() {
        log.info("Before each test in " + AcademyClassUpdateSuccessCaseTestes.class.getSimpleName());
    }

    @BeforeAll
    void beforeSuiteTest() {
        log.info("Before all tests in " + AcademyClassUpdateSuccessCaseTestes.class.getSimpleName());
    }

    @Test
    void whenAllPayloadAcademyClassRequiredValid_thenReturnSuccessfully() throws Exception {

        this.academyClassRepository.deleteAll();
        this.employeeRepository.deleteAll();

        // created a valid academyClass
        LocalDate startDate = LocalDate.of(2023, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(2024, Month.DECEMBER, 20);
        this.academyClassTestUtils.createFakeClass(1, startDate, endDate, ClassType.DEVELOPER);
        Long academyClassId = this.academyClassRepository.findAll().get(0).getId();

        // created a new fake employee to be the instructor
        this.employeeTestUtils.createFakeEmployees(1);
        Long newInstructorId = this.employeeRepository.findAll().get(1).getId();

        String payload = "{" +
                "\"startDate\": \"2022-01-01\", " +
                "\"endDate\": \"2023-01-01\", " +
                "\"classType\" : \"DESIGN\", " +
                "\"instructorId\" : "  + newInstructorId +
                "}";

        mockMvc.perform(put("/academy-class/" + academyClassId)
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isOk());

        AcademyClass academyClass = this.academyClassRepository.findAll().get(0);
        assertThat(academyClass.getStartDate()).isEqualTo("2022-01-01");
        assertThat(academyClass.getEndDate()).isEqualTo("2023-01-01");
        assertThat(academyClass.getClassType()).isEqualTo(ClassType.DESIGN);
        assertThat(academyClass.getInstructor().getId()).isEqualTo(newInstructorId);

    }
}
