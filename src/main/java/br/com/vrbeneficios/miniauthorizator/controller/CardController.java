package br.com.vrbeneficios.miniauthorizator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.vrbeneficios.miniauthorizator.dto.CartaoDTO;
import br.com.vrbeneficios.miniauthorizator.entity.CardEntity;
import br.com.vrbeneficios.miniauthorizator.service.CardService;

@RestController
@RequestMapping("/cartoes")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping()
    public ResponseEntity<CartaoDTO> save(@RequestBody CartaoDTO cartaoDTO) {
        CardEntity card = new CardEntity(cartaoDTO.getNumeroCartao(), cartaoDTO.getSenha());
        try {
            cardService.save(card);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<CartaoDTO>(cartaoDTO, HttpStatus.UNPROCESSABLE_ENTITY);
        }        
        return new ResponseEntity<CartaoDTO>(cartaoDTO, HttpStatus.OK);
    }

}
