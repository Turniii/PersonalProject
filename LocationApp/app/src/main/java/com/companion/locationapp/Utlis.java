package com.companion.locationapp;

import com.companion.locationapp.model.User;

import java.util.ArrayList;

/**
 * Created by Turni on 12/13/16.
 */

public class Utlis {
    public static User getUserByNameFromList(String name, ArrayList<User> list){
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getName().equals(name)){
                return list.get(i);
            }
        }
        return null;
    }
}
