package edu.bsu.cashstorage.mapper;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CommonMapper {
    @Named("toBigDecimal")
    default BigDecimal map(String value) {
        return StringUtils.isEmpty(value) ? null : new BigDecimal(value, MathContext.DECIMAL32);
    }

    @Named("fromBigDecimal")
    default String map(BigDecimal bd) {
        if (Objects.isNull(bd)) {
            return null;
        }
        return bd.setScale(2, RoundingMode.DOWN).toPlainString();
    }
}
