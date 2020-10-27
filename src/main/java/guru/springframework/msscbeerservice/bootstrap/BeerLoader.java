package guru.springframework.msscbeerservice.bootstrap;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;

import guru.springframework.msscbeerservice.repositories.BeerRepository;

//@Component
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";
    public static final UUID BEER_1_UUID = UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb");
    public static final UUID BEER_2_UUID = UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd");
    public static final UUID BEER_3_UUID = UUID.fromString("026cc3c8-3a0c-4083-a05b-e908048c1b08");

    public BeerLoader(final BeerRepository beerRepository) {
    }

    @Override
    public void run(final String... args) throws Exception {
        // using data.sql file now

    }

}
