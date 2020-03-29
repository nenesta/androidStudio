package com.abhiandroid.viewpagerexample.myapplication;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextToSpeech tts;

    private int[] tabIcons = {
     /*
            R.drawable.apple,
            R.drawable.orange,
            R.drawable.grapes,
            R.drawable.banana
      */
            R.drawable.n01,
            R.drawable.n02,
            R.drawable.n03,
            R.drawable.n04
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        addTabs(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        tts = new TextToSpeech(this, this);


    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }
    private void addTabs(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
/*
        adapter.addFrag(new AppleFragment(), "MANZANA");
        adapter.addFrag(new OrangeFragment(), "NARANJA");
        adapter.addFrag(new GrapesFragment(), "UVA");
        adapter.addFrag(new BananaFragment(), "PLATANO");
 */
        adapter.addFrag(new AppleFragment(), "UNO");
        adapter.addFrag(new OrangeFragment(), "DOS");
        adapter.addFrag(new GrapesFragment(), "TRES");
        adapter.addFrag(new BananaFragment(), "CUATRO");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    public void onClick(View v) {
        TextView tv =  (TextView) v;
        speakOut(tv.getText());
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            Locale locSpanish = new Locale("spa", "MEX");

            int result = tts.setLanguage(locSpanish);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut(CharSequence text) {
        //Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,"id1");
    }

}