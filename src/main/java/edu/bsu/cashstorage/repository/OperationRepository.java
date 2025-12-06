package edu.bsu.cashstorage.repository;

import edu.bsu.cashstorage.entity.Operation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OperationRepository extends CrudRepository<Operation, UUID> {
    List<Operation> findByAccountOutcomeId(UUID accountId);

    List<Operation> findByAccountIncomeId(UUID accountId);

    List<Operation> findByAccountOutcomeIdAndDeletedFalse(UUID accountId);

    List<Operation> findByAccountIncomeIdAndDeletedFalse(UUID accountId);

    List<Operation> findByCategoryId(UUID categoryId);

    List<Operation> findByCategoryIdAndDeletedFalse(UUID categoryId);

    List<Operation> findByPlaceId(UUID placeId);

    List<Operation> findByOperationDateBetween(ZonedDateTime startDate, ZonedDateTime endDate);

    List<Operation> findByIsProcessedFalse();
}