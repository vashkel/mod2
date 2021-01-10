package com.epam.esm.service.impl;

import com.epam.esm.entity.User;
import com.epam.esm.modelDTO.user.UserDTO;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.security.service.JwtTokenProvider;
import com.epam.esm.service.UserService;
import com.epam.esm.util.DTOConverter.user.UserDTOConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private User user1;
    private User user2;
    private List<User> userList;

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private UserService userService = new UserServiceImpl(userRepository, jwtTokenProvider);

    @BeforeEach
    void setUp() {
        user1 = new User(1L, "userName1");
        user2 = new User(2L, "userName2");
        userList = Arrays.asList(user1, user2);
    }

    @Disabled
    @Test
    void testFindById() {
        Long userId = 1L;

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user1));

        UserDTO expectedUserTDO = UserDTOConverter.convertToUserDTOWithoutOrders(user1);
        UserDTO actualUserDTO = userService.findById(userId);

        Assertions.assertEquals(actualUserDTO, expectedUserTDO);
    }

}
