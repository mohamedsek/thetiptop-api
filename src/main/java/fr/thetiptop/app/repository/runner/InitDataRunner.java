package fr.thetiptop.app.repository.runner;


import fr.thetiptop.app.constant.Constants;
import fr.thetiptop.app.models.UserRoleModel;
import fr.thetiptop.app.repository.UserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Order(1)
public class InitDataRunner implements CommandLineRunner {

    final private static Logger LOG = LoggerFactory.getLogger(InitDataRunner.class);
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public void run(String... args) throws Exception {
        LOG.trace("- String Data initialization ....");
        addRoles();
        LOG.trace("- Data initialization Done !");
    }


    private void addRoles() {
        addRole(Constants.Roles.CUSTOMER);
        addRole(Constants.Roles.ADMIN);
        addRole(Constants.Roles.CHECKOUT_MACHINE);
    }

    private void addRole(String roleName) {
        Optional<UserRoleModel> byName = userRoleRepository.findByName(roleName);
        if (byName.isEmpty()) {
            UserRoleModel build = UserRoleModel.builder().name(roleName).build();
            userRoleRepository.save(build);
        }
    }
}
