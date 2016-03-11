package com.example.usager.mobile;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.braintreepayments.api.internal.HttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class RestoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //client pour post pi get des données dans BD
    private final OkHttpClient client = new OkHttpClient();
    private String result = "";//résultat de province
    private String cityResult = "";//résulat de ville
    private String ProcName = "";//nom de la province choisi
    private String cityName = "";//nom de la ville choisi
    private String restoName = "";//nom du resto choisi
    private String RestoResult = "";//résultat des restaurants
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toolbar.setTitleTextColor(getResources().getColor(R.color.textColorPrimary));
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setHomeButtonEnabled(true);

        //affiche les menu accessibles
        navigationView.getMenu().getItem(0).setVisible(false);
        navigationView.getMenu().getItem(2).setVisible(false);
        // navigationView.getMenu().getItem(2).setVisible(false);
        //navigationView.getMenu().getItem(1).setVisible(true);
        //  navigationView.getMenu().getItem(2).setVisible(true);
        // navigationView.getMenu().getItem(1).setVisible(true);



      /* provinces.add("province1");
        provinces.add("province2");
        provinces.add("province3");
        provinces.add("province4");
        provinces.add("province5");

        cities.add("ville1");
        cities.add("ville2");
        cities.add("ville3");
        cities.add("ville4");
        cities.add("ville5");
        cities.add("ville6");

        restos.add("resto1");
        restos.add("resto2");
        restos.add("resto3");
        restos.add("resto4");
        restos.add("resto5");
        restos.add("resto6");*/


        if (android.os.Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()

                    .permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }

        //va chercher les provinces de l'api
        try {
            result = GetProvinces("http://projetdeweb.azurewebsites.net/api/Provinces/GetProvinces");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            cityResult = GetWithPost("http://projetdeweb.azurewebsites.net/api/Cities/getCities", "1");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            RestoResult = GetWithPost("http://projetdeweb.azurewebsites.net/api/Restaurants/getRestaurants", "1");
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {//ajoute les provinces dans une liste
            result.trim();
            result = result.substring(1, result.length() - 1);
            result = result.replace("\\", "");
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Shared.provinces.add(jsonObject.optString("Name"));
                Shared.provincesID.add(jsonObject.optInt("ID"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final Spinner provinceSpinner = (Spinner) findViewById(R.id.spProvince);
        //ajoute les provinces dans spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, Shared.provinces);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        provinceSpinner.setAdapter(adapter);


        //le onChange du Spinner de province
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ProcName = provinceSpinner.getSelectedItem().toString();
                try {
                    //va chercher l'id de la province
                    int procId = 1;
                    for (int i = 0; i < Shared.provinces.size(); i++) {
                        if (Shared.provinces.get(i) == ProcName) {
                            procId = Shared.provincesID.get(i);
                        }
                    }
                    //va chercher les villes de la province
                    cityResult = GetWithPost("http://projetdeweb.azurewebsites.net/api/Cities/getCities", Integer.toString(procId));
                    try {
                        cityResult.trim();
                        cityResult = cityResult.substring(1, cityResult.length() - 1);
                        cityResult = cityResult.replace("\\", "");
                        JSONArray jsonArray = new JSONArray(cityResult);
                        //ajoute les noms et les id des villes
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Shared.cities.add(jsonObject.optString("Name"));
                            Shared.citiesID.add(jsonObject.optInt("ID"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });


        final Spinner citySpinner = (Spinner) findViewById(R.id.spCity);
        //ajoute des villes dans spinner
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, Shared.cities);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        citySpinner.setAdapter(adapter2);


        //le onChange du Spinner de ville
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                cityName = citySpinner.getSelectedItem().toString();
                try {
                    //va chercher l'id de la province
                    int cityId = 1;
                    for (int i = 0; i < Shared.cities.size(); i++) {
                        if (Shared.cities.get(i) == cityName) {
                            cityId = Shared.citiesID.get(i);
                        }
                    }
                    //va chercher les villes de la province
                    RestoResult = GetWithPost("http://projetdeweb.azurewebsites.net/api/Restaurants/getRestaurants", Integer.toString(cityId));
                    try {
                        RestoResult.trim();
                        RestoResult = RestoResult.substring(1, RestoResult.length() - 1);
                        RestoResult = RestoResult.replace("\\", "");
                        JSONArray jsonArray = new JSONArray(RestoResult);
                        //ajoute les noms et les id des villes
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Shared.restos.add(jsonObject.optString("Name"));
                            Shared.restosID.add(jsonObject.optInt("ID"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        Spinner restoSpinner = (Spinner) findViewById(R.id.spResto);

        //ajoute des provinces random dans la liste pour teste
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, Shared.restos);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        restoSpinner.setAdapter(adapter3);

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_connect) {
            //Ouvre l'activité pour permettre de se connecter
            Intent ActivConnect = new Intent(getApplicationContext(),
                    com.example.usager.mobile.LoginActivity.class);
            startActivity(ActivConnect);

        } else if (id == R.id.nav_disconnect) {
            //Je sais pas quoi mettre

        } else if (id == R.id.nav_choixResto) {
            //Ouvre l'activité pour choisir un restaurant
            Intent ActivResto = new Intent(getApplicationContext(),
                    com.example.usager.mobile.RestoActivity.class);
            startActivity(ActivResto);

        } else if (id == R.id.nav_voirMenu) {
            //Ouvre l'activité pour voir les détail du menu
            Intent ActivMenu = new Intent(getApplicationContext(),
                    com.example.usager.mobile.MealListActivity.class);
            startActivity(ActivMenu);

        } else if (id == R.id.nav_voirCommande) {
            //Ouvre l'activité pour voir les détail du menu
            Intent ActivCommander = new Intent(getApplicationContext(),
                    com.example.usager.mobile.OrderActivity.class);
            startActivity(ActivCommander);

        } else if (id == R.id.nav_facture) {
            //Ouvre l'activité pour voir les détail du menu
            Intent ActivFacture = new Intent(getApplicationContext(),
                    com.example.usager.mobile.PayBillActivity.class);
            startActivity(ActivFacture);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //va chercher la liste de provinces dans la bd
    public String GetProvinces(String url) throws Exception {
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

    //get la ville de la province choisi
    public String GetWithPost(String url, String ID) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("ID", ID)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        
        return response.body().string();
    }

    //confirmation du choix de restaurant
    public void ChooseRestoClick()
    {
        Spinner restoSpinner = (Spinner) findViewById(R.id.spResto);
        restoName = restoSpinner.getSelectedItem().toString();
        //va chercher l'id du resto
        for (int i = 0; i < Shared.restos.size(); i++) {
            if (Shared.restos.get(i) == restoName) {
                Shared.restoID = Shared.restosID.get(i);
            }
        }
    }

}
