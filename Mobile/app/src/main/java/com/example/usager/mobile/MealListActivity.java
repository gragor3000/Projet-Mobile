package com.example.usager.mobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.example.usager.mobile.dummy.MealsContent;

import java.util.List;

/**
 * An activity representing a list of Meals. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MealDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MealListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    //private boolean mTwoPane;

    MealsListFragment VueRepas;                         //Lien vers MealsListFragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);
        VueRepas = new MealsListFragment();

        /*Remplie la liste avec tout les repas*/
        ListView listView = (ListView) findViewById(R.id.menu);
        ListViewAdapter adapter = new ListViewAdapter(this, VueRepas.getMenu());
        listView.setAdapter(adapter);

        /*Permet l'écoute du clique de l'usager dans la liste*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Ouvre l'activité pour visualisé la description du repas
                Intent ActivDescr = new Intent(getApplicationContext(),
                        com.example.usager.mobile.MealDetailActivity.class);
                ActivDescr.putExtra("SelectedMeal", position);
                startActivity(ActivDescr);

            }
        });
        /*Fin de l'écoute du clique*/

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
