package model;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "users") // "user" — допустимо, если в PostgreSQL обернуто в кавычки
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "users.user_id_user_seq", allocationSize = 1)
    @Column(name = "id_user")
    private int idUser;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "role", nullable = false)
    private String role;

    // Getters

    public int getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    // Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // equals & hashCode — норм

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;

        UserEntity that = (UserEntity) o;

        if (idUser != that.idUser) return false;
        if (!name.equals(that.name)) return false;
        if (!password.equals(that.password)) return false;
        return email.equals(that.email);
    }

    @Override
    public int hashCode() {
        int result = idUser;
        result = 31 * result + name.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
