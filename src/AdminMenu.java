import java.util.Scanner;

public class AdminMenu {
    private static final Scanner sc = new Scanner(System.in);
    private final VehicleService vehicleService = new VehicleService();

    public void start() {
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1) List Vehicles");
            System.out.println("2) Add Vehicle");
            System.out.println("3) Update Vehicle");
            System.out.println("4) Delete Vehicle");
            System.out.println("0) Exit");
            System.out.print("Choice: ");
            String ch = sc.nextLine();

            switch (ch) {
                case "1" -> listVehicles();
                case "2" -> addVehicle();
                case "3" -> updateVehicle();
                case "4" -> deleteVehicle();
                case "0" -> { return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void listVehicles() {
        var list = vehicleService.getAllVehicles();
        vehicleService.printVehicles(list);
    }

    private void addVehicle() {
        System.out.print("Type (CAR/MOTORCYCLE/HELICOPTER): ");
        VehicleType type = VehicleType.valueOf(sc.nextLine().trim().toUpperCase());

        System.out.print("Brand: "); String brand = sc.nextLine();
        System.out.print("Model: "); String model = sc.nextLine();
        System.out.print("Value (TL): "); long valueTl = Long.parseLong(sc.nextLine());
        System.out.print("Hourly price: "); long ph = Long.parseLong(sc.nextLine());
        System.out.print("Daily price: "); long pd = Long.parseLong(sc.nextLine());
        System.out.print("Weekly price: "); long pw = Long.parseLong(sc.nextLine());
        System.out.print("Monthly price: "); long pm = Long.parseLong(sc.nextLine());

        Vehicle v = new Vehicle(0, type, brand, model, valueTl, ph, pd, pw, pm, true);
        vehicleService.addVehicle(v);
        System.out.println("Vehicle added ✅ id=" + v.getId());
    }

    private void updateVehicle() {
        System.out.print("Enter vehicle id to update: ");
        int id = Integer.parseInt(sc.nextLine());
        Vehicle v = vehicleService.getVehicleById(id);
        if (v == null) {
            System.out.println("Vehicle not found!");
            return;
        }

        System.out.print("New brand ("+v.getBrand()+"): "); v.setBrand(sc.nextLine());
        System.out.print("New model ("+v.getModel()+"): "); v.setModel(sc.nextLine());
        System.out.print("New value ("+v.getValueTl()+"): "); v.setValueTl(Long.parseLong(sc.nextLine()));

        vehicleService.updateVehicle(v);
        System.out.println("Vehicle updated ✅");
    }

    private void deleteVehicle() {
        System.out.print("Enter vehicle id to delete: ");
        int id = Integer.parseInt(sc.nextLine());
        vehicleService.deleteVehicle(id);
        System.out.println("Vehicle deleted ✅");
    }
}
