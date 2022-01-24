package com.banco.Saint_Patrik.Repositories;

import com.banco.Saint_Patrik.Entities.Card;
import com.banco.Saint_Patrik.Entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * BUSCAR TODOS LOS USUARIOS ORDENADOS POR APELLIDO EN FORMA DESCENDENTE
     *
     * FIND ALL USERS SORTED BY LAST NAME IN DESCENDING FORM
     *
     * @return
     */
    @Query("SELECT u FROM User u ORDER BY u.surname DESC")
    public List<User> searchAll();

    /**
     * BUSCAR LOS USUARIOS POR ID
     *
     * SEARCH USERS BY ID
     *
     * @param id
     * @return
     */
    @Query("SELECT u FROM User u WHERE u.id = :id")
    public User searchById(@Param("id") String id);

    /**
     * BUSCAR LOS USUARIOS POR NOMBRE
     *
     * SEARCH USERS BY NAME
     *
     * @param name
     * @return
     */
    @Query("SELECT u FROM User u WHERE u.name = :name")
    public User searchByName(@Param("name") String name);

    /**
     * BUSCAR LOS USUARIOS POR APELLIDO
     *
     * SEARCH USERS BY LAST NAME
     *
     * @param surname
     * @return
     */
    @Query("SELECT u FROM User u WHERE u.surname = :surname")
    public User searchBySurname(@Param("surname") String surname);

    /**
     * BUSCAR LOS USUARIOS POR NÚMERO DE DOCUMENTO
     *
     * FIND USERS BY DOCUMENT NUMBER
     *
     * @param document
     * @return
     */
    @Query("SELECT u FROM User u WHERE u.document = :document")
    public User searchByDocument(@Param("document") String document);

    /**
     * BUSCAR LAS TARJETAS DEL USUARIO
     * FIND THE USER'S CARDS
     * @param idUser
     * @return 
     */
    @Query("SELECT u.card FROM User u WHERE u.id = :idUser")
    public List<Card> searchCardsFromUser(@Param("idUser") String idUser);

    //BUSCAR USUARIOS HABILITADOS
    @Query("SELECT u from User u WHERE u.enabled = true ORDER BY u.surname ASC")
    public List<User> searchUserByEnabled();

    //BUSCAR USUARIOS DESHABILITADOS
    @Query("SELECT u from User u WHERE u.enabled = false ORDER BY u.surname ASC")
    public List<User> searchUserByDisabled();

    //BUSCAR UN USUARIO POR ID DE TARJETA
    @Query("SELECT u.card FROM User u WHERE u.id = :id")
    public User searchUserByIdCard(@Param("id") String id);

    //BUSCAR UNA TARJETA POR ID DE USUARIO
    @Query("SELECT c.user from Card c WHERE c.id = :id ")
    public User searchCardByUser(@Param("id") String id);

}
