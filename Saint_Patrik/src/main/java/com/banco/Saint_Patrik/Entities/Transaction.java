package com.banco.Saint_Patrik.Entities;

import com.banco.Saint_Patrik.Enum.Type_Transaction;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date dateTransaction;

    @Enumerated(EnumType.STRING)
    private Type_Transaction type;
    private Double amount;
    private Boolean enabled;

    @ManyToOne//(cascade = CascadeType.ALL)
    private User user;

    @ManyToOne//(cascade = CascadeType.ALL)
    private Card card;

    public Transaction() {
        this.enabled = true;
    }

    public Transaction(String id, Date dateTransaction, Type_Transaction type, Double amount, Boolean enabled, User user, Card card) {
        this.id = id;
        this.dateTransaction = dateTransaction;
        this.type = type;
        this.amount = amount;
        this.enabled = enabled;
        this.user = user;
        this.card = card;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the dateTransaction
     */
    public Date getDateTransaction() {
        return dateTransaction;
    }

    /**
     * @param dateTransaction the dateTransaction to set
     */
    public void setDateTransaction(Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    /**
     * @return the type
     */
    public Type_Transaction getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Type_Transaction type) {
        this.type = type;
    }

    /**
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * @return the enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the card
     */
    public Card getCard() {
        return card;
    }

    /**
     * @param card the card to set
     */
    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "Transaction{" + "id=" + id + ", dateTransaction=" + dateTransaction + ", type=" + type + ", amount=" + amount + ", enabled=" + enabled + ", user=" + user + ", card=" + card + '}';
    }

}
