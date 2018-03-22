package com.example.vladislav.screen.mainscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vladislav.data.CurrencyData;
import com.example.vladislav.data.repository.MainRepository;
import com.example.vladislav.menu.R;

import java.util.List;

/**
 * Created by d3m1d0v on 03.03.2018.
 */

public class MainScreenFragment extends Fragment implements MainScreenContract.View, SwipeRefreshLayout.OnRefreshListener {

    private MainScreenContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        mPresenter.start();

        return view;
    }

    @Override
    public void showCurrenciesData(List<CurrencyData> currencies) {
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
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showRefreshFailedToast() {
        mSwipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this.getContext(), "Refresh failed =(", Toast.LENGTH_SHORT).show();
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
            MainScreenFragment.this.mPresenter.onCurrencyItemClick(mCurrencyData.getName());

            OnSelectedRelativeLayoutListener listener = (OnSelectedRelativeLayoutListener) getActivity();
            listener.onSelectedRelativeLayout(mCurrencyData.getName());
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

        private List<CurrencyData> mCurrencies;

        public CryptoCurrencyAdapter(List<CurrencyData> mCurrencies) {
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

    public interface OnSelectedRelativeLayoutListener {

        void onSelectedRelativeLayout(String currencyName);
    }
}
