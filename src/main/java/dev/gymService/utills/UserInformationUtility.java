package dev.gymService.utills;

import java.util.Random;

public class UserInformationUtility {
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
    private static final int PASSWORD_LENGTH = 10;
    private static final Random random = new Random();

    private static long traineeId = 0;
    private static long trainerId = 0;
    private static long trainingId = 0;

    public static long generateTraineeId(){
        return traineeId++;
    }

    public static long generateTrainerId(){
        return trainerId++;
    }

    public static long generateTrainingId(){
        return trainingId++;
    }
    public String generateUserName(String firstName, String lastName){
        return firstName.concat(".").concat(lastName);
    }

    public String generatePassword(){
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i=0; i<PASSWORD_LENGTH; i++){
            int index = random.nextInt(CHARS.length());
            password.append(CHARS.charAt(index));
        }
        return password.toString();
    }
}
