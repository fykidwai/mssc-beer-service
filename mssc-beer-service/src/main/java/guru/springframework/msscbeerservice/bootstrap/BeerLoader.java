package guru.springframework.msscbeerservice.bootstrap;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;

@Component
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public BeerLoader(final BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(final String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if (beerRepository.count() != 0) {
            return;
        }
        beerRepository.save(Beer.builder().beerName("Mango Bobs").beerStyle("IPA").quantityToBrew(200).minOnHand(20)
            .upc(123456L).price(BigDecimal.valueOf(12.95)).build());
        beerRepository.save(Beer.builder().beerName("Galaxy Cat").beerStyle("PALE_ALE").quantityToBrew(200).minOnHand(20)
            .upc(123455L).price(BigDecimal.valueOf(11.95)).build());
    }

}
