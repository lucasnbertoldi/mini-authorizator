package br.com.vrbeneficios.miniauthorizator.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.vrbeneficios.miniauthorizator.dto.CartaoDTO;
import br.com.vrbeneficios.miniauthorizator.entity.CardEntity;
import br.com.vrbeneficios.miniauthorizator.service.CardService;
import br.com.vrbeneficios.miniauthorizator.service.TransactionService;

@RestController
@RequestMapping("/cartoes")
public class CardController {

    @Autowired
    private CardService cardService;
    @Autowired
    private TransactionService transactionService;

    @PostMapping()
    public ResponseEntity<CartaoDTO> save(@RequestBody CartaoDTO cartaoDTO) {
        CardEntity card = new CardEntity(cartaoDTO.getNumeroCartao(), cartaoDTO.getSenha());
        try {
            cardService.save(card);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<CartaoDTO>(cartaoDTO, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<CartaoDTO>(cartaoDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "{numero}")
    public ResponseEntity<String> getBalance(@PathVariable String numero) {
        try {
            CardEntity card = cardService.getCard(numero);
            BigDecimal balance = transactionService.getCardBalance(card);
            return new ResponseEntity<String>(balance.toString(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
        }
    }
}
