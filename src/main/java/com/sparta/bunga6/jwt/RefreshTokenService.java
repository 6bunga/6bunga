package com.sparta.bunga6.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void save(String username, String refreshToken) {
        RefreshToken existToken = refreshTokenRepository.findByUsername(username).orElse(null);
        if (existToken == null) {
            refreshTokenRepository.save(new RefreshToken(username, refreshToken));
        } else {
            existToken.update(refreshToken);
        }
//        refreshTokenRepository.save(new RefreshToken(username, refreshToken));
    }

}
