import java.sql.Connection;
import java.sql.DriverManager;

public class Db {
    private static final String URL  = "jdbc:postgresql://localhost:5432/rentals";
    private static final String USER = "postgres";
    private static final String PASS = "Yeni_Sifre123"; // kendi şifren

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            throw new RuntimeException("DB connection failed: " + e.getMessage(), e);
        }
    }
}
