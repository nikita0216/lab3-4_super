import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import model.UserEntity;

@ManagedBean
@RequestScoped
public class LoginBean {
    private String name;
    private String password;
    private SessionFactory sessionFactory;

    public LoginBean() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public String login() {
        try (Session session = sessionFactory.openSession()) {
            Query<UserEntity> query = session.createQuery(
                    "FROM UserEntity WHERE name = :name AND password = :password", UserEntity.class);
            query.setParameter("name", name);
            query.setParameter("password", password);
            UserEntity user = query.uniqueResult();

            if (user != null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", user.getName());
                return "welcome?faces-redirect=true";
            }

        else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new javax.faces.application.FacesMessage("Неверный логин или пароль."));
                return null;
            }
        }
    }

    // Геттеры и сеттеры
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
}
