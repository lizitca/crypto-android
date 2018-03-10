package com.example.vladislav.screen.mainscreen;

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

import com.example.vladislav.data.CurrencyBaseInfo;
import com.example.vladislav.data.NetworkRepository;
import com.example.vladislav.data.TestCryptoRepository;
import com.example.vladislav.menu.R;

import java.util.List;

/**
 * Created by d3m1d0v on 03.03.2018.
 */

public class MainScreenFragment extends Fragment implements MainScreenContract.View {

    private MainScreenContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new MainScreenPresenter(this, NetworkRepository.getInstance());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.main_screen);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPresenter.start();

        return view;
    }

    @Override
    public void showAllCurrenciesInfoItems(List<CurrencyBaseInfo> currencies) {
        mAdapter = new CryptoCurrencyAdapter(currencies);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showInfoToast(String currencyName) {
        Toast.makeText(this.getContext(), String.format("On %s clicked.", currencyName), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUpdatedInfo() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(MainScreenContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDestroy();
    }

    /**
     * Implementation RecyclerView.ViewHolder
     */
    private class CryptoCurrencyHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

        private CurrencyBaseInfo mCurrencyInfo;
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
            MainScreenFragment.this.mPresenter.onCurrencyItemClick(mCurrencyInfo.getName());
        }

        public void setCurrencyData(CurrencyBaseInfo info) {
            mCurrencyInfo = info;

            mTitle.setText(mCurrencyInfo.getName());
            mValue.setText(mCurrencyInfo.getPriceValue());
            mChange.setText(mCurrencyInfo.getChangeValue());

            switch (mCurrencyInfo.getChangeValue().charAt(0)) {
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

        private List<CurrencyBaseInfo> mCurrencies;

        public CryptoCurrencyAdapter(List<CurrencyBaseInfo> mCurrencies) {
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
