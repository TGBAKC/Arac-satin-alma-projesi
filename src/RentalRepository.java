import java.util.ArrayList;
import java.util.List;

public class RentalRepository {

    List<Rental> rentals = new ArrayList<>();





    public Rental save(Rental rental){

  rentals.add(rental);

        return rental;
    }


    public List<Rental> listByUser(int userId) {
        List<Rental> result = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.getUserId() == userId) {
                result.add(rental);
            }
        }
        return result;
    }


    public List<Rental> listByVehicle(int vehicleId){
        List<Rental> result = new ArrayList<>();
        for (Rental r : rentals) {
            if (r.getVehicleId() == vehicleId) {
                result.add(r);
            }
        }
        return result;
    }













}
