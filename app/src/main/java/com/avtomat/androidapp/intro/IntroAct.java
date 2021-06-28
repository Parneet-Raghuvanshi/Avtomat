package com.avtomat.androidapp.intro;

import android.content.Intent;
import android.os.Bundle;

import com.chyrta.onboarder.OnboarderActivity;
import com.chyrta.onboarder.OnboarderPage;
import com.avtomat.androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class IntroAct extends OnboarderActivity {

    List<OnboarderPage> onboarderPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onboarderPages = new ArrayList<OnboarderPage>();

        // Create your first page
        OnboarderPage onboarderPage1 = new OnboarderPage("Atam Nirbhar Bharat", "Atmanirbhar Bharat, which translates to 'self-reliant India' or 'self-sufficient India'",R.drawable.atma_nirbhar);
        OnboarderPage onboarderPage2 = new OnboarderPage("Re-Skilling","Get Ready to furnish your skills",R.drawable.skills);
        OnboarderPage onboarderPage3 = new OnboarderPage("Easy Courses","Our Application Provide Easy Course to get Ready for Post Covid-India",R.drawable.certified);

        // You can define title and description colors (by default white)
        onboarderPage1.setTitleColor(R.color.colorAccent);
        onboarderPage1.setDescriptionColor(R.color.colorAccent);
        onboarderPage2.setTitleColor(R.color.colorAccent);
        onboarderPage2.setDescriptionColor(R.color.colorAccent);
        onboarderPage3.setTitleColor(R.color.colorAccent);
        onboarderPage3.setDescriptionColor(R.color.colorAccent);
        onboarderPage1.setTitleTextSize(26);
        onboarderPage1.setDescriptionTextSize(20);
        onboarderPage2.setTitleTextSize(26);
        onboarderPage2.setDescriptionTextSize(20);
        onboarderPage3.setTitleTextSize(26);
        onboarderPage3.setDescriptionTextSize(20);

        // Don't forget to set background color for your page
        onboarderPage1.setBackgroundColor(R.color.white);
        onboarderPage2.setBackgroundColor(R.color.white);
        onboarderPage3.setBackgroundColor(R.color.white);

        // Add your pages to the list
        onboarderPages.add(onboarderPage1);
        onboarderPages.add(onboarderPage2);
        onboarderPages.add(onboarderPage3);

        // And pass your pages to 'setOnboardPagesReady' method
        setOnboardPagesReady(onboarderPages);
        shouldUseFloatingActionButton(true);
        setActiveIndicatorColor(R.color.colorPrimary);
        setSkipButtonHidden();
    }

    @Override
    public void onFinishButtonPressed() {
        Intent intent = new Intent(IntroAct.this, ChooseLang.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}