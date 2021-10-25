package nl.hu.dp.ovchip.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Reiziger {
    @Id
    @Column(name = "reiziger_id")
    private int reizigerID;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private LocalDate geboortedatum;
    @OneToOne(mappedBy = "reiziger", cascade = CascadeType.ALL)
    private Adres adres;
    @OneToMany(mappedBy = "reiziger", cascade = CascadeType.ALL)
    private List<OVChipkaart> ovChipkaartList = new ArrayList<>();

    public Reiziger(int reizigerID, String voorletters, String tussenvoegsel, String achternaam, LocalDate geboortedatum) {
        this.reizigerID = reizigerID;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public Reiziger() {

    }

    public int getReizigerID() {
        return reizigerID;
    }

    public void setReizigerID(int reizigerID) {
        this.reizigerID = reizigerID;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletter) {
        this.voorletters = voorletter;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public List<OVChipkaart> getOvChipkaartList() {
        return ovChipkaartList;
    }

    public void addOVChipkaart(OVChipkaart ovChipkaart) {
        if (!ovChipkaartList.contains(ovChipkaart)) {
            ovChipkaartList.add(ovChipkaart);
        }
    }

    public void addOVChipkaartList(List<OVChipkaart> ovChipkaartList) {
        for (OVChipkaart ovChipkaart : ovChipkaartList) {
            if (!ovChipkaartList.contains(ovChipkaart)) {
                ovChipkaartList.add(ovChipkaart);
            }
        }
    }

    @Override
    public String toString() {
        return "Reiziger{" +
                "reiziger_id=" + reizigerID +
                ", voornaam='" + voorletters + '\'' +
                ", tussenvoegsel='" + tussenvoegsel + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", geboortedatum=" + geboortedatum +
                ", adres=" + adres +
                '}';
    }
}
