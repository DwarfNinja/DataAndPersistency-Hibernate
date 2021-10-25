package nl.hu.dp.ovchip.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @Column(name="product_nummer")
    private int productNummer;
    private String naam;
    private String beschrijving;
    private double prijs;

//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private OVChipkaart ovChipkaart;

    @ManyToMany(mappedBy = "productList", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OVChipkaart> ovChipkaartList = new ArrayList<OVChipkaart>() {
    };

    public Product(int productNummer, String naam, String beschrijving, double prijs) {
        this.productNummer = productNummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public Product() {
    }

    public int getProductNummer() {
        return productNummer;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public List<OVChipkaart> getOvChipkaartList() {
        return ovChipkaartList;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productNummer=" + productNummer +
                ", naam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                ", prijs=" + prijs +'}';
    }
}
