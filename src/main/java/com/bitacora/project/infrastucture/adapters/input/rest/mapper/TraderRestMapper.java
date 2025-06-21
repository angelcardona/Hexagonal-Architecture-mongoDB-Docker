package com.bitacora.project.infrastucture.adapters.input.rest.mapper;

import java.util.List;


import org.mapstruct.Mapper;

import com.bitacora.project.domain.Trade;
import com.bitacora.project.infrastucture.adapters.input.rest.model.response.TradeResponse;

@Mapper(componentModel = "spring")
public interface TraderRestMapper {


    TradeResponse toTradeResponse(Trade trade);

    List<TradeResponse> toTradeResponseList(List<Trade> trades);


}
