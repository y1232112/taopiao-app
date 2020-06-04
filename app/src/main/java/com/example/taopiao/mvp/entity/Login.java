package com.example.taopiao.mvp.entity;

public class Login {
    private Integer id;
    private String loginame;
    private String password;
    private long tocken;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginame() {
        return loginame;
    }

    public void setLoginame(String loginame) {
        this.loginame = loginame;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getTocken() {
        return tocken;
    }

    public void setTocken(long tocken) {
        this.tocken = tocken;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", loginame='" + loginame + '\'' +
                ", password='" + password + '\'' +
                ", tocken=" + tocken +
                '}';
    }
}
