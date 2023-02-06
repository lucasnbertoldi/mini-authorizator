package br.com.vrbeneficios.miniauthorizator.service;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vrbeneficios.miniauthorizator.entity.CardEntity;
import br.com.vrbeneficios.miniauthorizator.entity.TransactionEntity;
import br.com.vrbeneficios.miniauthorizator.repository.TransactionRepository;
import br.com.vrbeneficios.miniauthorizator.util.interfaces.SystemDate;
import jakarta.transaction.Transactional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardService cardService;
    @Autowired
    private SystemDate systemDate;

    public BigDecimal getCardBalance(CardEntity card) {
        BigDecimal balance = transactionRepository.getCardBalance(card);
        return balance == null ? BigDecimal.ZERO : balance;
    }

    @Transactional
    public TransactionEntity save(TransactionEntity transaction, String password) throws IllegalAccessException {
        verifyPassword(transaction.getCard(), password);
        transaction.setDateTime(systemDate.getCurrentDateTime());
        transactionRepository.save(transaction);
        return transaction;
    }

    private void verifyPassword(CardEntity card, String password) throws IllegalAccessException {
        if (!Objects.equals(password, cardService.getPasswordHash(password, card.getId()))) {
            throw new IllegalAccessException("A senha está incorreta");
        }
    }

}
