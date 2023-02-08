package br.com.dea.management.user.dto;

import br.com.dea.management.user.domain.User;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    private String name;
    private String email;
    private String linkedin;

    public static List<UserDto> fromUsers(List<User> users) {
        return users.stream().map(user -> {
            UserDto userDto = UserDto.fromUser(user);
            return userDto;
        }).collect(Collectors.toList());
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setLinkedin(user.getLinkedin());

        return userDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }
}
