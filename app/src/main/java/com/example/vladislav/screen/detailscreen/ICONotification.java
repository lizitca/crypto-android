package com.example.vladislav.screen.detailscreen;

import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vladislav.menu.R;

import java.text.DecimalFormat;

public class ICONotification extends AppCompatActivity {
private double cash = 7000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iconotification);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setTitle("Уведомление");
        customSeekBar(R.id.priceplus, R.id.seekBar1, R.id.minus1, R.id.plus1, 0);
        customSeekBar(R.id.priceminus, R.id.seekBar3, R.id.minus2, R.id.plus2, 100);

    }

    private void customSeekBar(@IdRes int priceplusId,
                               @IdRes int seekBarId,
                               @IdRes int minusId,
                               @IdRes int plusId,
                                int defaultValue){
        Button btnminus1;
        Button btnplus1;
        //subscribe to seek bar event
        final TextView priceplus=findViewById(priceplusId);
        priceplus.setText("0 $");
        final SeekBar sk= findViewById(seekBarId);
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                if(progress==0)
                    progress=1;
                DecimalFormat df = new DecimalFormat("###.### $");
                priceplus.setText(df.format(((cash*2)/100.0) * progress));
            }
        });
        btnminus1 = findViewById(minusId);
        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sk.setProgress(sk.getProgress()-1);
            }
        };
        btnminus1.setOnClickListener(oclBtnOk);

        btnplus1 = findViewById(plusId);
        View.OnClickListener oclBtnOk1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sk.setProgress(sk.getProgress()+1);
            }
        };
        btnplus1.setOnClickListener(oclBtnOk1);
        sk.setProgress(defaultValue);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


}
