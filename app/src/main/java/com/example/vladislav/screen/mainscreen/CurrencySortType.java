package com.example.vladislav.screen.mainscreen;

/**
 * Created by d3m1d0v on 28.03.2018.
 */

enum CurrencySortType {

    /*
     * Sort alphabetically
     */
    ALPHABETICAL,

    /*
     * Sort by price
     */
    PRICE,

    /*
     * Sort by 24h change
     */
    CHANGE24H;

    /*
     * Returns default sort type
     */
    static CurrencySortType getDefault() {
        return PRICE;
    }

}
