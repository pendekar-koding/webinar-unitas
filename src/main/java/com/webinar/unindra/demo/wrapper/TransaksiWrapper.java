package com.webinar.unindra.demo.wrapper;

import com.webinar.unindra.demo.common.wrapper.ReferenceBaseWrapper;

import java.math.BigDecimal;

public class TransaksiWrapper extends ReferenceBaseWrapper {

    private String kodeTransaksi;
    private Integer jumlah;
    private BigDecimal totalHarga;

//    private Customer customer;
    private Long idCustomer;
    private String namaCustomer;

//    private Paket paket;
    private Long idPaket;
    private String namaPaket;

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public BigDecimal getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(BigDecimal totalHarga) {
        this.totalHarga = totalHarga;
    }

    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getNamaCustomer() {
        return namaCustomer;
    }

    public void setNamaCustomer(String namaCustomer) {
        this.namaCustomer = namaCustomer;
    }

    public Long getIdPaket() {
        return idPaket;
    }

    public void setIdPaket(Long idPaket) {
        this.idPaket = idPaket;
    }

    public String getNamaPaket() {
        return namaPaket;
    }

    public void setNamaPaket(String namaPaket) {
        this.namaPaket = namaPaket;
    }
}
