package com.example.usager.mobile;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
}
