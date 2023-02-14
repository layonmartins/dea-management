package br.com.dea.management.user.controller;

import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.dto.UserDto;
import br.com.dea.management.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "User", description = "The Student API")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "Load the list of users paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Page or Page Size params not valid"),
            @ApiResponse(responseCode = "500", description = "Error fetching student list")
    })
    @GetMapping("/users")
    public Page<UserDto> getUsers(
            @RequestParam(required = true) Integer page,
            @RequestParam(required = true) Integer pageSize
    ) {

        log.info(String.format("Fetching users : page : %s : pageSize: %s", page, pageSize));

        Page<User> usersPaged = this.userService.findAllStudentsPaginated(page, pageSize);
        Page<UserDto> usersDto = usersPaged.map(user -> UserDto.fromUser(user));

        log.info(String.format("Users loaded successfully: Users : %s", usersDto.getContent()));

        return usersDto;
    }
}
