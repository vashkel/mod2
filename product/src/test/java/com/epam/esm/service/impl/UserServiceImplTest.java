package com.epam.esm.service.impl;

import com.epam.esm.entity.User;
import com.epam.esm.modelDTO.tag.TagDTO;
import com.epam.esm.modelDTO.user.UserDTO;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.util.DTOConverter.tag.TagDTOConverter;
import com.epam.esm.util.DTOConverter.user.UserDTOConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    private UserDTOConverter converter;

    @InjectMocks
    private UserService userService = new UserServiceImpl(userRepository, converter);

    @BeforeEach
    void setUp(){
        user1 = userCreator(1L, "userName1");
        user2 = userCreator(2L, "userName2");
        userList = Arrays.asList(user1, user2);
    }

    private User userCreator(Long userId, String name){
        User user = new User(userId, name);
        return user;
    }

    @Test
     void testFindById(){
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));
        UserDTO expectedUserTDO = UserDTOConverter.convertToUserDTOWithoutOrders(user1);
        UserDTO actualUserDTO = userService.findById(1L);
        Assertions.assertEquals(actualUserDTO, expectedUserTDO);
    }

}
