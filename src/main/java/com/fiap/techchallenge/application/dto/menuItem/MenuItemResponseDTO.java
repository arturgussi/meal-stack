package com.fiap.techchallenge.application.dto.menuItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MenuItemResponseDTO(
    Long id,
    String name,
    String description,
    BigDecimal price,
    boolean onlyOnSite,
    String photoPath,
    Long restaurantId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
