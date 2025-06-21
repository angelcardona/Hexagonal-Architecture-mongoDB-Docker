package com.bitacora.project.infrastucture.adapters.output.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.bitacora.project.domain.Trade;
import com.bitacora.project.infrastucture.adapters.output.persistence.entity.TradeEntity;

@Mapper(componentModel = "spring")
public interface TradeMapper {
    
    //It allows map the domain entitie(Trade) to persitence entitie
    TradeEntity toTradeEntity(Trade trade);
    //It allows map the persistence entitie  to persitence domain entitie
    Trade toTrade(TradeEntity tradeEntity);
    List<Trade> toTradeList(List<TradeEntity> tradeEntities);

}
