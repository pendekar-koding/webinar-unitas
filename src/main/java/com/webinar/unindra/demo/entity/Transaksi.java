package com.webinar.unindra.demo.entity;

import com.webinar.unindra.demo.common.model.ReferenceBase;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transaksi")
public class Transaksi extends ReferenceBase {

    private Integer jumlah;
    private BigDecimal totalHarga;

    private Customer customer;
    private Paket paket;

    @Column(name = "jumlah", nullable = false)
    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }
    @Column(name = "total_harga", nullable = false)
    public BigDecimal getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(BigDecimal totalHarga) {
        this.totalHarga = totalHarga;
    }
    @ManyToOne
    @JoinColumn(name = "customer_id")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    @ManyToOne
    @JoinColumn(name = "paket_id")
    public Paket getPaket() {
        return paket;
    }

    public void setPaket(Paket paket) {
        this.paket = paket;
    }
}
