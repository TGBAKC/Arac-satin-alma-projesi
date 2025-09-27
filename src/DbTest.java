import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbTest {
    public static void main(String[] args) {
        try (Connection con = Db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM vehicle")) {

            rs.next();
            System.out.println("Vehicle count = " + rs.getInt(1)); // 6 beklenir
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
