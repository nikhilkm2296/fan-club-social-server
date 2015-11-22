package com.pyxsasys.fc.social.repository;

import com.pyxsasys.fc.social.domain.FixtureConfig;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the FixtureConfig entity.
 */
public interface FixtureConfigRepository extends MongoRepository<FixtureConfig,String> {

}
