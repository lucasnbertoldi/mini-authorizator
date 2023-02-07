package br.com.vrbeneficios.miniauthorizator.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.vrbeneficios.miniauthorizator.dto.CartaoDTO;
import br.com.vrbeneficios.miniauthorizator.dto.TransacaoDTO;
import br.com.vrbeneficios.miniauthorizator.entity.CardEntity;
import br.com.vrbeneficios.miniauthorizator.service.CardService;
import br.com.vrbeneficios.miniauthorizator.service.TransactionService;
import br.com.vrbeneficios.miniauthorizator.util.constant.TransactionResponse;
import br.com.vrbeneficios.miniauthorizator.util.interfaces.JSONConversor;
import br.com.vrbeneficios.miniauthorizator.util.interfaces.JSONConversorException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CardService cardService;
    @Autowired
    private JSONConversor jsonConversor;
    @Autowired
    private TransactionService transactionService;

    private final String TRANSACTION_PATH = "/transacoes";
    private final String CARD_NUMBER = "4444444444444444";
    private final String NONEXISTENT_CARD_NUMBER = "0000000000000000";
    private final String CARD_PASSWORD = "123";
    private final String INCORRECT_CARD_PASSWORD = "1234";

    @Test
    public void transactionTest() throws Exception {

        CardEntity card = saveNewCard();

      //  testIncorrectPassword();
      //  testNonExistentCardNumber();

      //  testSaveAndBalance(card);

    }

    private void testSaveAndBalance(CardEntity card) throws JSONConversorException, Exception {

        saveTransaction("100");
        testBalance(card, new BigDecimal("400"));

        saveTransaction("-55.5");
        testBalance(card, new BigDecimal("344.5"));

        saveTransaction("-44.50");
        testBalance(card, new BigDecimal("300"));

        saveTransaction("-300");
        testBalance(card, new BigDecimal("0"));

        testInsuficientCardBalance(card);
    }

    private void testInsuficientCardBalance(CardEntity card) throws JSONConversorException, Exception {
        BigDecimal initialValue = transactionService.getCardBalance(card);

        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao(NONEXISTENT_CARD_NUMBER);
        transacaoDTO.setSenhaCartao(CARD_PASSWORD);
        transacaoDTO.setValor("100");

        this.mockMvc
                .perform(post(TRANSACTION_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(jsonConversor.convertDTOToJSON(transacaoDTO)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(TransactionResponse.SALDO_INSUFICIENTE.code));

        BigDecimal finalValue = transactionService.getCardBalance(card);

        assertTrue(initialValue.compareTo(finalValue) == 0);
    }

    private void testBalance(CardEntity card, BigDecimal correctValue) {
        BigDecimal value = transactionService.getCardBalance(card);
        assertTrue(value.compareTo(correctValue) == 0);
    }

    private void saveTransaction(String value) throws JSONConversorException, Exception {
        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao(CARD_NUMBER);
        transacaoDTO.setSenhaCartao(CARD_PASSWORD);
        transacaoDTO.setValor(value);

        this.mockMvc
                .perform(post(TRANSACTION_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(jsonConversor.convertDTOToJSON(transacaoDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    private void testNonExistentCardNumber() throws JSONConversorException, Exception {
        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao(NONEXISTENT_CARD_NUMBER);
        transacaoDTO.setSenhaCartao(CARD_PASSWORD);
        transacaoDTO.setValor("100");

        this.mockMvc
                .perform(post(TRANSACTION_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(jsonConversor.convertDTOToJSON(transacaoDTO)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(TransactionResponse.CARTAO_INEXISTENTE.code));
    }

    private void testIncorrectPassword() throws JSONConversorException, Exception {

        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao(CARD_NUMBER);
        transacaoDTO.setSenhaCartao(INCORRECT_CARD_PASSWORD);
        transacaoDTO.setValor("100");

        this.mockMvc
                .perform(post(TRANSACTION_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(jsonConversor.convertDTOToJSON(transacaoDTO)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(TransactionResponse.SENHA_INVALIDA.code));
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
