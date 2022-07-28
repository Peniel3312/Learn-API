package com.yvkalume.learnapi.meteo;

import java.util.List;

public class Meteo {
    private String product;
    private int init;
    private List<DataSerie> dataseries;

    public String getProduct() {
        return product;
    }

    public int getInit() {
        return init;
    }

    public List<DataSerie> getDataSeries() {
        return dataseries;
    }

}
