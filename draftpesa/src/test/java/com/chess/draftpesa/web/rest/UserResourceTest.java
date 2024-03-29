package com.chess.draftpesa.web.rest;

import com.chess.draftpesa.config.SecurityConfig;
import com.chess.draftpesa.domain.Enumarations.PlayerStatus;
import com.chess.draftpesa.domain.Enumarations.UserRole;
import com.chess.draftpesa.domain.Enumarations.UserStatus;
import com.chess.draftpesa.domain.User;
import com.chess.draftpesa.service.UserService;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@Import(SecurityConfig.class)
public class UserResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void createUserShouldReturnCreatedUser() throws Exception {
        User user = new User();
        user.setId(3L);
        user.setFirstName("Joan");
        user.setMiddleName("Doe");
        user.setLastName("Mulwa");
        user.setFullName("Joan Mulwa");
        user.setUsername("mulwaJoan");
        user.setEmail("mulwajoaninah@gmail.com");
        user.setPassword("admin");
        user.setUserRole(UserRole.PLAYER);
        user.setUserStatus(UserStatus.ACTIVE);
        user.setPlayerStatus(PlayerStatus.ACTIVE);
        user.setCreatedBy("mulwajoaninah@gmail.com");
        user.setLastUpdatedBy("mulwajoaninah@gmail.com");
        user.setCreatedAt(LocalDateTime.now().toString());
        user.setLastUpdatedAt(LocalDateTime.now().toString());

        given(userService.createUser(any())).willReturn(user);

        ObjectMapper objectMapper = new ObjectMapper().configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true).registerModule(new JavaTimeModule());
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/users").with((csrf()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(userJson));
    }
}
