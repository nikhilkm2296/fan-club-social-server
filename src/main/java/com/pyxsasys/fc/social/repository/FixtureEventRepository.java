package com.pyxsasys.fc.social.repository;

import com.pyxsasys.fc.social.domain.FixtureEvent;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the FixtureEvent entity.
 */
public interface FixtureEventRepository extends MongoRepository<FixtureEvent,String> {

}
