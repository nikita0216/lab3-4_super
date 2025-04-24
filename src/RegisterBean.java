import model.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class RegisterBean {

    private String name;
    private String password;
    private String email;
    private String role = "user"; // По умолчанию пользователь

    private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public String register() {
        if (name == null || password == null || email == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "All fields are required.", null));
            return null;
        }

        if (isUserExists(name)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username already exists.", null));
            return null;
        }

        return registerUser();
    }

    private boolean isUserExists(String username) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("FROM UserEntity WHERE name = :name", UserEntity.class)
                    .setParameter("name", username)
                    .uniqueResult() != null;
        }
    }

    public String registerUser() {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(role);

        Transaction tx = null;

        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Registration successful!", null));

            return "index.xhtml?faces-redirect=true";

        } catch (Exception e) {
            if (tx != null) tx.rollback();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration failed. Please try again.", null));

            e.printStackTrace(); // важно для логов
            return null;
        }
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
