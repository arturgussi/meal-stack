package com.fiap.techchallenge.application.usecases.menuItem;

import com.fiap.techchallenge.application.gateways.menuItem.MenuItemGateway;
import com.fiap.techchallenge.domain.entities.MenuItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListAllMenuItemsUseCaseTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    @InjectMocks
    private ListAllMenuItemsUseCase listAllMenuItemsUseCase;

    @Test
    @DisplayName("Deve listar todos os itens de um restaurante com sucesso")
    void shouldListAllMenuItemsSuccessfully() {
        MenuItem item1 = new MenuItem(1L, "Pizza", "Delicious", new BigDecimal("50.00"), true, "/images/pizza.jpg", 1L);
        MenuItem item2 = new MenuItem(2L, "Burger", "Tasty", new BigDecimal("30.00"), false, "/images/burger.jpg", 1L);

        when(menuItemGateway.findAllByRestaurantId(1L)).thenReturn(List.of(item1, item2));

        List<MenuItem> result = listAllMenuItemsUseCase.execute(1L);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Pizza");
        assertThat(result.get(1).getName()).isEqualTo("Burger");
    }
}
