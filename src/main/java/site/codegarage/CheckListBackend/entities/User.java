package site.codegarage.CheckListBackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    public User(Role role, String name, String lastname, String username, String email, String password) {
        this.role = role;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userID;

    private String name;
    private String lastname;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
