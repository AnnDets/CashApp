package edu.bsu.cashstorage.entity.enums;

import lombok.Getter;

import lombok.Getter;

@Getter
public enum AccountType {
    CASH(0, "Cash"),
    CARD(1, "Card"),
    BANK_ACCOUNT(2, "Bank Account"),
    CREDIT(3, "Credit"),
    DEPOSIT(4, "Deposit");

    private final Integer value;
    private final String displayName;

    AccountType(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }
}
