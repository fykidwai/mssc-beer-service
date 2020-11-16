package guru.springframework.msscbeerservice.bootstrap;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class BeerLoader implements CommandLineRunner {

    private static final int MN_12 = 12;
    private static final String STR_12_95 = "12.95";
    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";
    /*
     * public static final UUID BEER_1_UUID = UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb"); public static final
     * UUID BEER_2_UUID = UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd"); public static final UUID BEER_3_UUID =
     * UUID.fromString("026cc3c8-3a0c-4083-a05b-e908048c1b08");
     */

    private final BeerRepository beerRepository;

    @Override
    public void run(final String... args) throws Exception {
        if (beerRepository.count() == 0) {
            loadBeerObjects();
        }
    }

    private void loadBeerObjects() {
        final var b1 = Beer.builder().beerName("Mango Bobs").beerStyle(BeerStyleEnum.IPA).minOnHand(MN_12)
            .quantityToBrew(200).price(new BigDecimal(STR_12_95)).upc(BEER_1_UPC).build();

        final var b2 = Beer.builder().beerName("Galaxy Cat").beerStyle(BeerStyleEnum.PALE_ALE).minOnHand(MN_12)
            .quantityToBrew(200).price(new BigDecimal(STR_12_95)).upc(BEER_2_UPC).build();

        final var b3 = Beer.builder().beerName("Pinball Porter").beerStyle(BeerStyleEnum.PALE_ALE).minOnHand(MN_12)
            .quantityToBrew(200).price(new BigDecimal(STR_12_95)).upc(BEER_3_UPC).build();

        beerRepository.save(b1);
        beerRepository.save(b2);
        beerRepository.save(b3);
    }
}
