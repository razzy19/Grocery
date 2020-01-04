package com.example.grocersapp.Views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;

import Utilities.CheckInternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.grocersapp.Controller.CartAdapter;
import com.example.grocersapp.Controller.ProductItemAdapter;
import com.example.grocersapp.Model.CartItem;
import com.example.grocersapp.Model.ProductItem;
import com.example.grocersapp.Model.CartData;
import com.example.grocersapp.R;
import com.example.grocersapp.api.RetrofitClient;
import com.example.grocersapp.database.DatabaseClient;
import com.example.grocersapp.fragments.NotifyFragment;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Cart extends AppCompatActivity {

    CartAdapter cartAdapter;
    RecyclerView recyclerView;
    Context context;
    JSONArray jsonArray;
    JSONObject jsonObject;
    Map m;
    Button button;
    Iterator iterator;
    List<CartData> taskList;
    ConstraintLayout layoutemptycart;

    List<CartData> tasks2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        CartApplication cartApplication = (CartApplication)getApplication();
        cartApplication.cart = this;

        getTasks();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //your method
                if(CheckInternetConnection.checkConnection(Cart.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            new Timer().cancel();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        }
                    });

                }
                if(!CheckInternetConnection.checkConnection(Cart.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(Cart.this, "इंटरनेट कनेक्टिव्हिटी उपलब्ध नाही ", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        }, 0, 1000);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My Cart");
        button=findViewById(R.id.btn_proceed);
        layoutemptycart=findViewById(R.id.layout_empty_cart);
        recyclerView = findViewById(R.id.cart_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        jsonArray = new JSONArray();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                iterator = tasks2.iterator();
//                while (iterator.hasNext())
//                {
//                    Log.d("LIVEDATA",iterator.next()+"");
//                    CartItem c = new CartItem();
//                }


                    try
                    {
                        for (CartData t: tasks2) {
                            Log.d("LIVEDATA",t.getName());
                            jsonObject = new JSONObject();
                            jsonObject.put("item_id", t.getItem_id());
                            jsonObject.put("qty", t.getQty());
                            jsonObject.put("unit_price", t.getPrice());
                            Log.d("item_id2", t.getItem_id() + "");
                            Log.d("qty", t.getQty() + "");
//                        jsonObject = new JSONObject(m);
                            jsonArray.put(jsonObject);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                Intent intent =new Intent(Cart.this,OrderDetails.class);
                intent.putExtra("orderdata",jsonArray+"");
                startActivity(intent);

                Log.d("jsonarray", jsonArray.toString());
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id= item.getItemId();

        if(id == R.id.main_clear){

            new AlertDialog.Builder(Cart.this)
                    .setTitle("क्लिअर कार्ट")
                    .setMessage("आपली खात्री आहे की आपण कार्ट साफ करू इच्छिता?")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            Toast.makeText(Cart.this,"Cleared",Toast.LENGTH_SHORT).show();
                            deleteTask();

                            recyclerView.setVisibility(View.INVISIBLE);

                            button.setTextColor(getApplication().getResources().getColor(R.color.grey)); //TAKE DEFAULT COLOR
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


        }

        return super.onOptionsItemSelected(item);
    }

    public void deleteTask() {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(context).getAppDatabase()
                        .taskDao()
                        .deleteAll();

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                taskList.clear();
                layoutemptycart.setVisibility(View.VISIBLE);
                button.setEnabled(false);
                cartAdapter.notifyDataSetChanged();
//                Toast.makeText(mctx, "Deleted", Toast.LENGTH_LONG).show();
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();
    }




    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<CartData>> {

            @Override
            protected List<CartData> doInBackground(Void... voids) {
                taskList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<CartData> tasks) {
                super.onPostExecute(tasks);
                tasks2 = tasks;

                if(tasks2.size()==-1){

                }
                cartAdapter= new CartAdapter(Cart.this,tasks,layoutemptycart,recyclerView,button);
                recyclerView.setAdapter(cartAdapter);

                if(cartAdapter.getItemCount()==0){
                    layoutemptycart.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    button.setEnabled(false);
                    button.setTextColor(getApplication().getResources().getColor(R.color.grey)); //TAKE DEFAULT COLOR
                }


            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

}
