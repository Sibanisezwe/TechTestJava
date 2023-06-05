package co.za.anycompany.datalayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import co.za.anycompany.model.Customer;

@Repository
public class CustomerRepository {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public static Customer load(int customerId) {
        Connection connection = getDBConnection();
        PreparedStatement prpstmt = null;
        ResultSet resultSet = null;
        Customer customer = new Customer();
        try {
            prpstmt = connection.prepareStatement("select * from CUSTOMER where customerId = ?");
            prpstmt.setInt(1, customerId);
            resultSet = prpstmt.executeQuery();
            while (resultSet.next()) {
                customer.setName(resultSet.getString("NAME"));
                customer.setCountry(resultSet.getString("COUNTRY"));
                customer.setDateOfBirth(resultSet.getDate("DATE_OF_BIRTH"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (prpstmt != null)
                    prpstmt.close();
                if (resultSet != null)
                    resultSet.close();
                if (connection != null)
                	connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return customer;
    }


    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

}