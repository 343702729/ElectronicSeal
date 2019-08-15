package com.nfc.electronicseal.bean;

public class PasswordUpdateBean {
    private String oldPwd;
    private String newPwd;

    public PasswordUpdateBean(String oldPwd, String newPwd){
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
    }
}
