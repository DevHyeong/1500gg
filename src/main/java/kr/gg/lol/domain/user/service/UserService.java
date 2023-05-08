package kr.gg.lol.domain.user.service;

import kr.gg.lol.domain.user.dto.UserDto;
import kr.gg.lol.domain.user.entity.User;
import kr.gg.lol.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean validateNickname(String nickname){
        return userRepository.findByNickname(nickname).isEmpty();
    }

    public UserDto signIn(UserDto userDto){
        User user = new User(userDto);
        UserDto securedUser = new UserDto(userRepository.save(user));
        SecurityContextHolder.getContext().setAuthentication(securedUser);

        return securedUser;
    }
}
