package com.banco.Saint_Patrik.Repositories;

import com.banco.Saint_Patrik.Entities.Card;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {

    /**
     * BUSCAR TODAS LAS TARJETAS ORDENADAS POR NÚMERO DE TARJETA
     *
     * SEARCH ALL CARDS SORTED BY CARD NUMBER
     *
     * @return
     */
    @Query("SELECT c FROM Card c ORDER BY c.numberCard")
    public List<Card> searchAll();

    /**
     * BUSCAR UNA TARJETA POR ID DE TARJETA
     *
     * SEARCH FOR A CARD BY CARD ID
     *
     * @param id
     * @return
     */
    @Query("SELECT c FROM Card c WHERE c.id = :id")
    public Card searchById(@Param("id") String id);

    /**
     * BUSCAR TARJETAS HABILITDAS
     *
     * SEARCH FOR ENABLED CARDS
     *
     * @return
     */
    @Query("SELECT c from Card c WHERE c.enabled = true ORDER BY c.numberCard ASC")
    public List<Card> searchCardByEnabled();

    /**
     * BUSCAR TARJETAS DESHABILITDAS
     *
     * SEARCH FOR DISABLED CARDS
     *
     * @return
     */
    @Query("SELECT c from Card c WHERE c.enabled = false ORDER BY c.numberCard ASC")
    public List<Card> searchCardByDisabled();

    /**
     * BUSCAR TARJETAS POR NUMERO DE TARJETA
     *
     * SEARCH CARDS BY CARD NUMBER
     *
     * @param NumberCard
     * @return
     */
    @Query("SELECT c from Card c WHERE c.numberCard = :NumberCard")
    public Card searchCardByNumberCard(@Param("NumberCard") String NumberCard);

    /**
     * BUSCAR TARJETAS POR USUARIO
     *
     * SEARCH CARDS BY USER
     *
     * @param id
     * @return
     */
    @Query("SELECT c from Card c WHERE c.user.id = :id")
    public Card searchCardByUser(@Param("id") String id);
}
