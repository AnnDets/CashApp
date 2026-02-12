package edu.bsu.cashstorage.validator;

import edu.bsu.cashstorage.entity.Operation;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OperationValidator {

    /**
     * Validates the operation and returns null if valid, or an error message if invalid.
     */
    public String validate(Operation entity) {
        return switch (entity.getOperationType()) {
            case INCOME -> validateIncome(entity);
            case OUTCOME -> validateOutcome(entity);
            case OWN -> validateOwn(entity);
            case TRANSFER -> validateTransfer(entity);
        };
    }

    private String validateTransfer(Operation entity) {
        if (Objects.isNull(entity.getAccountIncome()))
            return "TRANSFER operation must have a destination account (accountIncome).";
        if (Objects.isNull(entity.getAccountOutcome()))
            return "TRANSFER operation must have a source account (accountOutcome).";
        if (Objects.isNull(entity.getIncome()))
            return "TRANSFER operation must have an income amount.";
        if (Objects.isNull(entity.getOutcome()))
            return "TRANSFER operation must have an outcome amount.";
        if (Objects.nonNull(entity.getPlace()))
            return "TRANSFER operation must not have a place.";
        return null;
    }

    private String validateOwn(Operation entity) {
        if (Objects.isNull(entity.getAccountIncome()))
            return "OWN operation must have a destination account (accountIncome).";
        if (Objects.isNull(entity.getPlace()))
            return "OWN operation must have a place.";
        if (Objects.isNull(entity.getIncome()))
            return "OWN operation must have an income amount.";
        if (Objects.nonNull(entity.getOutcome()))
            return "OWN operation must not have an outcome amount.";
        if (Objects.nonNull(entity.getAccountOutcome()))
            return "OWN operation must not have a source account (accountOutcome).";
        return null;
    }

    private String validateOutcome(Operation entity) {
        if (Objects.isNull(entity.getAccountOutcome()))
            return "OUTCOME operation must have a source account (accountOutcome).";
        if (Objects.isNull(entity.getOutcome()))
            return "OUTCOME operation must have an outcome amount.";
        if (Objects.nonNull(entity.getIncome()))
            return "OUTCOME operation must not have an income amount.";
        if (Objects.nonNull(entity.getAccountIncome()))
            return "OUTCOME operation must not have a destination account (accountIncome).";
        return null;
    }

    private String validateIncome(Operation entity) {
        if (Objects.isNull(entity.getAccountIncome()))
            return "INCOME operation must have a destination account (accountIncome).";
        if (Objects.isNull(entity.getIncome()))
            return "INCOME operation must have an income amount.";
        if (Objects.nonNull(entity.getOutcome()))
            return "INCOME operation must not have an outcome amount.";
        if (Objects.nonNull(entity.getAccountOutcome()))
            return "INCOME operation must not have a source account (accountOutcome).";
        return null;
    }
}
