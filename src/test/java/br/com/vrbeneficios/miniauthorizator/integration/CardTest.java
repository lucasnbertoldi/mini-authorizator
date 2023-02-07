package br.com.vrbeneficios.miniauthorizator.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import br.com.vrbeneficios.miniauthorizator.dto.CartaoDTO;
import br.com.vrbeneficios.miniauthorizator.util.constant.CardConstants;
import br.com.vrbeneficios.miniauthorizator.util.interfaces.JSONConversor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class CardTest {

    private final String CARD_PATH = "/cartoes";

    private final String CARD_NUMBER = "2222222222222222";
    private final String NOT_FOUND_CARD_NUMBER = "0000000000000000";
    private final String CARD_PASSWORD = "123";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JSONConversor jsonConversorService;

    @Test
    public void saveCard() throws Exception {

        CartaoDTO cartaoDTO = new CartaoDTO();
        cartaoDTO.setNumeroCartao(CARD_NUMBER);
        cartaoDTO.setSenha(CARD_PASSWORD);

        testSaveNewCard(cartaoDTO);
        testGetBalance(cartaoDTO.getNumeroCartao());

        testSaveAlreadySavedCard(cartaoDTO);
        testGetBalanceToNotFoundCard(NOT_FOUND_CARD_NUMBER);

    }

    private void testGetBalanceToNotFoundCard(String number) throws Exception {
        this.mockMvc
                .perform(get(CARD_PATH + "/" + number).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private void testGetBalance(String number) throws Exception {
        MockHttpServletResponse mockResponse = this.mockMvc
                .perform(get(CARD_PATH + "/" + number).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        BigDecimal value = new BigDecimal(mockResponse.getContentAsString());
        assertTrue(value.compareTo(CardConstants.INITIAL_BALANCE) == 0);
    }

    private void testSaveAlreadySavedCard(CartaoDTO cartaoDTO) throws Exception {
        String cartaoJSON = jsonConversorService.convertDTOToJSON(cartaoDTO);
        this.mockMvc
                .perform(post(CARD_PATH).contentType(MediaType.APPLICATION_JSON).content(cartaoJSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(cartaoJSON, true));
    }

    private void testSaveNewCard(CartaoDTO cartaoDTO) throws Exception {
        String cartaoJSON = jsonConversorService.convertDTOToJSON(cartaoDTO);
        this.mockMvc
                .perform(post(CARD_PATH).contentType(MediaType.APPLICATION_JSON).content(cartaoJSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(cartaoJSON, true));
    }

}
