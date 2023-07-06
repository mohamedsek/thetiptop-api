package fr.thetiptop.app.service;

import fr.thetiptop.app.models.UserModel;

import java.util.Optional;

public interface CustomUserService {

    Optional<UserModel> getCurrentUser();

    String getCurrentUsername();
}
