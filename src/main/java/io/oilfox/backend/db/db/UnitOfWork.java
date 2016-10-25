package io.oilfox.backend.db.db;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.inject.Inject;

/**
 * Created by ipusic on 1/21/16.
 */
public class UnitOfWork {
    SessionProvider sessionProvider;

    // created by open
    private static ThreadLocal<Session> session = new ThreadLocal<>();
    private static ThreadLocal<Transaction> tx = new ThreadLocal<>();

    @Inject
    public UnitOfWork(SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    public void open() {
        session.set(sessionProvider.open());
        tx.set(session.get().beginTransaction());
    }

    public void openTx() {
        tx.set(session.get().beginTransaction());
    }

    public void close() {

        if (tx.get() != null) {

            if (tx.get().getStatus().isNotOneOf(TransactionStatus.COMMITTED, TransactionStatus.COMMITTING, TransactionStatus.NOT_ACTIVE)) {
                tx.get().rollback();
            }

            tx.remove();
        }

        if (session.get() != null ) {

            session.get().close();
            session.remove();
        }
    }

    public void commit() {
        tx.get().commit();
    }

    public Session db() {
        if (session == null || session.get() == null) {
            open();
        }

        return session.get();
    }
}
