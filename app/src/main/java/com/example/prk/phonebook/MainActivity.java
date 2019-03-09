package com.example.prk.phonebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.prk.phonebook.adapter.TodosAdapter;
import com.example.prk.phonebook.model.Todos;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ArrayList<Todos> listTodos;
    RecyclerView rvPerson;
    Button addButton;
    String URL = "https://api.backendless.com/85E41635-3550-CE9C-FFFA-72B1B7873C00/927034C4-79EE-0E7A-FF63-5868C69BBE00/data/todos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        addButton = (Button)findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InsertActivity.class);
                startActivity(intent);
            }
        });

        listTodos = new ArrayList<>();

        rvPerson = (RecyclerView) findViewById(R.id.rv_person);
        rvPerson.setLayoutManager(new LinearLayoutManager(this));

        final TodosAdapter todosAdapter = new TodosAdapter(this, listTodos);
        rvPerson.setAdapter(todosAdapter);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i = 0; i < response.length(); i++){
                                Todos todos = new Todos();
                                todos.setTitle(response.getJSONObject(i).get("title").toString());
                                todos.setDescription(response.getJSONObject(i).get("description").toString());
                                todos.setCompleted((Boolean) response.getJSONObject(i).get("completed"));
                                todos.setObjectId(response.getJSONObject(i).get("objectId").toString());

                                listTodos.add(todos);
                            }

                            todosAdapter.notifyDataSetChanged();
                        }
                        catch (Exception e){

                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.e("errorVolley", error.toString());
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
