package edu.bsu.cashstorage.service;

import edu.bsu.cashstorage.dto.operation.InputOperationDTO;
import edu.bsu.cashstorage.dto.operation.ListOperationDTO;
import edu.bsu.cashstorage.dto.operation.SimpleOperationDTO;
import edu.bsu.cashstorage.dto.operation.filter.OperationFilterDTO;
import edu.bsu.cashstorage.entity.Account;
import edu.bsu.cashstorage.entity.Operation;
import edu.bsu.cashstorage.filter.SpecificationService;
import edu.bsu.cashstorage.mapper.OperationMapper;
import edu.bsu.cashstorage.repository.AccountRepository;
import edu.bsu.cashstorage.repository.OperationRepository;
import edu.bsu.cashstorage.validator.OperationValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperationService {
    private final OperationRepository operationRepository;
    private final AccountRepository accountRepository;
    private final SpecificationService specificationService;
    private final OperationMapper operationMapper;
    private final OperationValidator operationValidator;

    private void populateSystemFields(Operation entity) {
        Instant now = Instant.now();
        entity.setDeleted(false)
                .setExternalSystemOperationId("not-implemented")
                .setUpdated(now)
                .setCreated(now)
                .setIsProcessed(false);
    }

    private void calculateBalanceForInsert(Operation entity) {
        Account income = null, outcome = null;
        if (Objects.nonNull(entity.getAccountIncome())) {
            income = accountRepository.findById(entity.getAccountIncome().getId()).orElse(null);
        }
        if (Objects.nonNull(entity.getAccountOutcome())) {
            outcome = accountRepository.findById(entity.getAccountOutcome().getId()).orElse(null);
        }

        if (Objects.nonNull(income)) {
            BigDecimal incomeValue = income.getCurrentBalance().add(entity.getIncome());
            income.setCurrentBalance(incomeValue);
            accountRepository.save(income);
        }

        if (Objects.nonNull(outcome)) {
            BigDecimal outcomeValue = outcome.getCurrentBalance().subtract(entity.getOutcome());
            outcome.setCurrentBalance(outcomeValue);
            accountRepository.save(outcome);
        }
    }

    private void calculateBalanceForUpdate(Operation forUpdate, Operation updated) {
        Account income = null, outcome = null;
        if (Objects.nonNull(forUpdate.getAccountIncome())) {
            income = accountRepository.findById(forUpdate.getAccountIncome().getId()).orElse(null);
        }
        if (Objects.nonNull(forUpdate.getAccountOutcome())) {
            outcome = accountRepository.findById(forUpdate.getAccountOutcome().getId()).orElse(null);
        }

        if (Objects.nonNull(income)) {
            BigDecimal incomeValue = income.getCurrentBalance().subtract(forUpdate.getIncome());
            income.setCurrentBalance(incomeValue);
            accountRepository.save(income);
        }

        if (Objects.nonNull(outcome)) {
            BigDecimal outcomeValue = outcome.getCurrentBalance().add(forUpdate.getOutcome());
            outcome.setCurrentBalance(outcomeValue);
            accountRepository.save(outcome);
        }

        calculateBalanceForInsert(updated);
    }

    @Transactional(readOnly = true)
    public List<ListOperationDTO> filterOperations(UUID userId, OperationFilterDTO filter) {
        Specification<Operation> spec = specificationService.createSpecification(userId, filter);
        List<Operation> operations = operationRepository.findAll(spec);
        return operationMapper.toListDTO(operations);
    }

    @Transactional(readOnly = true)
    public List<ListOperationDTO> listOperations(UUID userId) {
        List<Operation> operations = operationRepository.findByUserId(userId);
        return operationMapper.toListDTO(operations);
    }

    @Transactional
    public SimpleOperationDTO createOperation(UUID userId, InputOperationDTO dto) {
        Operation entity = operationMapper.toEntity(dto, userId);

        String validationError = operationValidator.validate(entity);
        if (validationError != null) {
            throw new IllegalArgumentException(validationError);
        }
        populateSystemFields(entity);
        calculateBalanceForInsert(entity);

        entity = operationRepository.save(entity);
        return operationMapper.toSimpleDTO(entity);
    }

    @Transactional
    public void deleteOperation(UUID userId, UUID operationId) {
        operationRepository.findById(operationId).ifPresent(operation -> {
            operation.setDeleted(true)
                    .setUpdated(Instant.now());
            operationRepository.save(operation);
        });
    }

    @Transactional
    public SimpleOperationDTO updateOperation(UUID userId, UUID operationId, InputOperationDTO dto) {
        Operation toUpdate = operationRepository.findById(operationId)
                .orElseThrow(() -> new EntityNotFoundException("Operation with id: " + operationId + " not found!"));

        Operation updated = operationMapper.toEntity(dto, userId);

        String validationError = operationValidator.validate(updated);
        if (validationError != null) {
            throw new IllegalArgumentException(validationError);
        }
        calculateBalanceForUpdate(toUpdate, updated);

        operationMapper.patchEntity(updated, toUpdate);

        toUpdate.setUpdated(Instant.now());

        toUpdate = operationRepository.save(toUpdate);
        return operationMapper.toSimpleDTO(toUpdate);
    }
}
