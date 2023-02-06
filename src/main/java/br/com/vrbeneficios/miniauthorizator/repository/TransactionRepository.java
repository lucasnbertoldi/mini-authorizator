package br.com.vrbeneficios.miniauthorizator.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.vrbeneficios.miniauthorizator.entity.CardEntity;
import br.com.vrbeneficios.miniauthorizator.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {

    @Query("SELECT SUM(t.value) FROM transaction t where t.card = :cardId")
    public BigDecimal getCardBalance(@Param("cardId") CardEntity card);
}
