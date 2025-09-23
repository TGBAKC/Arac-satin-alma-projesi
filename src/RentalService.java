import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class RentalService {

    private RentalRepository rentalRepository;
    private VehicleService vehicleService;

    public RentalService(RentalRepository rentalRepository, VehicleService vehicleService) {
        this.rentalRepository = rentalRepository;
        this.vehicleService = vehicleService;
    }

    public VehicleService getVehicleService() {
        return vehicleService;
    }

    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public RentalRepository getRentalRepository() {
        return rentalRepository;
    }

    public void setRentalRepository(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public Rental startRental(User user, int vehicleId, LocalDateTime start, LocalDateTime end) {
        // 0) temel tarih kontrolü
        if (start == null || end == null || !start.isBefore(end)) {
            System.out.println("Geçersiz tarih aralığı");
            return null;
        }

        // 1) araç bul
        Vehicle foundVehicle = null;
        for (Vehicle vehicle : vehicleService.getVehicles()) {
            if (vehicle.getId() == vehicleId) {
                foundVehicle = vehicle;
                break;
            }
        }
        if (foundVehicle == null) {
            System.out.println("Araç bulunamadı");
            return null;
        }
        if (!foundVehicle.isActive()) {
            System.out.println("Araç aktif değil");
            return null;
        }

        // 2) çakışma kontrolü (yalnızca ACTIVE kiralamalar)
        List<Rental> rentalsForVehicle = rentalRepository.listByVehicle(vehicleId);
        for (Rental rental : rentalsForVehicle) {
            if (rental.getStatus() == RentalStatus.ACTIVE) {
                boolean overlaps = start.isBefore(rental.getEnd()) && rental.getStart().isBefore(end);
                if (overlaps) {
                    System.out.println("Bu araç seçilen tarihlerde kiralanmış");
                    return null;
                }
            }
        }

        // 3) iş kuralları
        long hours = Duration.between(start, end).toHours();
        long days = hours / 24;

        // 3.a kurumsal >= 30 gün
        if (user.getCustomerType() == CustomerType.CORPORATE && days < 30) {
            System.out.println("Kurumsal hesaplar en az 30 gün kiralayabilir");
            return null;
        }

        // 3.b değer > 2.000.000 TL ise: yaş >= 30 ve %10 depozito
        long deposit = 0;
        if (foundVehicle.getValueTl() > 2_000_000L) {
            if (user.getAge() < 30) {
                System.out.println("Bu araç için yaş ≥ 30 olmalı");
                return null;
            }
            deposit = Math.round(foundVehicle.getValueTl() * 0.10);
        }

        // 4) ücret hesaplama (Ay→Hafta→Gün→Saat)
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

        // 5) kayıt oluştur, kaydet, döndür
        // Not: Rental'da bu alanlar/metotlar olmalı: setVehicleId, setStart, setEnd,
        // setTotalPrice, setDepositAmount, setStatus; ayrıca RentalStatus enum.
        Rental newRental = new Rental(user.getId()); // veya boş kurucu varsa new Rental()
        newRental.setVehicleId(vehicleId);
        newRental.setStart(start);
        newRental.setEnd(end);
        newRental.setTotalPrice(totalPrice);
        newRental.setDepositAmount(deposit);
        newRental.setStatus(RentalStatus.ACTIVE);

        Rental saved = rentalRepository.save(newRental);
        System.out.println("Kiralama oluşturuldu. Toplam: " + totalPrice + " TL, Depozito: " + deposit + " TL");
        return saved;
    }


}
