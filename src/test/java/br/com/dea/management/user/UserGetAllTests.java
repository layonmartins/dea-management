package br.com.dea.management.user;


import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test-mysql")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class UserGetAllTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        log.info("Before each test in " + UserGetAllTests.class.getSimpleName());
    }

    @BeforeAll
    void beforeSuitTest() {
        log.info("Before all tests in " + UserGetAllTests.class.getSimpleName());
    }

    @Test
    void whenRequestingUserList_thenReturnListOfStudentPaginatedSuccessfully() throws Exception {
        this.userRepository.deleteAll();
        this.createFakeUsers(100);

        mockMvc.perform(MockMvcRequestBuilders.get("/users?page=0&pageSize=4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(4)))
                .andExpect(jsonPath("$.content[0].name", is("name 0")))
                .andExpect(jsonPath("$.content[0].email", is("email 0")))
                .andExpect(jsonPath("$.content[0].linkedin", is("linkedin 0")))
                .andExpect(jsonPath("$.content[1].name", is("name 1")))
                .andExpect(jsonPath("$.content[1].email", is("email 1")))
                .andExpect(jsonPath("$.content[1].linkedin", is("linkedin 1")))
                .andExpect(jsonPath("$.content[2].name", is("name 2")))
                .andExpect(jsonPath("$.content[2].email", is("email 2")))
                .andExpect(jsonPath("$.content[2].linkedin", is("linkedin 2")))
                .andExpect(jsonPath("$.content[3].name", is("name 3")))
                .andExpect(jsonPath("$.content[3].email", is("email 3")))
                .andExpect(jsonPath("$.content[3].linkedin", is("linkedin 3")));
    }

    private void createFakeUsers(int amount) {
        for (int i = 0; i < amount; i++) {
            User u = new User();
            u.setEmail("email " + i);
            u.setName("name " + i);
            u.setLinkedin("linkedin " + i);
            u.setPassword("pwd " + i);

            this.userRepository.save(u);
        }
    }

}
