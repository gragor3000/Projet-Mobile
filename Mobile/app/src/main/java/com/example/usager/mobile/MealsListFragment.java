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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.usager.mobile.Shared.FIRST_COLUMN;
import static com.example.usager.mobile.Shared.SECOND_COLUMN;

/**
 * Created by Patriack on 2016-02-29.
 */
public class MealsListFragment {

    //Client qui permet d'envoyer et récupérer des informations dans la base de donnée
    private final OkHttpClient client = new OkHttpClient();

    //Contient toutes les informations du menu du restorant
    ArrayList<Object[]> MenuResto = new ArrayList<Object[]>();


    //Constructeur du fragment qui instancie le menu du restaurant
    public MealsListFragment(){

        /*RequestBody formBody = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url("http://projetwebmobile.azurewebsites.net/api/MealsControllers/getMeals")
                .post(formBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            test = response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            test = "Marche Pas";
        }

        String a = test;*/

        Object[] Repas = new Object[3];

        for(int TRepas = 1; TRepas < 31; TRepas++){
            Repas = new Object[3];
            Repas[0] = "Repas" + Integer.toString(TRepas);
            Repas[1] = "10$";
            Repas[2] = "Il s'agit du repas numéro " + Integer.toString(TRepas);
            MenuResto.add(Repas);
        }
    }


    // Renvoie le menu en entier
    public ArrayList<HashMap<String, String>> getMenu() {

        ArrayList<HashMap<String, String>> TblRetour = new ArrayList<HashMap<String, String>>();

        for(int TRepas = 0; TRepas < MenuResto.size(); TRepas++){
            TblRetour.add(getRepas(TRepas));
        }

         return TblRetour;
    }


    // Renvoie qu'en seul repas
    public HashMap<String, String> getRepas(int iRepas) {

        HashMap<String, String> RepasRetour = new HashMap<String, String>();

        RepasRetour.put(FIRST_COLUMN, (String)MenuResto.get(iRepas)[0]);
        RepasRetour.put(SECOND_COLUMN, (String)MenuResto.get(iRepas)[1]);

        return RepasRetour;
    }


    //renvoie la description d'un repas
    public String getDescription(int iRepas){

        return (String)MenuResto.get(iRepas)[2];
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
            if(MenuResto.get(iRepas)[0] == NomRepas){
                String StrPrix = (String)MenuResto.get(iRepas)[1];
                fPrix = Float.parseFloat(StrPrix.substring(0, StrPrix.length()-1));
            }
            else{
                iRepas++;
            }
        }

        return fPrix;
    }

}
