package edu.bsu.cashstorage.repository;

import edu.bsu.cashstorage.entity.Operation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OperationRepository extends ListCrudRepository<Operation, UUID>, JpaSpecificationExecutor<Operation> {
    List<Operation> findAll(Specification<Operation> spec);

    List<Operation> findByUserId(UUID userId);

    void removeByAccountIncome_IdIsNullAndAccountOutcome_IdIsNullAndUserId(UUID userId);
}