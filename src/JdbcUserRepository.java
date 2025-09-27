import java.sql.*;

public class JdbcUserRepository implements UserRepository {

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT id, full_name, age, email, password_hash, role FROM app_user WHERE email = ?";
        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new User(
                        rs.getString("full_name"),
                        rs.getInt("age"),
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getString("role"),
                        CustomerType.INDIVIDUAL // şimdilik sabit
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("findByEmail failed: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM app_user WHERE email = ?";
        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("existsByEmail failed: " + e.getMessage(), e);
        }
    }

    @Override
    public User create(User u) {
        String sql = "INSERT INTO app_user (email,password_hash,full_name,role,age) " +
                "VALUES (?,?,?,?::user_role,?) RETURNING id";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getEmail());
            ps.setString(2, u.getPasswordHash());
            ps.setString(3, u.getName());
            ps.setString(4, u.getRole());
            ps.setInt(5,  u.getAge());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u.setId(rs.getInt(1));
                    return u;
                }
                throw new RuntimeException("User insert returned no id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("create user failed: " + e.getMessage(), e);
        }
    }
}
