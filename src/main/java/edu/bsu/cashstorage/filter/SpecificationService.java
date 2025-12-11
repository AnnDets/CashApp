package edu.bsu.cashstorage.filter;

import edu.bsu.cashstorage.dto.operation.filter.CategoryFilter;
import edu.bsu.cashstorage.dto.operation.filter.DateRangeFilter;
import edu.bsu.cashstorage.dto.operation.filter.OperationFilterDTO;
import edu.bsu.cashstorage.entity.Operation;
import edu.bsu.cashstorage.entity.enums.OperationType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecificationService {
    private Specification<Operation> addDateRangeFilter(Specification<Operation> spec, DateRangeFilter dateRangeFilter) {
        if (Objects.isNull(dateRangeFilter)) {
            return spec;
        }

        if (Objects.nonNull(dateRangeFilter.getFrom())) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("operationDate"), dateRangeFilter.getFrom()));
        }

        if (Objects.nonNull(dateRangeFilter.getTo())) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("operationDate"), dateRangeFilter.getTo()));
        }

        return spec;
    }

    private Specification<Operation> addAccountsRangeFilter(Specification<Operation> spec, List<UUID> accountIds) {
        if (CollectionUtils.isEmpty(accountIds)) {
            return spec;
        }

        return spec.and((root, query, cb) ->
                cb.or(
                        root.get("accountOutcome").get("id").in(accountIds),
                        root.get("accountIncome").get("id").in(accountIds))
        );
    }

    private Specification<Operation> addCategoryFilter(Specification<Operation> spec, CategoryFilter categoryFilter) {
        if (Objects.isNull(categoryFilter) || Objects.isNull(categoryFilter.getInclude()) || CollectionUtils.isEmpty(categoryFilter.getCategoryIds())) {
            return spec;
        }

        if (categoryFilter.getInclude()) {
            return spec.and((root, query, cb) ->
                    root.get("category").get("id").in(categoryFilter.getCategoryIds()));
        } else {
            return spec.and((root, query, cb) ->
                    cb.not(root.get("category").get("id").in(categoryFilter.getCategoryIds())));
        }
    }

    private Specification<Operation> addNotProcessedFilter(Specification<Operation> spec, Boolean notProcessed) {
        if (Objects.isNull(notProcessed)) {
            return spec;
        }

        return spec.and((root, query, cb) ->
                cb.equal(root.get("isProcessed"), !notProcessed));
    }

    private Specification<Operation> addOperationTypesFilter(Specification<Operation> spec, List<OperationType> operationTypes) {
        if (CollectionUtils.isEmpty(operationTypes)) {
            return spec;
        }

        return spec.and((root, query, criteriaBuilder) ->
                root.get("operationType").in(operationTypes));
    }

    public Specification<Operation> createSpecification(UUID userId, OperationFilterDTO filter) {
        Specification<Operation> spec = (root, query, cb) ->
                cb.equal(root.get("user").get("id"), userId);

        spec = addDateRangeFilter(spec, filter.getDateRange());
        spec = addAccountsRangeFilter(spec, filter.getAccountIds());
        spec = addCategoryFilter(spec, filter.getCategoryFilter());
        spec = addNotProcessedFilter(spec, filter.getNotProcessed());
        spec = addOperationTypesFilter(spec, filter.getOperationTypes());


        return spec;
    }
}
