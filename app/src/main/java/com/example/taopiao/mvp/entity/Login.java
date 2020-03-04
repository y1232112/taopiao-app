package com.example.taopiao.mvp.entity;

public class Login {
    private String loginame;
    private String password;
    private long tocken;

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
                "loginame='" + loginame + '\'' +
                ", password='" + password + '\'' +
                ", tocken=" + tocken +
                '}';
    }
}
