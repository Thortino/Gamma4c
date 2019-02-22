package com.example.snitch.profside1;

public class Studente
{
    private String name;
    private String mac;
    private boolean presente;
    private int assenzeTot;

    public Studente(String name, String mac) {
        this.name = name;
        this.mac = mac;
        this.presente = false;
        this.assenzeTot = 0;
    }

    public Studente(Studente s)
    {
        this.name=s.name;
        this.mac=s.mac;
        this.presente=s.presente;
        this.assenzeTot=s.assenzeTot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

    public int getAssenzeTot() {
        return assenzeTot;
    }

    public void setAssenzeTot(int assenzeTot) {
        this.assenzeTot = assenzeTot;
    }
}
