package com.example.usager.mobile.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Modèle de repas et d'allergies
2016-02-17 : Marc Lauzon
Structure du modèle de repas.
Structure du modèle d'allergies.
Convertion du contenu démonstrateur.
Constructeur par défaut et optionnel.

À FAIRE :
- Obtenir les informations de la BD.
- Créer les éléments à ajouter dans le contenu.
- ??? IMAGES ???
 */
public class MealsContent {

    /* Tableau des repas. */
    public static final List<Meal> ITEMS = new ArrayList<Meal>();

    /* Map des repas. */
    public static final Map<String, Meal> ITEM_MAP = new HashMap<String, Meal>();

    /* Ajout du repas au tableau et la map. */
    public static void addItem(Meal item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /****** DEBUG : Test de l'ajout d'item ********/
    static{
        addItem(new Meal("1", "Spaghetti", "Pâtes spaghetti servi avec une sauce rosée", 15.0f, 0.10f));
        addItem(new Meal("2", "Lasagne", "Pâtes lasagne servi avec une sauce au boeuf haché et fromage parmesan gratiné.", 15.0f));
        addItem(new Meal("4", "Steak Frite", "Steak à cuisson désiré servi sur un lit de frite.", 20.0f));
    }
    /****** DEBUG : Test de l'ajout d'item ********/

    /* Structure repas. */
    public static class Meal {
        public final String id;         //Identifiant.
        public final String name;       //Nom du repas.
        public final String desc;       //Description.
        public final float price;       //Prix du repas.
        public final float discount;    //Rabais applicable.
        public final ArrayList<Allergy> allergies;  //Liste d'allergies concernées.

        //Constructeur sans rabais et sans allergie.
        public Meal(String _id, String _name, String _desc, float _price){
            this(_id, _name, _desc, _price, 0.0f, null);
        }

        //Constructeur sans allergie.
        public Meal(String _id, String _name, String _desc, float _price, float _discount){
            this(_id, _name, _desc, _price, _discount, null);
        }

        //Constructeur sans rabais.
        public Meal(String _id, String _name, String _desc, float _price, ArrayList<Allergy> _allergies){
            this(_id, _name, _desc, _price, 0.0f, _allergies);
        }

        //Constructeur par défaut.
        public Meal(String _id, String _name, String _desc, float _price, float _discount, ArrayList<Allergy> _allergies) {
            this.id = _id;
            this.name = _name;
            this.desc = _desc;
            this.price = _price;
            this.discount = _discount;
            this.allergies = _allergies;
        }

        @Override //Réécriture de toString.
        public String toString() {
            return name;
        }
    }

    /* Structure allergie. */
    public static class Allergy {
        public final String id;     //Identifiant.
        public final String name;   //Nom de l'allergie.

        //Constructeur par défaut.
        public Allergy(String id, String name){
            this.id = id;
            this.name = name;
        }

        @Override //Réécriture de toString.
        public String toString() { return name; }
    }
}
