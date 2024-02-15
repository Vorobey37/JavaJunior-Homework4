package org.example;

import org.example.models.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        //Создаем таблицу при помощи JDBC
        createTableOnJDBC();

        //Далее работа с Hibernate
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Course course = Course.create();
            session.save(course);
            System.out.println("Save in DB");
            Course retrivedCourse = session.get(Course.class, course.getId());

            retrivedCourse.updateTitle();
            retrivedCourse.updateDuration();
            session.update(retrivedCourse);

            session.delete(retrivedCourse);

            session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }



    }

    private static Connection getConnectionOnJDBC() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "1234";

        return DriverManager.getConnection(url, user, password);
    }

    private static void useDbOnJDBC(Connection connection) throws SQLException {
        String useDatabaseSQL =  "USE schoolDB;";

        try(PreparedStatement statement = connection.prepareStatement(useDatabaseSQL))
        {
            statement.execute();
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS courses (id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(45), duration INT);";
        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.execute();
        }
    }

    private static void createTableOnJDBC() throws SQLException {
        Connection curentConnection = getConnectionOnJDBC();
        useDbOnJDBC(curentConnection);
        createTable(curentConnection);
        curentConnection.close();
        System.out.println("Sucсess");
    }

}