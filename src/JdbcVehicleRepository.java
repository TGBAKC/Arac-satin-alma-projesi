import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcVehicleRepository implements VehicleRepository {

    private Vehicle map(ResultSet rs) throws SQLException {
        return new Vehicle(
                rs.getInt("id"),
                VehicleType.valueOf(rs.getString("type")),
                rs.getString("brand"),
                rs.getString("model"),
                rs.getLong("value_tl"),
                rs.getLong("price_hour"),
                rs.getLong("price_day"),
                rs.getLong("price_week"),
                rs.getLong("price_month"),
                rs.getBoolean("is_active")
        );
    }

    @Override
    public Vehicle findById(int id) {
        String sql = "SELECT * FROM vehicle WHERE id=?";
        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<Vehicle> findAll() {
        String sql = "SELECT * FROM vehicle ORDER BY id";
        try (Connection con = Db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            List<Vehicle> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Vehicle create(Vehicle v) {
        String sql = "INSERT INTO vehicle(type,brand,model,value_tl,price_hour,price_day,price_week,price_month,is_active) " +
                "VALUES (?,?,?,?,?,?,?,?,?) RETURNING id";
        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getType().name());
            ps.setString(2, v.getBrand());
            ps.setString(3, v.getModel());
            ps.setLong(4, v.getValueTl());
            ps.setLong(5, v.getPriceHour());
            ps.setLong(6, v.getPriceDay());
            ps.setLong(7, v.getPriceWeek());
            ps.setLong(8, v.getPriceMonth());
            ps.setBoolean(9, v.isActive());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { v.setId(rs.getInt(1)); return v; }
                throw new RuntimeException("Vehicle insert failed");
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(Vehicle v) {
        String sql = "UPDATE vehicle SET brand=?, model=?, value_tl=?, price_hour=?, price_day=?, price_week=?, price_month=?, is_active=? WHERE id=?";
        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getBrand());
            ps.setString(2, v.getModel());
            ps.setLong(3, v.getValueTl());
            ps.setLong(4, v.getPriceHour());
            ps.setLong(5, v.getPriceDay());
            ps.setLong(6, v.getPriceWeek());
            ps.setLong(7, v.getPriceMonth());
            ps.setBoolean(8, v.isActive());
            ps.setInt(9, v.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM vehicle WHERE id=?";
        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }




    }
    public List<Vehicle> search(VehicleType type, String brand, Long min, Long max, int limit, int offset) {
        StringBuilder sb = new StringBuilder("SELECT * FROM vehicle WHERE is_active=true");
        List<Object> params = new ArrayList<>();

        if (type != null) { sb.append(" AND type=?"); params.add(type.name()); }
        if (brand != null && !brand.isBlank()) { sb.append(" AND LOWER(brand) LIKE ?"); params.add("%"+brand.toLowerCase()+"%"); }
        if (min != null) { sb.append(" AND price_day>=?"); params.add(min); }
        if (max != null) { sb.append(" AND price_day<=?"); params.add(max); }

        sb.append(" ORDER BY id LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sb.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i+1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                List<Vehicle> list = new ArrayList<>();
                while (rs.next()) list.add(map(rs));
                return list;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

}
