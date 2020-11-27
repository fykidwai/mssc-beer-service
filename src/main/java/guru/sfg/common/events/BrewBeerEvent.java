package guru.sfg.common.events;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {

    private static final long serialVersionUID = 938264382931294419L;

    public BrewBeerEvent(final BeerDto beerDto) {
        super(beerDto);
    }

}
