package io.oilfox.backend.api.startup.routines;

import com.google.inject.Inject;
import io.oilfox.backend.db.entities.DbOilfox;
import io.oilfox.backend.db.entities.DbOrder;
import io.oilfox.backend.db.entities.DbTank;
import io.oilfox.backend.db.entities.DbUser;
import io.oilfox.backend.db.db.UnitOfWork;
import io.oilfox.backend.api.shared.helpers.PasswordHash;
import io.oilfox.backend.api.shared.startup.common.StartupRoutine;
import org.hibernate.criterion.Restrictions;

import java.util.*;

/**
 * Created by ipusic on 2/8/16.
 */
public class StartupDefaultFixtures extends StartupRoutine {

    @Inject
    UnitOfWork unitOfWork;

    @Override
    public void run() throws Exception {
        List<String> admins = new ArrayList<>();
        admins.add("admin@oilfox.io");
        admins.add("igor@oilfox.io");
        admins.add("ivan@oilfox.io");

        try {
            unitOfWork.open();

            for (String adminEmail : admins) {
                DbUser existingUser = (DbUser) unitOfWork.db().createCriteria(DbUser.class)
                        .add(Restrictions.eq("email", adminEmail))
                        .uniqueResult();

                if (existingUser == null) {
                    DbUser admin = new DbUser();
                    admin.firstname = "Firstname";
                    admin.lastname = "Lastname";
                    admin.email = adminEmail;
                    admin.mobile = "+49 000 0000 0000";
                    admin.passhash = PasswordHash.createHash("password");
                    admin.isAdmin = true;
                    admin.createdAt = new Date();
                    unitOfWork.db().save(admin);

                    final boolean loadDevFixtures = Objects.equals(System.getenv("DEV_FIXTURES"), "true");
                    if (loadDevFixtures) {
                        DbTank tank = new DbTank();
                        tank.name = "tank";
                        tank.height = 2000;
                        tank.volume = 4000;
                        tank.shape = 0;
                        tank.city = "munich";
                        tank.street = "zielstatt";
                        tank.country = "germany";
                        tank.zipcode = "81379";
                        tank.user = admin;
                        unitOfWork.db().save(tank);

                        DbOrder order = new DbOrder();
                        order.volume = 20;
                        order.price = 100;
                        order.notes = "";
                        order.status = "";
                        order.user = admin;
                        unitOfWork.db().save(order);

                        for (int i = 0; i < 2; i++) {
                            DbOilfox oilfox = new DbOilfox();
                            oilfox.name = UUID.randomUUID().toString();
                            oilfox.hwid = "112233445566";
                            oilfox.tank = tank;
                            oilfox.token = UUID.randomUUID();
                            oilfox.user = admin;
                            oilfox.ssid = UUID.randomUUID().toString();
                            unitOfWork.db().save(oilfox);
                        }
                    }
                }
            }

            unitOfWork.commit();
        } finally {
            unitOfWork.close();
        }
    }
}
