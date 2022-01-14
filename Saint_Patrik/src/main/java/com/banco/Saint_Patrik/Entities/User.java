package com.banco.Saint_Patrik.Entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;
    private String name;
    private String surname;

    @Column(unique = true)
    private String document;
    private String mail;
    private Boolean enabled;

    @OneToMany//(cascade = CascadeType.ALL)
    private List<Card> card;

    @OneToMany//(cascade = CascadeType.ALL)
    private List<Transaction> transaction;

    public User() {
         this.enabled = true;
    }

    public User(String id, String name, String surname, String document, String mail, Boolean enabled, List<Card> card, List<Transaction> transaction) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.document = document;
        this.mail = mail;
        this.enabled = enabled;
        this.card = card;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return the document
     */
    public String getDocument() {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(String document) {
        this.document = document;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
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
     * @return the card
     */
    public List<Card> getCard() {
        return card;
    }

    /**
     * @param card the card to set
     */
    public void setCard(List<Card> card) {
        this.card = card;
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
        return "User{" + "id=" + id + ", name=" + name + ", surname=" + surname + ", document=" + document + ", mail=" + mail + ", enabled=" + enabled + ", card=" + card + ", transaction=" + transaction + '}';
    }

}
