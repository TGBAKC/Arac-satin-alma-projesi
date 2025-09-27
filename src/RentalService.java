import java.time.Duration;
import java.time.LocalDateTime;

public class RentalService {

    private final JdbcRentalRepository rentalRepo = new JdbcRentalRepository();
    private final VehicleService vehicleService;

    public RentalService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public Rental startRental(User user, int vehicleId, LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null || !start.isBefore(end)) {
            System.out.println("Invalid date range");
            return null;
        }

        Vehicle foundVehicle = vehicleService.getVehicleById(vehicleId);
        if (foundVehicle == null || !foundVehicle.isActive()) {
            System.out.println("Vehicle not found or not active");
            return null;
        }

        if (rentalRepo.hasOverlapActive(vehicleId, start, end)) {
            System.out.println("This vehicle is not available for the selected dates");
            return null;
        }

        long hours = Duration.between(start, end).toHours();
        long days = hours / 24;

        if (user.getCustomerType() == CustomerType.CORPORATE && days < 30) {
            System.out.println("Corporate accounts must rent for at least 30 days");
            return null;
        }

        long deposit = 0;
        if (foundVehicle.getValueTl() > 2_000_000L) {
            if (user.getAge() < 30) {
                System.out.println("For this vehicle, renter must be ≥ 30 years old");
                return null;
            }
            deposit = Math.round(foundVehicle.getValueTl() * 0.10);
        }

        long totalHours = Math.max(1, Duration.between(start, end).toHours());
        long allDays = totalHours / 24;
        long hoursRemainder = totalHours % 24;
        long months = allDays / 30;
        long daysAfterMonths = allDays % 30;
        long weeks = daysAfterMonths / 7;
        long daysAfterWeeks = daysAfterMonths % 7;

        long totalPrice = 0;
        totalPrice += months * foundVehicle.getPriceMonth();
        totalPrice += weeks  * foundVehicle.getPriceWeek();
        totalPrice += daysAfterWeeks * foundVehicle.getPriceDay();
        totalPrice += hoursRemainder * foundVehicle.getPriceHour();

        Rental newRental = new Rental(user.getId());
        newRental.setVehicleId(vehicleId);
        newRental.setStart(start);
        newRental.setEnd(end);
        newRental.setTotalPrice(totalPrice);
        newRental.setDepositAmount(deposit);
        newRental.setStatus(RentalStatus.ACTIVE);

        rentalRepo.save(newRental);
        System.out.println("Rental created. Total: " + totalPrice + " TL, Deposit: " + deposit + " TL");
        return newRental;
    }

    public void listMyRentals(User user) {
        var list = rentalRepo.listByUser(user.getId());
        if (list.isEmpty()) {
            System.out.println("You have no rentals.");
            return;
        }
        for (Rental r : list) {
            System.out.println("VehicleID=" + r.getVehicleId()
                    + " | " + r.getStart() + " → " + r.getEnd()
                    + " | Status=" + r.getStatus()
                    + " | Total=" + r.getTotalPrice()
                    + " | Deposit=" + r.getDepositAmount());
        }
    }
}
