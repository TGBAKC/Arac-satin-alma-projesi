import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcRentalRepository {

    public Rental save(Rental r) {
        String sql = """
            INSERT INTO rental(user_id, vehicle_id, start_ts, end_ts, status, deposit_pct, deposit_amt, total_price)
            VALUES (?,?,?,?,?::rental_status,?,?,?)
            RETURNING id
        """;
        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getUserId());
            ps.setInt(2, r.getVehicleId());
            ps.setTimestamp(3, Timestamp.valueOf(r.getStart()));
            ps.setTimestamp(4, Timestamp.valueOf(r.getEnd()));
            ps.setString(5, r.getStatus().name());
            ps.setBigDecimal(6, java.math.BigDecimal.valueOf(r.getDepositAmount() == 0 ? 0 : 10)); // bilgi amaçlı
            ps.setLong(7, r.getDepositAmount());
            ps.setLong(8, r.getTotalPrice());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return r;
                }
                throw new RuntimeException("Rental insert returned no id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("rental save failed: " + e.getMessage(), e);
        }
    }

    public boolean hasOverlapActive(int vehicleId, LocalDateTime start, LocalDateTime end) {
        String sql = """
            SELECT 1 FROM rental
            WHERE vehicle_id = ?
              AND status = 'ACTIVE'
              AND NOT (end_ts <= ? OR start_ts >= ?)
            LIMIT 1
        """;
        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, vehicleId);
            ps.setTimestamp(2, Timestamp.valueOf(start));
            ps.setTimestamp(3, Timestamp.valueOf(end));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("overlap check failed: " + e.getMessage(), e);
        }
    }

    public List<Rental> listByUser(int userId) {
        String sql = "SELECT * FROM rental WHERE user_id=? ORDER BY id DESC";
        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Rental> list = new ArrayList<>();
                while (rs.next()) {
                    Rental r = new Rental(userId);
                    r.setVehicleId(rs.getInt("vehicle_id"));
                    r.setStart(rs.getTimestamp("start_ts").toLocalDateTime());
                    r.setEnd(rs.getTimestamp("end_ts").toLocalDateTime());
                    r.setStatus(RentalStatus.valueOf(rs.getString("status")));
                    r.setDepositAmount(rs.getLong("deposit_amt"));
                    r.setTotalPrice(rs.getLong("total_price"));
                    list.add(r);
                }
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException("listByUser failed: " + e.getMessage(), e);
        }
    }

    public void cancel(long rentalId) { updateStatus(rentalId, "CANCELLED"); }

    public void complete(long rentalId) { updateStatus(rentalId, "COMPLETED"); }

    private void updateStatus(long rentalId, String status) {
        String sql = "UPDATE rental SET status=?::rental_status WHERE id=?";
        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setLong(2, rentalId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("update status failed: " + e.getMessage(), e);
        }
    }
}
