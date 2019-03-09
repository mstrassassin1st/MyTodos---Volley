package com.example.prk.phonebook.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.prk.phonebook.MainActivity;
import com.example.prk.phonebook.R;
import com.example.prk.phonebook.model.Todos;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.ViewHolder> {

    Context context;
    ArrayList<Todos> listTodos;
    private Context mContext;

    public TodosAdapter(Context context, ArrayList<Todos> listTodos){
        this.context = context;
        mContext = context;
        this.listTodos = listTodos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_person, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(listTodos.get(i));
    }

    @Override
    public int getItemCount() {
        return listTodos.size();
    }

    public void setData(ArrayList<Todos> listTodos){
        this.listTodos = listTodos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        Button btnDelete;
        Button btnCompleted;


        public ViewHolder(View itemView){
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnCompleted = itemView.findViewById(R.id.btnCompleted);
        }

        public void bind(final Todos todos){
            tvTitle.setText(todos.getTitle());
            if(todos.getCompleted() == true){
                btnCompleted.setText("Set As Incomplete");
                btnCompleted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String URL = "https://api.backendless.com/85E41635-3550-CE9C-FFFA-72B1B7873C00/927034C4-79EE-0E7A-FF63-5868C69BBE00/data/todos/" + todos.getObjectId();

                        JSONObject object = new JSONObject();
                        try {
                            object.put("completed", false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JsonObjectRequest request = new JsonObjectRequest(
                                Request.Method.PUT,
                                URL,
                                object,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        ((MainActivity)context).recreate();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(mContext,"Error: "+error.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                        requestQueue.add(request);


                    }
                });
            }else{
                btnCompleted.setText("Set As Completed");
                btnCompleted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String URL = "https://api.backendless.com/85E41635-3550-CE9C-FFFA-72B1B7873C00/927034C4-79EE-0E7A-FF63-5868C69BBE00/data/todos/" + todos.getObjectId();

                        JSONObject object = new JSONObject();
                        try {
                            object.put("completed",true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JsonObjectRequest request = new JsonObjectRequest(
                                Request.Method.PUT,
                                URL,
                                object,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        ((MainActivity)context).recreate();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(mContext,"Error: "+error.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                        requestQueue.add(request);
                    }
                });
            }
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String URL = "https://api.backendless.com/85E41635-3550-CE9C-FFFA-72B1B7873C00/927034C4-79EE-0E7A-FF63-5868C69BBE00/data/todos/" + todos.getObjectId();

                    JSONObject object = new JSONObject();

                    JsonObjectRequest request = new JsonObjectRequest(
                            Request.Method.DELETE,
                            URL,
                            object,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    ((MainActivity)context).recreate();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(mContext,"Error: "+error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                    RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                    requestQueue.add(request);
                }
            });
        }
    }
}
