package com.fiap.techchallenge.application.usecases.menuItem;

import com.fiap.techchallenge.application.gateways.menuItem.MenuItemGateway;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeleteMenuItemUseCase Tests")
class DeleteMenuItemUseCaseTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    @InjectMocks
    private DeleteMenuItemUseCase deleteMenuItemUseCase;

    @Test
    @DisplayName("Should delete menu item when it exists")
    void shouldDeleteMenuItemWhenExists() {
        Long id = 1L;
        when(menuItemGateway.findById(id)).thenReturn(Optional.of(mock(com.fiap.techchallenge.domain.entities.MenuItem.class)));

        deleteMenuItemUseCase.execute(id);

        verify(menuItemGateway, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Should throw exception when menu item does not exist")
    void shouldThrowExceptionWhenNotExists() {
        Long id = 1L;
        when(menuItemGateway.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> deleteMenuItemUseCase.execute(id));
        verify(menuItemGateway, never()).deleteById(any());
    }
}
