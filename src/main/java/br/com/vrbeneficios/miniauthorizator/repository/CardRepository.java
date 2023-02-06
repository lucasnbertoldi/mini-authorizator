package br.com.vrbeneficios.miniauthorizator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vrbeneficios.miniauthorizator.entity.CardEntity;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Integer> {

    public CardEntity findByNumber(String number);

}
