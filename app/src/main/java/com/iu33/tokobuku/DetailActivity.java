package com.iu33.tokobuku;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.iu33.tokobuku.adapter.AdapterBuku;
import com.iu33.tokobuku.model.ModelBuku;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DetailActivity extends AppCompatActivity {

    //TODO Create param
    String id_buku;
    @BindView(R.id.judul)
    TextView judul;
    @BindView(R.id.gambar)
    ImageView gambar;
    @BindView(R.id.harga)
    TextView harga;
    @BindView(R.id.pengarang)
    TextView pengarang;
    @BindView(R.id.keterangan)
    TextView keterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        //TODO get Data from Intent
        id_buku = getIntent().getStringExtra("id_buku");
        //TODO check Toast
        Toast.makeText(this, id_buku, Toast.LENGTH_SHORT).show();

        //TODO create CLient
        OkHttpClient network = new OkHttpClient();


        //TODO post methode
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id_buku", id_buku)
                .build();
        //TODO create URL
        final String url = AdapterBuku.BASE_URL+"detail_buku.php";
        //TODO put request
        final Request request = new Request.Builder()
                .url(url)
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

//        //TODO GET METODE
//        final String url = AdapterBuku.BASE_URL + "detail_buku.php";
//
//        Uri uri = Uri.parse(url).buildUpon()
//                .appendQueryParameter("id_buku", id_buku)
//                .build();
//        String result_url = uri.toString();
//
//        //TODO put request
//        Request request = new Request.Builder()
//                .url(result_url)
//                .build();

        //TODO execute
        network.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //TODO when error
                        Toast.makeText(DetailActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //TODO when success
                String result = response.body().string();
                try {
                    JSONObject object = new JSONObject(result);
                    String success = object.getString("success");
                    if (success.equalsIgnoreCase("true")) {
                        JSONArray array = object.getJSONArray("buku");
                        ModelBuku buku = null;
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            buku = new ModelBuku(jsonObject);
                        }
                        final ModelBuku databuku = buku;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                judul.setText(databuku.getNama_buku());
                                keterangan.setText(databuku.getKeterangan_buku());
                                harga.setText(databuku.getHarga_buku());
                                pengarang.setText(databuku.getPengarang_buku());
                                String url_gambar = AdapterBuku.BASE_URL + "image/" + databuku.getGambar_buku();
                                Glide.with(DetailActivity.this)
                                        .load(url_gambar)
                                        .placeholder(R.mipmap.ic_launcher)
                                        .error(R.mipmap.ic_launcher)
                                        .into(gambar);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}