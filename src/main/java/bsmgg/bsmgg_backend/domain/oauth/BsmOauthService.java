package bsmgg.bsmgg_backend.domain.oauth;

import bsmgg.bsmgg_backend.domain.user.domain.User;
import bsmgg.bsmgg_backend.global.error.exception.BSMGGException;
import bsmgg.bsmgg_backend.global.error.exception.ErrorCode;
import leehj050211.bsmOauth.BsmOauth;
import leehj050211.bsmOauth.dto.resource.BsmUserResource;
import leehj050211.bsmOauth.exception.BsmOAuthCodeNotFoundException;
import leehj050211.bsmOauth.exception.BsmOAuthInvalidClientException;
import leehj050211.bsmOauth.exception.BsmOAuthTokenNotFoundException;
import leehj050211.bsmOauth.type.BsmUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BsmOauthService {

    private final BsmOauth bsmOauth;

    public User oauth(String authCode) {
        try {
            String token = bsmOauth.getToken(authCode);
            BsmUserResource resource = bsmOauth.getResource(token);
            return getUser(resource);
        } catch (BsmOAuthCodeNotFoundException e) {
            throw new BSMGGException(ErrorCode.OAUTH_CODE_NOT_FOUND);
        } catch (BsmOAuthTokenNotFoundException e) {
            throw new BSMGGException(ErrorCode.OAUTH_TOKEN_NOT_FOUND);
        } catch (BsmOAuthInvalidClientException e) {
            throw new BSMGGException(ErrorCode.OAUTH_INVALID_CLIENT);
        } catch (IOException e) {
            throw new BSMGGException(ErrorCode.IO_ERROR);
        }
    }

    public User getUser(BsmUserResource resource) {
        User user = User.builder()
                .email(resource.getEmail())
                .code(resource.getUserCode())
                .nickname(resource.getNickname())
                .name(resource.getRole() == BsmUserRole.STUDENT ?
                        resource.getStudent().getName() : resource.getTeacher().getName())
                .role(resource.getRole())
                .build();
        if(resource.getRole() == BsmUserRole.STUDENT) {
            user.setName(resource.getStudent().getName());
            user.setIsGraduate(resource.getStudent().getIsGraduate());
            user.setEnrolledAt(resource.getStudent().getEnrolledAt());
            user.setGrade(resource.getStudent().getGrade());
            user.setClassNo(resource.getStudent().getClassNo());
            user.setStudentNo(resource.getStudent().getStudentNo());
        }
        return user;
    }
}
