/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praktikum.toko_kue;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Febriyanti Azahra
 */
@Entity
@Table(name = "kasir")
@NamedQueries({
    @NamedQuery(name = "Kasir.findAll", query = "SELECT k FROM Kasir k"),
    @NamedQuery(name = "Kasir.findByIdKasir", query = "SELECT k FROM Kasir k WHERE k.idKasir = :idKasir"),
    @NamedQuery(name = "Kasir.findByNamaKasir", query = "SELECT k FROM Kasir k WHERE k.namaKasir = :namaKasir"),
    @NamedQuery(name = "Kasir.findByJenisKel", query = "SELECT k FROM Kasir k WHERE k.jenisKel = :jenisKel")})
public class Kasir implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Kasir")
    private String idKasir;
    @Basic(optional = false)
    @Column(name = "Nama_Kasir")
    private String namaKasir;
    @Basic(optional = false)
    @Column(name = "Jenis_Kel")
    private Character jenisKel;

    public Kasir() {
    }

    public Kasir(String idKasir) {
        this.idKasir = idKasir;
    }

    public Kasir(String idKasir, String namaKasir, Character jenisKel) {
        this.idKasir = idKasir;
        this.namaKasir = namaKasir;
        this.jenisKel = jenisKel;
    }

    public String getIdKasir() {
        return idKasir;
    }

    public void setIdKasir(String idKasir) {
        this.idKasir = idKasir;
    }

    public String getNamaKasir() {
        return namaKasir;
    }

    public void setNamaKasir(String namaKasir) {
        this.namaKasir = namaKasir;
    }

    public Character getJenisKel() {
        return jenisKel;
    }

    public void setJenisKel(Character jenisKel) {
        this.jenisKel = jenisKel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKasir != null ? idKasir.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kasir)) {
            return false;
        }
        Kasir other = (Kasir) object;
        if ((this.idKasir == null && other.idKasir != null) || (this.idKasir != null && !this.idKasir.equals(other.idKasir))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "praktikum.toko_kue.Kasir[ idKasir=" + idKasir + " ]";
    }
    
}
