import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Team {

    private String teamName;
    private String engineSupplier;
    private List<Car> cars = new ArrayList<>();


    public Team(String teamName, String engineSupplier, List<Car> cars) {
        this.teamName = teamName;
        this.engineSupplier = engineSupplier;
        this.cars = cars;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getEngineSupplier() {
        return engineSupplier;
    }

    public List<Car> getCars() {
        return cars;
    }

    public Optional<List<Car>> getCarsOptionally() {
        return Optional.of(cars);
    }


    @Override
    public String toString() {
        return "Team{" +
                "teamName='" + teamName + '\'' +
                ", engineSupplier='" + engineSupplier + '\'' +
                ", cars=" + cars +
                '}';
    }
}
