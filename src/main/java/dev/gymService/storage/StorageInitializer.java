package dev.gymService.storage;

import jakarta.annotation.PostConstruct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

public class StorageInitializer {
    private final InMemoryStorage storage;

    public StorageInitializer(InMemoryStorage storage) {
        this.storage = storage;
    }

    @PostConstruct
    public void initialize() {
        Properties prop = new Properties();
        try (InputStream input = Files.newInputStream(Paths.get("src/main/resources/application.properties"))) {
            prop.load(input);
            String storageFile = prop.getProperty("storage.file.path");
            BufferedReader reader = new BufferedReader(new FileReader(storageFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(",");
                if (elements[0].equals("trainees")) {
                    storage.add("trainees", Long.parseLong(elements[1].trim()), Arrays.asList(Arrays.copyOfRange(elements, 2, elements.length)));
                }
            }
            System.out.println(storage.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
