package br.com.vrbeneficios.miniauthorizator.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.vrbeneficios.miniauthorizator.entity.CardEntity;
import br.com.vrbeneficios.miniauthorizator.service.CardService;

@SpringBootTest
@AutoConfigureMockMvc
public class CardTest {

    @Autowired
    private CardService cardService;

    private final String CARD_NUMBER = "1111111111111111";
    private final String CARD_PASSWORD = "123";
    
    @Test
    public void saveCard() {

        testSaveNewCard();
        testSaveAlreadySavedCard();

    }

    private void testSaveAlreadySavedCard() {
        CardEntity cardAlreadySaved = new CardEntity(CARD_NUMBER, CARD_PASSWORD);

        assertThrows(IllegalArgumentException.class, () -> {
            cardService.save(cardAlreadySaved);
        });
    }

    private void testSaveNewCard() {
        CardEntity newCard = new CardEntity(CARD_NUMBER, CARD_PASSWORD);
        cardService.save(newCard);

        assertEquals(newCard.getNumber(), CARD_NUMBER);
        assertEquals(newCard.getPassword(), CARD_PASSWORD);
    }

}
