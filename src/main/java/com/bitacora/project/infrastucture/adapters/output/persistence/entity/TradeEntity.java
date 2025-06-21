package com.bitacora.project.infrastucture.adapters.output.persistence.entity;

import java.time.Instant;

import org.springframework.data.mongodb.core.mapping.Document;

import com.bitacora.project.domain.TradeStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("trades")
public class TradeEntity {

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
