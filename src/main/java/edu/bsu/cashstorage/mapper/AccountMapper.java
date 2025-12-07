package edu.bsu.cashstorage.mapper;

import edu.bsu.cashstorage.dto.account.AccountDTO;
import edu.bsu.cashstorage.dto.account.AccountInputDTO;
import edu.bsu.cashstorage.dto.account.ListAccountDTO;
import edu.bsu.cashstorage.dto.account.SimpleAccountDTO;
import edu.bsu.cashstorage.entity.Account;
import edu.bsu.cashstorage.mapper.config.BankMapper;
import edu.bsu.cashstorage.mapper.config.CurrencyMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CurrencyMapper.class, BankMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AccountMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user.id", source = "userId"),
            @Mapping(target = "bank.id", source = "accountInputDTO.bankId"),
            @Mapping(target = "currency.id", source = "accountInputDTO.currencyId")
    })
    Account toEntity(AccountInputDTO accountInputDTO, UUID userId);

    AccountDTO toDTO(Account account);

    SimpleAccountDTO toSimpleDTO(Account account);

    @Mapping(target = "bankIcon", source = "bank.icon")
    ListAccountDTO toListDTO(Account account);

    List<ListAccountDTO> toListDTO(List<Account> accounts);
}
