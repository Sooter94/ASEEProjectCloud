package com.example.edu_s.infocarmadrid.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edu_s.infocarmadrid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainContentFragment extends Fragment {

    Activity mActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inf = inflater.inflate(R.layout.fragment_main_content, container, false);

        return inf;

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mActivity = activity;
    }


    @Override
    public void onResume() {

        super.onResume();

        // Obtenemos las preferencias del usuario
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( mActivity );

    }

    @Override
    public void onPause() {
        super.onPause();

        // Almacena valores actuales de la vista

        SharedPreferences values = getContext().getSharedPreferences( "last_values" , getContext().MODE_PRIVATE );
        SharedPreferences.Editor editor = values.edit();
    }

}
