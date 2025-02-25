package dev.gymService.configuration;

import dev.gymService.dao.TraineeDAO;
import dev.gymService.service.implementations.TraineeServiceImpl;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.storage.InMemoryStorage;
import dev.gymService.storage.StorageInitializer;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class AppConfig {
    @Bean
    public TraineeService traineeService() {
        return new TraineeServiceImpl(traineeDAO());
    }

    @Bean
    public InMemoryStorage inMemoryStorage() {
        return new InMemoryStorage();
    }

    @Bean
    public TraineeDAO traineeDAO() {
        return new TraineeDAO(inMemoryStorage());
    }

    @Bean
    public StorageInitializer storageInitializer() {
        return new StorageInitializer(inMemoryStorage());
    }
}
