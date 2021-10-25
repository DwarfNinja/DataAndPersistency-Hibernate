package nl.hu.dp.ovchip.persistency.dao;

import nl.hu.dp.ovchip.domain.OVChipkaart;
import nl.hu.dp.ovchip.domain.Product;
import nl.hu.dp.ovchip.domain.Reiziger;
import nl.hu.dp.ovchip.persistency.interfaces.OVChipkaartDAO;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {
    private Session session;

    public OVChipkaartDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try {
            session.beginTransaction();
            session.save(ovChipkaart);
            session.getTransaction().commit();
            return true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try {
            session.beginTransaction();
            session.update(ovChipkaart);
            session.getTransaction().commit();
            return true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try {
            session.beginTransaction();
            session.delete(ovChipkaart);
            session.getTransaction().commit();
            return true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public OVChipkaart findById(int id) {
        return session.get(OVChipkaart.class, id);
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        Query query = session.createQuery("from ov_chipkaart where reiziger = :reiziger");
        query.setParameter("reiziger", reiziger);

        return query.getResultList();
    }

    @Override
    public List<OVChipkaart> findByProduct(Product product) {
        Query query = session.createQuery("from ov_chipkaart ovc join fetch ovc.productList ovcp where ovcp.productNummer = :product_nummer");
        query.setParameter("product_nummer", product.getProductNummer());

        return query.getResultList();
    }

    @Override
    public List<OVChipkaart> findAll() {
        Query query = session.createQuery("from ov_chipkaart");

        return query.getResultList();
    }
}
