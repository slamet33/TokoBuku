package com.iu33.tokobuku;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.iu33.tokobuku.adapter.AdapterBuku;
import com.iu33.tokobuku.model.ModelBuku;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //TODO Create param
    ArrayList<ModelBuku> listBuku = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //TODO set View Linear or Grid on Recycler View
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        //TODO request Server
        OkHttpClient network = new OkHttpClient();

        //TODO inpun url methode get
        String URL = AdapterBuku.BASE_URL+"read_buku.php";
        final Request request = new Request.Builder().url(URL).build();

        //TODO create Progress Dialog to loading
        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "", "Loading", false);

        //TODO begin execute request
        network.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //TODO when failure
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Tidak ada koneksi / Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //TODO check respon
                String result = response.body().string();
                try{
                    //TODO get Object JSon From response
                    JSONObject object = new JSONObject(result);
                    String success = object.getString("success");
                    String message = object.getString("message");
                    if (success.equalsIgnoreCase("true")){
                        //TODO get Array Buku
                        JSONArray array = object.getJSONArray("buku");
                        //TODO looping to get data item
                        for (int i = 0; i < array.length(); i++) {
                            //TODO get Json in array item
                            JSONObject jsonObject = array.getJSONObject(i);
                            ModelBuku modelBuku = new ModelBuku(jsonObject);
                            listBuku.add(modelBuku);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                AdapterBuku adapter = new AdapterBuku(listBuku,MainActivity.this);
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

}