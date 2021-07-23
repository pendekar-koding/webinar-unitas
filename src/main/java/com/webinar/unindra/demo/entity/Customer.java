package com.webinar.unindra.demo.entity;

import com.webinar.unindra.demo.common.model.ReferenceBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer extends ReferenceBase {

    private String nama;
    private String mobilePhone;
    private String email;

    @Column(name = "nama", length = 30, nullable = false)
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
    @Column(name = "mobile_phone", length = 15, nullable = false)
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    @Column(name = "email", length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
