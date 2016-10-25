package io.oilfox.backend.api.shared.test.helpers;

import com.google.inject.Injector;
import io.oilfox.backend.api.shared.helpers.PasswordHash;
import io.oilfox.backend.db.entities.*;
import io.oilfox.backend.db.db.UnitOfWork;
import io.oilfox.backend.api.shared.properties.ApplicationPropertiesLoader;
import io.oilfox.backend.api.shared.properties.FlywayProperties;
import io.oilfox.backend.api.shared.startup.Bootstrapper;
import org.hibernate.Session;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created by ipusic on 1/21/16.
 */
public class TestContext implements AutoCloseable {

    public UnitOfWork unitOfWork;
    public Bootstrapper bootstrapper;

    public TestContext(Class<? extends Bootstrapper> klass) throws Exception {
        this(klass, null);
    }

    public TestContext(Class<? extends Bootstrapper> klass, Consumer<Injector> consumer) throws Exception {

        this.bootstrapper = klass.newInstance();

        Injector injector = InjectorHelper.createInjector();

        ApplicationPropertiesLoader loader = injector.getInstance(ApplicationPropertiesLoader.class);

        // this may be override by the consumer
        loader.setProperty("flyway.enabled", "false");

        FlywayProperties flywayProperties = injector.getInstance(FlywayProperties.class);

        if (consumer != null) {
            consumer.accept(injector);
        }

        // drop old data, load new fixture data
        DatabaseHelper databaseHelper = injector.getInstance(DatabaseHelper.class);
        //databaseHelper.recreateDatabase();

        if (flywayProperties.getEnabled() == false) {
            // no flyway? bootstrap ourselves
           // databaseHelper.bootstrapDatabase();
        }

        this.bootstrapper.start(injector);
        this.unitOfWork = Bootstrapper.resolveInstance(UnitOfWork.class);
    }

    public DbUser createUser(String firstname, String lastname) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return createUser(firstname, lastname, "asdfasdf");
    }

    public DbUser createUser(String firstname, String lastname, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        DbUser account = new DbUser();
        account.firstname = firstname;
        account.lastname = lastname;
        account.passhash = PasswordHash.createHash(password);
        account.email = firstname + "." + lastname + "@does.not.exist";
        account.mobile = "+49 000 0000 0000";
        account.createdAt = new Date();
        db().save(account);

        return account;
    }

    public DbUser createUser(String firstname, String lastname, String email, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        DbUser account = new DbUser();
        account.firstname = firstname;
        account.lastname = lastname;
        account.email = email;
        account.passhash = PasswordHash.createHash(password);
        account.mobile = "+49 000 0000 0000";
        account.createdAt = new Date();
        db().save(account);

        return account;
    }

    public DbUser createAdmin(String firstname, String lastname, String email, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        DbUser account = new DbAdmin();
        account.firstname = firstname;
        account.lastname = lastname;
        account.email = email;
        account.passhash = PasswordHash.createHash(password);
        account.mobile = "+49 000 0000 0000";
        account.createdAt = new Date();
        db().save(account);

        return account;
    }

    public DbOrder createOrder(DbUser user) {
        DbOrder order = new DbOrder();
        order.notes = "some order notes";
        order.price = 123f;
        order.status = "OK";
        order.user = user;
        order.volume = 50;
        db().save(order);

        return order;
    }

    public DbSession createSession(DbUser user) {
        DbSession session = new DbSession();
        session.user = user;
        session.lastactive = new Date();
        session.createdAt = new Date();
        db().save(session);

        return session;
    }

    public DbZipcode createZipcode(String code) {
        DbZipcode zipcode = new DbZipcode();
        zipcode.code = code;
        db().save(zipcode);

        return zipcode;
    }

    public DbOilfox createOilfox(DbTank tank, DbUser user) {
        DbOilfox oilfox = new DbOilfox();
        oilfox.user = user;
        oilfox.hwid = "123456789A";
        oilfox.name = "my oilfox";
        oilfox.token = UUID.randomUUID();
        oilfox.tank = tank;
        oilfox.ssid = "<my-network-ssid>";
        oilfox.lastseen = new Date();
        oilfox.createdAt = new Date();
        db().save(oilfox);

        return oilfox;
    }

    public DbOilfox createOilfox(DbUser user) {
        DbOilfox oilfox = new DbOilfox();
        oilfox.user = user;
        oilfox.hwid = "123456789A";
        oilfox.name = "my oilfox";
        oilfox.token = UUID.randomUUID();
        oilfox.tank = null;
        oilfox.ssid = "<my-network-ssid>";
        oilfox.lastseen = new Date();
        oilfox.createdAt = new Date();
        db().save(oilfox);

        return oilfox;
    }



    public DbTank createTank(DbUser user) {
        DbTank tank = new DbTank();
        tank.user = user;
        tank.name = "some tank";
        tank.createdAt = new Date();
        tank.city = "munich";
        tank.street = "zielstatt";
        tank.country = "germany";
        tank.volume = 10;
        tank.height = 10;
        tank.zipcode = "81379";
        db().save(tank);

        return tank;
    }

    public DbUser createPartner(String name) throws InvalidKeySpecException, NoSuchAlgorithmException {

        //partner.name = name;

        DbUser partner = new DbUser();

        partner.email = "zzzz@yy.com";
        partner.firstname = "zz";
        partner.lastname = "ww";
        partner.type = "partner";
        db().save(partner);


        return partner;
    }

    public DbActivityLog createActivityLog(String body) throws InterruptedException {
        DbActivityLog log = new DbActivityLog();
        log.payload = body;
        log.topic = DbActivityLog.Topic.METERING_REQUEST;
        log.origin = "origin";
        log.createdAt = new Date();
        db().save(log);
        Thread.sleep(100);

        return log;
    }

    public <T> T resolve(Class<T> cls) {
        return Bootstrapper.resolveInstance(cls);
    }

    public Session db() {
        return unitOfWork.db();
    }

    @Override
    public void close() throws Exception {
        this.unitOfWork.close();
        this.bootstrapper.close();
    }
}
