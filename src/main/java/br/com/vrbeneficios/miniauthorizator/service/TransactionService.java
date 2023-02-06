package br.com.vrbeneficios.miniauthorizator.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vrbeneficios.miniauthorizator.entity.CardEntity;
import br.com.vrbeneficios.miniauthorizator.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public BigDecimal getCardBalance(CardEntity card) {
        BigDecimal balance = transactionRepository.getCardBalance(card);
        return balance == null ? BigDecimal.ZERO : balance;
    }

}
