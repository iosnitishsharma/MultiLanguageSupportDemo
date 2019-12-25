package com.nitish.demos.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nitish.demos.multilanguagedemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
//Created by Nitish Sharma on 25 Dec, 2019
public class MainActivity extends AppCompatActivity {

//https://stackoverflow.com/questions/3217492/list-of-language-codes-in-yaml-or-json
    TextView textView;
    private Button openLang;
    private List<LanguageModel> list;
    private LanguagesDialog languageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);


        openLang = findViewById(R.id.languageChooser);
        openLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                languageDialog.showSpinerDialog();
            }
        });

        preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        setLocale();

        setUpLanguagesDialog();
    }

    // put up in apputils
    private void setLocale() {
        Locale locale = new Locale(preferences.getString("lang","en"));
        Locale.setDefault(locale);
        Configuration configuration = getResources().getConfiguration();
        configuration.setLocale(locale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Resources resources = createConfigurationContext(configuration).getResources();
            updateUI(resources);
        }
        else{
            //handle below 24
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
            updateUI(getResources());
        }
    }

    private void updateUI(Resources resources) {
        textView.setText(resources.getString(R.string.hellostate));
    }


    private SharedPreferences preferences;
    private void setUpLanguagesDialog() {
        list = AppUtils.getListOfLanguageCode(this);
        Toast.makeText(this, "list"+list.size(), Toast.LENGTH_SHORT).show();
        languageDialog = new LanguagesDialog(this, (ArrayList<LanguageModel>) list,
                "Select or Search Language");
        languageDialog.setTitleColor(getResources().getColor(R.color.colorAccent));
        languageDialog.setSearchIconColor(getResources().getColor(R.color.colorAccent));
        languageDialog.setSearchTextColor(getResources().getColor(R.color.colorAccent));
        languageDialog.setItemColor(getResources().getColor(R.color.colorAccent));
        languageDialog.setItemDividerColor(getResources().getColor(R.color.colorAccent));
        languageDialog.setCloseColor(getResources().getColor(R.color.colorAccent));
        languageDialog.setCancellable(true);
        languageDialog.setShowKeyboard(false);
        languageDialog.bindOnSpinerListener(new OnLanguageClick() {
            @Override
            public void onClick(LanguageModel item, int position) {
                Toast.makeText(MainActivity.this, item.getCode()+ "  " + position + "", Toast.LENGTH_SHORT).show();

                preferences.edit().putString("lang", item.getCode()).apply();
                // activity needs to be recreated
                //needs to restart alert to be shown here
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
            }

        });

    }
}