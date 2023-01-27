package br.com.dea.management.user.service;

import br.com.dea.management.exceptions.NotFoundException;
import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUser() {
        return this.userRepository.findAll();
    }

    public User findUserByEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);

        if(user.isPresent()) {
            return user.get();
        }

        throw new NotFoundException(User.class, email);

        //return user.orElseThrow(() -> new NotFoundException(User.class, email));

    }

    public Page<User> findAllStudentsPaginated(Integer page, Integer pageSize) {
        return this.userRepository.findAllPaginated(PageRequest.of(page, pageSize));
    }

}
