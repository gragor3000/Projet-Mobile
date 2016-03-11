package com.example.usager.mobile;

import static com.example.usager.mobile.Shared.FIRST_COLUMN;
import static com.example.usager.mobile.Shared.SECOND_COLUMN;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrderActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Contient la commande le l'utilisateur
    private ArrayList<HashMap<String, String>> listCommande;
    private int iRepasSelect = -1;                       //Contient l'indice du repas sélectionner
    MealsListFragment VueRepas;                         //Lien vers MealsListFragment
    private final OkHttpClient client = new OkHttpClient();
    private String result = "";


    /******************************************************************
     * To Do 3 Mars:
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        VueRepas = new MealsListFragment();
        listCommande = new ArrayList<HashMap<String, String>>();
        AfficherLstOrder();
        AfficherPrixTotal();

        /*Code consernant le toolbar partager*/
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
        /*Fin code consernant le toolbar partager*/


        /*Permet l'écoute du clique de l'usager dans la liste*/
        ListView listView=(ListView)findViewById(R.id.menu);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                android.widget.Button BtnAjout = (android.widget.Button) findViewById(R.id.AddBtn);

                /*Si on est en mode ajout, ajoute le repas dans la commande, puis reaffiche la commande*/
                if (BtnAjout.getText().toString().equalsIgnoreCase(getString(R.string.Order_Cancel))) {
                    AjouterRepas(position);
                    AfficherLstOrder();
                    Button BtnAdd = (Button) findViewById(R.id.AddBtn);
                    BtnAdd.setText(getString(R.string.Order_Ajouter));
                } else { /*Sinon prend en note l'indice de l'élément sélectionner*/
                    iRepasSelect = position;
                }
            }
        });
        /*Fin de l'écoute du clique*/

        /*Crée l'écoute pour le bouton ajouter*/
        Button BtnAjout = (Button) findViewById(R.id.AddBtn);
        BtnAjout.setOnClickListener(new Button.OnClickListener() {
            /*Permet d'ajouter un nouveau plat a la commande*/
            public void onClick(View vue) {

                //Si on appuie sur le boutton Pour qu'il passe en mode Ajout, afficher le menu du restaurant
                if (((Button) vue).getText().toString().equalsIgnoreCase(getString(R.string.Order_Ajouter))) {

                    AfficherLstRepas();
                    //Renomme le bouton
                    ((Button) vue).setText(getString(R.string.Order_Cancel));

                } else {//Réaffiche la commande du client

                    AfficherLstOrder();
                    //Renomme le bouton
                    ((Button) vue).setText(getString(R.string.Order_Ajouter));
                }
            }
        });
        /*Fin de l'écoute du bouton ajouter*/

        /*Crée l'écoute pour le bouton pour appeler le serveur*/
        final Button BtnWaiter = (Button) findViewById(R.id.WaiterBtn);
        BtnWaiter.setOnClickListener(new Button.OnClickListener() {
            /*Permet d'ajouter un nouveau plat a la commande*/
            public void onClick(View vue) {

                //Le bouton change de couleur pour dire que le serveur à été appelé.
                BtnWaiter.setBackgroundColor(0x009933);
            }
        });
        /*Fin de l'écoute du bouton Call serveur*/

        /*Crée l'écoute pour le bouton Next*/
        Button BtnNext = (Button) findViewById(R.id.NextBtn);
        BtnNext.setOnClickListener(new Button.OnClickListener(){
            /*Permet de passez à la prochaine étape*/
            public void onClick(View vue) {

                //Si on appuie sur le boutton lorsqu'il est en mode "Commander", passer au prochain mode
                if (((Button) vue).getText().toString().equalsIgnoreCase(getString(R.string.Order_Next))) {

                    //EnvoyerCommande

                    /*if (android.os.Build.VERSION.SDK_INT > 9) {

                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()

                                .permitAll().build();

                        StrictMode.setThreadPolicy(policy);
                    }

                    //va chercher les provinces de l'api
                    try {
                        result = EnvoyerCommande("http://projetdeweb.azurewebsites.net/api/Orders/CreateOrder", "1", "Plus de bacon", "1Pizza10...");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/

                    //Shared.billID = indice de facture

                    //Désactive le bouton de retrait de plat, après avoir envoyer la commande
                    Button BtnMinus = (Button) findViewById(R.id.MinusBtn);
                    BtnMinus.setEnabled(false);

                    //Change le text du bouton pour "Bill"
                            ((Button) vue).setText(getString(R.string.Order_End));
                } else {//Passez à l'activité pour payer le repas

                    //Ouvre l'activité pour payé une facture
                    Intent ActivBill = new Intent(getApplicationContext(),
                                                  com.example.usager.mobile.PayBillActivity.class);
                    ActivBill.putExtra("MontantTotal", VueRepas.getTotal(listCommande));
                    startActivity(ActivBill);
                }
            }
        });
        /*Fin de l'écoute du bouton ajouter*/
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

        }else if (id == R.id.nav_choixResto) {
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


    /*Permet d'afficher le menu du restaurant*/
    private void AfficherLstRepas() {

        ListView listView = (ListView) findViewById(R.id.menu);
        ListViewAdapter adapter = new ListViewAdapter(this, VueRepas.getMenu());
        listView.setAdapter(adapter);
    }

    /*Permet d'afficher la commande de l'usager*/
    private void AfficherLstOrder(){
        ListView listView = (ListView) findViewById(R.id.menu);
        ListViewAdapter adapter = new ListViewAdapter(this, listCommande);
        listView.setAdapter(adapter);
    }

    /*Permet d'afficher le prix total de la commande*/
    private void AfficherPrixTotal(){
        TextView TxtTotal = (TextView) findViewById(R.id.BillTotal);

        TxtTotal.setText(VueRepas.getSubTotal(listCommande));
    }


    //Augmente la quantité de pla commandé
    public void PlusClick(View vue){
        //Si un plat à été sélectionner
        if (iRepasSelect > -1 && iRepasSelect < listCommande.size()) {
            ListView listView = (ListView) findViewById(R.id.menu);
            HashMap<String, String> temp = listCommande.get(iRepasSelect);
            String ligne = temp.values().toString();
            String quantite = ligne.substring(ligne.indexOf(",") + 1, ligne.length() - 1);
            String quantitenoblank = quantite.replaceAll("\\s+", "");
            //Tant qu'on n'a pas atteint 99, il est possible d'ajouter un plat
            if (Integer.parseInt(quantitenoblank) < 99) {
                listCommande.remove(iRepasSelect);
                Integer quantiteInt = Integer.parseInt(quantitenoblank) + 1;
                temp.put(SECOND_COLUMN, quantiteInt.toString());
                listCommande.add(iRepasSelect,temp);
                ListViewAdapter adapter = new ListViewAdapter(this, listCommande);
                listView.setAdapter(adapter);

                //Fait appel de l'API pour Ajouter Un Plat à la commande
            }

            //Affiche le prix de la commande
            AfficherPrixTotal();
        }
    }

    //Soustrait des éléments à la commande
    public void MinusClick(View vue){
        if (iRepasSelect > -1 && iRepasSelect < listCommande.size()) {
            ListView listView = (ListView) findViewById(R.id.menu);
            HashMap<String, String> temp = listCommande.get(iRepasSelect);

            String ligne = temp.values().toString();
            String quantite = ligne.substring(ligne.indexOf(",") + 1, ligne.length() - 1);
            String quantitenoblank = quantite.replaceAll("\\s+", "");
            Integer quantiteInt = Integer.parseInt(quantitenoblank) - 1;
            listCommande.remove(iRepasSelect);
            //si le nombre de plat reste supérieur à zéro, replace le plat dans la commande
            if (quantiteInt != 0) {
                temp.put(SECOND_COLUMN, quantiteInt.toString());
                listCommande.add(iRepasSelect, temp);
            }

            ListViewAdapter adapter = new ListViewAdapter(this, listCommande);
            listView.setAdapter(adapter);

            //Affiche le prix de la commande
            AfficherPrixTotal();
        }

    }


    /*Permet d'ajouter un plat à la liste*/
    private void AjouterRepas(int iRepas){

        //Si l'usager peut ajouter un plat à sa commande
        if(PeuxAjouter(VueRepas.getRepas(iRepas).get(FIRST_COLUMN))){

            //Trouve le bouton de changement de statut
            Button BoutonNext = (Button) findViewById(R.id.NextBtn);

            //Si le boutton affiche "Bill" faire l'appel d'ajout après commande
            if (BoutonNext.getText().toString().equalsIgnoreCase(getString(R.string.Order_End))) {

                //Faire appel ajout repas après commande

            }

            //Crée un nouvelle élément a ajouter dans la commande du client
            HashMap<String, String> NeoRepas = new HashMap<String, String>();
            NeoRepas.put(FIRST_COLUMN, VueRepas.getRepas(iRepas).get(FIRST_COLUMN));
            NeoRepas.put(SECOND_COLUMN, "1");

            /*Puis ajouter l'élément dans la liste*/
            listCommande.add(NeoRepas);

            //Affiche le prix de la commande
            AfficherPrixTotal();
        }
    }


    /*Permet de vérifier dans la liste de repas qu'un indice ne s'y trouve pas déjà*/
    private boolean PeuxAjouter(String RepasNom){

        boolean DejaAjouter = false;

        //Si la liste n'est pas vide, la parcourrir pour chercher la présence du plat
        if(listCommande!=null){
            for(int iList=0; iList < listCommande.size(); iList++){
                if(!DejaAjouter) {
                    if(listCommande.get(iList).get(FIRST_COLUMN) == RepasNom){
                        DejaAjouter = true;
                    }
                }
            }
        }
        return !DejaAjouter;
    }

    public String EnvoyerCommande(String url,String ID, String comment, String meals) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("ClientID", ID)
                .add("Comment", comment)
                .add("LstMeals", meals)
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
}
