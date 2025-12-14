package edu.bsu.cashstorage.mapper;

import edu.bsu.cashstorage.dto.account.AccountDTO;
import edu.bsu.cashstorage.dto.account.AccountInputDTO;
import edu.bsu.cashstorage.dto.account.ListAccountDTO;
import edu.bsu.cashstorage.dto.account.SimpleAccountDTO;
import edu.bsu.cashstorage.dto.config.IdDTO;
import edu.bsu.cashstorage.entity.Account;
import edu.bsu.cashstorage.mapper.config.BankMapper;
import edu.bsu.cashstorage.mapper.config.CurrencyMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CurrencyMapper.class, BankMapper.class, CommonMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AccountMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "creditLimit", source = "accountInputDTO.creditLimit", qualifiedByName = "toBigDecimal")
    @Mapping(target = "currentBalance", source = "accountInputDTO.currentBalance", qualifiedByName = "toBigDecimal")
    Account toEntity(AccountInputDTO accountInputDTO, UUID userId);

    @Mapping(target = "creditLimit", source = "creditLimit", qualifiedByName = "fromBigDecimal")
    @Mapping(target = "currentBalance", source = "currentBalance", qualifiedByName = "fromBigDecimal")
    AccountDTO toDTO(Account account);

    SimpleAccountDTO toSimpleDTO(Account account);

    @Mapping(target = "bankIcon", source = "bank.icon")
    @Mapping(target = "creditLimit", source = "creditLimit", qualifiedByName = "fromBigDecimal")
    @Mapping(target = "currentBalance", source = "currentBalance", qualifiedByName = "fromBigDecimal")
    ListAccountDTO toListDTO(Account account);

    List<ListAccountDTO> toListDTO(List<Account> accounts);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "creditLimit", source = "creditLimit", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "bank", source = "bank", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    void patchAccount(Account updated, @MappingTarget Account fromDB);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "savingsAccount", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "includeInTotalBalance", ignore = true)
    @Mapping(target = "defaultAccount", ignore = true)
    @Mapping(target = "currentBalance", ignore = true)
    @Mapping(target = "currency", ignore = true)
    @Mapping(target = "creditLimit", ignore = true)
    @Mapping(target = "cardNumber4", ignore = true)
    @Mapping(target = "cardNumber3", ignore = true)
    @Mapping(target = "cardNumber2", ignore = true)
    @Mapping(target = "cardNumber1", ignore = true)
    @Mapping(target = "bank", ignore = true)
    @Mapping(target = "archiveAccount", ignore = true)
    Account toEntity(IdDTO dto);

    @Named("accountSimpleSet")
    Account simpleSet(Account account);
}
