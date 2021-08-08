package com.webinar.unindra.demo.wrapper;

import com.webinar.unindra.demo.common.wrapper.ReferenceBaseWrapper;

import java.math.BigDecimal;

public class TransactionWrapper extends ReferenceBaseWrapper {

    private String codeTransaction;
    private Integer total;
    private BigDecimal totalPrice;

//    private Customer customer;
    private Long idCustomer;
    private String namaCustomer;

//    private Paket paket;
    private Long idPaket;
    private String namaPaket;

    public String getCodeTransaction() {
        return codeTransaction;
    }

    public void setCodeTransaction(String codeTransaction) {
        this.codeTransaction = codeTransaction;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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
