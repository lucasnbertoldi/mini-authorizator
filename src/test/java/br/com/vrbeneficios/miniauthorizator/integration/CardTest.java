package br.com.vrbeneficios.miniauthorizator.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

    @Test
    public void saveCard() throws Exception {

        JSONObject cardBodyJSON = new JSONObject();
        cardBodyJSON.put("numeroCartao", CARD_NUMBER);
        cardBodyJSON.put("senha", CARD_PASSWORD);

        testSaveNewCard(cardBodyJSON);
        testSaveAlreadySavedCard(cardBodyJSON);

    }

    private void testSaveAlreadySavedCard(JSONObject cardBodyJSON) throws Exception {
        this.mockMvc
                .perform(post(SAVE_CARD_PATH).contentType(MediaType.APPLICATION_JSON).content(cardBodyJSON.toString()))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(cardBodyJSON.toString(), true))
                .andReturn().getResponse();
    }

    private void testSaveNewCard(JSONObject cardBodyJSON) throws Exception {
        this.mockMvc
                .perform(post(SAVE_CARD_PATH).contentType(MediaType.APPLICATION_JSON).content(cardBodyJSON.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(cardBodyJSON.toString(), true))
                .andReturn().getResponse();
    }
}
