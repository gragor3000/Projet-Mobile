package com.example.usager.mobile;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.usager.mobile.dummy.MealsContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.zip.Inflater;

import okhttp3.FormBody;
import okhttp3.Headers;
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

    ArrayList<Object[]> MenuResto;
    private String result = "";
    private final OkHttpClient client = new OkHttpClient();

    //Constructeur du fragment qui instancie le menu du restaurant
    public MealsListFragment(){
        MenuResto = new ArrayList<Object[]>();
        if (android.os.Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()

                    .permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }

        //va chercher les provinces de l'api
        try {
            result = GetMenuPost("http://projetdeweb.azurewebsites.net/api/Meals/getMeals", "1");
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            result.trim();
            result = result.substring(1, result.length() - 1);
            result = result.replace("\r\n", "");
            result = result.replace("\\", "");
                    result = result.replace("rn", "");
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                Object[] temp = new Object[3];
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                temp[0] = jsonObject.optString("Name");
                temp[1] = parsePrix(jsonObject.optString("Price"));
                temp[2] = jsonObject.optString("Desc");
                MenuResto.add(temp);
                //Shared.provinces.add(jsonObject.optString("Name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*
        for (int i = 0; i < 31; i++) {
            Object[] temp = new Object[3];
            temp[0] = "Name "+Integer.toString(i);
            temp[1] = Double.toString(Math.random() * 100).substring(0,5);
            temp[2] = "Desc "+Integer.toString(i);
            MenuResto.add(temp);
            //Shared.provinces.add(jsonObject.optString("Name"));
        }*/
    }

    public String GetMenuPost(String url,String ID) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("ID", ID)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        //System.out.println(response.body().string());

        return response.body().string();
    }


    // Renvoie le menu en entier
    public ArrayList<HashMap<String, String>> getMenu() {

        ArrayList<HashMap<String, String>> Result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> temps;

        for(int iRepas = 0; iRepas < MenuResto.size(); iRepas++){

            temps = new HashMap<String, String>();
            temps.put(FIRST_COLUMN, (String)MenuResto.get(iRepas)[0]);
            temps.put(SECOND_COLUMN, (String)MenuResto.get(iRepas)[1]);
            Result.add(temps);
        }

        return Result;
    }


    // Renvoie qu'en seul repas
    public HashMap<String, String> getRepas(int iRepas) {

        HashMap<String, String> Result = new HashMap<String, String>();
        Result.put(FIRST_COLUMN, (String)MenuResto.get(iRepas)[0]);
        Result.put(SECOND_COLUMN, (String)MenuResto.get(iRepas)[1]);

        return Result;
    }

    //renvoie le nom d'un repas en particulier
    public String getNom(int iRepas){

        return (String)MenuResto.get(iRepas)[0];
    }

    //renvoie le prix d'un repas en particulier
    public String getPrix(int iRepas){

        return (String)MenuResto.get(iRepas)[1] + "$";
    }

    //renvoie la reduction d'un repas en particulier
    public String getReduc(int iRepas){

        return "Il n'y a pas de réduction";
    }

    //renvoie la description d'un repas en particulier
    public String getDescr(int iRepas){

        return (String)MenuResto.get(iRepas)[2];
    }

    public String GetMenu(String url) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .build();


        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
        }

        return response.body().string();

    }

    //Renvoie le total que devra débourser l'usager
    public String getSubTotal(ArrayList<HashMap<String, String>> LstCommande){

        float PrixTotal = 0.0f;         //Contient le total de la facture

        //Pour tout les repas commandé, additionner le cout au cout total
        for(int iCommande = 0; iCommande < LstCommande.size(); iCommande++){
            int iFois = Integer.parseInt(LstCommande.get(iCommande).get(SECOND_COLUMN));
            Float iPrix = getPrix(LstCommande.get(iCommande).get(FIRST_COLUMN));
            PrixTotal += (iFois * iPrix);
        }

        return ("Total : " + parsePrix(String.valueOf(PrixTotal)) + "$");
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

        //Calcule le total en ajoutant la TPS et TVQ
        PrixTotal = PrixTotal + (PrixTotal * 0.05f) + (PrixTotal * 0.09975f);

        return ("Total : " + parsePrix(String.valueOf(PrixTotal)) + "$");
    }

    //Transforme un string pour qu'il ait l'apparence d'une valeur monétaire
    private String parsePrix(String pprix){

        String[] TblPrix = pprix.split(Pattern.quote("."));

        if(TblPrix[1].length() < 2){
            TblPrix[1] = TblPrix[1] + "0";
        }

        return TblPrix[0] + "." + TblPrix[1].substring(0,2);
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
                fPrix = Float.parseFloat(StrPrix);
            }
            else{
                iRepas++;
            }
        }

        return fPrix;
    }

}
