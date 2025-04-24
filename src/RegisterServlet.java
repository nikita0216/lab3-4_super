import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.UserEntity;

@WebServlet(name = "Register", urlPatterns = "/Register")
public class RegisterServlet extends HttpServlet {
    private static final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");
        String email = request.getParameter("email");

        UserEntity user = new UserEntity();
        user.setName(name);
        user.setPassword(pass);
        user.setEmail(email);

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(user);

            session.getTransaction().commit();
            response.sendRedirect(""); // переход на логин после регистрации
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Ошибка при регистрации");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("register.jsp");
    }
}
