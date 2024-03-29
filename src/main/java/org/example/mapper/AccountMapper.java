package org.example.mapper;

import org.example.dto.RegisterRequest;
import org.example.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
      componentModel = MappingConstants.ComponentModel.SPRING
)
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    Account mapRegisterDtoToAccount(RegisterRequest registerRequest);
}
