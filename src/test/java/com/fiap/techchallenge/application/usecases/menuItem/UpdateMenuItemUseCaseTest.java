package com.fiap.techchallenge.application.usecases.menuItem;

import com.fiap.techchallenge.application.gateways.menuItem.MenuItemGateway;
import com.fiap.techchallenge.domain.entities.MenuItem;
import com.fiap.techchallenge.infrastructure.exception.BusinessRuleException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateMenuItemUseCaseTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    @InjectMocks
    private UpdateMenuItemUseCase updateMenuItemUseCase;

    private MenuItem existingItem;
    private MenuItem updates;

    @BeforeEach
    void setUp() {
        existingItem = new MenuItem(1L, "Pizza", "Delicious", new BigDecimal("50.00"), true, "/images/pizza.jpg", 1L);
        updates = new MenuItem(null, "Pizza Premium", "Very Delicious", new BigDecimal("60.00"), true, "/images/pizza_premium.jpg", 1L);
    }

    @Test
    @DisplayName("Deve atualizar um item com sucesso")
    void shouldUpdateMenuItemSuccessfully() {
        when(menuItemGateway.findById(1L)).thenReturn(Optional.of(existingItem));
        when(menuItemGateway.existsByNameAndRestaurantId("Pizza Premium", 1L)).thenReturn(false);
        when(menuItemGateway.save(any(MenuItem.class))).thenReturn(updates);

        MenuItem result = updateMenuItemUseCase.execute(1L, updates);

        assertThat(result).isNotNull();
        verify(menuItemGateway, times(1)).save(any(MenuItem.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o item não existir")
    void shouldThrowExceptionWhenMenuItemNotFound() {
        when(menuItemGateway.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> updateMenuItemUseCase.execute(1L, updates))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(menuItemGateway, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o novo nome já existir no restaurante")
    void shouldThrowExceptionWhenNewNameAlreadyExists() {
        when(menuItemGateway.findById(1L)).thenReturn(Optional.of(existingItem));
        when(menuItemGateway.existsByNameAndRestaurantId("Pizza Premium", 1L)).thenReturn(true);

        assertThatThrownBy(() -> updateMenuItemUseCase.execute(1L, updates))
                .isInstanceOf(BusinessRuleException.class);

        verify(menuItemGateway, never()).save(any());
    }
}
