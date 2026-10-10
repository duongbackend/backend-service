package com.duong.backendservice.service.impl;

import com.duong.backendservice.entity.User;
import com.duong.backendservice.exception.AppException;
import com.duong.backendservice.exception.ErrorCode;
import com.duong.backendservice.repository.UserRepository;
import com.duong.backendservice.service.TOTPService;
import dev.samstevens.totp.code.*;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import dev.samstevens.totp.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "TOTP-SERVICE")
public class TOTPServiceImpl implements TOTPService {
    private final UserRepository userRepository;

    @Override
    public String generateQrCode(String userId) throws QrGenerationException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        SecretGenerator secretGenerator = new DefaultSecretGenerator();
        String secret = secretGenerator.generate();

        user.setSecret(secret);
        userRepository.save(user);

        QrData data = new QrData.Builder()
                .label(user.getEmail())
                .secret(secret)
                .issuer("Backend Service")
                .algorithm(HashingAlgorithm.SHA1)
                .digits(6)
                .period(30)
                .build();

        QrGenerator generator = new ZxingPngQrGenerator();
        byte[] imageData = generator.generate(data);

        return Utils.getDataUriForImage(imageData, generator.getImageMimeType());
    }

    @Override
    public boolean verifyCode(String userId, String code) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean isValid = isCodeValid(user.getSecret(), code);
        if(!isValid){
            return false;
        }

        user.setTotpEnabled(true);
        userRepository.save(user);

        log.info("TOTP verification successful for user: {}", userId);
        return false;
    }

    @Override
    public boolean isCodeValid(String secret, String code) {
        if(!StringUtils.hasText(secret)){
            return false;
        }

        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        CodeVerifier codeVerifier = new DefaultCodeVerifier(codeGenerator, timeProvider);

        return codeVerifier.isValidCode(secret, code);
    }
}
