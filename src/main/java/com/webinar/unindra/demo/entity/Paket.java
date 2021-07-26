package com.webinar.unindra.demo.entity;

import com.webinar.unindra.demo.common.model.ReferenceBase;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "paket")
public class Paket extends ReferenceBase {

    private String namaPaket;
    private BigDecimal harga;

    public String getNamaPaket() {
        return namaPaket;
    }

    public void setNamaPaket(String namaPaket) {
        this.namaPaket = namaPaket;
    }

    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }
}
