package com.banco.Saint_Patrik.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Card {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;
    private String numberCard;
    private Integer pin;
    private Double credit;
    private Boolean enabled;

    public Card() {
        this.enabled = true;
    }

    public Card(String id, String numberCard, Integer pin, Double credit, Boolean enabled) {
        this.id = id;
        this.numberCard = numberCard;
        this.pin = pin;
        this.credit = credit;
        this.enabled = enabled;
    }

    /**
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * 
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * 
     * @return the numberCard
     */
    public String getNumberCard() {
        return numberCard;
    }
    /**
     * 
     * @param numberCard the numberCard to set
     */
    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }
    /**
     * 
     * @return the pin
     */
    public Integer getPin() {
        return pin;
    }
    /**
     * 
     * @param pin the pin to set
     */
    public void setPin(Integer pin) {
        this.pin = pin;
    }
    /**
     * 
     * @return the credit
     */
    public Double getCredit() {
        return credit;
    }
    /**
     * 
     * @param credit the credit to set
     */
    public void setCredit(Double credit) {
        this.credit = credit;
    }
    /**
     * 
     * @return enabled status
     */
    public Boolean getEnabled() {
        return enabled;
    }
    /**
     * 
     * @param enabled the status to set
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Card{" + "id=" + id + ", numberCard=" + numberCard + ", pin=" + pin + ", credit=" + credit + ", enabled=" + enabled + '}';
    }

}
