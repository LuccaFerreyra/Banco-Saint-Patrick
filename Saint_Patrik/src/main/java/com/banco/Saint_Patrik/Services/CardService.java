package com.banco.Saint_Patrik.Services;

import com.banco.Saint_Patrik.Entities.Card;
import com.banco.Saint_Patrik.Entities.Transaction;
import com.banco.Saint_Patrik.Errors.ServiceError;
import com.banco.Saint_Patrik.Repositories.CardRepository;
import com.banco.Saint_Patrik.Repositories.TransactionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * MÉTODO QUE MUESTRA EL SALDO DE UNA DE LAS TARJETAS DEL CLIENTE
     *
     * @param id
     * @return
     * @throws ServiceError
     */
    @Transactional(readOnly = true)
    public Double searchcardAmountByIdCard(String id) throws ServiceError {
        try {
            Card card = cardRepository.searchById(id);
            return card.getCredit();
        } catch (Exception e) {
            throw new ServiceError("HUBO UN ERROR AL MOSTRAR EL SALDO DE LA TARJETA");
        }
    }

    /**
     * MÉTODO QUE MUESTRA UNA LISTA DE TODAS LAS TRANSACCIONES DE UNA TARJETA
     * DEL CLIENTE
     *
     * @param id
     * @return
     * @throws ServiceError
     */
    @Transactional(readOnly = true)
    public List<Transaction> searchAllTransactions(String id) throws ServiceError {
        try {
            List<Transaction> listTransactions = transactionRepository.searchTransactionByCardId(id);
            return listTransactions;
        } catch (Exception e) {
            throw new ServiceError("HUBO UN ERROR AL MOSTRAR TODAS LAS TRANSACCIONES DE ESTA TARJETA");
        }
    }

    /**
     * MÉTODO PARA DAR DE BAJA UNA TARJETA DEL CLIENTE (PARA USUARIOS ROL ADMIN)
     *
     * @param idUser
     * @param cardId
     * @throws ServiceError
     */
    @Transactional
    public void unable(String idUser, String cardId) throws ServiceError {
        try {
            Card card = cardRepository.searchById(cardId);

            card.setEnabled(Boolean.FALSE);
            cardRepository.save(card);

        } catch (Exception e) {
            throw new ServiceError("HUBO UN ERROR AL DAR DE BAJA LA TARJETA");
        }
    }

    /**
     * MÉTODO PARA DAR DE ALTA UNA TARJETA DEL CLIENTE (PARA USUARIOS ROL ADMIN)
     *
     * @param idUser
     * @param cardId
     * @throws ServiceError
     */
    @Transactional
    public void able(String idUser, String cardId) throws ServiceError {
        try {
            Card card = cardRepository.searchById(cardId);

            card.setEnabled(Boolean.TRUE);
            cardRepository.save(card);
        } catch (Exception e) {
            throw new ServiceError("HUBO UN ERROR AL DAR DE ALTA LA TARJETA");
        }
    }

    /**
     * MÉTODO PARA MOSTRAR LAS TARJETAS POR ESTADO DE ALTAS
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<Card> cardListEnabled() {
        return cardRepository.searchCardByEnabled();
    }

    /**
     * MÉTODO PARA MOSTRAR LAS TARJETAS POR ESTADO DE BAJAS
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<Card> cardListDisabled() {
        return cardRepository.searchCardByDisabled();
    }
}
