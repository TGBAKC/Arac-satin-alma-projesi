import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class CustomerMenu {
    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final VehicleService vehicleService = new VehicleService();
    private final RentalService rentalService;
    private final User current;

    public CustomerMenu(User current) {
        this.current = current;
        this.rentalService = new RentalService(vehicleService);
    }

    public void start() {
        while (true) {
            System.out.println("\n=== CUSTOMER MENU ===");
            System.out.println("1) Search Vehicles (Filter + Pagination)");
            System.out.println("2) Rent a Vehicle");
            System.out.println("3) View My Rentals");
            System.out.println("0) Exit");
            System.out.print("Choice: ");
            String ch = sc.nextLine();

            switch (ch) {
                case "1" -> doSearch();
                case "2" -> doRent();
                case "3" -> rentalService.listMyRentals(current);
                case "0" -> { return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void doSearch() {
        System.out.print("Type (CAR/MOTORCYCLE/HELICOPTER/empty): ");
        String t = sc.nextLine().trim().toUpperCase();
        VehicleType type = t.isBlank() ? null : VehicleType.valueOf(t);

        System.out.print("Brand (optional): ");
        String brand = sc.nextLine().trim();

        System.out.print("Min daily price (optional): ");
        String minStr = sc.nextLine().trim();
        Long min = minStr.isBlank() ? null : Long.parseLong(minStr);

        System.out.print("Max daily price (optional): ");
        String maxStr = sc.nextLine().trim();
        Long max = maxStr.isBlank() ? null : Long.parseLong(maxStr);

        int page = 1, size = 3;
        while (true) {
            List<Vehicle> list = vehicleService.search(type, brand, min, max, page, size);
            if (list.isEmpty()) {
                System.out.println("No records found.");
                return;
            }
            System.out.println("\nPage " + page + ":");
            vehicleService.printVehicles(list);

            System.out.print("[N]ext / [P]rev / [E]xit: ");
            String nav = sc.nextLine().trim().toUpperCase();
            if (nav.equals("N")) page++;
            else if (nav.equals("P")) page = Math.max(1, page - 1);
            else break;
        }
    }

    private void doRent() {
        try {
            System.out.print("Vehicle ID: ");
            int vehicleId = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Start date (yyyy-MM-dd HH:mm): ");
            LocalDateTime start = LocalDateTime.parse(sc.nextLine().trim(), DF);

            System.out.print("End date (yyyy-MM-dd HH:mm): ");
            LocalDateTime end = LocalDateTime.parse(sc.nextLine().trim(), DF);

            rentalService.startRental(current, vehicleId, start, end);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
