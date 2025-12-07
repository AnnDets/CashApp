package edu.bsu.cashstorage.mapper.config;

import edu.bsu.cashstorage.dto.config.bank.BankDTO;
import edu.bsu.cashstorage.dto.config.bank.SimpleBankDTO;
import edu.bsu.cashstorage.entity.config.Bank;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface BankMapper {
    BankDTO toDTO(Bank bank);

    Bank toEntity(BankDTO dto);

    List<BankDTO> toDTO(List<Bank> bank);

    List<Bank> toEntity(List<BankDTO> dto);

    SimpleBankDTO toSimpleDTO(Bank bank);

    @Mapping(target = "icon", ignore = true)
    @Mapping(target = "country", ignore = true)
    Bank toSimpleEntity(SimpleBankDTO dto);

    List<SimpleBankDTO> toSimpleDTO(List<Bank> bank);

    List<Bank> toSimpleEntity(List<SimpleBankDTO> dto);
}
