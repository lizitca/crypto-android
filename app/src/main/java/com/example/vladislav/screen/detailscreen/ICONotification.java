package com.example.vladislav.screen.detailscreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.vladislav.menu.R;

public class ICONotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iconotification);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Уведомление");
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


}
