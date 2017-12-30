package com.iu33.tokobuku.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iu33.tokobuku.DetailActivity;
import com.iu33.tokobuku.R;
import com.iu33.tokobuku.model.ModelBuku;

import java.util.ArrayList;



/**
 * Created by  on 12/21/2017.
 */

public class AdapterBuku extends RecyclerView.Adapter<AdapterBuku.BukuViewHolder> {
    //TODO create static url
    public static final String BASE_URL = "http://192.168.95.39/toko_buku/";
    //TODO descrip Variable
    public ArrayList<ModelBuku> bukuModel;
    public Context context;

    //TODO Create COnstructor
    public AdapterBuku(ArrayList<ModelBuku> bukuModel, Context context) {
        this.bukuModel = bukuModel;
        this.context = context;
    }

    @Override
    public BukuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO Connect with layout
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new BukuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BukuViewHolder holder, final int position) {
        //TODO acrtion Set Object on list Item
        String url_image = BASE_URL +"image/"+bukuModel.get(position).getGambar_buku();
        String title = bukuModel.get(position).getNama_buku();

        holder.textBuku.setText(title);
        Glide.with(context)
                .load(url_image)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.imageBuku);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO action to go to Detail Activirty
                Intent go = new Intent(context, DetailActivity.class);
                //TODO send Parameter
                go.putExtra("id_buku", bukuModel.get(position).getId_buku());
                context.startActivity(go);
            }
        });
    }

    @Override
    public int getItemCount() {
        //TODO size data from buku model
        return bukuModel.size();
    }

    public class BukuViewHolder extends RecyclerView.ViewHolder {
        //TODO descrip id in layout listitem
        ImageView imageBuku;
        TextView textBuku;
        public BukuViewHolder(View itemView) {
            super(itemView);
            imageBuku = itemView.findViewById(R.id.imageBuku);
            textBuku = itemView.findViewById(R.id.textBuku);
        }
    }
}