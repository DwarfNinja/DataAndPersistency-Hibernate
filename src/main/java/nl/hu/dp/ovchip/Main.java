package nl.hu.dp.ovchip;

import nl.hu.dp.ovchip.domain.Adres;
import nl.hu.dp.ovchip.domain.OVChipkaart;
import nl.hu.dp.ovchip.domain.Product;
import nl.hu.dp.ovchip.domain.Reiziger;
import nl.hu.dp.ovchip.persistency.dao.AdresDAOHibernate;
import nl.hu.dp.ovchip.persistency.dao.OVChipkaartDAOHibernate;
import nl.hu.dp.ovchip.persistency.dao.ProductDAOHibernate;
import nl.hu.dp.ovchip.persistency.dao.ReizigerDAOHibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
@SpringBootApplication
@PropertySource("classpath:application.properties")
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Main.class, args);
        testFetchAll();
        testReizigerDAO();
        testAdresDAO();
        testOVChipkaartDAO();
        testProductDAO();
    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }

    private static void testReizigerDAO() throws SQLException {
        Session session = getSession();

        ReizigerDAOHibernate reizigerDAO= new ReizigerDAOHibernate(session);

        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = reizigerDAO.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", LocalDate.parse(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        reizigerDAO.save(sietske);
        reizigers = reizigerDAO.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        // Update aangemaakte reiziger
        System.out.println("[Test] ReizigerDAO.update() geeft de volgende resultaten na het updaten van de achternaam:\nVoor de update: " + reizigerDAO.findById(77));
        sietske.setAchternaam("Jansen");
        reizigerDAO.update(sietske);
        System.out.println("Na de update: " + reizigerDAO.findById(77) + "\n");

        // Vindt reiziger met gegeven ID
        System.out.println("[Test] ReizigerDAO.findbyId() met ID 77 geeft de volgende reiziger: \n" + reizigerDAO.findById(77) + "\n");

        // Vindt reizigers met gegeven geboortedatum
        System.out.println("[Test] ReizigerDAO.findByGbdatum() met geboortedatum 1981-03-14 geeft de volgende reiziger: \n" + reizigerDAO.findByGbdatum(gbdatum) + "\n");

        // Vindt delete reiziger
        System.out.println("[Test] ReizigerDAO.delete() geeft de volgende resultaten na het deleten van reiziger met ID 77");
        reizigerDAO.delete(reizigerDAO.findById(77));
        for (Reiziger reiziger : reizigerDAO.findAll()) {
            System.out.println(reiziger);
        }
        System.out.println();
        session.close();
    }

    private static void testAdresDAO() throws SQLException {
        Session session = getSession();

        AdresDAOHibernate adresDAO = new AdresDAOHibernate(session);
        ReizigerDAOHibernate reizigerDAO = new ReizigerDAOHibernate(session);

        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle adressen op uit de database
        List<Adres> adressen = adresDAO.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // Maak een nieuw adres aan en persisteer deze in de database
        Reiziger johndoe = new Reiziger(25, "J", "", "Doe", LocalDate.parse("1995-05-25"));
        Adres heidelberglaan = new Adres(77, "3584 CS", "15", "Heidelberglaan", "Utrecht", johndoe);
        johndoe.setAdres(heidelberglaan);
        reizigerDAO.save(johndoe);
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        adressen = adresDAO.findAll();
        System.out.println(adressen.size() + " adressen\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        // Update aangemaakte adres
        System.out.println("[Test] AdresDAO.update() geeft de volgende resultaten na het updaten van de straatnaam:\nVoor de update: " + adresDAO.findById(77));
        heidelberglaan.setStraat("Padualaan");
        adresDAO.update(heidelberglaan);
        System.out.println("Na de update: " + adresDAO.findById(77) + "\n");

        // Vindt reiziger met gegeven ID
        System.out.println("[Test] AdresDAO.findbyId() met ID 77 geeft het volgende adres: \n" + adresDAO.findById(77) + "\n");

        // Vindt adres met gegeven reiziger
        System.out.println("[Test] AdresDAO.findByReiziger() geeft het volgende adres: \n" + adresDAO.findByReiziger(johndoe) + "\n");

        // Vindt delete adres
        System.out.println("[Test] AdresDAO.delete() geeft de volgende resultaten na het deleten van adres met ID 77");
        adresDAO.delete(adresDAO.findById(77));
        reizigerDAO.delete(reizigerDAO.findById(25));
        for (Adres adres : adresDAO.findAll()) {
            System.out.println(adres);
        }
        System.out.println();
        session.close();
    }

    private static void testOVChipkaartDAO() throws SQLException {
        Session session = getSession();

        OVChipkaartDAOHibernate ovChipkaartDAO = new OVChipkaartDAOHibernate(session);
        ReizigerDAOHibernate reizigerDAO = new ReizigerDAOHibernate(session);

        System.out.println("\n---------- Test OVChipkaartDAO -------------");

        // Haal alle adressen op uit de database
        List<OVChipkaart> ovchipkaarten = ovChipkaartDAO.findAll();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende ovchipkaarten:");
        for (OVChipkaart o : ovchipkaarten) {
            System.out.println(o);
        }
        System.out.println();

        // Maak een nieuw ovchipkaart aan en persisteer deze in de database
        Reiziger johndoe = new Reiziger(25, "J", "", "Doe", LocalDate.parse("1995-05-25"));
        reizigerDAO.save(johndoe);
        OVChipkaart ovChipkaart = new OVChipkaart(85, LocalDate.of(2020,10,6), 2, 10.00, johndoe);
        ovChipkaartDAO.save(ovChipkaart);
        System.out.print("[Test] Eerst " + ovchipkaarten.size() + " ovchipkaarten, na OVChipkaartDAO.save() ");
        ovchipkaarten = ovChipkaartDAO.findAll();
        System.out.println(ovchipkaarten.size() + " ovchipkaarten\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        // Update aangemaakte ovchipkaart
        System.out.println("[Test] OVChipkaartDAO.update() geeft de volgende resultaten na het updaten van de saldo:\nVoor de update: " + ovChipkaartDAO.findById(85));
        ovChipkaart.setSaldo(26.00);
        ovChipkaartDAO.update(ovChipkaart);
        System.out.println("Na de update: " + ovChipkaartDAO.findById(85) + "\n");

        // Vindt reiziger met gegeven ID
        System.out.println("[Test] OVChipkaartDAO.findbyId() met ID 85 geeft het volgende adres: \n" + ovChipkaartDAO.findById(85) + "\n");

        // Vindt ovchipkaart met gegeven reiziger
        System.out.println("[Test] OVChipkaartDAO.findByReiziger() geeft de volgende ovchipkaart: \n" + ovChipkaartDAO.findByReiziger(johndoe) + "\n");

        // Vindt delete adres
        System.out.println("[Test] OVChipkaartDAO.delete() geeft de volgende resultaten na het deleten van adres met ID 77");
        ovChipkaartDAO.delete(ovChipkaartDAO.findById(85));
        reizigerDAO.delete(johndoe);
        for (OVChipkaart ovchipkaart : ovChipkaartDAO.findAll()) {
            System.out.println(ovchipkaart);
        }
        System.out.println();
        session.close();
    }

    private static void testProductDAO() throws SQLException {
        Session session = getSession();

        ProductDAOHibernate productDAO = new ProductDAOHibernate(session);

        System.out.println("\n---------- Test ProductDAO -------------");

        // Haal alle producten op uit de database
        List<Product> producten = productDAO.findAll();
        System.out.println("[Test] ProdoductDAO.findAll() geeft de volgende ovchipkaarten:");
        for (Product p : producten) {
            System.out.println(p);
        }
        System.out.println();

        // Maak een nieuw product aan en persisteer deze in de database
        Product product = new Product(44, "Maandproduct", "Dit is een maandproduct", 77.00);
        productDAO.save(product);
        System.out.print("[Test] Eerst " + producten.size() + " producten, na ProductDAO.save() ");
        producten = productDAO.findAll();
        System.out.println(producten.size() + " ovchipkaarten\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        // Update aangemaakte product
        System.out.println("[Test] ProductDAO.update() geeft de volgende resultaten na het updaten van de naam:\nVoor de update: " + productDAO.findById(44));
        product.setNaam("JaarProduct");
        productDAO.update(product);
        System.out.println("Na de update: " + productDAO.findById(44) + "\n");

        // Vindt het product met gegeven ID
        System.out.println("[Test] OVChipkaartDAO.findbyId() met ID 85 geeft het volgende product: \n" + productDAO.findById(44) + "\n");

        // Vindt delete adres
        System.out.println("[Test] ProductDAO.delete() geeft de volgende producten na het deleten van id met ID 44");
        productDAO.delete(productDAO.findById(44));
        for (Product product1 : productDAO.findAll()) {
            System.out.println(product1);
        }
        System.out.println();
    }
}