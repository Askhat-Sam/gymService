package dev.gymService.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="trainer")
@PrimaryKeyJoinColumn(name = "id")
public class Trainer extends User {
    private Long id;
    @Column(name = "specialization")
    private Long specialization;
    @Column(name = "user_id")
    private Long userId;
    @ManyToMany(mappedBy = "trainers", fetch = FetchType.EAGER)
    private List<Trainee> trainees;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trainer", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Training> trainings;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true, nullable = false)
    private User user;
    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            this.userId = user.getId();  // Ensure userId is correctly set
        }
    }
    @Override
    public String toString() {
        return "Trainer{" +
                " id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", userId='" + specialization + '\'' +
                ", userName='" + super.getUserName() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                '}';
    }
}
