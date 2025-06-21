package com.bitacora.project.application.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.bitacora.project.application.ports.input.TradePort;
import com.bitacora.project.application.ports.output.TradePortOut;
import com.bitacora.project.domain.Trade;
import com.bitacora.project.domain.exceptions.InvalidTradeDataException;
import com.bitacora.project.domain.exceptions.TradeNotFoundException;
import com.bitacora.project.infrastucture.adapters.input.rest.model.request.TradeCreateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeService implements TradePort {

    private TradePortOut tradePortOut;

    @Override
    public Trade registerTrade(TradeCreateRequest request) {
        if(request.getEntryPrice()<=0 || request.getExitPrice()<=0){
            throw new InvalidTradeDataException("The entry price have to be positive");
        }
        Trade newTrade= new Trade(
        request.getAsset(),
        request.getEntryPrice(),
        request.getExitPrice(),
        request.getDescription(),
        request.getImageUrl()
        );
        return tradePortOut.save(newTrade);
    }

    @Override
    public Trade getTradebyId(String id) {
        return tradePortOut.findById(id)
        .orElseThrow(()->new TradeNotFoundException(id));
    }

    @Override
    public List<Trade> getAllTrades() {
        return tradePortOut.findAll();
    }

    @Override
    public void deleteTrade(String id) {
       if(tradePortOut.findById(id).isEmpty()){
        throw new TradeNotFoundException(id);
       }
       tradePortOut.deleteTrade(id);
       
    }

}
