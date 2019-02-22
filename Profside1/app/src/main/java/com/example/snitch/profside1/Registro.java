package com.example.snitch.profside1;

import android.util.Log;

public class Registro
{
    Studente classe[];

    public Registro(int dim)
    {
        this.classe = new Studente[dim];
    }

    public Studente[] getClasse() {
        return classe;
    }

    public boolean aggiungi (Studente s)
    {
        for(int i=0;i<50;i++)
        {
            if(classe[i]==null)
            {
                classe[i]=new Studente(s);
                return true;
            }
        }
        return false;
    }

    public String mac(int pos)
    {
        String boh=classe[pos].getMac();
        return boh;
    }

    @Override
    public String toString() {
        return classe[1].getMac();
    }
}
