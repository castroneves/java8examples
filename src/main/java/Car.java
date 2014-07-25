public class Car {

    private String driverName;
    private String driverSurname;
    private Integer wins;

    public Car(String driverName, String driverSurname, Integer wins) {
        this.driverName = driverName;
        this.driverSurname = driverSurname;
        this.wins = wins;
    }


    public String getDriverName() {
        return driverName;
    }

    public String getDriverSurname() {
        return driverSurname;
    }

    public Integer getWins() {
        return wins;
    }

    @Override
    public String toString() {
        return driverName + ' '+ driverSurname;
    }
}
