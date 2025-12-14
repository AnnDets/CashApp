package edu.bsu.cashstorage.mapper.config;

import edu.bsu.cashstorage.dto.config.CurrencyDTO;
import edu.bsu.cashstorage.entity.config.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CurrencyMapper {
    CurrencyDTO toDto(Currency c);

    Currency toEntity(CurrencyDTO dto);

    List<CurrencyDTO> toDto(List<Currency> dto);

    List<Currency> toEntity(List<CurrencyDTO> dto);
}
