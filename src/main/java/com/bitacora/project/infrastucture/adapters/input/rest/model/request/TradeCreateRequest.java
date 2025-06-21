package com.bitacora.project.infrastucture.adapters.input.rest.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Here we define the resquest
 * wich fields the application needs to create a new trade
 * also we define the constraints 
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TradeCreateRequest {

    @NotBlank(message = "El activo no puede estar vacio")
    private String asset;

    @NotNull(message = "El precio de entrada es requerido")
    @Positive(message = "El precio debe ser Positivo")
    private Double entryPrice;

    @NotNull(message = "El precio de entrada es requerido")
    @Positive(message = "El precio debe ser Positivo")
    private Double exitPrice;
    
    private String description;
    private String imageUrl;

}
