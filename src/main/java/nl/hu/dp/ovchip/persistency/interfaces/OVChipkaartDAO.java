package nl.hu.dp.ovchip.persistency.interfaces;

import nl.hu.dp.ovchip.domain.OVChipkaart;
import nl.hu.dp.ovchip.domain.Product;
import nl.hu.dp.ovchip.domain.Reiziger;

import java.util.List;

public interface OVChipkaartDAO {

    boolean save(OVChipkaart ovChipkaart);
    boolean update(OVChipkaart ovChipkaart);
    boolean delete(OVChipkaart ovChipkaart);
    OVChipkaart findById(int id);
    List<OVChipkaart> findByReiziger(Reiziger reiziger);
    List<OVChipkaart> findByProduct(Product product);
    List<OVChipkaart> findAll();
}
