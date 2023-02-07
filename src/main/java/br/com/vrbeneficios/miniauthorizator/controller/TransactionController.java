package br.com.vrbeneficios.miniauthorizator.controller;

import java.math.BigDecimal;

import javax.management.OperationsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vrbeneficios.miniauthorizator.dto.TransacaoDTO;
import br.com.vrbeneficios.miniauthorizator.entity.CardEntity;
import br.com.vrbeneficios.miniauthorizator.entity.TransactionEntity;
import br.com.vrbeneficios.miniauthorizator.service.CardService;
import br.com.vrbeneficios.miniauthorizator.service.TransactionService;
import br.com.vrbeneficios.miniauthorizator.util.constant.TransactionResponse;

@RestController()
@RequestMapping("/transacoes")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CardService cardService;

    @PostMapping()
    public ResponseEntity<String> save(@RequestBody TransacaoDTO transacaoDTO) {
        CardEntity card = null;
        try {
            card = cardService.getCard(transacaoDTO.getNumeroCartao());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(TransactionResponse.CARTAO_INEXISTENTE.code,
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        TransactionEntity transaction = new TransactionEntity(null, getNegativeValue(transacaoDTO.getValor()),
                card);

        try {
            transactionService.save(transaction, transacaoDTO.getSenhaCartao());
        } catch (IllegalAccessException e) {
            return new ResponseEntity<String>(TransactionResponse.SENHA_INVALIDA.code, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (OperationsException e) {
            return new ResponseEntity<String>(TransactionResponse.SALDO_INSUFICIENTE.code,
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<String>("", HttpStatus.CREATED);
    }

    private BigDecimal getNegativeValue(String value) {
        return new BigDecimal(value).negate();
    }
}
