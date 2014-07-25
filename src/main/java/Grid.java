import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

public class Grid {

    private List<Team> teams;

    public Grid(List<Team> teams) {
        this.teams = teams;
    }

    public List<Team> performExternalLogicOld(Predicate<Team> func) {
        List<Team> result = new ArrayList<>();
        for(Team t : teams) {
            if(func.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    public List<Team> performExternalLogic(Predicate<Team> func) {
        return teams.parallelStream().filter(func).collect(toList());
    }

 //------------------------------------------------------------------------------------------------------


    public List<Car> listAllCarsOld(){
        List<Car> result = new ArrayList<>();
        for(Team t : teams) {
            for(Car c : t.getCars()) {
                result.add(c);
            }
        }
        return result;
    }

    public List<Car> listAllCarsSteams() {
        return teams.stream()
                .flatMap(t -> t.getCars().stream()).collect(toList());
    }

 //------------------------------------------------------------------------------------------------------

    public List<Car> carsForTeamOld(String teamName) {
        List<Car> result = new ArrayList<>();
        for(Team t : teams) {
            if(t.getTeamName().equalsIgnoreCase(teamName)){
                for(Car c : t.getCars()) {
                    result.add(c);
                }
            }
        }
        return result;
    }

    public List<Car> carsForTeam(String teamName) {
        return teams.stream().filter(t -> t.getTeamName().equalsIgnoreCase(teamName))
                .flatMap(t -> t.getCars().stream())
                .collect(toList());

    }
    //------------------------------------------------------------------------------------------------------

    public Integer winsForGivenTeamOld(String teamName) {
        int total = 1;
        for(Team t : teams) {
            if(t.getTeamName().equalsIgnoreCase(teamName)) {
                for(Car c : t.getCars()) {
                    total += c.getWins();
                }
            }
        }
        return total;
    }

    public Integer winsForGivenTeam(String teamName) {
        return teams.parallelStream().filter(t -> t.getTeamName().equalsIgnoreCase(teamName))
                .flatMap(t -> t.getCars().stream())
                .map(c -> c.getWins())
                .reduce(0,(acc, wins) -> acc + wins);
    }

    //------------------------------------------------------------------------------------------------------

    public Map<Boolean, List<Team>> paritionGridByTeamsWithWinners(){
        List<Team> winners = new ArrayList<>();
        List<Team> nonWinners = new ArrayList<>();
        for (Team t : teams) {
            for(Car c : t.getCars()) {
                if(c.getWins() > 0) {
                    winners.add(t);
                    break;
                }
            }
            if(!winners.contains(t)) {
                nonWinners.add(t);
            }
        }
        Map<Boolean, List<Team>> result = new HashMap<>();
        result.put(true, winners);
        result.put(false, nonWinners);
        return result;
    }

    public Map<Boolean, List<Team>> paritionGridByTeamsWithWinnersStreams(){
        return teams.stream()
                .collect(partitioningBy(team ->
                        team.getCars().stream().anyMatch(car -> car.getWins() > 0)));
    }

    //-------------------------------------------------------------------------------------------------

    public Map<String, List<Team>> groupTheData() {
        return teams.stream().collect(groupingBy(team -> team.getEngineSupplier()));
    }
    //------------------------------------------------------------------------------------------------------

    public Team teamOfDriverOld(String surname) {
        for(Team t : teams) {
            for(Car c : t.getCars()) {
                if(c.getDriverSurname().equalsIgnoreCase(surname)) {
                    return t;
                }
            }
        }
        return null;
    }

    public Optional<Team> teamOfDriver(String surname) {
       return teams.stream().filter(t ->
               t.getCars().stream()
                       .anyMatch(c -> c.getDriverSurname().equalsIgnoreCase(surname)))
               .findFirst();
    }


    //------------------------------------------------------------------------------------------------------

    public void doStuffWithTeamOfDriver(String surname) {
        Optional<Team> team = teamOfDriver(surname);
        team.ifPresent(Grid::printTeam);

        Optional<List<Car>> optionalList = team.map(Team::getCars);

        Optional<List<Car>> optList = team.flatMap(Team::getCarsOptionally);

    }

    private static void printTeam(Team team) {
        System.out.println(team.getTeamName() + "-" + team.getEngineSupplier());
        System.out.println("Drivers : ");
        team.getCars().stream().forEach(System.out::println);
    }









}
