
public class Vehicle {

    private int id;


    private VehicleType type;


    private String brand;


    private String model;


    private long valueTl;


    private long priceHour;
    private long priceDay;
    private long priceWeek;
    private long priceMonth;


    private boolean isActive;


    public Vehicle(int id, VehicleType type, String brand, String model, long valueTl, long priceHour, long priceDay, long priceWeek, long priceMonth, boolean isActive) {
        this.id = id;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.valueTl = valueTl;
        this.priceHour = priceHour;
        this.priceDay = priceDay;
        this.priceWeek = priceWeek;
        this.priceMonth = priceMonth;
        this.isActive = isActive;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getValueTl() {
        return valueTl;
    }

    public void setValueTl(long valueTl) {
        this.valueTl = valueTl;
    }

    public long getPriceDay() {
        return priceDay;
    }

    public void setPriceDay(long priceDay) {
        this.priceDay = priceDay;
    }

    public long getPriceHour() {
        return priceHour;
    }

    public void setPriceHour(long priceHour) {
        this.priceHour = priceHour;
    }

    public long getPriceWeek() {
        return priceWeek;
    }

    public void setPriceWeek(long priceWeek) {
        this.priceWeek = priceWeek;
    }

    public long getPriceMonth() {
        return priceMonth;
    }

    public void setPriceMonth(long priceMonth) {
        this.priceMonth = priceMonth;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}