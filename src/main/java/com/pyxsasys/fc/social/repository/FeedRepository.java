package com.pyxsasys.fc.social.repository;

import com.pyxsasys.fc.social.domain.Feed;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Feed entity.
 */
public interface FeedRepository extends MongoRepository<Feed,String> {

}
