package br.com.dea.management.user.controller;

import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.dto.UserDto;
import br.com.dea.management.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public Page<UserDto> getUsers(
            @RequestParam Integer page,
            @RequestParam Integer pageSize
    ) {
        Page<User> usersPaged = this.userService.findAllStudentsPaginated(page, pageSize);
        Page<UserDto> usersDto = usersPaged.map(user -> UserDto.fromUser(user));

        return usersDto;
    }
}
