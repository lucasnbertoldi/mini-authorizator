package br.com.vrbeneficios.miniauthorizator.unit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import javax.management.OperationsException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.vrbeneficios.miniauthorizator.dto.CartaoDTO;
import br.com.vrbeneficios.miniauthorizator.entity.CardEntity;
import br.com.vrbeneficios.miniauthorizator.entity.TransactionEntity;
import br.com.vrbeneficios.miniauthorizator.service.CardService;
import br.com.vrbeneficios.miniauthorizator.service.TransactionService;
import br.com.vrbeneficios.miniauthorizator.util.constant.CardConstants;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionTest {

    private final String CARD_NUMBER = "3333333333333333";
    private final String CARD_PASSWORD = "123";
    private final String INCORRECT_CARD_PASSWORD = "1234";

    @Autowired
    private CardService cardService;
    @Autowired
    private TransactionService transactionService;

    @Test
    public void transactionTest() throws Exception {

        CardEntity card = saveNewCard();

        testBalance(card, CardConstants.INITIAL_BALANCE);

        testSavePositiveValue(card);
        testIncorrectPassword(card);

        testSaveTransactional(card);

    }

    private void testSaveTransactional(CardEntity card) throws IllegalAccessException, OperationsException {
        saveTransaction(card, new BigDecimal("-100"));
        testBalance(card, new BigDecimal("400"));

        saveTransaction(card, new BigDecimal("-55.5"));
        testBalance(card, new BigDecimal("344.5"));

        saveTransaction(card, new BigDecimal("-44.50"));
        testBalance(card, new BigDecimal("300"));

        saveTransaction(card, new BigDecimal("-300"));
        testBalance(card, new BigDecimal("0"));

        testInsuficientCardBalance(card);
    }

    private void testInsuficientCardBalance(CardEntity card) {
        assertThrows(OperationsException.class, () -> {
            saveTransaction(card, new BigDecimal("-100"), CARD_PASSWORD);
        });
    }

    private void testSavePositiveValue(CardEntity card) {
        assertThrows(IllegalArgumentException.class, () -> {
            saveTransaction(card, new BigDecimal("100"));
        });
    }

    private void testBalance(CardEntity card, BigDecimal correctValue) {
        BigDecimal value = transactionService.getCardBalance(card);
        assertTrue(value.compareTo(correctValue) == 0);
    }

    private void saveTransaction(CardEntity card, BigDecimal value) throws IllegalAccessException, OperationsException {
        saveTransaction(card, value, CARD_PASSWORD);
    }

    private void saveTransaction(CardEntity card, BigDecimal value, String password) throws IllegalAccessException, OperationsException {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setCard(card);
        transaction.setValue(value);
        transactionService.save(transaction, password);
    }

    private void testIncorrectPassword(CardEntity card) throws IllegalAccessException {
        assertThrows(IllegalAccessException.class, () -> {
            saveTransaction(card, new BigDecimal("-100"), INCORRECT_CARD_PASSWORD);
        });
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
