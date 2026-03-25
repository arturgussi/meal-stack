package com.fiap.techchallenge.application.usecases.menuItem;

import com.fiap.techchallenge.application.gateways.menuItem.MenuItemGateway;
import com.fiap.techchallenge.application.gateways.restaurant.RestaurantGateway;
import com.fiap.techchallenge.domain.entities.MenuItem;
import com.fiap.techchallenge.domain.entities.Restaurant;
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
class CreateMenuItemUseCaseTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private CreateMenuItemUseCase createMenuItemUseCase;

    private MenuItem menuItem;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        menuItem = new MenuItem(null, "Pizza", "Delicious", new BigDecimal("50.00"), true, "/images/pizza.jpg", 1L);
        restaurant = new Restaurant();
        restaurant.setId(1L);
    }

    @Test
    @DisplayName("Deve criar um item do cardápio com sucesso")
    void shouldCreateMenuItemSuccessfully() {
        when(restaurantGateway.findById(1L)).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.existsByNameAndRestaurantId("Pizza", 1L)).thenReturn(false);
        when(menuItemGateway.save(any(MenuItem.class))).thenReturn(menuItem);

        MenuItem result = createMenuItemUseCase.execute(menuItem);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Pizza");
        verify(menuItemGateway, times(1)).save(menuItem);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o restaurante não existir")
    void shouldThrowExceptionWhenRestaurantNotFound() {
        when(restaurantGateway.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> createMenuItemUseCase.execute(menuItem))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Restaurante não encontrado");

        verify(menuItemGateway, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o item já existir no restaurante")
    void shouldThrowExceptionWhenMenuItemAlreadyExists() {
        when(restaurantGateway.findById(1L)).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.existsByNameAndRestaurantId("Pizza", 1L)).thenReturn(true);

        assertThatThrownBy(() -> createMenuItemUseCase.execute(menuItem))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("Já existe um item com este nome");

        verify(menuItemGateway, never()).save(any());
    }
}
