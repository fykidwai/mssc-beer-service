package guru.springframework.msscbeerservice.services.inventory;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import guru.springframework.msscbeerservice.services.inventory.model.BeerInventoryDto;

@FeignClient("inventory-failover")
public interface InventoryFailoverFeignClient {

    @GetMapping(path = "/inventory-failover")
    ResponseEntity<List<BeerInventoryDto>> getOnhandInventory();
}
