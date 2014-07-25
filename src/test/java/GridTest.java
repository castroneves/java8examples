import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Arrays.*;
import static org.testng.Assert.assertEquals;


public class GridTest {

    private Grid grid;

    @BeforeMethod
    public void populateGrid() {
        Car helio = new Car("Helio", "Castroneves", 1);
        Car will = new Car("Will", "Power", 2);
        Car juan = new Car("Juan", "Montoya", 1);
        Car tony = new Car("Tony", "Kanaan", 0);
        Car scott = new Car("Scott", "Dixon", 0);
        Car charlie = new Car("Charlie", "Kimbell", 0);
        Car marco = new Car("Marco", "Andretti", 0);
        Car hinch = new Car("James", "Hinchcliffe", 0);
        Car carlos = new Car("Carlos", "Munoz", 0);
        Car rhr = new Car("Ryan", "Hunter-Raey", 1);
        Car michael = new Car("Michael", "Aleshin", 0);
        Car simon = new Car("Simon", "Pagenaud", 1);

        Team penske = new Team("Penske", "Chevrolet", asList(helio, will, juan));
        Team ganassi = new Team("Ganassi", "Chevrolet", asList(tony, scott, charlie));
        Team andretti = new Team("Andretti Autosport", "Honda", asList(marco, hinch, carlos, rhr));
        Team schmidt = new Team("Schmidt Peterson", "Honda", asList(michael, simon));

        grid = new Grid(asList(penske, ganassi, andretti, schmidt));
    }

    @Test
    public void externalLogicPassedInFromClient() {
        List<Team> anonInnerClas = grid.performExternalLogicOld(team -> team.getTeamName().equalsIgnoreCase("Ganassi"));

        List<Team> lambda = grid.performExternalLogicOld(t -> t.getTeamName().equalsIgnoreCase("Ganassi"));
        Function<Team, String> t1 = (Team t) -> t.toString();


        assertEquals(anonInnerClas,lambda);
    }

    @Test
    public void combiningLists() {
        List<Car> loops = grid.listAllCarsOld();
        List<Car> streams = grid.listAllCarsSteams();

        assertEquals(loops, streams);
        streams.stream().forEach(System.out::println);
    }


    @Test
    public void allCarsForAGivenTeam() {
        List<Car> loops = grid.carsForTeamOld("Andretti Autosport");
        List<Car> streams = grid.carsForTeam("Andretti Autosport");

        assertEquals(loops,streams);
        streams.stream().forEach(System.out::println);
    }

    @Test
    public void reductionOverSum() {
        Integer loops = grid.winsForGivenTeamOld("penske");
        Integer streams = grid.winsForGivenTeam("penske");

        System.out.println(streams);
        assertEquals(streams, loops);
    }

    @Test
    public void partitionDataOverPredicate() {
        Map<Boolean, List<Team>> loops = grid.paritionGridByTeamsWithWinners();
        Map<Boolean, List<Team>> streams = grid.paritionGridByTeamsWithWinnersStreams();

        assertEquals(loops, streams);

        System.out.println("Winners:");
        System.out.println("----------");
        streams.get(true).stream().map(Team::getTeamName).forEach(System.out::println);
        System.out.println("\nNon-winners:");
        System.out.println("----------");
        streams.get(false).stream().map(Team::getTeamName).forEach(System.out::println);
    }


    @Test
    public void chris() {
        System.out.println(grid.groupTheData());
    }


    @Test
    public void teamOfAGivenDriver() {
        Team loops = grid.teamOfDriverOld("castroneves");

        Optional<Team> streams = grid.teamOfDriver("castroneves");
        assertEquals(loops, streams.get());
        streams.ifPresent(System.out::println);
    }




    @Test
    public void testGrid() {
        List<Car> old = grid.listAllCarsOld();
        List<Car> newl = grid.listAllCarsSteams();

        assertEquals(old,newl);
//        old.stream().forEach(System.out::println);
//        newl.stream().forEach(System.out::println);

        grid.doStuffWithTeamOfDriver("munoz");

        grid.groupTheData();



    }
}
