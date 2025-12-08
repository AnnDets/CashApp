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
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.boot.context.properties.PropertyMapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CurrencyMapper.class, BankMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AccountMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user.id", source = "userId"),
            @Mapping(target = "bank", expression = "java(accountInputDTO.getBankId() != null ? new Bank().setId(accountInputDTO.getBankId()) : null)"),
            @Mapping(target = "currency.id", source = "accountInputDTO.currencyId")
    })
    Account toEntity(AccountInputDTO accountInputDTO, UUID userId);

    AccountDTO toDTO(Account account);

    SimpleAccountDTO toSimpleDTO(Account account);

    @Mapping(target = "bankIcon", source = "bank.icon")
    ListAccountDTO toListDTO(Account account);

    List<ListAccountDTO> toListDTO(List<Account> accounts);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void patchAccount(Account updated, @MappingTarget Account fromDB);
}
