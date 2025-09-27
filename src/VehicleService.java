import java.util.List;

public class VehicleService {
    private final VehicleRepository repo = new JdbcVehicleRepository();

    public List<Vehicle> getAllVehicles() { return repo.findAll(); }

    public Vehicle getVehicleById(int id) { return repo.findById(id); }

    public Vehicle addVehicle(Vehicle v) { return repo.create(v); }

    public void updateVehicle(Vehicle v) { repo.update(v); }

    public void deleteVehicle(int id) { repo.delete(id); }

    public void printVehicles(List<Vehicle> vehicles) {
        for (Vehicle v : vehicles) {
            System.out.println(
                    v.getId() + " | " +
                            v.getType() + " | " +
                            v.getBrand() + " " + v.getModel() +
                            " | Daily: " + v.getPriceDay() +
                            " | Active: " + v.isActive()
            );
        }
    }

    public List<Vehicle> search(VehicleType type, String brand, Long min, Long max, int page, int size) {
        int offset = (Math.max(1, page) - 1) * size;
        return repo.search(type, brand, min, max, size, offset);
    }
}
