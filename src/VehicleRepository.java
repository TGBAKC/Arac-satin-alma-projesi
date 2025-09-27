import java.util.List;

public interface VehicleRepository {
    Vehicle findById(int id);
    List<Vehicle> findAll();
    Vehicle create(Vehicle v);
    void update(Vehicle v);
    void delete(int id);


    List<Vehicle> search(VehicleType type, String brand, Long min, Long max, int limit, int offset);
}
