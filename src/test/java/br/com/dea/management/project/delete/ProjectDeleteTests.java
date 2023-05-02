package br.com.dea.management.project.delete;

import br.com.dea.management.AcademyTestUtils;
import br.com.dea.management.academyclass.ClassType;
import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.academyclass.repository.AcademyClassRepository;
import br.com.dea.management.employee.EmployeeTestUtils;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.project.ProjectTestUtils;
import br.com.dea.management.project.domain.Project;
import br.com.dea.management.project.repository.ProjectRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test-msql")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class ProjectDeleteTests {

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

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectTestUtils projectTestUtils;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @BeforeEach
    void beforeEach() {
        log.info("Before each test in " + ProjectDeleteTests.class.getSimpleName());
    }

    @BeforeAll
    void beforeSuiteTest() {
        log.info("Before all tests in " + ProjectDeleteTests.class.getSimpleName());
    }

    @Test
    void whenDeleteAProjectThatDoesNotExist_thenReturn404() throws Exception {

        this.projectRepository.deleteAll();

        mockMvc.perform(delete("/project/1")
                        .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));

    }

    @Test
    void whenDeleteAProject_thenReturnSuccessfully() throws Exception {

        // Preparing for the Test
        this.projectRepository.deleteAll();
        this.academyClassRepository.deleteAll();
        this.employeeRepository.deleteAll();

        // created a valid project
        this.projectTestUtils.createFakeProject(1);

        //get the project Id
        Long projectId = this.projectRepository.findAll().get(0).getId();


        mockMvc.perform(delete("/project/" + projectId)
                        .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // check if the project has already deleted from DataBase
        List<Project> project = this.projectRepository.findAll();
        assertThat(project.size()).isEqualTo(0);

    }

}
