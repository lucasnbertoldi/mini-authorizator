package br.com.vrbeneficios.miniauthorizator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vrbeneficios.miniauthorizator.entity.CardEntity;
import br.com.vrbeneficios.miniauthorizator.repository.CardRepository;
import jakarta.transaction.Transactional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Transactional
    public void save(CardEntity card) {
        if (alreadyExists(card)) {
            throw new IllegalArgumentException("O cartão com o número " + card.getNumber() + " cartão já existe.");
        }

        cardRepository.save(card);
    }

    private boolean alreadyExists(CardEntity card) {
        return cardRepository.findByNumber(card.getNumber()) != null;
    }
}
