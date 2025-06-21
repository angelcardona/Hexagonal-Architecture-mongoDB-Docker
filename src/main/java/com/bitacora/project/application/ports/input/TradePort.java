package com.bitacora.project.application.ports.input;

import java.util.List;


import com.bitacora.project.domain.Trade;
import com.bitacora.project.infrastucture.adapters.input.rest.model.request.TradeCreateRequest;

public interface TradePort {
    Trade registerTrade(TradeCreateRequest request);
    Trade getTradebyId(String id);
    List<Trade> getAllTrades();
    void deleteTrade(String id);

}
