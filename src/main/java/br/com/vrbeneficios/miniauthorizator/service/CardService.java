package br.com.vrbeneficios.miniauthorizator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vrbeneficios.miniauthorizator.entity.CardEntity;
import br.com.vrbeneficios.miniauthorizator.repository.CardRepository;
import br.com.vrbeneficios.miniauthorizator.util.interfaces.HashGenerator;
import jakarta.transaction.Transactional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private HashGenerator hashGenerator;

    @Transactional
    public CardEntity save(CardEntity card) {
        if (alreadyExists(card)) {
            throw new IllegalArgumentException("O cartão com o número " + card.getNumber() + " cartão já existe.");
        }

        card = cardRepository.save(card);
        card = updateToSaltPassword(card);

        return card;
    }

    private CardEntity updateToSaltPassword(CardEntity card) {
        card.setPassword(getPasswordHash(card.getPassword(), card.getId()));
        cardRepository.save(card);
        return card;
    }

    public String getPasswordHash(String password, Integer cardId) {
        String saltHash = hashGenerator.generateSaltHash(password, cardId + "");
        return saltHash;
    }

    private boolean alreadyExists(CardEntity card) {
        return cardRepository.findByNumber(card.getNumber()) != null;
    }
}
