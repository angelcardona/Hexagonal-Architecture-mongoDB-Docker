package com.bitacora.project.infrastucture.adapters.input.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitacora.project.application.ports.input.TradePort;

import com.bitacora.project.infrastucture.adapters.input.rest.mapper.TraderRestMapper;
import com.bitacora.project.infrastucture.adapters.input.rest.model.request.TradeCreateRequest;
import com.bitacora.project.infrastucture.adapters.input.rest.model.response.TradeResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/trades")
@RequiredArgsConstructor
public class TraderRestController {

    //Entry port
    private final TradePort tradePort;
    private final TraderRestMapper mapper;


    @GetMapping("/api")
    /*
     * The Trade port talks in term of domain it means it returns a entitie domain
     * but we need provide a reponse for this reason we use the mapper to return a 
     * response in terms of Trade response
     */
    public List<TradeResponse> findAll(){
        return mapper.toTradeResponseList(tradePort.getAllTrades());
    } 
    @GetMapping("/api/{id}")
    public TradeResponse getTradebyId(@PathVariable String id){
        return mapper.toTradeResponse(tradePort.getTradebyId(id));
    }
    
    @PostMapping("/api")
    public ResponseEntity<TradeResponse> save(@Valid@RequestBody TradeCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toTradeResponse(
                    tradePort.registerTrade(request)
                ));
        
       
    }
    @DeleteMapping("/api/{id}")
    public void delete(@PathVariable String id){
        tradePort.deleteTrade(id);
    }

}
