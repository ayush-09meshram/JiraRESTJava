package com.ansible.UIBackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Variables {
    String name;
    String password;
    String tcs;

    @Override
    public String toString() {
        return super.toString();
    }

    public Variables(String name, String password, String tcs) {
        this.name = name;
        this.password = password;
        this.tcs = tcs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTcs() {
        return tcs;
    }

    public void setTcs(String tcs) {
        this.tcs = tcs;
    }
}
