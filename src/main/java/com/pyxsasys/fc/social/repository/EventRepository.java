package com.pyxsasys.fc.social.repository;

import com.pyxsasys.fc.social.domain.Event;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Event entity.
 */
public interface EventRepository extends MongoRepository<Event,String> {

}
