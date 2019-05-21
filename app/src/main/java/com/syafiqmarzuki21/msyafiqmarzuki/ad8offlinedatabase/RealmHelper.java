package com.syafiqmarzuki21.msyafiqmarzuki.ad8offlinedatabase;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class RealmHelper {
    private Context context;
    private Realm realm;

    public RealmHelper(Context context) {
        this.context = context;
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    //method insert
    public void insertData (CatatanModel catatan){
        realm.beginTransaction();
        realm.copyToRealm(catatan);
        realm.commitTransaction();
        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                Toast.makeText(context,"Data Berhasil tambah",Toast.LENGTH_SHORT).show();
            }
        });
        realm.close();
    }

    public long getNextId(){
        if(realm.where(CatatanModel.class).count() != 0){
            long id = realm.where(CatatanModel.class).max("id").longValue();
            return id+1;
        }else{
            return 1;
        }
    }
    //menampilkan data
    public List<CatatanModel> showData(){
        RealmResults<CatatanModel> datahasil = realm.where(CatatanModel.class).findAll();
        List<CatatanModel> datalist = new ArrayList<>();
        datalist.addAll(realm.copyFromRealm(datahasil));
        realm.commitTransaction();
        return datalist;
    }

    //menampilkan satu data
    public  CatatanModel showOneData(Integer id){
        CatatanModel data = realm.where(CatatanModel.class).equalTo("id",id).findFirst();
        return data;

    }

    //update
    public void updateData(CatatanModel catatan){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(catatan);
        realm.commitTransaction();
        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                Toast.makeText(context,"Berhasil update",Toast.LENGTH_SHORT).show();
            }
        });
        realm.close();
    }
    //delete

    public void deleteData(Integer id){
        realm.beginTransaction();
        CatatanModel catatan = realm.where(CatatanModel.class).equalTo("id",id).findFirst();
        catatan.deleteFromRealm();
        realm.commitTransaction();
        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                Toast.makeText(context,"Berhasil hapus",Toast.LENGTH_SHORT).show();

            }
        });
    }


}
