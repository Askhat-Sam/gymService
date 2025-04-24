package dev.gymService.security;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.User;
import dev.gymService.repository.interfaces.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByUserName(username);

        if (user instanceof Trainee) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_TRAINEE")));
        } else if (user instanceof Trainer) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_TRAINER")));
        } else {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_USER")));
        }
    }
}
