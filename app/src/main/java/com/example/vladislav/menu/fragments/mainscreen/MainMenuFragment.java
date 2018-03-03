package com.example.vladislav.menu.fragments.mainscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vladislav.menu.MenuContract;
import com.example.vladislav.menu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by d3m1d0v on 03.03.2018.
 */

public class MainMenuFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.main_screen);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new CryptoCurrencyAdapter(generateCurrencies());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private List<CryptoCurrencyData> generateCurrencies() {
        List<CryptoCurrencyData> currencies = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            CryptoCurrencyData data = new CryptoCurrencyData();
            data.value = "$" + (i * 333);
            data.name = "Bitcoin " + i;
            data.change = i%2 == 0 ? "+" : "-";
            data.change += "2.22%";
            data.change = i%10 == 0 ? "0.00%" : data.change;
            currencies.add(data);
        }
        return currencies;
    }

    private class CryptoCurrencyData {
        String name;
        String value;
        String change;
    }

    /**
     * Implementation RecyclerView.ViewHolder
     */
    private class CryptoCurrencyHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

        private CryptoCurrencyData mCurrencyData;
        private TextView mTitle;
        private TextView mValue;
        private TextView mChange;

        public CryptoCurrencyHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.currency_title);
            mValue = (TextView) itemView.findViewById(R.id.currency_value);
            mChange = (TextView) itemView.findViewById(R.id.currency_change);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(
                    MainMenuFragment.this.getContext(),
                    "you clicked on " + mCurrencyData.name,
                    Toast.LENGTH_SHORT)
                    .show();
        }

        public void setCurrencyData(CryptoCurrencyData currency) {
            mCurrencyData = currency;

            mTitle.setText(currency.name);
            mValue.setText(currency.value);
            mChange.setText(currency.change);

            switch (currency.change.charAt(0)) {
                case '+':
                    mChange.setTextColor(getResources().getColor(R.color.currency_item_change_positiv));
                    break;

                case '-':
                    mChange.setTextColor(getResources().getColor(R.color.currency_item_change_negative));
                    break;

                default:
                    mChange.setTextColor(getResources().getColor(R.color.currency_item_change_non));
                    break;
            }
        }
    }

    /**
     * Implementation RecyclerView.Adapter
     */
    private class CryptoCurrencyAdapter extends RecyclerView.Adapter<CryptoCurrencyHolder> {

        private List<CryptoCurrencyData> mCurrencies;

        public CryptoCurrencyAdapter(List<CryptoCurrencyData> mCurrencies) {
            this.mCurrencies = mCurrencies;
        }

        @Override
        public CryptoCurrencyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CryptoCurrencyHolder(LayoutInflater
                .from(getActivity())
                .inflate(R.layout.main_screen_currency_item, parent, false));
        }

        @Override
        public void onBindViewHolder(CryptoCurrencyHolder holder, int position) {
            holder.setCurrencyData(mCurrencies.get(position));
        }

        @Override
        public int getItemCount() {
            return mCurrencies.size();
        }
    }
}
