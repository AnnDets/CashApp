package edu.bsu.cashstorage.dto.account;

import edu.bsu.cashstorage.dto.config.CurrencyDTO;
import edu.bsu.cashstorage.entity.enums.AccountType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class ListAccountDTO {
    private UUID id;
    private String name;
    private AccountType type;
    private CurrencyDTO currency;
    private String creditLimit;
    private String currentBalance;
    private String bankIcon;
    private Boolean savingsAccount;
    private Boolean archiveAccount;
}
