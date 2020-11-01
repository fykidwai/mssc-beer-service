package guru.springframework.msscbeerservice.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.exception.NotFoundException;
import guru.springframework.msscbeerservice.web.mappers.BeerMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerPagedList;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDto> getAllBeer() {
        return beerRepository.findAll().stream().map(beerMapper::beerToBeerDtoWithInventory).collect(Collectors.toList());
    }

    @Override
    @Cacheable(cacheNames = "beerListCache", key = "beerId", condition = "#showInventoryOnHand == false")
    public BeerDto getBeerById(final UUID beerId, final boolean showInventoryOnHand) {
        if (showInventoryOnHand) {
            return beerMapper
                .beerToBeerDtoWithInventory(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
        }
        return beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
    }

    @Override
    public BeerDto createNewBeer(final BeerDto beerDto) {
        return beerMapper.beerToBeerDtoWithInventory(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeerById(final UUID beerId, final BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDtoWithInventory(beerRepository.save(beer));
    }

    @Override
    @Cacheable(cacheNames = "beerCache", condition = "#showInventoryOnHand==false")
    public BeerPagedList listBeers(final String beerName, final BeerStyleEnum beerStyle, final PageRequest pageRequest,
        final boolean showInventoryOnHand) {
        final Page<Beer> beerPage;

        if (StringUtils.isNotBlank(beerName) && beerStyle != null) {
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (StringUtils.isNotBlank(beerName)) {
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (beerStyle != null) {
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        final Stream<BeerDto> bearDtoStream;
        if (showInventoryOnHand) {
            bearDtoStream = beerPage.getContent().stream().map(beerMapper::beerToBeerDtoWithInventory);
        } else {
            bearDtoStream = beerPage.getContent().stream().map(beerMapper::beerToBeerDto);
        }
        return new BeerPagedList(bearDtoStream.collect(Collectors.toList()),
            PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
            beerPage.getTotalElements());
    }

    @Override
    @Cacheable(cacheNames = "beerUpcCache")
    public BeerDto getBeerByUPC(final String upc) {
        return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc).orElseThrow(NotFoundException::new));
    }

}
