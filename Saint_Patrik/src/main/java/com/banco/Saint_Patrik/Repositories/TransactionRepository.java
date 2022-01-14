package com.banco.Saint_Patrik.Repositories;

import com.banco.Saint_Patrik.Entities.Transaction;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    //BUSCO TODAS LAS TRANSACCIONES ORDENADAS POR FECHA EN FORMA ASCENDENTE
    @Query("SELECT t FROM Transaction t ORDER BY t.dateTransaction ASC")
    public List<Transaction> searchAll();

    //BUSCO LAS TRANSACCIONES POR ID
    @Query("SELECT t FROM Transaction t WHERE t.id = :id")
    public Transaction searchById(@Param("id") String id);

    //BUSCO LAS TRANSACCIONES POR FECHA
    @Query("SELECT t FROM Transaction t WHERE t.dateTransaction = :dateTransaction")
    public Transaction searchByDate(@Param("dateTransaction") Date dateTransaction);

    //BUSCO LAS TRANSACCIONES POR TIPO DE TRANSACCIÓN
    @Query("SELECT t FROM Transaction t WHERE t.type = :type")
    public Transaction searchByType(@Param("type") String type);

    //BUSCO TRANSACCIONES POR USUARIO
    @Query("SELECT t FROM  Transaction t WHERE t.user.id = :id")
    public List<Transaction> searchTransactionByUser(@Param("id") String id);

    //BUSCO TRANSACCIONES POR ID TARJETA
    @Query("SELECT t FROM  Transaction t WHERE t.card.id = :card_id")
    public List<Transaction> searchTransactionByCardId(@Param("card_id") String id);

    //BUSCO TRANSACCIONES POR ALTAS
    @Query("SELECT t from Transaction t WHERE t.enabled = true ORDER BY t.dateTransaction ASC")
    public List<Transaction> searchTransactionByEnabled();

    //BUSCO TRANSACCIONES POR BAJAS
    @Query("SELECT t from Transaction t WHERE t.enabled = false ORDER BY t.dateTransaction ASC")
    public List<Transaction> searchTransactionByDisabled();

    //BUSCO TRANSACCIONES DE LOS ÚLTIMOS 30 DÍAS
    @Query(value = "SELECT t from Transaction t WHERE t.dateTransaction BETWEEN  "
            + "DATE_SUB(NOW(), INTERVAL 30 DAY) AND NOW() AND t.user.id = :user_id", nativeQuery = true)
    public List<Transaction> searchTransactionByLast30Days(@Param("user_id") String id);

}
