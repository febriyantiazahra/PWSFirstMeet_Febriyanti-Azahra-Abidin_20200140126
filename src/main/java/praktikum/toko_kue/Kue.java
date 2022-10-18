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
@Table(name = "kue")
@NamedQueries({
    @NamedQuery(name = "Kue.findAll", query = "SELECT k FROM Kue k"),
    @NamedQuery(name = "Kue.findByIdKue", query = "SELECT k FROM Kue k WHERE k.idKue = :idKue"),
    @NamedQuery(name = "Kue.findByNamaKue", query = "SELECT k FROM Kue k WHERE k.namaKue = :namaKue"),
    @NamedQuery(name = "Kue.findByHarga", query = "SELECT k FROM Kue k WHERE k.harga = :harga")})
public class Kue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Kue")
    private String idKue;
    @Basic(optional = false)
    @Column(name = "Nama_Kue")
    private String namaKue;
    @Basic(optional = false)
    @Column(name = "Harga")
    private String harga;

    public Kue() {
    }

    public Kue(String idKue) {
        this.idKue = idKue;
    }

    public Kue(String idKue, String namaKue, String harga) {
        this.idKue = idKue;
        this.namaKue = namaKue;
        this.harga = harga;
    }

    public String getIdKue() {
        return idKue;
    }

    public void setIdKue(String idKue) {
        this.idKue = idKue;
    }

    public String getNamaKue() {
        return namaKue;
    }

    public void setNamaKue(String namaKue) {
        this.namaKue = namaKue;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKue != null ? idKue.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kue)) {
            return false;
        }
        Kue other = (Kue) object;
        if ((this.idKue == null && other.idKue != null) || (this.idKue != null && !this.idKue.equals(other.idKue))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "praktikum.toko_kue.Kue[ idKue=" + idKue + " ]";
    }
    
}
