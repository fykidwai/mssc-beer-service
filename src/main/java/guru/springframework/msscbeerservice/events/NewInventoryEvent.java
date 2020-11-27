package guru.springframework.msscbeerservice.events;

import guru.springframework.msscbeerservice.web.model.BeerDto;

public class NewInventoryEvent extends BeerEvent {

    private static final long serialVersionUID = -1767703357372754708L;

    public NewInventoryEvent(final BeerDto beerDto) {
        super(beerDto);
    }

}
