package com.iu33.tokobuku.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hp on 12/21/2017.
 */

public class ModelBuku {
    String nama_buku;
    String gambar_buku;
    String keterangan_buku;
    String pengarang_buku;
    String harga_buku;


    String id_buku;

    public String getId_buku() {
        return id_buku;
    }

    public void setId_buku(String id_buku) {
        this.id_buku = id_buku;
    }

    public String getNama_buku() {
        return nama_buku;
    }

    public void setNama_buku(String nama_buku) {
        this.nama_buku = nama_buku;
    }

    public String getGambar_buku() {
        return gambar_buku;
    }

    public void setGambar_buku(String gambar_buku) {
        this.gambar_buku = gambar_buku;
    }

    public String getKeterangan_buku() {
        return keterangan_buku;
    }

    public void setKeterangan_buku(String keterangan_buku) {
        this.keterangan_buku = keterangan_buku;
    }

    public String getPengarang_buku() {
        return pengarang_buku;
    }

    public void setPengarang_buku(String pengarang_buku) {
        this.pengarang_buku = pengarang_buku;
    }

    public String getHarga_buku() {
        return harga_buku;
    }

    public void setHarga_buku(String harga_buku) {
        this.harga_buku = harga_buku;
    }

    //TOFO generate setter and getter

    public ModelBuku(JSONObject object){
        try {

            this.id_buku = object.getString("id_buku");
            this.nama_buku = object.getString("nama_buku");
            this.pengarang_buku = object.getString("pengarang_buku");
            this.harga_buku= object.getString("harga_buku");
            this.keterangan_buku= object.getString("keterangan_buku");
            this.gambar_buku = object.getString("gambar_buku");

        } catch (JSONException e){
            e.printStackTrace();
        }
    }

}
