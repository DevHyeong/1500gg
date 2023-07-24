package kr.gg.lol.domain.user.service;

import kr.gg.lol.domain.user.oauth.jwt.TokenProvider;
import kr.gg.lol.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;
    @Mock
    private UserRepository userRepository;
    private TokenProvider tokenProvider = new TokenProvider();

    @BeforeEach
    void setup(){
        userService = new UserService(userRepository, tokenProvider);
    }
    @Test
    void testSignInThenReturnUser(){

    }
}