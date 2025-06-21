package com.bitacora.project.infrastucture.adapters.output.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bitacora.project.infrastucture.adapters.output.persistence.entity.TradeEntity;

@Repository
public interface TradeRepository extends MongoRepository <TradeEntity , String>{

}
