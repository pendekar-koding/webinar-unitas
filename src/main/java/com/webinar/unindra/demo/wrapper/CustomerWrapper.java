package com.webinar.unindra.demo.wrapper;

import com.webinar.unindra.demo.common.wrapper.ReferenceBaseWrapper;

public class CustomerWrapper extends ReferenceBaseWrapper {
    private String name;
    private String mobilePhone;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
