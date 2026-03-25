package com.fiap.techchallenge.application.usecases.userTypes;

import com.fiap.techchallenge.application.gateways.userType.UserTypeGateway;
import com.fiap.techchallenge.application.usecases.userType.CreateUserTypeUseCase;
import com.fiap.techchallenge.domain.entities.UserType;
import com.fiap.techchallenge.infrastructure.exception.BusinessRuleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateUserTypeUseCase Tests")
class CreateUserTypeUseCaseTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    @InjectMocks
    private CreateUserTypeUseCase createUserTypeUseCase;

    private UserType userType;

    @BeforeEach
    void setup() {
        userType = new UserType();
        userType.setName("CLIENTE");
    }

    @Test
    @DisplayName("Should create a new user type when name does not exist")
    void shouldCreateUserType() {
        // Arrange
        when(userTypeGateway.existsByName(anyString())).thenReturn(false);
        when(userTypeGateway.save(any(UserType.class))).thenAnswer(invocation -> {
            UserType saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        // Act
        UserType result = createUserTypeUseCase.execute(userType);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("CLIENTE");
        verify(userTypeGateway, times(1)).existsByName("CLIENTE");
        verify(userTypeGateway, times(1)).save(userType);
    }

    @Test
    @DisplayName("Should throw exception when user type name already exists")
    void shouldThrowExceptionWhenUserTypeAlreadyExists() {
        // Arrange
        when(userTypeGateway.existsByName(anyString())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> createUserTypeUseCase.execute(userType))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessage("Tipo de usuário já cadastrado: CLIENTE");

        verify(userTypeGateway, times(1)).existsByName("CLIENTE");
        verify(userTypeGateway, never()).save(any(UserType.class));
    }
}
