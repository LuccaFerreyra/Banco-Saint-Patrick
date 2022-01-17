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

    //BUSCO TODOS LOS USUARIOS ORDENADOS POR APELLIDO EN FORMA DESCENDENTE
    @Query("SELECT u FROM User u ORDER BY u.surname DESC")
    public List<User> searchAll();

    //BUSCO LOS USUARIOS POR ID
    @Query("SELECT u FROM User u WHERE u.id = :id")
    public User searchById(@Param("id") String id);

    //BUSCO LOS USUARIOS POR NOMBRE
    @Query("SELECT u FROM User u WHERE u.name = :name")
    public User searchByName(@Param("name") String name);

    //BUSCO LOS USUARIOS POR APELLIDO
    @Query("SELECT u FROM User u WHERE u.surname = :surname")
    public User searchBySurname(@Param("surname") String surname);

    //BUSCO LOS USUARIOS POR NÚMERO DE DOCUMENTO
    @Query("SELECT u FROM User u WHERE u.document = :document")
    public User searchByDocument(@Param("document") String document);

    //BUSCO LAS TARJETAS DEL USUARIO
    @Query("SELECT u.card FROM User u WHERE u.id = :idUser")
    public List<Card> searchCardsFromUser(@Param("idUser") String idUser);

    //BUSCO USUARIOS POR ALTAS
    @Query("SELECT u from User u WHERE u.enabled = true ORDER BY u.surname ASC")
    public List<User> searchUserByEnabled();

    //BUSCO USUARIOS POR BAJAS
    @Query("SELECT u from User u WHERE u.enabled = false ORDER BY u.surname ASC")
    public List<User> searchUserByDisabled();

    //BUSCO UN USUARIO POR ID DE TARJETA
    @Query("SELECT u.card FROM User u WHERE u.id = :id")
    public User searchUserByIdCard(@Param("id") String id);

    //BUSCO UNA TARJETA POR ID DE USUARIO
    @Query("SELECT c.user from Card c WHERE c.id = :id ")
    public User searchCardByUser(@Param("id") String id);

}
