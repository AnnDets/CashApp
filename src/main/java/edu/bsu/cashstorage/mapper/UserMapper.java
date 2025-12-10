package edu.bsu.cashstorage.mapper;

import edu.bsu.cashstorage.dto.config.IdDTO;
import edu.bsu.cashstorage.dto.user.PatchUserDTO;
import edu.bsu.cashstorage.dto.user.UserDTO;
import edu.bsu.cashstorage.entity.User;
import edu.bsu.cashstorage.security.dto.AuthRequestDTO;
import edu.bsu.cashstorage.security.dto.RegisterRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {
    UserDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    User toEntity(AuthRequestDTO authRequestDTO);

    @Mapping(target = "id", ignore = true)
    User toEntity(RegisterRequestDTO registerRequestDTO);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toEntity(PatchUserDTO patchUserDTO);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", ignore = true)
    User toEntity(IdDTO dto);

    @Named("userSimpleSet")
    User simpleSet(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    void patchEntity(User updated, @MappingTarget User fromDB);
}
