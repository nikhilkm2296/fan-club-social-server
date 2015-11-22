package com.pyxsasys.fc.social.repository;

import com.pyxsasys.fc.social.domain.FeedConfig;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the FeedConfig entity.
 */
public interface FeedConfigRepository extends MongoRepository<FeedConfig,String> {

}
