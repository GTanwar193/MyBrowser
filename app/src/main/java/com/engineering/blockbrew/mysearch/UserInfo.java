package com.engineering.blockbrew.mysearch;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by Gaurav Kumar Tanwar on 24-08-2018.
 */
public class UserInfo {

    public static final String MY_PREFERENCES = "Wallet prefrence";

    private Context mContext;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    public UserInfo(Context mContext) {
        this.mContext = mContext;
        sharedpreferences = mContext.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }



    public void setBookMarklist(ArrayList<String> BookMarklist) {

        // editor.putString("menu_item_list", menu_item_list);
        editor.putInt("BookMarklist", BookMarklist.size());
        for (int i = 0; i < BookMarklist.size(); i++) {
            editor.remove("BookMarklist_" + i);
            editor.putString("BookMarklist_" + i, BookMarklist.get(i));
        }
        editor.commit();

    }

    public ArrayList<String> getBookMarklist() {
        ArrayList<String> sKey = new ArrayList<>();
        {
            sKey.clear();
            int size = sharedpreferences.getInt("BookMarklist", 0);

            for (int i = 0; i < size; i++) {
                sKey.add(sharedpreferences.getString("BookMarklist_" + i, null));
            }
            // return sharedpreferences.getString("menu_item_list", "");
        }
        return sKey;
    }



    public void clearAll() {
        editor.clear().commit();
    }
}
