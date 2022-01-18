package com.banco.Saint_Patrik.Services;

import com.banco.Saint_Patrik.Entities.Card;
import com.banco.Saint_Patrik.Entities.Transaction;
import com.banco.Saint_Patrik.Errors.ServiceError;
import com.banco.Saint_Patrik.Repositories.CardRepository;
import com.banco.Saint_Patrik.Repositories.TransactionRepository;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class CardService implements UserDetailsService {

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
    public void disable(String idUser, String cardId) throws ServiceError {
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
    public void enable(String idUser, String cardId) throws ServiceError {
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

    /**
     *
     * @param cardNumber
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String cardNumber) throws UsernameNotFoundException {
        Card card = cardRepository.getById(cardNumber);

        if (card != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            /*Creo una lista de permisos - "ROLE_" + cliente.getRol() - concateno la palabra ROL con el enumerador
            ADMIN O USUARIO*/
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + card.getUser().getTypeRole());
            permisos.add(p1);

            //Esto me permite guardar el OBJETO USUARIO LOGUEADO, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            /* HttpSession - RETIENE Y MANTIENE INFORMACIÓN DE LA SESIÓN LOGUEADA CON CIERTO USUARIO*/
            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("clienteSession", card); // llave + valor

            User user = new User(card.getNumberCard(), String.valueOf(card.getPin()), permisos);

            return user;

        } else {

            return null;
        }
    }
}
