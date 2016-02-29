package com.example.usager.mobile;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

public class OrderActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<String[][]> LstRepas = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

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
    private void AfficherLstRepas(){

        ListView Affichage = (ListView) findViewById(R.id.CtnPanier);


    }


    /*Permet de passez à la prochaine étape*/
    private void NextStepClick(View vue){

        //Si on appuie sur le boutton lorsqu'il est en mode "Commander", passer au prochain mode
        if(((android.widget.Button)vue).getText().toString().equalsIgnoreCase(getString(R.string.Order_Next))) {

            //Envoyer la commande sur le serveur web

            //Change le text du bouton pour "Bill"
            ((android.widget.Button) vue).setText(getString(R.string.Order_End));
        }
        else{//Passez à l'activité pour payer le repas

            //A complété

        }
    }


    /*Permet d'ajouter un nouveau plat a la commande*/
    private void AddClick(View vue){

        //Recupère la référence au conteneur en mode paysage
        View Menu = (View) findViewById(R.id.CtnMenu);

        //Si le menu n'est pas afficher, on se trouve en mode portrait, donc l'afficher
        if(Menu == null) {
           // Intent iMenu = new Intent (this, com.example.usager.mobile.) //Appeler le fragment contenant les repas lister
        }
    }


    /*Permet d'ajouter un plat à la liste*/
    private void AjouterRepas(int RepasID){

        //Si l'usager peut ajouter un plat à sa commande
        if(PeuxAjouter(RepasID)){


            //Trouve le bouton de changement de statut
            Button BoutonNext = (Button) findViewById(R.id.NextBtn);

            //Si le boutton affiche "Bill" faire l'appel d'ajout après commande
            if (BoutonNext.getText().toString().equalsIgnoreCase(getString(R.string.Order_End))) {

            }
        }
    }


    /*Permet d'augmenter le nombre de plat voulu*/
    private void AjouterQuantite(int NumListe){

        //LstRepas.get(NumListe)[1] += 1;

    }


    /*Permet de vérifier dans la liste de repas qu'un indice ne s'y trouve pas déjà*/
    private boolean PeuxAjouter(int RepasID){

        boolean DejaAjouter = false;
        /*
        //Si la liste n'est pas vide, la parcourrir pour chercher la présence du plat
        if(LstRepas!=null){
            for(int iList=0; iList < LstRepas.size(); iList++){
                if(!DejaAjouter) {
                    if(LstRepas.get(iList)[0] == RepasID){
                        DejaAjouter = true;
                    }
                }
            }
        }               */
        return !DejaAjouter;
    }
}
