package br.com.vrbeneficios.miniauthorizator.service;

import java.math.BigDecimal;
import java.util.Objects;

import javax.management.OperationsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vrbeneficios.miniauthorizator.entity.CardEntity;
import br.com.vrbeneficios.miniauthorizator.entity.TransactionEntity;
import br.com.vrbeneficios.miniauthorizator.repository.TransactionRepository;
import br.com.vrbeneficios.miniauthorizator.util.interfaces.SystemDate;

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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public TransactionEntity save(TransactionEntity transaction, String password)
            throws IllegalAccessException, OperationsException {
        verifyPassword(transaction.getCard(), password);
        verifyValue(transaction);
        verifyCardBalance(transaction);
        transaction.setDateTime(systemDate.getCurrentDateTime());
        transactionRepository.save(transaction);
        return transaction;
    }

    private void verifyValue(TransactionEntity transaction) {
        if (transaction.getValue().compareTo(BigDecimal.ZERO) >= 0) {
            throw new IllegalArgumentException("O valor da transação deve ser negativo.");
        }
    }

    private void verifyCardBalance(TransactionEntity transaction) throws OperationsException {
        BigDecimal balaceValue = transactionRepository.getCardBalance(transaction.getCard());
        if (transaction.getValue().abs().compareTo(balaceValue) > 0) {
            throw new OperationsException("O cartão não tem saldo o suficiente. Saldo: " + balaceValue.toString()
                    + " Operação: " + transaction.getValue());
        }
    }

    private void verifyPassword(CardEntity card, String password) throws IllegalAccessException {
        if (!Objects.equals(card.getPassword(), cardService.getPasswordHash(password, card.getId()))) {
            throw new IllegalAccessException("A senha está incorreta");
        }
    }

}
