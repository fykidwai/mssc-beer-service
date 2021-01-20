package guru.springframework.msscbeerservice.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;

import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerPagedList;
import guru.sfg.brewery.model.BeerStyleEnum;

public interface BeerService {

    BeerDto getBeerById(UUID beerId, boolean showInventoryOnHand);

    BeerDto createNewBeer(BeerDto beerDto);

    BeerDto updateBeerById(UUID beerId, BeerDto beerDto);

    List<BeerDto> getAllBeer();

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest of, boolean showInventoryOnHand);

    BeerDto getBeerByUPC(String upc);

}
