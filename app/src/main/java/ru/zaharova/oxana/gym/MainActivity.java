package ru.zaharova.oxana.gym;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ru.zaharova.oxana.gym.fragments.AsyncTaskFragment;
import ru.zaharova.oxana.gym.fragments.CalendarFragment;
import ru.zaharova.oxana.gym.fragments.DatabaseFragment;
import ru.zaharova.oxana.gym.fragments.RealTimeFragment;
import ru.zaharova.oxana.gym.fragments.SensorsViewFragment;
import ru.zaharova.oxana.gym.fragments.WeatherFragment;
import ru.zaharova.oxana.gym.fragments.WeatherRetrofitFragment;
import ru.zaharova.oxana.gym.fragments.WorkoutDetailFragment;
import ru.zaharova.oxana.gym.fragments.WorkoutListFragment;
import ru.zaharova.oxana.gym.interfaces.OnListItemClickListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnListItemClickListener {

    private FragmentManager fragmentManager;
    private WorkoutListFragment workoutListFragment;
    private SensorsViewFragment sensorsViewFragment;
    private CalendarFragment calendarViewFragment;
    private AsyncTaskFragment asyncTaskFragment;
    private WeatherFragment weatherFragment;
    private DatabaseFragment databaseFragment;
    private WeatherRetrofitFragment weatherRetrofitFragment;
    private RealTimeFragment realTimeFragment;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        workoutListFragment = new WorkoutListFragment();
        sensorsViewFragment = new SensorsViewFragment();
        calendarViewFragment = new CalendarFragment();
        asyncTaskFragment = new AsyncTaskFragment();
        weatherFragment = new WeatherFragment();
        weatherRetrofitFragment = new WeatherRetrofitFragment();
        databaseFragment = new DatabaseFragment();
        realTimeFragment = new RealTimeFragment();
        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            setFragment(workoutListFragment);
        }

        intent = new Intent(MainActivity.this, BackgroundService.class);
        startService(intent);
    }

    private void setFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("")
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_add:
                Toast.makeText(getApplicationContext(), getString(R.string.add_description),
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), getString(R.string.settings_description),
                        Toast.LENGTH_SHORT).show();
                break;
        }
       return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_notes) {
            setFragment(databaseFragment);
        } else if (id == R.id.nav_weather) {
            setFragment(weatherFragment);
        } else if (id == R.id.nav_weather_retrofit) {
            setFragment(weatherRetrofitFragment);
        } else if (id == R.id.nav_calendar) {
            setFragment(calendarViewFragment);

        } else if (id == R.id.nav_calculate) {
            setFragment(asyncTaskFragment);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_sensors) {
            setFragment(sensorsViewFragment);
        } else if (id == R.id.nav_real_time) {
            setFragment(realTimeFragment);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListItemClickListener(int index) {
        WorkoutDetailFragment detailFragment = WorkoutDetailFragment.initFragment(index);
        setFragment(detailFragment);
    }

    @Override
    protected void onDestroy() {
        stopService(intent);
        super.onDestroy();
    }

}
