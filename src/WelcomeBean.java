import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import model.UserEntity;
import model.Product;
import model.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

@ManagedBean
@RequestScoped
public class WelcomeBean {

    private String username;
    private boolean admin;
    private List<Product> productList;
    private List<Order> orderList;

    private static SessionFactory sessionFactory;

    static {
        // Инициализируем SessionFactory один раз для всех экземпляров
        try {
            sessionFactory = new Configuration()
                    .configure() // hibernate.cfg.xml
                    .addAnnotatedClass(UserEntity.class)
                    .addAnnotatedClass(Product.class)
                    .addAnnotatedClass(Order.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError("Initial SessionFactory creation failed: " + ex);
        }
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        username = (String) context.getExternalContext().getSessionMap().get("username");

        if (username == null) {
            System.out.println("Нет username в сессии — пользователь не авторизован.");
            return;
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            UserEntity user = session.createQuery(
                            "FROM UserEntity WHERE name = :name", UserEntity.class)
                    .setParameter("name", username)
                    .uniqueResult();

            if (user != null) {
                System.out.println("Роль пользователя: " + user.getRole());

                admin = "admin".equalsIgnoreCase(user.getRole());

                if (admin) {
                    orderList = session.createQuery("FROM Order", Order.class).getResultList();
                } else {
                    productList = session.createQuery("FROM Product", Product.class).getResultList();
                }
            } else {
                System.out.println("Пользователь с именем '" + username + "' не найден.");
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Геттеры
    public String getUsername() {
        return username;
    }

    public boolean isAdmin() {
        return admin;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }
}
