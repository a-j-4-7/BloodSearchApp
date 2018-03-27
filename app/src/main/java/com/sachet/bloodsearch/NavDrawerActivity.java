package com.sachet.bloodsearch;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class NavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = getClass().getSimpleName();
    TextView search, bloodlist, details;
    ViewPager pager;
    String username;
    DatabaseHelper databaseHelper;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        pager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tablayout);

        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(pager);

        databaseHelper = new DatabaseHelper(getApplicationContext());


        username = getIntent().getStringExtra("username");
        Log.i(TAG, "onCreate: userName " + username);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            showExitAlertDialog();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            showLogoutAlertDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.nav_menu_why) {
            Intent intent = new Intent(this, WhyDonateActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_menu_about) {
            Toast.makeText(this, "UNDER CONSTRUCTION !!!!", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_menu_logout) {
            showLogoutAlertDialog();

        } else if (id == R.id.nav_menu_guidelines) {
            Intent intent = new Intent(this, DonorGuidelinesActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void showLogoutAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.dialog_style);
        dialog.setTitle("LOG OUT !!!");
        dialog.setMessage("Are you sure you want to log out ???");
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
                Intent intent = new Intent(NavDrawerActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        dialog.setIcon(R.drawable.ic_logout_24dp);
        dialog.setCancelable(false);
        dialog.show();

    }

    public void showExitAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.dialog_style);
        dialog.setTitle("EXIT APP !!!");
        dialog.setMessage("Are you sure you want to exit the application ???");
        dialog.setIcon(R.drawable.ic_exit_to_app_black_24dp);
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDestroy();
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);


            }
        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }


}
