package com.bitacora.project.infrastucture.adapters.output.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.bitacora.project.application.ports.output.TradePortOut;
import com.bitacora.project.domain.Trade;
import com.bitacora.project.infrastucture.adapters.output.persistence.mapper.TradeMapper;
import com.bitacora.project.infrastucture.adapters.output.persistence.repository.TradeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TradePersistenceAdapter implements TradePortOut{


    /*
     * The output adapter is responsible of receive the data that comes the frontend of any other fluent
     * but it comes in format of domain entitie and it should mapping to persistence entitie
     * the adapter is responsible to do that 
     */

    private final TradeRepository repository;

        private final TradeMapper mapper;

    @Override
    public Trade save(Trade trade) {
        /*
         * Here we receive a domain entitie, if we want to save it in persistence
         * we have to change it to persistence entitie,because it is the entitie of repository
         * then we have to map again but the reposotorie entity to domain
         * because it is the entitie that the method returns
         */

        return mapper.toTrade(repository.save(mapper.toTradeEntity(trade)));
    }

    @Override
    public Optional<Trade> findById(String id) {
        
        /*The method return a entitie(Persitence)
         * we use the mapper to cast the entitie persistence to domain entitie
         */
        return repository.findById(id)
               .map(mapper::toTrade);
    }

    @Override
    public List<Trade> findAll() {
        
        return mapper.toTradeList(repository.findAll());
    }

    @Override
    public void deleteTrade(String id) {
        repository.deleteById(id);
    }

}
