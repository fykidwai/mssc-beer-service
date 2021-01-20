package guru.sfg.brewery.model.events;

import guru.sfg.brewery.model.BeerDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent {

    private static final long serialVersionUID = -1767703357372754708L;

    public NewInventoryEvent(final BeerDto beerDto) {
        super(beerDto);
    }

}
