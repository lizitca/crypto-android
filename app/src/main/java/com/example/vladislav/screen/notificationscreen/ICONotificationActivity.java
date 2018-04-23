package com.example.vladislav.screen.notificationscreen;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.IdRes;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vladislav.menu.R;

import java.text.DecimalFormat;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class ICONotificationActivity extends AppCompatActivity {
    /*@BindView(R.id.textView2) TextView cryptoNameText;
    @BindView(R.id.textView3) TextView cryptoValueText;*/
    TextView cryptoNameText;
    TextView cryptoValueText;
    private float cash = 7000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iconotification);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //ButterKnife.bind(this);
        cryptoNameText = (TextView) findViewById(R.id.textView2);
        cryptoValueText = (TextView) findViewById(R.id.textView3);

        cash = getIntent().getFloatExtra("price", 0);
        String name = getIntent().getStringExtra("name");
        cryptoNameText.setText(name);
        cryptoValueText.setText(String.format("%,.2f $", cash));

        setTitle("Уведомление");
        customSeekBar(R.id.priceplus, R.id.seekBar1, R.id.minus1, R.id.plus1, 0);
        customSeekBar(R.id.priceminus, R.id.seekBar3, R.id.minus2, R.id.plus2, 100);


        //disable buttons while checkbox1 is not checked
        final Button mButton=(Button)findViewById( R.id.minus1);
        final Button pButton=(Button)findViewById( R.id.plus1);
        final SeekBar seekBar1=findViewById( R.id.seekBar1);
        enableCustomControl(mButton,pButton,seekBar1,false);
        CheckBox mCheckBox= ( CheckBox ) findViewById( R.id.pricehighter);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                enableCustomControl(mButton,pButton,seekBar1,isChecked);
            }
        });
        //disable buttons while checkbox2 is not checked
        final Button m1Button=(Button)findViewById( R.id.minus2);
        final Button p1Button=(Button)findViewById( R.id.plus2);
        final SeekBar seekBar2=findViewById( R.id.seekBar3);
        enableCustomControl(m1Button,p1Button,seekBar2,false);
        CheckBox CheckBox= ( CheckBox ) findViewById( R.id.checkBox2);
        CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                enableCustomControl(m1Button,p1Button,seekBar2,isChecked);

            }
        });
    }

    private  void  enableCustomControl( Button mbutton, Button pbutton, SeekBar seekBar, boolean isEnabled){

        mbutton.setEnabled(isEnabled);
        pbutton.setEnabled(isEnabled);
        seekBar.setEnabled(isEnabled);

        if(isEnabled)
        {
            mbutton.getBackground().setColorFilter(ResourcesCompat.getColor(getResources(),
                    R.color.background_menu, null),
                    PorterDuff.Mode.MULTIPLY);
            pbutton.getBackground().setColorFilter(ResourcesCompat.getColor(getResources(),
                    R.color.background_menu, null),
                    PorterDuff.Mode.MULTIPLY);
        }
        else
        {
            mbutton.getBackground().setColorFilter(Color.DKGRAY, PorterDuff.Mode.MULTIPLY);
            pbutton.getBackground().setColorFilter(Color.DKGRAY, PorterDuff.Mode.MULTIPLY);
        }

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
