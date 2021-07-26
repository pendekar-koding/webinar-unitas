package com.webinar.unindra.demo.wrapper;

import com.webinar.unindra.demo.common.wrapper.ReferenceBaseWrapper;

import java.math.BigDecimal;

public class PaketWrapper extends ReferenceBaseWrapper {

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
