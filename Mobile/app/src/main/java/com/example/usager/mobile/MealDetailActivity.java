package com.example.usager.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * An activity representing a single Meal detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MealListActivity}.
 */
public class MealDetailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    MealsListFragment VueRepas;                         //Lien vers MealsListFragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);
        VueRepas = new MealsListFragment();

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

        //Récupère les informations envoyer par la page qui la invoquer
        Bundle invocation = getIntent().getExtras();

        //Récupère les information du plat est les affiche
        //Le nom du plat
        TextView TxtNom = (TextView) findViewById(R.id.txtName);
        TxtNom.setText(VueRepas.getNom(invocation.getInt("SelectedMeal")));
        //Le prix du plat
        TextView TxtPrix = (TextView) findViewById(R.id.txtPrice);
        TxtPrix.setText(VueRepas.getPrix(invocation.getInt("SelectedMeal")));
        //La réduction du plat
        TextView TxtReduc = (TextView) findViewById(R.id.txtDiscount);
        TxtReduc.setText(VueRepas.getReduc(invocation.getInt("SelectedMeal")));
        //La description du plat
        TextView TxtDescr = (TextView) findViewById(R.id.txtDesc);
        TxtDescr.setText(VueRepas.getDescr(invocation.getInt("SelectedMeal")));
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
