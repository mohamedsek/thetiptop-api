package fr.thetiptop.app.service.impl;

import fr.thetiptop.app.models.ClientModel;
import fr.thetiptop.app.repository.ClientRepository;
import fr.thetiptop.app.service.ClientService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class DefaultClientService implements ClientService {

    private final Log logger = LogFactory.getLog(this.getClass());

    private ClientRepository clientRepository;

    public DefaultClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;

    }

    @Override
    public ClientModel findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }


}
