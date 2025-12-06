package edu.bsu.cashstorage.repository;

import edu.bsu.cashstorage.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends CrudRepository<Category, UUID> {
    List<Category> findByAuthorId(UUID authorId);

    List<Category> findByAuthorIdAndForIncomeTrue(UUID authorId);

    List<Category> findByAuthorIdAndForOutcomeTrue(UUID authorId);

    List<Category> findByAuthorIdAndMandatoryOutcomeTrue(UUID authorId);
}