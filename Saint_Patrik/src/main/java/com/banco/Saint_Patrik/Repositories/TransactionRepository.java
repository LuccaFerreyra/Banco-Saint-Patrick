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

    /**
     * BUSCAR TODAS LAS TRANSACCIONES ORDENADAS POR FECHA EN FORMA ASCENDENTE
     *
     * SEARCH ALL TRANSACTIONS SORTED BY DATE IN ASCENDING FORM
     *
     * @return
     */
    @Query("SELECT t FROM Transaction t ORDER BY t.dateTransaction ASC")
    public List<Transaction> searchAll();

    /**
     * BUSCAR LAS TRANSACCIONES POR ID
     *
     * SEARCH TRANSACTIONS BY ID
     *
     * @param id
     * @return
     */
    @Query("SELECT t FROM Transaction t WHERE t.id = :id")
    public Transaction searchById(@Param("id") String id);

    /**
     * BUSCAR LAS TRANSACCIONES POR FECHA
     *
     * SEARCH TRANSACTIONS BY DATE
     *
     * @param dateTransaction
     * @return
     */
    @Query("SELECT t FROM Transaction t WHERE t.dateTransaction = :dateTransaction")
    public Transaction searchByDate(@Param("dateTransaction") Date dateTransaction);

    /**
     * BUSCAR LAS TRANSACCIONES POR TIPO DE TRANSACCIÓN
     *
     * SEARCH TRANSACTIONS BY TRANSACTION TYPE
     *
     * @param type
     * @return
     */
    @Query("SELECT t FROM Transaction t WHERE t.type = :type")
    public Transaction searchByType(@Param("type") String type);

    /**
     * BUSCAR TRANSACCIONES POR USUARIO
     *
     * SEARCH TRANSACTIONS BY USER
     *
     * @param id
     * @return
     */
    @Query("SELECT t FROM  Transaction t WHERE t.user.id = :id")
    public List<Transaction> searchTransactionByUser(@Param("id") String id);

    /**
     * BUSCAR TRANSACCIONES POR ID TARJETA
     *
     * SEARCH TRANSACTIONS BY CARD ID
     *
     * @param id
     * @return
     */
    @Query("SELECT t FROM  Transaction t WHERE t.card.id = :card_id")
    public List<Transaction> searchTransactionByCardId(@Param("card_id") String id);

    /**
     * BUSCAR TRANSACCIONES HABILITADAS
     *
     * SEARCH FOR ENABLED TRANSACTIONS
     *
     * @return
     */
    @Query("SELECT t from Transaction t WHERE t.enabled = true ORDER BY t.dateTransaction ASC")
    public List<Transaction> searchTransactionByEnabled();

    /**
     * BUSCAR TRANSACCIONES DESHABILITADAS
     *
     * SEARCH FOR DISABLED TRANSACTIONS
     *
     * @return
     */
    @Query("SELECT t from Transaction t WHERE t.enabled = false ORDER BY t.dateTransaction ASC")
    public List<Transaction> searchTransactionByDisabled();

    /**
     * BUSCAR TRANSACCIONES DE LOS ÚLTIMOS 30 DÍAS
     *
     * SEARCH FOR TRANSACTIONS FROM THE LAST 30 DAYS
     *
     * @param id
     * @return
     */
    @Query(value = "SELECT t from Transaction t WHERE t.dateTransaction BETWEEN  "
            + "DATE_SUB(NOW(), INTERVAL 30 DAY) AND NOW() AND t.user.id = :user_id", nativeQuery = true)
    public List<Transaction> searchTransactionByLast30Days(@Param("user_id") String id);

}
