package guru.springframework.msscbeerservice.services.inventory;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import guru.springframework.msscbeerservice.services.inventory.model.BeerInventoryDto;
import lombok.extern.slf4j.Slf4j;

@Profile("!local-discovery")
@Slf4j
@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = true)
@Component
public class BeerInventoryServiceRestTemplateImpl implements BeerInventoryService {

    public static final String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";
    private final RestTemplate restTemplate;

    private String beerInventoryServiceHost;

    public void setBeerInventoryServiceHost(final String beerInventoryServiceHost) {
        this.beerInventoryServiceHost = beerInventoryServiceHost;
    }

    public BeerInventoryServiceRestTemplateImpl(final RestTemplateBuilder restTemplateBuilder,
        @Value("${sfg.brewery.inventory-user}") final String inventoryUser,
        @Value("${sfg.brewery.inventory-password}") final String inventoryPassword) {
        restTemplate = restTemplateBuilder.basicAuthentication(inventoryUser, inventoryPassword).build();
    }

    @Override
    public Integer getOnhandInventory(final UUID beerId) {
        log.debug("Calling Inventory service");

        final var responseEntity = restTemplate.exchange(beerInventoryServiceHost + INVENTORY_PATH, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<BeerInventoryDto>>() {
            }, beerId);
        // sum from inventory list
        return Objects.requireNonNull(responseEntity.getBody()).stream().mapToInt(BeerInventoryDto::getQuantityOnHand).sum();
    }

}
