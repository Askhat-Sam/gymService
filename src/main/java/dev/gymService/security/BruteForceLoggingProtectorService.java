package dev.gymService.security;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BruteForceLoggingProtectorService {

    private static final int MAX_ATTEMPTS = 3;
    private static final long BLOCK_TIME_MINUTES = 5;

    private static class LoginAttempt {
        int attempts;
        LocalDateTime lastFailed;
        LocalDateTime blockedUntil;
    }

    private final Map<String, LoginAttempt> attemptsMap = new ConcurrentHashMap<>();

    public void loginFailed(String username) {
        LoginAttempt attempt = attemptsMap.computeIfAbsent(username, k -> new LoginAttempt());
        attempt.attempts++;
        attempt.lastFailed = LocalDateTime.now();

        if (attempt.attempts >= MAX_ATTEMPTS) {
            attempt.blockedUntil = LocalDateTime.now().plusMinutes(BLOCK_TIME_MINUTES);
            System.out.println("User " + username + " is temporarily blocked until " + attempt.blockedUntil);
        }
    }

    public void loginSucceeded(String username) {
        attemptsMap.remove(username);
    }

    public boolean isBlocked(String username) {
        LoginAttempt attempt = attemptsMap.get(username);
        if (attempt == null || attempt.blockedUntil == null) {
            return false;
        }

        if (LocalDateTime.now().isAfter(attempt.blockedUntil)) {
            attemptsMap.remove(username);
            return false;
        }

        return true;
    }
}