package com.banco.Saint_Patrik.Services;

import com.banco.Saint_Patrik.Entities.Card;
import com.banco.Saint_Patrik.Entities.User;
import com.banco.Saint_Patrik.Errors.ServiceError;
import com.banco.Saint_Patrik.Repositories.CardRepository;
import com.banco.Saint_Patrik.Repositories.UserRepository;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    /**
     *
     * @param idUser
     * @return
     */
    @Transactional(readOnly = true)
    public List<Card> cardsFromUser(String idUser) {
        return userRepository.searchCardsFromUser(idUser);
    }

    /**
     * MÉTODO PARA MOSTRAR LOS USUARIOS POR ESTADO DE ALTAS
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<User> userListEnabled() {
        return userRepository.searchUserByEnabled();
    }

    /**
     * MÉTODO PARA MOSTRAR LOS USUARIOS POR ESTADO DE BAJAS
     *
     * @return
     * @throws com.banco.Saint_Patrik.Errors.ServiceError
     */
    @Transactional(readOnly = true)
    public List<User> userListDisabled() throws ServiceError {
        if (userRepository.searchUserByDisabled() == null) {
            throw new ServiceError("no hay usuarios deshabilitados");
        }
        return userRepository.searchUserByDisabled();
    }

    /**
     * METODO PARA TRAER UN USUARIO ESPECIFICO
     *
     * @param idUser
     * @throws com.banco.Saint_Patrik.Errors.ServiceError
     * @Param idUser
     * @return User
     */
    public User user(String idUser) throws ServiceError {
        if (userRepository.searchById(idUser) == null) {
            throw new ServiceError("el usuario no existe");
        }
        return userRepository.searchById(idUser);
    }
}
