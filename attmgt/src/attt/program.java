package attt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class program {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/sample";
        String user = "root";
        String password = "Dhar@123";

        try {
            Connection c = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = c.prepareStatement("INSERT INTO details (id, name) VALUES (?, ?)");

            ps.setInt(1, 1);
            ps.setString(2, "kokila");
            ps.executeUpdate(); // use executeUpdate for INSERT, UPDATE, DELETE
            System.out.println("success");

            ps.close(); // Always close the PreparedStatement and Connection
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
