package com.fiap.techchallenge.application.usecases.user;

import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.domain.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Find Users By Name Use Case Tests")
class FindUsersByNameUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private FindUsersByNameUseCase findUsersByNameUseCase;

    @Test
    @DisplayName("Should return list of users when name matches")
    void shouldReturnUsersWhenNameMatches() {
        User user = new User();
        user.setName("João Silva");
        when(userGateway.findByNameContainingIgnoreCase("João")).thenReturn(List.of(user));

        List<User> result = findUsersByNameUseCase.execute("João");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("João Silva");
    }

    @Test
    @DisplayName("Should return empty list when no name matches")
    void shouldReturnEmptyListWhenNoMatch() {
        when(userGateway.findByNameContainingIgnoreCase("Unknown")).thenReturn(Collections.emptyList());

        List<User> result = findUsersByNameUseCase.execute("Unknown");

        assertThat(result).isEmpty();
    }
}
