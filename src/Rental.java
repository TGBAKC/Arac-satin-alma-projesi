// Rental.java
import java.time.LocalDateTime;

public class Rental {
    private long id;               // <-- eklendi
    private int userId;
    private int vehicleId;
    private LocalDateTime start;
    private LocalDateTime end;
    private long totalPrice;
    private long depositAmount;
    private RentalStatus status;

    public Rental() { }            // <-- boş kurucu

    public Rental(int userId) { this.userId = userId; }

    // --- getters & setters ---
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getVehicleId() { return vehicleId; }
    public void setVehicleId(int vehicleId) { this.vehicleId = vehicleId; }
    public LocalDateTime getStart() { return start; }
    public void setStart(LocalDateTime start) { this.start = start; }
    public LocalDateTime getEnd() { return end; }
    public void setEnd(LocalDateTime end) { this.end = end; }
    public long getTotalPrice() { return totalPrice; }
    public void setTotalPrice(long totalPrice) { this.totalPrice = totalPrice; }
    public long getDepositAmount() { return depositAmount; }
    public void setDepositAmount(long depositAmount) { this.depositAmount = depositAmount; }
    public RentalStatus getStatus() { return status; }
    public void setStatus(RentalStatus status) { this.status = status; }
}
