package dev.gymService.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="trainee")
@PrimaryKeyJoinColumn(name = "id")
public class Trainee extends User{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "address")
    private String address;

    @Column(name = "user_id")
    private Long userId;



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
        return "Trainee{" +
                "id=" + id +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", userId=" + userId + '\'' +
                ", password='" + getPassword() + '\'' +
//                ", user=" + user +
                '}';
    }
}
