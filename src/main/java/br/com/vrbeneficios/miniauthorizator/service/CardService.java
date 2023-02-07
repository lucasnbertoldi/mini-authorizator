package br.com.vrbeneficios.miniauthorizator.service;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vrbeneficios.miniauthorizator.entity.CardEntity;
import br.com.vrbeneficios.miniauthorizator.entity.TransactionEntity;
import br.com.vrbeneficios.miniauthorizator.repository.CardRepository;
import br.com.vrbeneficios.miniauthorizator.repository.TransactionRepository;
import br.com.vrbeneficios.miniauthorizator.util.constant.CardConstants;
import br.com.vrbeneficios.miniauthorizator.util.interfaces.HashGenerator;
import br.com.vrbeneficios.miniauthorizator.util.interfaces.SystemDate;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private HashGenerator hashGenerator;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private SystemDate systemDate;

    @Transactional
    public CardEntity save(CardEntity card) {
        if (alreadyExists(card)) {
            throw new IllegalArgumentException("O cartão com o número " + card.getNumber() + " cartão já existe.");
        }

        card = cardRepository.save(card);
        card = updateToSaltPassword(card);

        TransactionEntity transaction = new TransactionEntity();
        transaction.setCard(card);
        transaction.setValue(CardConstants.INITIAL_BALANCE);
        transaction.setDateTime(systemDate.getCurrentDateTime());
        transactionRepository.save(transaction);

        return card;
    }

    public CardEntity getCard(String number) throws IllegalArgumentException {
        CardEntity card = cardRepository.findByNumber(number);
        if (card == null) {
            throw new IllegalArgumentException("Cartão não encontrado");
        }
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
