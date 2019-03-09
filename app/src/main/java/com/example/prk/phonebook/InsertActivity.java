package com.example.prk.phonebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class InsertActivity extends AppCompatActivity {

    EditText etTitle;
    EditText etDescription;
    Button btnSubmit;
    String URL = "https://api.backendless.com/85E41635-3550-CE9C-FFFA-72B1B7873C00/927034C4-79EE-0E7A-FF63-5868C69BBE00/data/todos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        etTitle = (EditText)findViewById(R.id.etTitle);
        etDescription = (EditText)findViewById(R.id.etDescription);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String description = etDescription.getText().toString();

                if(title.equals("") || description.equals("")){
                    Toast.makeText(InsertActivity.this, "Every form must be filled!",Toast.LENGTH_SHORT).show();
                }else{
                    JSONObject data = new JSONObject();
                    try{
                        data.put("title", title);
                        data.put("description", description);
                    }
                    catch (Exception e){

                    }

                    JsonObjectRequest request = new JsonObjectRequest(
                            Request.Method.PUT,
                            URL,
                            data,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }
                    );

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(request);
                }
            }
        });
    }
}
