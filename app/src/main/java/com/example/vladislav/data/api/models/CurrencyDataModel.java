package com.example.vladislav.data.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by d3m1d0v on 10.03.2018.
 */

public class CurrencyDataModel {

    @SerializedName("FROMSYMBOL")
    @Expose
    private String fROMSYMBOL;
    @SerializedName("TOSYMBOL")
    @Expose
    private String tOSYMBOL;
    @SerializedName("MARKET")
    @Expose
    private String mARKET;
    @SerializedName("PRICE")
    @Expose
    private String pRICE;
    @SerializedName("LASTUPDATE")
    @Expose
    private String lASTUPDATE;
    @SerializedName("LASTVOLUME")
    @Expose
    private String lASTVOLUME;
    @SerializedName("LASTVOLUMETO")
    @Expose
    private String lASTVOLUMETO;
    @SerializedName("LASTTRADEID")
    @Expose
    private String lASTTRADEID;
    @SerializedName("VOLUMEDAY")
    @Expose
    private String vOLUMEDAY;
    @SerializedName("VOLUMEDAYTO")
    @Expose
    private String vOLUMEDAYTO;
    @SerializedName("VOLUME24HOUR")
    @Expose
    private String vOLUME24HOUR;
    @SerializedName("VOLUME24HOURTO")
    @Expose
    private String vOLUME24HOURTO;
    @SerializedName("OPENDAY")
    @Expose
    private String oPENDAY;
    @SerializedName("HIGHDAY")
    @Expose
    private String hIGHDAY;
    @SerializedName("LOWDAY")
    @Expose
    private String lOWDAY;
    @SerializedName("OPEN24HOUR")
    @Expose
    private String oPEN24HOUR;
    @SerializedName("HIGH24HOUR")
    @Expose
    private String hIGH24HOUR;
    @SerializedName("LOW24HOUR")
    @Expose
    private String lOW24HOUR;
    @SerializedName("LASTMARKET")
    @Expose
    private String lASTMARKET;
    @SerializedName("CHANGE24HOUR")
    @Expose
    private String cHANGE24HOUR;
    @SerializedName("CHANGEPCT24HOUR")
    @Expose
    private String cHANGEPCT24HOUR;
    @SerializedName("CHANGEDAY")
    @Expose
    private String cHANGEDAY;
    @SerializedName("CHANGEPCTDAY")
    @Expose
    private String cHANGEPCTDAY;
    @SerializedName("SUPPLY")
    @Expose
    private String sUPPLY;
    @SerializedName("MKTCAP")
    @Expose
    private String mKTCAP;
    @SerializedName("TOTALVOLUME24H")
    @Expose
    private String tOTALVOLUME24H;
    @SerializedName("TOTALVOLUME24HTO")
    @Expose
    private String tOTALVOLUME24HTO;

    public String getFROMSYMBOL() {
        return fROMSYMBOL;
    }

    public void setFROMSYMBOL(String fROMSYMBOL) {
        this.fROMSYMBOL = fROMSYMBOL;
    }

    public String getTOSYMBOL() {
        return tOSYMBOL;
    }

    public void setTOSYMBOL(String tOSYMBOL) {
        this.tOSYMBOL = tOSYMBOL;
    }

    public String getMARKET() {
        return mARKET;
    }

    public void setMARKET(String mARKET) {
        this.mARKET = mARKET;
    }

    public String getPRICE() {
        return pRICE;
    }

    public void setPRICE(String pRICE) {
        this.pRICE = pRICE;
    }

    public String getLASTUPDATE() {
        return lASTUPDATE;
    }

    public void setLASTUPDATE(String lASTUPDATE) {
        this.lASTUPDATE = lASTUPDATE;
    }

    public String getLASTVOLUME() {
        return lASTVOLUME;
    }

    public void setLASTVOLUME(String lASTVOLUME) {
        this.lASTVOLUME = lASTVOLUME;
    }

    public String getLASTVOLUMETO() {
        return lASTVOLUMETO;
    }

    public void setLASTVOLUMETO(String lASTVOLUMETO) {
        this.lASTVOLUMETO = lASTVOLUMETO;
    }

    public String getLASTTRADEID() {
        return lASTTRADEID;
    }

    public void setLASTTRADEID(String lASTTRADEID) {
        this.lASTTRADEID = lASTTRADEID;
    }

    public String getVOLUMEDAY() {
        return vOLUMEDAY;
    }

