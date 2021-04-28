package io.davlac.user.managment.service.mapper;

import io.davlac.user.managment.domain.User;
import io.davlac.user.managment.service.dto.UserCreationDTO;
import io.davlac.user.managment.service.dto.UserDTO;
import org.apache.commons.codec.digest.DigestUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User entity);

    @Mapping(source = "password", target = "password", qualifiedByName = "hashPassword")
    User toEntity(UserCreationDTO dto);

    @Named("hashPassword")
    static String hashPassword(String password) {
        return DigestUtils.sha256Hex(password);
    }

}
