package com.example.vladislav.screen.mainscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vladislav.data.CurrencyData;
import com.example.vladislav.data.repository.MainRepository;
import com.example.vladislav.menu.R;
import com.example.vladislav.screen.detailscreen.DetailScreenActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by d3m1d0v on 03.03.2018.
 */

public class MainScreenFragment extends Fragment implements
        MainScreenContract.View, SwipeRefreshLayout.OnRefreshListener {

    private static final String CURRENCY_NAME = "currencyName";

    private MainScreenContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private CryptoCurrencyAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        new MainScreenPresenter(this, MainRepository.getInstance());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);

        mRecyclerView = view.findViewById(R.id.main_screen);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSwipeRefreshLayout = view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        mAdapter = new CryptoCurrencyAdapter(new ArrayList<CurrencyData>());
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.start();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_alphabetical:
                mPresenter.onAlphabetSortSelected();
                break;

            case R.id.menu_price:
                mPresenter.onPriceSortSelected();
                break;

            case R.id.menu_change:
                mPresenter.onChangeSortSelected();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void showCurrenciesData(List<CurrencyData> currencies) {
            mAdapter.setDataList(currencies);
    }

    @Override
    public void startDetailActivity(CurrencyData currency) {
        getActivity().startActivity(
                new Intent(getContext(), DetailScreenActivity.class)
                        .putExtra(CURRENCY_NAME, currency.getName())
        );
    }

    @Override
    public void notifyAdapter() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRefreshFailedToast() {
        Toast.makeText(this.getContext(), "Refresh failed =(", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRefreshAnimation() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshAnimation() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setPresenter(MainScreenContract.Presenter presenter) {
        mPresenter = presenter;
    }


    /**
     * Implementation {@link SwipeRefreshLayout.OnRefreshListener}
     */
    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.onRefreshRequested();
    }


    /**
     * Implementation {@link RecyclerView.ViewHolder}
     */
    private class CryptoCurrencyHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

        private CurrencyData mCurrencyData;
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
            MainScreenFragment.this.mPresenter.onCurrencyItemClick(mCurrencyData);
        }

        public void setCurrencyData(CurrencyData data) {
            mCurrencyData = data;

            mTitle.setText(mCurrencyData.getName());
            mValue.setText(mCurrencyData.getFormattedPrice());
            mChange.setText(mCurrencyData.getFormattedChange());

            switch (mCurrencyData.getFormattedChange().charAt(0)) {
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
     * Implementation {@link RecyclerView.Adapter}
     */
    private class CryptoCurrencyAdapter extends RecyclerView.Adapter<CryptoCurrencyHolder> {

        private List<CurrencyData> mDataList;

        CryptoCurrencyAdapter(List<CurrencyData> mCurrencies) {
            this.mDataList = mCurrencies;
        }

        @Override
        public CryptoCurrencyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CryptoCurrencyHolder(LayoutInflater
                .from(getActivity())
                .inflate(R.layout.main_screen_currency_item, parent, false));
        }

        @Override
        public void onBindViewHolder(CryptoCurrencyHolder holder, int position) {
            holder.setCurrencyData(mDataList.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        public void setDataList(List<CurrencyData> dataList) {
            mDataList = dataList;
            notifyDataSetChanged();
        }
    }
}
