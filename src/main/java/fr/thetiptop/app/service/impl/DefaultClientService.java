package fr.thetiptop.app.service.impl;

import fr.thetiptop.app.models.ClientModel;
import fr.thetiptop.app.repository.ClientRepository;
import fr.thetiptop.app.service.ClientService;
import fr.thetiptop.app.service.CustomUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class DefaultClientService implements ClientService {

    private final Log logger = LogFactory.getLog(this.getClass());

    private ClientRepository clientRepository;

    private CustomUserService customUserService;

    public DefaultClientService(ClientRepository clientRepository, CustomUserService customUserService) {
        this.clientRepository = clientRepository;
        this.customUserService = customUserService;
    }

    @Override
    public ClientModel findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public ClientModel getCurrentCustomer() {
        String email = customUserService.getCurrentUsername();
        ClientModel currentUser = this.findByEmail(email);

        if (currentUser == null) {
            logger.error("User should exist and should be of type Client/Customer");
            throw new IllegalStateException("User should exist and should be of type Client/Customer");
        }
        return currentUser;
    }


}
