package com.fiap.techchallenge.application.usecases.menuItem;

import com.fiap.techchallenge.application.gateways.menuItem.MenuItemGateway;
import com.fiap.techchallenge.domain.entities.MenuItem;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindMenuItemByIdUseCaseTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    @InjectMocks
    private FindMenuItemByIdUseCase findMenuItemByIdUseCase;

    private MenuItem menuItem;

    @BeforeEach
    void setUp() {
        menuItem = new MenuItem(1L, "Pizza", "Delicious", new BigDecimal("50.00"), true, "/images/pizza.jpg", 1L);
    }

    @Test
    @DisplayName("Deve buscar um item pelo ID com sucesso")
    void shouldFindMenuItemByIdSuccessfully() {
        when(menuItemGateway.findById(1L)).thenReturn(Optional.of(menuItem));

        MenuItem result = findMenuItemByIdUseCase.execute(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Pizza");
    }

    @Test
    @DisplayName("Deve lançar exceção quando o item não for encontrado")
    void shouldThrowExceptionWhenMenuItemNotFound() {
        when(menuItemGateway.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> findMenuItemByIdUseCase.execute(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Item do cardápio não encontrado");
    }
}
