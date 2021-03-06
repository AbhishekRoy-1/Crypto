package com.example.crypto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText search;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    private ArrayList<CurrencyModel> currencyModelArrayList;
    private currencyAdapter currencyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Crypto);
        setContentView(R.layout.activity_main);
        search=findViewById(R.id.search);
        recyclerView=findViewById(R.id.recycleView);
        progressBar=findViewById(R.id.progressBar);
        currencyModelArrayList=new ArrayList<>();
        currencyAdapter= new currencyAdapter(this, currencyModelArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(currencyAdapter);
        getCurrencyData();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterCurrency(editable.toString());
            }
        });
    }
    private void filterCurrency(String currency){
        ArrayList<CurrencyModel> filteredList= new ArrayList<>();
        for (CurrencyModel item:currencyModelArrayList){
            if (item.getName().toLowerCase().contains(currency.toLowerCase())){
                filteredList.add(item);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(this, "No currency found for searched Query", Toast.LENGTH_SHORT).show();
        }else{
            currencyAdapter.filterList(filteredList);
        }
    }

    private void getCurrencyData() {
        progressBar.setVisibility(View.VISIBLE);
        String BASE_URL= "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?convert=INR";
        RequestQueue requestQueue =Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, BASE_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    for (int i=0;i<dataArray.length();i++){
                        JSONObject dataObj= dataArray.getJSONObject(i);
                        String name=dataObj.getString("name");
                        String symbol= dataObj.getString("symbol");
                        int rank= dataObj.getInt("cmc_rank");
                        JSONObject quote= dataObj.getJSONObject("quote");
                        JSONObject INR= quote.getJSONObject("INR");
                        double price= INR.getDouble("price");
                        String time= INR.getString("last_updated");
                        currencyModelArrayList.add(new CurrencyModel(name,symbol,rank,price,time));

                    }
                    currencyAdapter.notifyDataSetChanged();


                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Failed to extract json Data", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Failed to extract data", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers= new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY","ea299b7c-a7c0-4991-848e-02b405764a59");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}