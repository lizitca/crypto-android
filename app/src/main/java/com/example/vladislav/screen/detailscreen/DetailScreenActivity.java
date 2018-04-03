package com.example.vladislav.screen.detailscreen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;

import com.example.vladislav.app.Constant;
import com.example.vladislav.data.CurrencyData;
import com.example.vladislav.data.repository.CryptoRepository;
import com.example.vladislav.screen.notificationscreen.ICONotificationActivity;
import com.example.vladislav.menu.R;
import com.github.mikephil.charting.charts.LineChart;

import com.example.vladislav.data.repository.MainRepository;

import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class DetailScreenActivity extends AppCompatActivity implements CryptoRepository.GetDataCallback, CryptoRepository.RefreshCallback {

    @BindView(R.id.dollarVal) TextView dolVal;
    @BindView(R.id.rublVal) TextView rublVal;
    @BindView(R.id.changeHours1Val) TextView change1Hval;
    @BindView(R.id.changeHours24Val) TextView change24Hval;
    @BindView(R.id.changeDays7) TextView change7Dval;
    @BindView(R.id.lvData) ListView lvMain;


    private String currencyName;
    private DetailScreenChart mChart;
    private CurrencyData currentCurrency;
    private MyArrayAdapter adapter;

    private final CryptoRepository mRepository = MainRepository.getInstance();

    private Timer mTimer;
    private UpdateChart mUpdateChart;

    private final ArrayList<Pair<String, String>> dataFields = new ArrayList<>();


    String[] fieldValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_screen);
        ButterKnife.bind(this);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        currencyName = getIntent().getStringExtra("currencyName");
        setTitle(currencyName);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mChart = new DetailScreenChart((LineChart) findViewById(R.id.chart), currencyName);
        mChart.initialize();

        fieldValues = getResources().getStringArray(R.array.details_fields);

        dataFields.add(new Pair<>(fieldValues[0], "0"));
        dataFields.add(new Pair<>(fieldValues[1], "0"));
        dataFields.add(new Pair<>(fieldValues[2], "0"));
        adapter = new MyArrayAdapter(this, dataFields);

        lvMain.setAdapter(adapter);

        if (mTimer != null) {
            mTimer.cancel();
        }

        mTimer = new Timer();
        mUpdateChart = new UpdateChart();

        mTimer.schedule(mUpdateChart, 0, 1000);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu_detail_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_notification:
                Intent intent = new Intent(this, ICONotificationActivity.class);
                intent.putExtra("price", currentCurrency.getPrice());
                intent.putExtra("name", currentCurrency.getName());
                this.startActivity(intent);
                break;
            case android.R.id.home:
                finish();
                return true;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }

    @Override
    public void onData(@Nullable CurrencyData data) {
        if (currentCurrency == null || !currentCurrency.getLastUpdate().equals(data.getLastUpdate())) {
            currentCurrency = data;
            updateViewChart();
            updateViewFields();
        }
    }

    @Override
    public void notify(boolean successful) {
        if (successful) {
            updateChart();
        } else {
            Toast.makeText(this, "Refresh failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateChart() {
        mRepository.getCurrencyData(currencyName, this);
    }

    private void updateRepository() {
        mRepository.updateCurrencyData(currencyName, this);
    }

    private void updateViewChart() {
        mChart.addEntry(currentCurrency.getPrice());
    }

    private void updateViewFields() {
        String  tmp,
                up = getResources().getString(R.string.arrow_up),
                down = getResources().getString(R.string.arrow_down);
        int tmpClr,
            clr_up = getResources().getColor(R.color.color_up),
            clr_down = getResources().getColor(R.color.color_down);

        dolVal.setText(String.format(Locale.getDefault(), "%,.2f $", currentCurrency.getPrice()));
        rublVal.setText(String.format(Locale.getDefault(), "%,.2f %s", currentCurrency.getPrice() * 57, "\u20bd"));

        tmp = (currentCurrency.getChange() >= 0) ? up : down;
        tmpClr = (currentCurrency.getChange() >= 0) ? clr_up : clr_down;

        change1Hval.setText(String.format(Locale.getDefault(), "%+.3f%% %s", currentCurrency.getChange(), tmp));
        change1Hval.setTextColor(tmpClr);

        tmp = (currentCurrency.getChange1H() >= 0) ? up : down;
        tmpClr = (currentCurrency.getChange1H() >= 0) ? clr_up : clr_down;

        change24Hval.setText(String.format(Locale.getDefault(), "%+.3f%% %s", currentCurrency.getChange1H(), tmp));
        change24Hval.setTextColor(tmpClr);

        tmp = (currentCurrency.getChange7D() >= 0) ? up : down;
        tmpClr = (currentCurrency.getChange7D() >= 0) ? clr_up : clr_down;

        change7Dval.setText(String.format(Locale.getDefault(), "%+.3f%% %s", currentCurrency.getChange7D(), tmp));
        change7Dval.setTextColor(tmpClr);

        dataFields.set(0, new Pair<String, String>(fieldValues[0],  currentCurrency.getCapitalization()));
        dataFields.set(1, new Pair<String, String>(fieldValues[1], currentCurrency.getSupply()));
        dataFields.set(2, new Pair<String, String>(fieldValues[2], currentCurrency.getVolume24H()));

        adapter.notifyDataSetChanged();
    }

    public class MyArrayAdapter extends ArrayAdapter<Pair<String, String>> {
        private final Context context;
        private final ArrayList<Pair<String, String>> values;

        public MyArrayAdapter(Context context, ArrayList<Pair<String, String>> values) {
            super(context, R.layout.detail_screen_list_item, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.detail_screen_list_item, parent, false);
            TextView titleText = (TextView) rowView.findViewById(R.id.detail_list_title);
            TextView valText = (TextView) rowView.findViewById(R.id.detail_list_val);
            titleText.setText(values.get(position).first);
            valText.setText(values.get(position).second);

            return rowView;
        }
    }

    class UpdateChart extends TimerTask {
        @Override
        public void run() {
            updateRepository();
//            Log.d(Constant.TAG, "Timer tick");
        }
    }
}
