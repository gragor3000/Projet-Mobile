package com.example.usager.mobile;
import java.util.Date;

/**
 * Created by Mic on 21/02/2016.
 */
//table des utilisateurs
public class Users {
    // nom de la table
    public static final String TABLE = "Users";

    // nom des colonnes
    public static final String KEY_ID = "ID";
    public static final String KEY_firstName = "firstName";
    public static final String KEY_lastName = "lastName";
    public static final String KEY_email = "email";
    public static final String KEY_pw = "pw";
    public static final String KEY_birth = "birth";
    public static final String KEY_postal = "postal";


    public int ID;
    public String firstName;
    public String lastName;
    public String email;
    public String pw;
    public String birth;
    public String postal;
}
