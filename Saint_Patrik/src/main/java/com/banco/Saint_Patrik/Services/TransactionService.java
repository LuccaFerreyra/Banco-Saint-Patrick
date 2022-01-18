package com.banco.Saint_Patrik.Services;

import com.banco.Saint_Patrik.Entities.Card;
import com.banco.Saint_Patrik.Entities.Transaction;
import com.banco.Saint_Patrik.Entities.User;
import com.banco.Saint_Patrik.Enum.Type_Transaction;
import com.banco.Saint_Patrik.Errors.ServiceError;
import com.banco.Saint_Patrik.Repositories.CardRepository;
import com.banco.Saint_Patrik.Repositories.TransactionRepository;
import com.banco.Saint_Patrik.Repositories.UserRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    /**
     * MÉTODO PARA REALIZAR UNA NUEVA TRANSACCIÓN
     *
     * @param idUser
     * @param idUserDestiny
     * @param idCardOwn
     * @param idCardDestiy
     * @param amount
     * @throws ServiceError
     */
    @Transactional
    public void newTransaction(String idUser, String idUserDestiny, String idCardOwn, String idCardDestiy, Double amount) throws ServiceError {

        if (idUser == null || idUser.isEmpty()) {
            throw new ServiceError("EL CLIENTE BUSCADO NO EXISTE");
        }
        if (idUserDestiny == null || idUserDestiny.isEmpty()) {
            throw new ServiceError("EL CLIENTE DESTINO BUSCADO NO EXISTE");
        }
        if (idCardOwn == null || idCardOwn.isEmpty()) {
            throw new ServiceError("EL NÚMERO DE TARJETA DE ORIGEN NO PUEDE ESTAR VACÍO");
        }
        if (idCardDestiy == null || idCardDestiy.isEmpty()) {
            throw new ServiceError("EL NÚMERO DE TARJETA DE DESTINO NO PUEDE ESTAR VACÍO");
        }
        if (amount == null || String.valueOf(amount).isEmpty()) {
            throw new ServiceError("EL MONTO DE LA TRANSACCIÓN NO PUEDE ESTAR VACÍO");
        }

        Transaction transaction1 = new Transaction();

        Date dateTransaction1 = new Date();

        User user1 = userRepository.searchById(idUser);
        Card cardOwn = cardRepository.searchById(idCardOwn);

        if (cardOwn.getCredit() >= amount) {
            transaction1.setUser(user1);
            transaction1.setCard(cardOwn);
            transaction1.setDateTransaction(dateTransaction1);
            transaction1.setAmount(amount);
            transaction1.setType(Type_Transaction.SEND);

            cardOwn.setCredit(cardOwn.getCredit() - amount);

            transactionRepository.save(transaction1);

            Transaction transaction2 = new Transaction();

            Date dateTransaction2 = new Date();

            User user2 = userRepository.searchById(idUser);
            Card cardDestiny = cardRepository.searchById(idCardDestiy);

            transaction2.setUser(user2);
            transaction2.setCard(cardDestiny);
            transaction2.setDateTransaction(dateTransaction2);
            transaction2.setAmount(amount);
            transaction2.setType(Type_Transaction.RECEIVED);

            cardDestiny.setCredit(cardDestiny.getCredit() + amount);

            transactionRepository.save(transaction2);

            /**
             * ENVÍO DE MAIL CONFIRMANDO TRANSACCIÓN
             */
            String from = "bancosaintpatrick@gmail.com";
            String to_1 = user2.getMail();
            String subject = "CONFIRMACIÓN DE TRANSACCIÓN";
            String body_1 = "\n\n Datos de la transferencia: "
                    + "\nDe: " + user1.getName() + " " + user1.getSurname()
                    + "\nCorreo: " + user1.getMail()
                    + "\nPara: " + user2.getName() + " " + user2.getSurname()
                    + "\nCorreo: " + user2.getMail()
                    + "\nMonto: Transferecia por $" + amount;

            MailService mail_1 = new MailService();
            mail_1.sendMail(from, to_1, subject, body_1);

            String to_2 = user1.getMail();

            String body_2 = "\n\n Datos de la transferencia: "
                    + "\nDe: " + user1.getName() + " " + user1.getSurname()
                    + "\nCorreo: " + user1.getMail()
                    + "\nPara: " + user2.getName() + " " + user2.getSurname()
                    + "\nCorreo: " + user2.getMail()
                    + "\nMonto: Transferecia por $" + amount;

            MailService mail_2 = new MailService();
            mail_2.sendMail(from, to_2, subject, body_2);

        } else {
            throw new ServiceError("EL SALDO DISPONIBLE NO ALCANZA PARA REALIZAR LA TRANSACCIÓN");
        }
    }

    /**
     * MÉTODO PARA ELIMINAR UNA TRASACCIÓN
     *
     * @param id
     * @throws ServiceError
     */
    @Transactional
    public void deleteTransaction(String id) throws ServiceError {

        Optional<Transaction> respuesta = transactionRepository.findById(id);
        if (respuesta.isPresent()) {
            Transaction transaction = respuesta.get();
            transactionRepository.delete(transaction);
        } else {
            throw new ServiceError("NO SE ENCONTRÓ LA TRANSACCIÓN BUSCADA");
        }
    }

    /**
     * MÉTODO QUE MUESTRA UNA LISTA DE TODAS LAS TRANSACCIONES DEL BANCO
     *
     * @return
     * @throws ServiceError
     */
    @Transactional(readOnly = true)
    public List<Transaction> searchAll() throws ServiceError {
        try {
            List<Transaction> listTransaction = transactionRepository.searchAll();
            return listTransaction;
        } catch (Exception e) {
            throw new ServiceError("HUBO UN ERROR AL MOSTRAR TODAS LAS TRANSACCIONES DEL CLIENTE");
        }
    }

    /**
     * MÉTODO QUE MUETSRA LAS TRANSACCIONES DEL CLIENTE EN LOS ÚLTIMOS 30 DÍAS
     *
     * @param idUser
     * @return
     * @throws ServiceError
     */
    @Transactional(readOnly = true)
    public List<Transaction> searchTransactionByLast30Days(String idUser) throws ServiceError {
        try {
            List<Transaction> listTransaction = transactionRepository.searchTransactionByLast30Days(idUser);
            return listTransaction;
        } catch (Exception e) {
            throw new ServiceError("HUBO UN ERROR AL MOSTRAR LAS TRANSACCIONES DE LOS ÚLTIMOS 30 DÍAS");
        }
    }

    /**
     * MÉTODO QUE MUETSRA UNA TRANSACCIÓN BUSCADA POR ID
     *
     * @param id
     * @return
     * @throws ServiceError
     */
    @Transactional(readOnly = true)
    public Transaction searchById(String id) throws ServiceError {
        Optional<Transaction> respuesta = transactionRepository.findById(id);
        if (respuesta.isPresent()) {
            Transaction transaction = respuesta.get();
            return transaction;
        } else {
            throw new ServiceError("NO SE ENCONTRÓ LA TRANSACCIÓN BUSCADA");
        }
    }

    /**
     * MÉTODO QUE MUESTRA UNA TRANSACCIÓN POR FECHA
     *
     * @param dateTransaction
     * @return
     * @throws ServiceError
     */
    @Transactional(readOnly = true)
    public Transaction searchByDate(Date dateTransaction) throws ServiceError {
        try {
            return transactionRepository.searchByDate(dateTransaction);
        } catch (Exception e) {
            throw new ServiceError("NO SE ENCONTRÓ LA TRANSACCIÓN BUSCADA");
        }
    }

    /**
     * MÉTODO QUE MUESTRA UNA TRANSACCIÓN POR TIPO
     *
     * @param type
     * @return
     * @throws ServiceError
     */
    @Transactional(readOnly = true)
    public Transaction searchByType(String type) throws ServiceError {
        try {
            return transactionRepository.searchByType(type);
        } catch (Exception e) {
            throw new ServiceError("NO SE ENCONTRÓ LA TRANSACCIÓN BUSCADA");
        }
    }

    /**
     * MÉTODO QUE MUESTRA UNA LISTA DE TODAS LAS TRANSACCIONES DEL CLIENTE
     *
     * @param id
     * @return
     * @throws ServiceError
     */
    @Transactional(readOnly = true)
    public List<Transaction> searchTransactionByUser(String id) throws ServiceError {
        try {
            List<Transaction> listTransaction = transactionRepository.searchTransactionByUser(id);
            return listTransaction;
        } catch (Exception e) {
            throw new ServiceError("NO SE ENCONTRÓ LA TRANSACCIÓN BUSCADA");
        }
    }

    /**
     * MÉTODO QUE MUESTRA UNA LISTA DE TRANSACCIONES POR CADA TARJETA
     *
     * @param id
     * @return
     * @throws ServiceError
     */
    @Transactional(readOnly = true)
    public List<Transaction> searchTransactionByCardId(String id) throws ServiceError {
        try {
            List<Transaction> listTransaction = transactionRepository.searchTransactionByCardId(id);
            return listTransaction;
        } catch (Exception e) {
            throw new ServiceError("NO SE ENCONTRÓ LA TRANSACCIÓN BUSCADA");
        }
    }

    /**
     * MÉTODO PARA DAR DE ALTA UNA TRANSACCIÓN
     *
     * @param id
     * @throws ServiceError
     */
    @Transactional
    public void enableTransaction(String id) throws ServiceError {
        Optional<Transaction> respuesta = transactionRepository.findById(id);
        if (respuesta.isPresent()) {
            Transaction transaction = respuesta.get();
            transaction.setEnabled(true);
            transactionRepository.save(transaction);
        } else {
            throw new ServiceError("NO SE ENCONTRÓ LA TRANSACCIÓN BUSCADA");
        }
    }

    /**
     * MÉTODO PARA DAR DE BAJA UNA TRANSACCIÓN
     *
     * @param id
     * @throws ServiceError
     */
    @Transactional
    public void disableTransaction(String id) throws ServiceError {
        Optional<Transaction> respuesta = transactionRepository.findById(id);
        if (respuesta.isPresent()) {
            Transaction transaction = respuesta.get();
            transaction.setEnabled(false);
            transactionRepository.save(transaction);
        } else {
            throw new ServiceError("NO SE ENCONTRÓ LA TRANSACCIÓN BUSCADA");
        }
    }

    /**
     * MÉTODO PARA MOSTRAR LAS TRANSACCIONES POR ESTADO DE ALTAS
     *
     * @return
     * @throws ServiceError
     */
    @Transactional(readOnly = true)
    public List<Transaction> searchTransactionByEnabled() throws ServiceError {
        try {
            List<Transaction> listTransaction = transactionRepository.searchTransactionByEnabled();
            return listTransaction;
        } catch (Exception e) {
            throw new ServiceError("HUBO UN ERROR AL MOSTRAR TODAS LAS TRANSACCIONES POR ALTAS");
        }
    }

    /**
     * MÉTODO PARA MOSTRAR LAS TRANSACCIONES POR ESTADO DE BAJAS
     *
     * @return
     * @throws ServiceError
     */
    @Transactional(readOnly = true)
    public List<Transaction> searchTransactionByDisbled() throws ServiceError {
        try {
            List<Transaction> listTransaction = transactionRepository.searchTransactionByDisabled();
            return listTransaction;
        } catch (Exception e) {
            throw new ServiceError("HUBO UN ERROR AL MOSTRAR TODAS LAS TRANSACCIONES POR BAJAS");
        }
    }
}
