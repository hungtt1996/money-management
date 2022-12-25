package com.hungtt.personal.repository;

import com.hungtt.personal.domain.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Group entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupRepository extends MongoRepository<Group, String> {}
