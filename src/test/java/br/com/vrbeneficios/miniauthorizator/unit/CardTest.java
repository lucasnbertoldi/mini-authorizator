package br.com.vrbeneficios.miniauthorizator.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.vrbeneficios.miniauthorizator.entity.CardEntity;
import br.com.vrbeneficios.miniauthorizator.service.CardService;
import br.com.vrbeneficios.miniauthorizator.service.TransactionService;
import br.com.vrbeneficios.miniauthorizator.util.constant.CardConstants;

@SpringBootTest
@AutoConfigureMockMvc
public class CardTest {

    @Autowired
    private CardService cardService;

    private final String CARD_NUMBER = "1111111111111111";
    private final String CARD_PASSWORD = "123";

    @Autowired
    private TransactionService transactionService;

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
        testPasswordHash(newCard);
        testInitialBalance(newCard);

    }

    private void testPasswordHash(CardEntity card) {
        assertEquals(card.getPassword(), cardService.getPasswordHash(CARD_PASSWORD, card.getId()));
    }

    private void testInitialBalance(CardEntity card) {
        BigDecimal initialBalance = transactionService.getCardBalance(card);
        assertTrue(initialBalance.compareTo(CardConstants.INITIAL_BALANCE) == 0);
    }

}
