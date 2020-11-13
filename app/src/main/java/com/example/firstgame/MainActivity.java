package com.example.firstgame;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TabHost;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class MainActivity extends TabActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Calendar c = Calendar.getInstance();
    int day = c.get(Calendar.DAY_OF_WEEK)-1;
    final String LOG_TAG = "myLogs";

   String tabID_position = "tag"+day;
Button btnSettings;
Switch switchAll;
    boolean switch_used=false;
    int juft_toq;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_WEEK = "juft_toq";
    private SharedPreferences mSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    Button btnSettings = (Button) findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });
        Switch switchAll = (Switch) findViewById(R.id.switch_all);
        switchAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch_used == false)
                    switch_used = true;
                else
                    switch_used = false;
                Log.d(LOG_TAG, "--- Switch used: ---"+switch_used);

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
   //     setSupportActionBar(toolbar);

        TabHost tabHost = getTabHost();

        //  TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        // инициализация
      //  tabHost.setup();

        TabHost.TabSpec tabSpec;

        // создаем вкладку и указываем тег
        tabSpec = tabHost.newTabSpec("tag1");
        // название вкладки
        tabSpec.setIndicator("Dushanba");
        // указываем id компонента из FrameLayout, он и станет содержимым
        Intent intent = new Intent(MainActivity.this, OneActivity.class);
        intent.putExtra("switch", switch_used);
        tabSpec.setContent(intent);

     //  tabSpec.setContent(R.id.dushanba);
        // добавляем в корневой элемент
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        // указываем название и картинку
        // в нашем случае вместо картинки идет xml-файл,
        // который определяет картинку по состоянию вкладки
        tabSpec.setIndicator("Seshanba");
//        tabSpec.setContent(new Intent(this, TwoActivity.class));
      // tabSpec.setContent(R.id.seshanba);
        tabHost.addTab(tabSpec);

        // создаем вкладку и указываем тег
        tabSpec = tabHost.newTabSpec("tag3");
        // название вкладки
        tabSpec.setIndicator("Chorshanba");
        // указываем id компонента из FrameLayout, он и станет содержимым
//        tabSpec.setContent(new Intent(this, ThreeActivity.class));
        tabHost.addTab(tabSpec);

        // создаем вкладку и указываем тег
        tabSpec = tabHost.newTabSpec("tag4");
        // название вкладки
        tabSpec.setIndicator("Payshanba");
        // указываем id компонента из FrameLayout, он и станет содержимым
//        tabSpec.setContent(new Intent(this, FourActivity.class));
        tabHost.addTab(tabSpec);

        // создаем вкладку и указываем тег
        tabSpec = tabHost.newTabSpec("tag5");
        // название вкладки
        tabSpec.setIndicator("Juma");
        // указываем id компонента из FrameLayout, он и станет содержимым
//        tabSpec.setContent(new Intent(this, FiveActivity.class));
        tabHost.addTab(tabSpec);

        // создаем вкладку и указываем тег
        tabSpec = tabHost.newTabSpec("tag6");
        // название вкладки
        tabSpec.setIndicator("Shanba");
        // указываем id компонента из FrameLayout, он и станет содержимым
//        tabSpec.setContent(new Intent(this, SixActivity.class));
        tabHost.addTab(tabSpec);

        // вторая вкладка будет выбрана по умолчанию
        tabHost.setCurrentTabByTag(tabID_position);

        // обработчик переключения вкладок
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                tabID_position = tabId;
                //      Toast.makeText(getBaseContext(), "tabId = " + tabID_position, Toast.LENGTH_SHORT).show();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(MainActivity.this, AddLesson.class);
                intent.putExtra("TabID", tabID_position);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSettings.contains(APP_PREFERENCES_WEEK)) {
            // Получаем число из настроек
            juft_toq = mSettings.getInt(APP_PREFERENCES_WEEK, 0);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(APP_PREFERENCES_WEEK, juft_toq);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        finish();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_lesson) {
            // Handle the camera action
        } else if (id == R.id.nav_homework) {

        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
