package com.banco.Saint_Patrik.Entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Card {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;
    private String numberCard;
    private String pin;
    private Double credit;
    private Boolean enabled;

    @OneToOne
    private User user;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true)
//    @JoinColumn(name = "transaction_id")
    private List<Transaction> transaction;

    public Card() {
        this.enabled = true;
    }

    public Card(String id, String numberCard, String pin, Double credit, Boolean enabled, User user, List<Transaction> transaction) {
        this.id = id;
        this.numberCard = numberCard;
        this.pin = pin;
        this.credit = credit;
        this.enabled = enabled;
        this.user = user;
        this.transaction = transaction;
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
     * @return the numberCard
     */
    public String getNumberCard() {
        return numberCard;
    }

    /**
     * @param numberCard the numberCard to set
     */
    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    /**
     * @return the pin
     */
    public String getPin() {
        return pin;
    }

    /**
     * @param pin the pin to set
     */
    public void setPin(String pin) {
        this.pin = pin;
    }

    /**
     * @return the credit
     */
    public Double getCredit() {
        return credit;
    }

    /**
     * @param credit the credit to set
     */
    public void setCredit(Double credit) {
        this.credit = credit;
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
     * @return the transaction
     */
    public List<Transaction> getTransaction() {
        return transaction;
    }

    /**
     * @param transaction the transaction to set
     */
    public void setTransaction(List<Transaction> transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "Card{" + "id=" + id + ", numberCard=" + numberCard + ", pin=" + pin + ", credit=" + credit + ", enabled=" + enabled + ", user=" + user + ", transaction=" + transaction + '}';
    }

}
