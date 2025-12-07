package edu.bsu.cashstorage.mapper;

import edu.bsu.cashstorage.dto.user.UserDTO;
import edu.bsu.cashstorage.entity.User;
import edu.bsu.cashstorage.security.dto.AuthRequestDTO;
import edu.bsu.cashstorage.security.dto.RegisterRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {
    UserDTO toDTO(User user);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "username", ignore = true)
    })
    User toEntity(AuthRequestDTO authRequestDTO);

    @Mapping(target = "id", ignore = true)
    User toEntity(RegisterRequestDTO registerRequestDTO);
}
