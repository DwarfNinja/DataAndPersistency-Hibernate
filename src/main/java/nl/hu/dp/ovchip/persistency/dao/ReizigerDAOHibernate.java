package nl.hu.dp.ovchip.persistency.dao;

import nl.hu.dp.ovchip.domain.Reiziger;
import nl.hu.dp.ovchip.persistency.interfaces.ReizigerDAO;
import org.hibernate.Session;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private Session session;

    public ReizigerDAOHibernate (Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            queueTransaction();
            session.save(reiziger);
            session.getTransaction().commit();
            return true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            queueTransaction();
            session.update(reiziger);
            session.getTransaction().commit();
            return true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            queueTransaction();
            session.delete(reiziger);
            session.getTransaction().commit();
            return true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public Reiziger findById(int id) {
        return session.get(Reiziger.class, id);
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        Query query = session.createQuery("from Reiziger where geboortedatum = :geboortedatum");
        query.setParameter("geboortedatum", LocalDate.parse(datum));

        return query.getResultList();
    }

    @Override
    public List<Reiziger> findAll() {
        Query query = session.createQuery("from Reiziger");

        return query.getResultList();
    }

    private boolean queueTransaction() {
        try {
            if (session.getTransaction().getStatus() != TransactionStatus.ACTIVE) {
                session.beginTransaction();
                return true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }
}
