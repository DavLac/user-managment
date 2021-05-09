package io.davlac.user.managment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.davlac.user.managment.controller.error.InternalServerException;
import io.davlac.user.managment.controller.error.UnauthorizedException;
import io.davlac.user.managment.domain.User;
import io.davlac.user.managment.repository.UserRepository;
import io.davlac.user.managment.service.dto.UserAccessTokenDTO;
import io.davlac.user.managment.service.dto.UserLoginDTO;
import io.davlac.user.managment.service.dto.UserRefreshTokenDTO;
import io.davlac.user.managment.service.dto.UserTokenResponseDTO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Value("${app.user.expiration-time-second}")
    private Long expirationTime;

    @Value("${app.user.refresh-token-time-second}")
    private Long refreshTokenTime;

    private final ObjectMapper mapper = new ObjectMapper();
    private final Base64 base64 = new Base64();

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserTokenResponseDTO login(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByName(userLoginDTO.getName()).orElseThrow(UnauthorizedException::new);

        checkPassword(userLoginDTO.getPassword(), user.getPassword());

        return new UserTokenResponseDTO(
                buildAccessToken(user),
                buildRefreshToken(user)
        );
    }

    public UserTokenResponseDTO refreshToken(String refreshToken) {
        UserRefreshTokenDTO userRefreshTokenDTO = stringToRefreshTokenObject(refreshToken);

        User user = userRepository.findByName(userRefreshTokenDTO.getName()).orElseThrow(UnauthorizedException::new);

        if (!user.getPassword().equals(userRefreshTokenDTO.getPassword())) {
            throw new UnauthorizedException();
        }

        return new UserTokenResponseDTO(
                buildAccessToken(user),
                buildRefreshToken(user)
        );
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
        } catch (Exception e) {
            throw new InternalServerException(String.format("Fail to serialize object = '%s'", objectToConvert.toString()));
        }
    }

    private UserRefreshTokenDTO stringJsonToUserRefreshTokenDTO(String stringObject) {
        try {
            return mapper.readValue(stringObject, UserRefreshTokenDTO.class);
        } catch (Exception e) {
            throw new InternalServerException(String.format("Fail to deserialize string = '%s'", stringObject));
        }
    }

    private UserRefreshTokenDTO stringToRefreshTokenObject(String refreshToken) {
        String stringRefreshTokenDecoded = new String(base64.decode(refreshToken.getBytes()));
        return stringJsonToUserRefreshTokenDTO(stringRefreshTokenDecoded);
    }

    private String buildAccessToken(User user) {
        UserAccessTokenDTO userAccessTokenDTO = new UserAccessTokenDTO(user.getName(), user.getRole(), expirationTime, refreshTokenTime);
        String userAccessTokenDTOStringJson = objectToStringJson(userAccessTokenDTO);
        return new String(base64.encode(userAccessTokenDTOStringJson.getBytes()));
    }

    private String buildRefreshToken(User user) {
        UserRefreshTokenDTO userRefreshTokenDTO = new UserRefreshTokenDTO(user.getName(), user.getPassword());
        String userRefreshTokenDTOStringJson = objectToStringJson(userRefreshTokenDTO);
        return new String(base64.encode(userRefreshTokenDTOStringJson.getBytes()));
    }
}
