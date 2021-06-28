package com.avtomat.androidapp.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.avtomat.androidapp.R;
import com.avtomat.androidapp.login.LoginAct;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.Locale;

public class ChooseLang extends AppCompatActivity {

    MaterialCardView card_english,card_hindi;
    TextView chooselang_title;
    MaterialButton continue_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        LoadLocale();
        setContentView(R.layout.activity_choose_lang);

        card_english = findViewById(R.id.card_english);
        card_hindi = findViewById(R.id.card_hindi);
        chooselang_title = findViewById(R.id.title_chooselang);
        continue_btn = findViewById(R.id.continue_btn);

        card_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale("en");
                reLang("en");
                card_english.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
                card_hindi.setCardBackgroundColor(getResources().getColor(R.color.grey));
            }
        });

        card_hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale("hi");
                reLang("hi");
                card_english.setCardBackgroundColor(getResources().getColor(R.color.grey));
                card_hindi.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
            }
        });

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseLang.this, LoginAct.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void reLang(String lang) {
        if (lang.equals("en")){
            chooselang_title.setText("Choose a Language to Continue");
            continue_btn.setText("Continue");
        }
        else if (lang.equals("hi")){
            chooselang_title.setText("जारी रखने के लिए एक भाषा चुनें");
            continue_btn.setText("जारी रखें");
        }
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void LoadLocale() {
        SharedPreferences preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = preferences.getString("My_Lang","");
        setLocale(language);
    }
}