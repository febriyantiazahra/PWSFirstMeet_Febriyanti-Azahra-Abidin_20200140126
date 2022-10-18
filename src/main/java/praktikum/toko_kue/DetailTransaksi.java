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
@Table(name = "detail_transaksi")
@NamedQueries({
    @NamedQuery(name = "DetailTransaksi.findAll", query = "SELECT d FROM DetailTransaksi d"),
    @NamedQuery(name = "DetailTransaksi.findByIdDetail", query = "SELECT d FROM DetailTransaksi d WHERE d.idDetail = :idDetail"),
    @NamedQuery(name = "DetailTransaksi.findByIdKue", query = "SELECT d FROM DetailTransaksi d WHERE d.idKue = :idKue"),
    @NamedQuery(name = "DetailTransaksi.findBySubtotal", query = "SELECT d FROM DetailTransaksi d WHERE d.subtotal = :subtotal"),
    @NamedQuery(name = "DetailTransaksi.findByBayar", query = "SELECT d FROM DetailTransaksi d WHERE d.bayar = :bayar"),
    @NamedQuery(name = "DetailTransaksi.findByKembalian", query = "SELECT d FROM DetailTransaksi d WHERE d.kembalian = :kembalian")})
public class DetailTransaksi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Detail")
    private String idDetail;
    @Column(name = "Id_Kue")
    private String idKue;
    @Basic(optional = false)
    @Column(name = "Subtotal")
    private String subtotal;
    @Basic(optional = false)
    @Column(name = "Bayar")
    private String bayar;
    @Basic(optional = false)
    @Column(name = "Kembalian")
    private String kembalian;

    public DetailTransaksi() {
    }

    public DetailTransaksi(String idDetail) {
        this.idDetail = idDetail;
    }

    public DetailTransaksi(String idDetail, String subtotal, String bayar, String kembalian) {
        this.idDetail = idDetail;
        this.subtotal = subtotal;
        this.bayar = bayar;
        this.kembalian = kembalian;
    }

    public String getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(String idDetail) {
        this.idDetail = idDetail;
    }

    public String getIdKue() {
        return idKue;
    }

    public void setIdKue(String idKue) {
        this.idKue = idKue;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getBayar() {
        return bayar;
    }

    public void setBayar(String bayar) {
        this.bayar = bayar;
    }

    public String getKembalian() {
        return kembalian;
    }

    public void setKembalian(String kembalian) {
        this.kembalian = kembalian;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetail != null ? idDetail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetailTransaksi)) {
            return false;
        }
        DetailTransaksi other = (DetailTransaksi) object;
        if ((this.idDetail == null && other.idDetail != null) || (this.idDetail != null && !this.idDetail.equals(other.idDetail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "praktikum.toko_kue.DetailTransaksi[ idDetail=" + idDetail + " ]";
    }
    
}
