package com.example.usager.mobile;

import android.app.ExpandableListActivity;
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
    private String result = "";
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
        navigationView.getMenu().getItem(2).setVisible(true);
        navigationView.getMenu().getItem(2).setVisible(true);
        navigationView.getMenu().getItem(2).setVisible(true);
        navigationView.getMenu().getItem(1).setVisible(true);



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
        String test = "";
        try {
            test = GetCities("http://projetwebmobile.azurewebsites.net/api/Cities/GetCities","2");
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            result.trim();
            result = result.substring(1, result.length() - 1);
            result = result.replace("\\", "");
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Shared.provinces.add(jsonObject.optString("Name"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Spinner provinceSpinner = (Spinner) findViewById(R.id.spProvince);

        //ajoute des provinces random dans la liste pour teste
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, Shared.provinces);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        provinceSpinner.setAdapter(adapter);

        //le onChange du Spinner de province
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            Spinner villeSpinner = (Spinner) findViewById(R.id.spCity);

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Toast.makeText(getApplicationContext(),"change",Toast.LENGTH_LONG).show();
                try {
                    GetCities("http://projetwebmobile.azurewebsites.net/api/Cities/GetCities", "2");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        Spinner citySpinner = (Spinner) findViewById(R.id.spCity);
        //ajoute des villes random dans la liste pour teste
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, Shared.cities);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        citySpinner.setAdapter(adapter2);


        //le onChange du Spinner de ville
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            Spinner restoSpinner = (Spinner) findViewById(R.id.spResto);

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                restoSpinner.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                restoSpinner.setVisibility(View.INVISIBLE);
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

       /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

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
    public String GetCities(String url,String ID) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("ID", ID)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        System.out.println(response.body().string());

        return response.body().string();
    }

    //get le resto de la ville choisi
    public String GetResto() throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("search", "Jurassic Park")
                .build();
        Request request = new Request.Builder()
                .url("https://en.wikipedia.org/w/index.php")
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        System.out.println(response.body().string());

        return response.body().string();
    }
}
