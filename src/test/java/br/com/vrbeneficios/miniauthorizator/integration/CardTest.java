package br.com.vrbeneficios.miniauthorizator.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.vrbeneficios.miniauthorizator.dto.CartaoDTO;
import br.com.vrbeneficios.miniauthorizator.util.JSONConversorService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.json.JSONObject;

@SpringBootTest
@AutoConfigureMockMvc
public class CardTest {

    private final String SAVE_CARD_PATH = "/cartoes";

    private final String CARD_NUMBER = "2222222222222222";
    private final String CARD_PASSWORD = "123";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JSONConversorService jsonConversorService;

    @Test
    public void saveCard() throws Exception {

        CartaoDTO cartaoDTO = new CartaoDTO();
        cartaoDTO.setNumeroCartao(CARD_NUMBER);
        cartaoDTO.setSenha(CARD_PASSWORD);

        testSaveNewCard(cartaoDTO);
        testSaveAlreadySavedCard(cartaoDTO);

    }

    private void testSaveAlreadySavedCard(CartaoDTO cartaoDTO) throws Exception {
        String cartaoJSON = jsonConversorService.convertDTOToJSON(cartaoDTO);
        this.mockMvc
                .perform(post(SAVE_CARD_PATH).contentType(MediaType.APPLICATION_JSON).content(cartaoJSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(cartaoJSON, true))
                .andReturn().getResponse();
    }

    private void testSaveNewCard(CartaoDTO cartaoDTO) throws Exception {
        String cartaoJSON = jsonConversorService.convertDTOToJSON(cartaoDTO);
        this.mockMvc
                .perform(post(SAVE_CARD_PATH).contentType(MediaType.APPLICATION_JSON).content(cartaoJSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(cartaoJSON, true))
                .andReturn().getResponse();
    }

}
