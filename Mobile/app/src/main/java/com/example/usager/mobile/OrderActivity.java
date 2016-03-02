package com.example.usager.mobile;

import static com.example.usager.mobile.Constants.FIRST_COLUMN;
import static com.example.usager.mobile.Constants.SECOND_COLUMN;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

public class OrderActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<int[]> RepasSelectionner = new ArrayList<int[]>();
    //Contient les éléments sélectionner
    private ArrayList<HashMap<String, String>> list;     //Permet L'affichage
    private ArrayList<HashMap<String, String>> listCommande;
    private int iRepasSelect = -1;                       //Contient l'indice du repas sélectionner
    MealsListFragment VueRepas;                         //Lien vers MealsListFragment


    /*******************************************************************
     * 8
     * <p/>
     * To Do 2 Mars:
     * -Ajout d'un onClickList qui prend l'indice du repas, avec VueRepas.GetRepas(int)
     * pour le placer dans RepasSelectionner
     * -Ajout d'un bouton pour augmenter le nombre du repas sélectionner
     * -Ajout d'un bouton pour diminuer le nombre de repas sélectionner
     * -Modification du onClik de BtnAdd pour afficher la liste de tout les repas
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        VueRepas = new MealsListFragment();
        listCommande = new ArrayList<HashMap<String, String>>();
        AfficherLstOrder();

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toolbar.setTitleTextColor(getResources().getColor(R.color.textColorPrimary));
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setHomeButtonEnabled(true);*/
        /*Permet l'écoute du clique de l'usager dans la liste*/
        ListView listView=(ListView)findViewById(R.id.menu);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                android.widget.Button BtnAjout = (android.widget.Button) findViewById(R.id.AddBtn);

                /*Si on est en mode ajout, ajoute le repas dans la liste*/
                if (BtnAjout.getText().toString().equalsIgnoreCase(getString(R.string.Order_Cancel))) {

                    AjouterRepas(position);

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

        /*Crée l'écoute pour le bouton Next*/
        Button BtnNext = (Button) findViewById(R.id.NextBtn);
        BtnNext.setOnClickListener(new Button.OnClickListener(){
            /*Permet de passez à la prochaine étape*/
            public void onClick(View vue) {

                //Si on appuie sur le boutton lorsqu'il est en mode "Commander", passer au prochain mode
                if (((Button) vue).getText().toString().equalsIgnoreCase(getString(R.string.Order_Next))) {

                    //Envoyer la commande sur le serveur web

                    //Change le text du bouton pour "Bill"
                    ((Button) vue).setText(getString(R.string.Order_End));
                } else {//Passez à l'activité pour payer le repas

                    //A complété

                }
            }
        });
        /*Fin de l'écoute du bouton ajouter*/




    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*Permet d'afficher la liste de plat à l'usager*/
    private void AfficherLstRepas() {

        ListView listView = (ListView) findViewById(R.id.menu);
        populateList();
        ListViewAdapter adapter = new ListViewAdapter(this, list);
        listView.setAdapter(adapter);
    }

    private void AfficherLstOrder(){
        ListView listView = (ListView) findViewById(R.id.menu);
        ListViewAdapter adapter = new ListViewAdapter(this, listCommande);
        listView.setAdapter(adapter);
    }

    // Devra prendre une liste en parametres (commande ou menu)
    private void populateList() {
        list = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "patate");
        temp.put(SECOND_COLUMN, "1");
        list.add(temp);
        temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "carotte");
        temp.put(SECOND_COLUMN, "1");
        list.add(temp);
        temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "test");
        temp.put(SECOND_COLUMN, "1");
        list.add(temp);
        temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "omg");
        temp.put(SECOND_COLUMN, "1");
        list.add(temp);
        temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "idk");
        temp.put(SECOND_COLUMN, "1");
        list.add(temp);
        temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "wtf");
        temp.put(SECOND_COLUMN, "1");
        list.add(temp);
    }

    public void PlusClick(View vue){
        if (iRepasSelect > -1 && iRepasSelect < listCommande.size()) {
            ListView listView = (ListView) findViewById(R.id.menu);
            HashMap<String, String> temp = listCommande.get(iRepasSelect);
            String ligne = temp.values().toString();
            String quantite = ligne.substring(ligne.indexOf(",") + 1, ligne.length() - 1);
            String quantitenoblank = quantite.replaceAll("\\s+", "");
            if (Integer.parseInt(quantitenoblank) < 99) {
                listCommande.remove(iRepasSelect);
                Integer quantiteInt = Integer.parseInt(quantitenoblank) + 1;
                temp.put(SECOND_COLUMN, quantiteInt.toString());
                listCommande.add(iRepasSelect,temp);
                ListViewAdapter adapter = new ListViewAdapter(this, listCommande);
                listView.setAdapter(adapter);
            }
        }

    }

    public void MinusClick(View vue){
        if (iRepasSelect > -1 && iRepasSelect < listCommande.size()) {
            ListView listView = (ListView) findViewById(R.id.menu);
            HashMap<String, String> temp = listCommande.get(iRepasSelect);

            String ligne = temp.values().toString();
            String quantite = ligne.substring(ligne.indexOf(",") + 1, ligne.length() - 1);
            String quantitenoblank = quantite.replaceAll("\\s+", "");
            Integer quantiteInt = Integer.parseInt(quantitenoblank) - 1;
            listCommande.remove(iRepasSelect);
            if (quantiteInt != 0) {
                temp.put(SECOND_COLUMN, quantiteInt.toString());
                listCommande.add(iRepasSelect, temp);
            }

            ListViewAdapter adapter = new ListViewAdapter(this, listCommande);
            listView.setAdapter(adapter);
        }

    }


    /*Permet d'ajouter un plat à la liste*/
    private void AjouterRepas(int iRepas){

        //Si l'usager peut ajouter un plat à sa commande
        if(PeuxAjouter(list.get(iRepas).get(FIRST_COLUMN))){

            //Trouve le bouton de changement de statut
            Button BoutonNext = (Button) findViewById(R.id.NextBtn);

            //Si le boutton affiche "Bill" faire l'appel d'ajout après commande
            if (BoutonNext.getText().toString().equalsIgnoreCase(getString(R.string.Order_End))) {

            }

            /*Puis ajouter l'élément dans la liste*/
            listCommande.add(list.get(iRepas));
        }
    }


    /*Permet d'augmenter le nombre de plat voulu*/
    private void AjouterQuantite(int NumListe){

        //LstRepas.get(NumListe)[1] += 1;

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
}
