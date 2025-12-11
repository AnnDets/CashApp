package edu.bsu.cashstorage.service;

import edu.bsu.cashstorage.dto.operation.InputOperationDTO;
import edu.bsu.cashstorage.dto.operation.ListOperationDTO;
import edu.bsu.cashstorage.dto.operation.SimpleOperationDTO;
import edu.bsu.cashstorage.dto.operation.filter.OperationFilterDTO;
import edu.bsu.cashstorage.entity.Operation;
import edu.bsu.cashstorage.filter.SpecificationService;
import edu.bsu.cashstorage.mapper.OperationMapper;
import edu.bsu.cashstorage.repository.OperationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperationService {
    private final OperationRepository operationRepository;
    private final SpecificationService specificationService;
    private final OperationMapper operationMapper;

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
        entity = operationRepository.save(entity);
        return operationMapper.toSimpleDTO(entity);
    }

    @Transactional
    public void deleteOperation(UUID userId, UUID operationId) {
        operationRepository.deleteById(operationId);
    }

    @Transactional
    public SimpleOperationDTO updateOperation(UUID userId, UUID operationId, InputOperationDTO dto) {
        Operation operation = operationRepository.findById(operationId)
                .orElseThrow(() -> new EntityNotFoundException("Operation with id: " + operationId + " not found!"));

        operationMapper.patchEntity(operationMapper.toEntity(dto, userId), operation);

        operation = operationRepository.save(operation);
        return operationMapper.toSimpleDTO(operation);
    }
}
