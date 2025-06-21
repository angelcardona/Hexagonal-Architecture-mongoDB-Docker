package com.bitacora.project.infrastucture.adapters.input.rest.model.response;

import java.time.Instant;

import com.bitacora.project.domain.TradeStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/*
 * Here we define the response
 * wich fields of domain will be expose to client 
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class TradeResponse {
     private String id;
    private String asset;
    private Double entryPrice;
    private Double exitPrice;
    private TradeStatus status;
    private Instant date;
    private String description;
    private String imageUrl;
    private double profitOrLoss;

}
