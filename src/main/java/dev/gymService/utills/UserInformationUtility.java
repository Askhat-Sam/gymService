package dev.gymService.utills;

import dev.gymService.model.Trainee;
import dev.gymService.storage.InMemoryStorage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
@Component
public class UserInformationUtility {
    private static InMemoryStorage inMemoryStorage;
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
    private static final int PASSWORD_LENGTH = 10;
    private static final Random random = new Random();


    public void setStorageInitializer(InMemoryStorage inMemoryStorage) {
        UserInformationUtility.inMemoryStorage = inMemoryStorage;
    }

    public static String generatePassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARS.length());
            password.append(CHARS.charAt(index));
        }
        return password.toString();
    }
}
