package com.bitacora.project.application.ports.output;

import java.util.List;
import java.util.Optional;

import com.bitacora.project.domain.Trade;
public interface TradePortOut {
    Trade save(Trade trade);
    Optional<Trade> findById(String id);
    List<Trade> findAll();
    void deleteTrade(String id);

}
