package com.example.usager.mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private List<Integer> LstRepas = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
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

        LstRepas.add(RepasID);

        //Trouve le bouton de changement de statut
        Button BoutonNext = (Button) findViewById(R.id.NextBtn);

        //Si le boutton affiche "Bill" faire l'appel d'ajout après commande
        if(BoutonNext.getText().toString().equalsIgnoreCase(getString(R.string.Order_End))){

        }
    }


}
