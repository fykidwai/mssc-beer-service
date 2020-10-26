package guru.springframework.msscbeerservice.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    private static final String SLASH = "/";
    private static final String API_V1_BEER = "/api/v1/beer";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void testGetBeerById() throws Exception {
        final var id = UUID.randomUUID();
        mockMvc.perform(get(API_V1_BEER + SLASH + id.toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testCreateNewBeer() throws Exception {
        final var beerDto = getValidBeerDto();
        final var beerDtoString = mapper.writeValueAsString(beerDto);

        mockMvc.perform(post(API_V1_BEER).contentType(MediaType.APPLICATION_JSON).content(beerDtoString))
            .andExpect(status().isCreated());
    }

    @Test
    void testUpdateBeerById() throws Exception {
        final var id = UUID.randomUUID();
        final var beerDto = getValidBeerDto();
        final var beerDtoString = mapper.writeValueAsString(beerDto);

        mockMvc
            .perform(put(API_V1_BEER + SLASH + id.toString()).contentType(MediaType.APPLICATION_JSON).content(beerDtoString))
            .andExpect(status().isNoContent());
    }

    BeerDto getValidBeerDto() {
        return BeerDto.builder().beerName("My Beer").beerStyle(BeerStyleEnum.ALE).upc(5454656l)
            .price(BigDecimal.valueOf(2.99)).build();
    }
}
