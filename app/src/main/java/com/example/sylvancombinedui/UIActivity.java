package com.example.sylvancombinedui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class UIActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState(); //rotates hamburger icon together with drawer

        if (savedInstanceState == null) { //if savedInstanceState is null, then the app is just
            //being started, and the default fragment can be loaded. However, if it's not null,
            //it won't be. Since onCreate is called when the phone is rotated, this if-statement
            //prevents the default fragment from being loaded every time you rotate the phone
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit(); //sets default fragment
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){ //switch between fragments depending on selection
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_create:
                startActivity(new Intent(UIActivity.this, OrderActivity.class));
                break;
            case R.id.nav_edit:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EditFragment()).commit();
                break;
            case R.id.nav_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HistoryFragment()).commit();
                break;
            case R.id.nav_inventory:
                startActivity(new Intent(UIActivity.this, InventoryActivity.class));
                break;
            case R.id.nav_user_profile: //Toast placeholders
                Toast.makeText(this, "Your User Profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_bluetooth:
                startActivity(new Intent(UIActivity.this, BluetoothActivity.class));
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UIActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); //closes this activity
                break;

        }

        drawer.closeDrawer(GravityCompat.START);

        return true; //returning false = no item selected even if action was triggered, so we want
    }                //to return true

    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)){ //use GravityCompat.END b/c the drawer is
            drawer.closeDrawer(GravityCompat.START);   //on the left side of the screen
        } else {
            super.onBackPressed();
        }
    }
}