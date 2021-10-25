package nl.hu.dp.ovchip.persistency.dao;

import nl.hu.dp.ovchip.domain.Adres;
import nl.hu.dp.ovchip.domain.Reiziger;
import nl.hu.dp.ovchip.persistency.interfaces.AdresDAO;
import org.hibernate.Session;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.Query;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    private Session session;

    public AdresDAOHibernate (Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Adres adres) {
        try {
            queueTransaction();
            session.save(adres);
            session.getTransaction().commit();
            return true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Adres adres) {
        try {
            queueTransaction();
            session.update(adres);
            session.getTransaction().commit();
            return true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            queueTransaction();
            session.delete(adres);
            session.getTransaction().commit();
            return true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public Adres findById(int id) {
        Query query = session.createQuery("from Adres where adresID = :adresID");
        query.setParameter("adresID", id);
        try {
            Object result = query.getSingleResult();
            return result instanceof Adres ? (Adres) result : null;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        Query query = session.createQuery("from Adres where reiziger = :reiziger");
        query.setParameter("reiziger", reiziger);

        try {
            Object result = query.getSingleResult();
            return result instanceof Adres ? (Adres) result : null;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Adres> findAll() {
        Query query = session.createQuery("from Adres");

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