    public void setVOLUMEDAY(String vOLUMEDAY) {
        this.vOLUMEDAY = vOLUMEDAY;
    }

    public String getVOLUMEDAYTO() {
        return vOLUMEDAYTO;
    }

    public void setVOLUMEDAYTO(String vOLUMEDAYTO) {
        this.vOLUMEDAYTO = vOLUMEDAYTO;
    }

    public String getVOLUME24HOUR() {
        return vOLUME24HOUR;
    }

    public void setVOLUME24HOUR(String vOLUME24HOUR) {
        this.vOLUME24HOUR = vOLUME24HOUR;
    }

    public String getVOLUME24HOURTO() {
        return vOLUME24HOURTO;
    }

    public void setVOLUME24HOURTO(String vOLUME24HOURTO) {
        this.vOLUME24HOURTO = vOLUME24HOURTO;
    }

    public String getOPENDAY() {
        return oPENDAY;
    }

    public void setOPENDAY(String oPENDAY) {
        this.oPENDAY = oPENDAY;
    }

    public String getHIGHDAY() {
        return hIGHDAY;
    }

    public void setHIGHDAY(String hIGHDAY) {
        this.hIGHDAY = hIGHDAY;
    }

    public String getLOWDAY() {
        return lOWDAY;
    }

    public void setLOWDAY(String lOWDAY) {
        this.lOWDAY = lOWDAY;
    }

    public String getOPEN24HOUR() {
        return oPEN24HOUR;
    }

    public void setOPEN24HOUR(String oPEN24HOUR) {
        this.oPEN24HOUR = oPEN24HOUR;
    }

    public String getHIGH24HOUR() {
        return hIGH24HOUR;
    }

    public void setHIGH24HOUR(String hIGH24HOUR) {
        this.hIGH24HOUR = hIGH24HOUR;
    }

    public String getLOW24HOUR() {
        return lOW24HOUR;
    }

    public void setLOW24HOUR(String lOW24HOUR) {
        this.lOW24HOUR = lOW24HOUR;
    }

    public String getLASTMARKET() {
        return lASTMARKET;
    }

    public void setLASTMARKET(String lASTMARKET) {
        this.lASTMARKET = lASTMARKET;
    }

    public String getCHANGE24HOUR() {
        return cHANGE24HOUR;
    }

    public void setCHANGE24HOUR(String cHANGE24HOUR) {
        this.cHANGE24HOUR = cHANGE24HOUR;
    }

    public String getCHANGEPCT24HOUR() {
        return cHANGEPCT24HOUR;
    }

    public void setCHANGEPCT24HOUR(String cHANGEPCT24HOUR) {
        this.cHANGEPCT24HOUR = cHANGEPCT24HOUR;
    }

    public String getCHANGEDAY() {
        return cHANGEDAY;
    }

    public void setCHANGEDAY(String cHANGEDAY) {
        this.cHANGEDAY = cHANGEDAY;
    }

    public String getCHANGEPCTDAY() {
        return cHANGEPCTDAY;
    }

    public void setCHANGEPCTDAY(String cHANGEPCTDAY) {
        this.cHANGEPCTDAY = cHANGEPCTDAY;
    }

    public String getSUPPLY() {
        return sUPPLY;
    }

    public void setSUPPLY(String sUPPLY) {
        this.sUPPLY = sUPPLY;
    }

    public String getMKTCAP() {
        return mKTCAP;
    }

    public void setMKTCAP(String mKTCAP) {
        this.mKTCAP = mKTCAP;
    }

    public String getTOTALVOLUME24H() {
        return tOTALVOLUME24H;
    }

    public void setTOTALVOLUME24H(String tOTALVOLUME24H) {
        this.tOTALVOLUME24H = tOTALVOLUME24H;
    }

    public String getTOTALVOLUME24HTO() {
        return tOTALVOLUME24HTO;
    }

    public void setTOTALVOLUME24HTO(String tOTALVOLUME24HTO) {
        this.tOTALVOLUME24HTO = tOTALVOLUME24HTO;
    }

    /**
     * Wrapper over {@link CurrencyDataModel}
     */
    public class Response {

        @SerializedName("RAW")
        @Expose
        private Map<String, Map<String, CurrencyDataModel>> data;

        public Map<String, Map<String, CurrencyDataModel>> getData() {
            return data;
        }

        public void setData(Map<String, Map<String, CurrencyDataModel>> data) {
            this.data = data;
        }
    }
}