package com.example.usager.mobile;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.usager.mobile.dummy.MealsContent;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

/**
 * Created by Patriack on 2016-02-29.
 */
public class MealsListFragment extends android.support.v4.app.ListFragment {

    private List<Object[]> LstRepas = null;

    //Constructeur test de la classe
    public MealsListFragment(){

        LstRepas= new ArrayList<Object[]>();
        String[] values = new String[] { "Android", "10"};
        LstRepas.add(values);
        values = new String[] { "Iphone", "5"};
        LstRepas.add(values);

        values = new String[] { "Iphone", "5"};
        LstRepas.add(values);

        values = new String[] { "Iphone", "5"};
        LstRepas.add(values);

        values = new String[] { "Iphone", "5"};
        LstRepas.add(values);

        values = new String[] { "Iphone", "5"};
        LstRepas.add(values);

        values = new String[] { "Iphone", "5"};
        LstRepas.add(values);

        values = new String[] { "Iphone", "5"};
        LstRepas.add(values);

        values = new String[] { "Iphone", "5"};
        LstRepas.add(values);

        values = new String[] { "Iphone", "5"};
        LstRepas.add(values);

        values = new String[] { "Iphone", "5"};
        LstRepas.add(values);

        values = new String[] { "Iphone", "5"};
        LstRepas.add(values);

        values = new String[] { "Iphone", "5"};
        LstRepas.add(values);

        values = new String[] { "Iphone", "5"};
        LstRepas.add(values);

        values = new String[] { "Iphone", "5"};
        LstRepas.add(values);

        values = new String[] { "Iphone", "5"};
        LstRepas.add(values);

        values = new String[] { "Iphone", "5"};
        LstRepas.add(values);

        values = new String[] { "Iphone", "5"};
        LstRepas.add(values);

        values = new String[] { "Iphone", "5"};
        LstRepas.add(values);


    }

    /*//Permet de récupérer une view liste de ce qui est contenu dans la variable LstRepas
    public View TousRepas(Context contexte, LayoutInflater inflateur, ViewGroup GrVue){
        
        MealsAdapter adaptateur = new MealsAdapter(contexte, LstRepas);

        setListAdapter(adaptateur);
        getView(0, null,Gr);
        return inflateur.inflate(R.layout.fragment_meals_list,GrVue,false);
    }*/

    //Permet de récupérer une view liste de ce qui est contenu dans la variable LstRepas
    public List<String> TousRepas(Context contexte, LayoutInflater inflateur, ViewGroup GrVue){

        MealsAdapter adaptateur = new MealsAdapter(contexte, LstRepas);

        //ListView NeoListVue = new ListView(contexte);
        List<String> test = new ArrayList<String>();
        for (int i=0;i<LstRepas.size();i++)
        {
            test.add( LstRepas.get(i)[0] + "\t\t\t\t\t" + LstRepas.get(i)[1]);
        }
        View salut = adaptateur.getView(0, null, GrVue);

        //NeoListVue.addFooterView(salut);

        return test;
    }


    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        super.onCreate(savedInstanceState);

        // use your custom layout
        MealsAdapter adapter = new MealsAdapter(getContext(), LstRepas);
        setListAdapter(adapter);
    }*/

    //permet de récupérer un repas contenu dans la liste
    public Object[] GetRepas (int iRepas){
        return LstRepas.get(iRepas);
    }

    /*Classe permettant d'adapter une liste de repas en listview*/
    public class MealsAdapter extends ArrayAdapter {
        private Context context;
        private List<Object[]> valeur;

        public MealsAdapter(Context context, List<Object[]> values) {
            super(context, R.layout.fragment_meals_list, values);
            this.context = context;
            this.valeur = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //Crée la vue qui sera une ListView
            View rowView = inflater.inflate(R.layout.fragment_meals_list, parent, false);
            //Insère le nom du plat
            TextView Nom = (TextView) rowView.findViewById(R.id.RepasNom);
            Nom.setText((String) valeur.get(position)[0]);
            //Insère le prix du plat
            TextView Prix = (TextView) rowView.findViewById(R.id.RepasPrix);
            Prix.setText((String) valeur.get(position)[1]);

            return rowView;
        }
    }

}
