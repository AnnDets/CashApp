package edu.bsu.cashstorage.validator;

import edu.bsu.cashstorage.entity.Operation;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OperationValidator {
    public boolean validate(Operation entity) {
        return switch (entity.getOperationType()) {
            case INCOME -> validateIncome(entity);
            case OUTCOME -> validateOutcome(entity);
            case OWN -> validateOwn(entity);
            case TRANSFER -> validateTransfer(entity);
        };
    }

    private boolean validateTransfer(Operation entity) {
        return Objects.nonNull(entity.getAccountIncome())
                && Objects.nonNull(entity.getAccountOutcome())
                && Objects.nonNull(entity.getIncome())
                && Objects.nonNull(entity.getOutcome())
                && Objects.isNull(entity.getPlace());
    }

    private boolean validateOwn(Operation entity) {
        return Objects.nonNull(entity.getAccountIncome())
                && Objects.nonNull(entity.getPlace())
                && Objects.nonNull(entity.getIncome())
                && Objects.isNull(entity.getOutcome())
                && Objects.isNull(entity.getAccountOutcome());
    }

    private boolean validateOutcome(Operation entity) {
        return Objects.nonNull(entity.getAccountOutcome())
                && Objects.nonNull(entity.getOutcome())
                && Objects.isNull(entity.getIncome())
                && Objects.isNull(entity.getAccountIncome());
    }

    private boolean validateIncome(Operation entity) {
        return Objects.nonNull(entity.getAccountIncome())
                && Objects.nonNull(entity.getIncome())
                && Objects.isNull(entity.getOutcome())
                && Objects.isNull(entity.getAccountOutcome());
    }
}
