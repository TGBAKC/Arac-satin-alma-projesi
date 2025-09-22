import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;

public class VehicleService {
    private List<Vehicle> vehicles = new ArrayList<>();


    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public VehicleService() {
        // 2 CAR
        vehicles.add(new Vehicle(1, VehicleType.CAR, "Tesla", "Model 3", 1500000,
                500, 5000, 20000, 60000, true));
        vehicles.add(new Vehicle(2, VehicleType.CAR, "BMW", "X5", 2000000,
                700, 7000, 25000, 80000, true));

        // 2 MOTORCYCLE
        vehicles.add(new Vehicle(3, VehicleType.MOTORCYCLE, "Yamaha", "R1", 800000,
                200, 2000, 7000, 20000, true));
        vehicles.add(new Vehicle(4, VehicleType.MOTORCYCLE, "Harley-Davidson", "Street 750", 1200000,
                300, 3000, 10000, 30000, true));

        // 2 HELICOPTER
        vehicles.add(new Vehicle(5, VehicleType.HELICOPTER, "Airbus", "H125", 5000000,
                5000, 40000, 120000, 400000, true));
        vehicles.add(new Vehicle(6, VehicleType.HELICOPTER, "Bell", "206", 3500000,
                4000, 30000, 100000, 300000, true));
    }


    public void listVehicles() {
        for (Vehicle v : vehicles) {
            System.out.println(v.getId() + " - " + v.getType() + " " + v.getBrand() + " " + v.getModel()
                    + " | Günlük: " + v.getPriceDay() + " TL");
        }
    }











}