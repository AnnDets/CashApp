package edu.bsu.cashstorage.repository;

import edu.bsu.cashstorage.entity.Place;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlaceRepository extends ListCrudRepository<Place, UUID> {
    List<Place> findByDescriptionContainingIgnoreCaseAndUsedBy_Id(String description, UUID authorId);
}