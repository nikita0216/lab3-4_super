package model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

@WebServlet(name = "Register", urlPatterns = "/Register")
public class RegisterServlet extends HttpServlet {
    private SessionFactory sessionFactory;

    @Override
    public void init() throws ServletException {
        // Initialize SessionFactory
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("pass");
        String email = request.getParameter("mail");

        // Validate input
        String errorMessage = validateInput(name, password, email);
        if (errorMessage != null) {
            // Set error message in request attribute
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else {
            if (registerUser(name, password, email)) {
                response.sendRedirect("index.jsp"); // Redirect to login page after successful registration
            } else {
                // Set error message in request attribute
                request.setAttribute("errorMessage", "Registration failed. Please try again.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle GET requests if necessary
    }

    private boolean registerUser(String name, String password, String email) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            // Create a new UserEntity object
            UserEntity user = new UserEntity();
            user.setName(name);
            user.setPassword(password);
            user.setEmail(email);

            // Save the user to the database
            session.save(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return false;
    }

    private String validateInput(String name, String password, String email) {
        if (name == null || name.trim().isEmpty() || name.contains(" ")) {
            return "Username cannot be empty or contain spaces.";
        }
        if (password == null || password.trim().isEmpty()) {
            return "Password cannot be empty.";
        }
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            return "Invalid email address.";
        }
        return null; // No validation errors
    }

    @Override
    public void destroy() {
        // Close SessionFactory
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}