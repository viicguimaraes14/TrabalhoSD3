
package com.example.ananda.trabalhosd3.Model;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Paises implements Serializable {

    public long id;

    /* Notações SerializedName a classe Paises para que o GsonConverter possa realizar o parser:*/
    @SerializedName("name")
    public String name;

    @SerializedName("capital")
    public String capital;

    @SerializedName("region")
    public String region;

    @SerializedName("subregion")
    public String subregion;



    public Paises(String name, String capital, String region, String subregion) {

        this.name = name;
        this.capital = capital;
        this.region = region;
        this.subregion = subregion;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public String getRegion() {
        return region;
    }

    public String getSubregion() {
        return subregion;
    }

    @Override
    public String toString() {
        return  (" País = " + name +
                " \nCapital = " + capital +
                " \nRegiao = " + region +
                " \nSubRegiao = " + subregion );
    }



}
