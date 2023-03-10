package br.com.vrbeneficios.miniauthorizator.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity(name = "transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateTime", nullable = false)
    private Date dateTime;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @JoinColumn(name = "card_id", referencedColumnName = "id")
    @ManyToOne
    private CardEntity card;

    public TransactionEntity() {
    }

    public TransactionEntity(Date dateTime, BigDecimal value, CardEntity card) {
        this.dateTime = dateTime;
        this.value = value;
        this.card = card;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public CardEntity getCard() {
        return card;
    }

    public void setCard(CardEntity card) {
        this.card = card;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TransactionEntity other = (TransactionEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


}
