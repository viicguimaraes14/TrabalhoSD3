
package com.example.ananda.trabalhosd3;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.ananda.trabalhosd3.Adapter.Adapter;
import com.example.ananda.trabalhosd3.Model.Paises;
import com.example.ananda.trabalhosd3.dao.Repositorio;
import com.example.ananda.trabalhosd3.util.HttpRetro;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Adapter adapter;
    private List<Paises> paisesList;
    private ListView listView;
    private SwipeRefreshLayout swiperefresh;
    Repositorio db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Repositorio(getBaseContext());
        swiperefresh = (SwipeRefreshLayout) findViewById((R.id.swiperefresh));
        //seta cores
        swiperefresh.setColorScheme(R.color.colorPrimary, R.color.colorAccent);
        swiperefresh.setOnRefreshListener(this);

        listView = (ListView) findViewById(R.id.listView);
        paisesList = new ArrayList<Paises>();

        adapter = new Adapter(this, paisesList);
        getDataRetro();

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplication(), paisesList.get(position).toString(), Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    public void onRefresh() {
        getDataRetro();
    }



    public void getDataRetro() {

        swiperefresh.setRefreshing(true);

        // se tiver conexao faz get, senao pega do sqlite
        if (isConnected()) {
            HttpRetro.getPCLientes().getPaises().enqueue(new Callback<List<Paises>>() {
                public void onResponse(Call<List<Paises>> call, Response<List<Paises>> response) {
                    if (response.isSuccessful()) {
                        List<Paises> paisesBody = response.body();
                        paisesList.clear();

                        db.excluirAll();

                        for (Paises paises : paisesBody) {
                            paisesList.add(paises);
                            db.inserir(paises);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        System.out.println(response.errorBody());
                    }
                    swiperefresh.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<List<Paises>> call, Throwable t) {
                    t.printStackTrace();
                }

            });

        } else {
            swiperefresh.setRefreshing(false);
            Toast.makeText(this, "Sem Conexão com a internet, listando países do banco...", Toast.LENGTH_SHORT).show();
            getDataSqlite();
        }
    }

    public Boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( cm != null ) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            return ni != null && ni.isConnected();
        }
        return false;
    }
    private void getDataSqlite() {
        paisesList.clear();
        paisesList.addAll(db.listarPaises());
        adapter.notifyDataSetChanged();
    }

}