package com.fiap.techchallenge.infrastructure.config.restaurant;

import com.fiap.techchallenge.application.gateways.restaurant.RestaurantGateway;
import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.application.usecases.restaurante.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantConfig {

    @Bean
    public CreateRestaurantUseCase createRestaurantUseCase(RestaurantGateway restaurantGateway, UserGateway userGateway) {
        return new CreateRestaurantUseCase(restaurantGateway, userGateway);
    }

    @Bean
    public DeleteRestaurantUseCase deleteRestaurantUseCase(RestaurantGateway restaurantGateway) {
        return new DeleteRestaurantUseCase(restaurantGateway);
    }

    @Bean
    public FindRestaurantByIdUseCase findRestaurantByIdUseCase(RestaurantGateway restaurantGateway) {
        return new FindRestaurantByIdUseCase(restaurantGateway);
    }

    @Bean
    public ListAllRestaurantsUseCase listAllRestaurantsUseCase(RestaurantGateway restaurantGateway) {
        return new ListAllRestaurantsUseCase(restaurantGateway);
    }

    @Bean
    public UpdateRestaurantUseCase updateRestaurantUseCase(RestaurantGateway restaurantGateway, UserGateway userGateway) {
        return new UpdateRestaurantUseCase(restaurantGateway, userGateway);
    }
}
