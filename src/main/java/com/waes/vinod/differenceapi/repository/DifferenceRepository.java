package com.waes.vinod.differenceapi.repository;

import com.waes.vinod.differenceapi.model.DiffDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * repository class for mongodb interactions
 */
@Repository
public interface DifferenceRepository extends MongoRepository<DiffDocument, String> {
}
