package com.banco.Saint_Patrik.Repositories;

import com.banco.Saint_Patrik.Entities.Card;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {

    //BUSCO TODAS LAS TARJETAS ORDENADAS POR NÚMERO DE TARJETA
    @Query("SELECT c FROM Card c ORDER BY c.numberCard")
    public List<Card> searchAll();

    //BUSCO LAS TARJETAS POR ID DE TARJETA
    @Query("SELECT c FROM Card c WHERE c.id = :id")
    public Card searchById(@Param("id") String id);

    @Query("SELECT c from Card c WHERE c.enabled = true ORDER BY c.numberCard ASC")
    public List<Card> searchCardByEnabled();

    //BUSCO TARJETAS POR BAJAS
    @Query("SELECT c from Card c WHERE c.enabled = false ORDER BY c.numberCard ASC")
    public List<Card> searchCardByDisabled();
    
    //BUSCO TARJETAS POR NUMERO DE TARJETA
    @Query("SELECT c from Card c WHERE c.numberCard = :NumberCard")
    public Card searchCardByNumberCard(@Param("NumberCard") String NumberCard);
}
