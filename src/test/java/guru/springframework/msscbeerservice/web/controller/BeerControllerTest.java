package guru.springframework.msscbeerservice.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerStyleEnum;
import guru.springframework.msscbeerservice.bootstrap.BeerLoader;
import guru.springframework.msscbeerservice.services.BeerService;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    private static final String SLASH = "/";
    private static final String API_V1_BEER = "/api/v1/beer";
    private static final String API_V1_BEER_UPC = "/api/v1/beerUpc";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BeerService beerService;

    @Test
    void testGetBeerById() throws Exception {
        final var id = UUID.randomUUID();
        given(beerService.getBeerById(any(), anyBoolean())).willReturn(getValidBeerDto());
        mockMvc.perform(get(API_V1_BEER + SLASH + id.toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testCreateNewBeer() throws Exception {
        final var beerDto = getValidBeerDto();
        final var beerDtoString = mapper.writeValueAsString(beerDto);
        given(beerService.createNewBeer(any())).willReturn(getValidBeerDto());
        mockMvc.perform(post(API_V1_BEER).contentType(MediaType.APPLICATION_JSON).content(beerDtoString))
            .andExpect(status().isCreated());
    }

    @Test
    void testUpdateBeerById() throws Exception {
        final var id = UUID.randomUUID();
        final var beerDto = getValidBeerDto();
        final var beerDtoString = mapper.writeValueAsString(beerDto);
        given(beerService.updateBeerById(any(), any())).willReturn(getValidBeerDto());
        mockMvc
            .perform(put(API_V1_BEER + SLASH + id.toString()).contentType(MediaType.APPLICATION_JSON).content(beerDtoString))
            .andExpect(status().isNoContent());
    }

    @Test
    void testGetBeerByUpc() throws Exception {
        given(beerService.getBeerByUPC(BeerLoader.BEER_1_UPC)).willReturn(getValidBeerDto());
        mockMvc.perform(get(API_V1_BEER_UPC + SLASH + BeerLoader.BEER_1_UPC).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    private BeerDto getValidBeerDto() {
        return BeerDto.builder().beerName("My Beer").beerStyle(BeerStyleEnum.ALE).upc(BeerLoader.BEER_1_UPC)
            .price(BigDecimal.valueOf(2.99)).build();
    }
}
