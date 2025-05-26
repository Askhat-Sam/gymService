package dev.gymService.model;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "trainee")
@PrimaryKeyJoinColumn(name = "user_id")
public class Trainee extends User implements UserDetails {
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "address")
    private String address;
    @Column(name = "user_id", insertable = false, updatable = false, nullable = false)
    private Long userId;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "trainee", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Training> trainings;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "trainee_trainer",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id")
    )
    private List<Trainer> trainers;

    public Trainee() {
    }

    public Trainee(LocalDate dateOfBirth, String address, Long userId, List<Training> trainings, List<Trainer> trainers) {
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.userId = userId;
        this.trainings = trainings;
        this.trainers = trainers;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }

    public List<Trainer> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<Trainer> trainers) {
        this.trainers = trainers;
    }

    public Role getRole() {
        return super.getRole();
    }

    public void setRole(Role role) {
        super.setRole(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRole().name()));
    }

    @Override
    public String getUsername() {
        return getUserName();
    }

    @Override
    public String toString() {
        return "Trainee{" +
                "username=" + getUsername() +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", userId=" + userId +
                ", password=" + getPassword() +
                '}';
    }
}
