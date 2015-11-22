package com.pyxsasys.fc.social.repository;

import com.pyxsasys.fc.social.domain.AccessDirectory;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the AccessDirectory entity.
 */
public interface AccessDirectoryRepository extends MongoRepository<AccessDirectory,String> {
    
    AccessDirectory findByEmail(String email);

}
