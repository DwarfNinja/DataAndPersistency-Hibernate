package nl.hu.dp.ovchip.persistency.dao;

import nl.hu.dp.ovchip.domain.OVChipkaart;
import nl.hu.dp.ovchip.domain.Reiziger;
import nl.hu.dp.ovchip.persistency.interfaces.AdresDAO;
import nl.hu.dp.ovchip.persistency.interfaces.OVChipkaartDAO;
import nl.hu.dp.ovchip.persistency.interfaces.ReizigerDAO;
import org.hibernate.Session;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.Query;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private Session session;

    public ReizigerDAOHibernate (Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            if (session.getTransaction().getStatus() != TransactionStatus.ACTIVE) {
                session.beginTransaction();
            }
            session.save(reiziger);

            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            if (session.getTransaction().getStatus() != TransactionStatus.ACTIVE) {
                session.beginTransaction();
            }
            session.update(reiziger);

            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            if (session.getTransaction().getStatus() != TransactionStatus.ACTIVE) {
                session.beginTransaction();
            }

            session.delete(reiziger);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
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
        query.setParameter("geboortedatum", java.sql.Date.valueOf(datum));

        return query.getResultList();
    }

    @Override
    public List<Reiziger> findAll() {
        Query query = session.createQuery("from Reiziger");

        return query.getResultList();
    }
}
