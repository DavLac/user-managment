package io.davlac.user.managment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.davlac.user.managment.controller.error.InternalServerException;
import io.davlac.user.managment.controller.error.UnauthorizedException;
import io.davlac.user.managment.domain.User;
import io.davlac.user.managment.repository.UserRepository;
import io.davlac.user.managment.service.dto.UserLoginDTO;
import io.davlac.user.managment.service.dto.UserTokenDTO;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class AuthService {

    @Value("${app.user.expiration-time-second}")
    private Long expirationTime;

    @Value("${app.user.refresh-token-time-second}")
    private Long refreshTokenTime;

    ObjectMapper mapper = new ObjectMapper();

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String login(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByName(userLoginDTO.getName()).orElseThrow(UnauthorizedException::new);

        checkPassword(userLoginDTO.getPassword(), user.getPassword());

        return buildToken(user);
    }

    private static void checkPassword(String inputPassword, String dbHashedPassword) {
        String hashedPassword = DigestUtils.sha256Hex(inputPassword);

        if (!dbHashedPassword.equals(hashedPassword)) {
            throw new UnauthorizedException();
        }
    }

    private String objectToStringJson(Object objectToConvert) {
        try {
            return mapper.writeValueAsString(objectToConvert);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(String.format("Fail to serialize object = '%s'", objectToConvert.toString()));
        }
    }

    private String buildToken(User user) {
        UserTokenDTO userTokenDTO = new UserTokenDTO(user.getName(), user.getRole(), expirationTime, refreshTokenTime);
        String userTokenStringJson = objectToStringJson(userTokenDTO);
        return Base64.getEncoder().encodeToString(userTokenStringJson.getBytes());
    }
}
