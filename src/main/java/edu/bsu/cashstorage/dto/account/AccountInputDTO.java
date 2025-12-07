package edu.bsu.cashstorage.dto.account;

import edu.bsu.cashstorage.entity.enums.AccountType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class AccountInputDTO {
    private String name;
    private AccountType type;
    private String currencyId;
    private String creditLimit;
    private String currentBalance;
    private Boolean includeInTotalBalance;
    private Boolean defaultAccount;
    private UUID bankId;
    private String cardNumber1;
    private String cardNumber2;
    private String cardNumber3;
    private String cardNumber4;
    private Boolean savingsAccount;
    private Boolean archiveAccount;
}
