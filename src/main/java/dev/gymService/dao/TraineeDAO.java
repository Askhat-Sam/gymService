package dev.gymService.dao;

import dev.gymService.model.Trainee;
import dev.gymService.storage.InMemoryStorage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
@Component
public class TraineeDAO implements Dao{

    private InMemoryStorage inMemoryStorage;

    public TraineeDAO(InMemoryStorage inMemoryStorage) {
        this.inMemoryStorage = inMemoryStorage;
    }

    @Override
    public Trainee getById(Long id) {
        //Get first name
        String firstName = (String) inMemoryStorage.getInMemoryStorage().get("trainees").get(id).get(0);

        //Get lastName
        String lastName = (String) inMemoryStorage.getInMemoryStorage().get("trainees").get(id).get(1);

        //Get userName
        String userName = (String) inMemoryStorage.getInMemoryStorage().get("trainees").get(id).get(2);

        //Get password
        String password = (String) inMemoryStorage.getInMemoryStorage().get("trainees").get(id).get(3);

        //Get if Active
        Boolean isActive = Boolean.valueOf(String.valueOf(inMemoryStorage.getInMemoryStorage().get("trainees").get(id).get(4)));

        //Get birth date
        LocalDate birthDate = LocalDate.parse(String.valueOf(inMemoryStorage.getInMemoryStorage().get("trainees").get(id).get(5)).trim());


        //Get adress
        String address = (String) inMemoryStorage.getInMemoryStorage().get("trainees").get(id).get(6);

        return new Trainee(firstName, lastName, userName, password, isActive, birthDate, address, id);
    }

    @Override
    public Object save(Object o) {
        return null;
    }

    @Override
    public void delete(Object o) {

    }
}
