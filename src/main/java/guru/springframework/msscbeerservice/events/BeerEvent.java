package guru.springframework.msscbeerservice.events;

import java.io.Serializable;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerEvent implements Serializable {

    private static final long serialVersionUID = 3738178240496566665L;
    
    private BeerDto beerDto;
}
