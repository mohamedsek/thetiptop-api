package fr.thetiptop.app.service;

import fr.thetiptop.app.models.ClientModel;

public interface ClientService {

    ClientModel findByEmail(String email);

    ClientModel getCurrentCustomer();

}
