package com.pyxsasys.fc.social.repository;

import com.pyxsasys.fc.social.domain.Fixture;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Fixture entity.
 */
public interface FixtureRepository extends MongoRepository<Fixture,String> {
    
    /**
     * Finds the fixture based on the match id
     * @param matchId the match id
     * @return {@link Fixture} instance
     */
    Fixture findByMatchId(String matchId);
}
