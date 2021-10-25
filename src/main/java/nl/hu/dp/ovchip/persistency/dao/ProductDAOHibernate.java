package nl.hu.dp.ovchip.persistency.dao;

import nl.hu.dp.ovchip.domain.Product;
import nl.hu.dp.ovchip.persistency.interfaces.ProductDAO;
import org.hibernate.Session;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.Query;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO {
    private Session session;

    public ProductDAOHibernate (Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Product product) {
        try {
            queueTransaction();
            session.save(product);
            session.getTransaction().commit();
            return true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Product product) {
        try {
            queueTransaction();
            session.update(product);
            session.getTransaction().commit();
            return true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Product product) {
        try {
            queueTransaction();
            session.delete(product);
            session.getTransaction().commit();
            return true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public Product findById(int id) {
        return session.get(Product.class, id);
    }

    @Override
    public List<Product> findAll() {
        Query query = session.createQuery("from Product");

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
