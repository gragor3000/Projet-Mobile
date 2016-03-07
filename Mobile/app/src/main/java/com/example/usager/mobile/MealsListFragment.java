package com.example.usager.mobile;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.usager.mobile.dummy.MealsContent;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

import static com.example.usager.mobile.Constants.FIRST_COLUMN;
import static com.example.usager.mobile.Constants.SECOND_COLUMN;

/**
 * Created by Patriack on 2016-02-29.
 */
public class MealsListFragment {

    ArrayList<HashMap<String, String>> MenuResto;

    //Constructeur du fragment qui instancie le menu du restaurant
    public MealsListFragment(){
        MenuResto = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "patate");
        temp.put(SECOND_COLUMN, "10$");
        MenuResto.add(temp);
        temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "carotte");
        temp.put(SECOND_COLUMN, "10$");
        MenuResto.add(temp);
        temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "test");
        temp.put(SECOND_COLUMN, "10$");
        MenuResto.add(temp);
        temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "omg");
        temp.put(SECOND_COLUMN, "10$");
        MenuResto.add(temp);
        temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "idk");
        temp.put(SECOND_COLUMN, "10$");
        MenuResto.add(temp);
        temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "wtf");
        temp.put(SECOND_COLUMN, "10$");
        MenuResto.add(temp);
    }


    // Renvoie le menu en entier
    public ArrayList<HashMap<String, String>> getMenu() {
         return MenuResto;
    }


    // Renvoie qu'en seul repas
    public HashMap<String, String> getRepas(int iRepas) {
        return MenuResto.get(iRepas);
    }


    //renvoie la description d'un repas
    public String getDescription(int iRepas){

        //A terminer

        return "Description";
    }

    //Renvoie le total que devra débourser l'usager
    public String getTotal(ArrayList<HashMap<String, String>> LstCommande){

        float PrixTotal = 0.0f;         //Contient le total de la facture

        //Pour tout les repas commandé, additionner le cout au cout total
        for(int iCommande = 0; iCommande < LstCommande.size(); iCommande++){
            int iFois = Integer.parseInt(LstCommande.get(iCommande).get(SECOND_COLUMN));
            Float iPrix = getPrix(LstCommande.get(iCommande).get(FIRST_COLUMN));
            PrixTotal += (iFois * iPrix);
        }

        return ("Total : " + String.valueOf(PrixTotal) + "$");
    }


    //Trouve le prix d'un élément dans la liste grace au nom
    private float getPrix (String NomRepas){

        int iRepas = 0;
        float fPrix = 0.0f;

        //Tant qu'il n'a pas trouver le repas, le chercher
        while (fPrix == 0.0f){
            //Si on trouve l'élément qu'on cherche
            if(MenuResto.get(iRepas).get(FIRST_COLUMN) == NomRepas){
                String StrPrix = MenuResto.get(iRepas).get(SECOND_COLUMN);
                fPrix = Float.parseFloat(StrPrix.substring(0, StrPrix.length()-1));
            }
            else{
                iRepas++;
            }
        }

        return fPrix;
    }

}
