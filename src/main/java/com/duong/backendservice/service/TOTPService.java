package com.duong.backendservice.service;

import dev.samstevens.totp.exceptions.QrGenerationException;

public interface TOTPService {
    String generateQrCode(String userId) throws QrGenerationException;

    boolean verifyCode(String userId, String code);

    boolean isCodeValid(String secret, String code);
}
