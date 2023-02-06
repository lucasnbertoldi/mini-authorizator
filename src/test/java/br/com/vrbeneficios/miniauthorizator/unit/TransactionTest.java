package br.com.vrbeneficios.miniauthorizator.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.vrbeneficios.miniauthorizator.dto.CartaoDTO;
import br.com.vrbeneficios.miniauthorizator.entity.CardEntity;
import br.com.vrbeneficios.miniauthorizator.service.CardService;
import br.com.vrbeneficios.miniauthorizator.service.TransactionService;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionTest {

    private final String CARD_NUMBER = "3333333333333333";
    private final String CARD_PASSWORD = "123";

    @Autowired
    private CardService cardService;
    @Autowired
    private TransactionService transactionService;

    @Test
    public void getBalance() throws Exception {

        CardEntity card = saveNewCard();
        BigDecimal value = transactionService.getCardBalance(card);
        assertEquals(value, BigDecimal.ZERO);

    }

    private CardEntity saveNewCard() {

        CartaoDTO cartaoDTO = new CartaoDTO();
        cartaoDTO.setNumeroCartao(CARD_NUMBER);
        cartaoDTO.setSenha(CARD_PASSWORD);

        CardEntity newCard = new CardEntity(CARD_NUMBER, CARD_PASSWORD);
        newCard = cardService.save(newCard);

        return newCard;

    }

}
