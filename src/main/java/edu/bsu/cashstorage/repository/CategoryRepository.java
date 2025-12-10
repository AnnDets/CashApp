package edu.bsu.cashstorage.repository;

import edu.bsu.cashstorage.entity.Category;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends ListCrudRepository<Category, UUID> {
    List<Category> findByAuthorId(UUID authorId);
}