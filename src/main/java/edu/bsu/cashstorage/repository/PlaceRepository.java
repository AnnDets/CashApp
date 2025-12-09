package edu.bsu.cashstorage.repository;

import edu.bsu.cashstorage.entity.Place;
import edu.bsu.cashstorage.entity.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface PlaceRepository extends ListCrudRepository<Place, UUID> {
    List<Place> findByAuthorId(UUID authorId);
    List<Place> findByAuthor_IdNotIn(List<UUID> ids);

    Boolean existsByAuthorIdAndDescription(UUID authorId, String description);

    List<Place> findByDescriptionContainingIgnoreCaseAndAuthor_Id(String description, UUID authorId);
    List<Place> findByDescriptionContainingIgnoreCaseAndAuthor_IdNotIn(String description, UUID authorId);
}