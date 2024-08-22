package com.togedog.auth.service;

import com.togedog.auth.jwt.JwtTokenizer;
import com.togedog.redis.tool.RedisTool;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final RedisTool redisTool;
    private final JwtTokenizer jwtTokenizer;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 사용자를 로그아웃 처리하는 메서드
     * <p>
     * 이 메서드는 주어진 사용자명에 해당하는 JWT 토큰을 삭제하여 로그아웃을 처리합니다.
     * 주로 Redis와 같은 저장소에서 토큰을 삭제하는 방식으로 로그아웃이 구현됩니다.
     *
     * @param username 로그아웃할 사용자의 사용자명
     * @return 로그아웃이 성공하면 true, 실패하면 false를 반환
     */
    public boolean logout(String username) {
        return jwtTokenizer.deleteRegisterToken(username);
    }

    public boolean isTokenValid(String userName) {
        return redisTemplate.hasKey(userName);
    }

}