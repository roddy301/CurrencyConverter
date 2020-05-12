package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText enterCurrency;
    private String baseCurrency;
    private ArrayList<currencyItem> currencyList;
    private currencyAdapter adapter;
    private Runnable runnable;

    public void getCurrencies(){
        //http://data.fixer.io/api/latest?access_key=d6702240f0bb4bdf0849deb4838813ea
        final String url = "http://data.fixer.io/api/latest?access_key=9e27d8a2e992e35dc0e5c141bf361169&base=";
        String urlWithBase = url.concat(TextUtils.isEmpty(baseCurrency) ? "USD" : baseCurrency.toUpperCase());

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, urlWithBase, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                        try {
                            response = response.getJSONObject("rates");
                            currencyList.clear();

                            for (int i = 0; i < response.names().length(); i++) {
                                String key = response.names().getString(i);
                                double value = Double.parseDouble(response.get(response.names().getString(i)).toString());

                                currencyList.add(new currencyItem(key, value));
                            }
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Error retrieving data",Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvCurrencies = findViewById(R.id.lvCurrency);
        enterCurrency = findViewById(R.id.enterCurrencyID);
        Button btnSearch = findViewById(R.id.searchButton);
        currencyList = new ArrayList<>();

        adapter = new currencyAdapter(this,R.layout.list_view_item,currencyList);
        lvCurrencies.setAdapter(adapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseCurrency = enterCurrency.getText().toString();

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        getCurrencies();
                    }
                };

                Thread thread = new Thread(null,runnable,"background");
                thread.start();

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }
}
