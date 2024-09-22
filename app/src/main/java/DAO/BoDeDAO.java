package DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import DTO.BienBao;
import DTO.BoDe;
import DTO.LoaiBienBao;
import Database.DbHelper;

public class BoDeDAO {
    private SQLiteDatabase db;
    public BoDeDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public ArrayList<BoDe> getAll(){
        String sql="SELECT * FROM DeThi";
        return (ArrayList<BoDe>) getData(sql);
    }
    @SuppressLint("Range")
    private ArrayList<BoDe> getData(String sql, String... selectionArgs) {
        ArrayList<BoDe> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            BoDe obj = new BoDe();
            obj.setId_de(c.getString(c.getColumnIndexOrThrow("id_de")));
            obj.setTen_de(c.getString(c.getColumnIndexOrThrow("ten_de")));

            list.add(obj);
            Log.e("BoDe", obj.getTen_de());
        }
        return list;
    }

    public void ThemBode(BoDe boDe) {
        ContentValues values = new ContentValues();
        values.put("id_de", boDe.getId_de());
        values.put("ten_de", boDe.getTen_de());

        db.insert("DeThi", null, values);
        db.close();
    }

    public boolean Kiemtramabode(String idloai) {
        String sql = "SELECT * FROM DeThi WHERE id_de=?";
        Cursor cursor = db.rawQuery(sql, new String[]{idloai});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean SuaBode(String mabode, String tenbode) {
        ContentValues values = new ContentValues();
        values.put("ten_de", tenbode);

        int rowsAffected = db.update("DeThi", values, "id_de=?", new String[]{mabode});
        return rowsAffected > 0;
    }

    public boolean delete(String id) {
        int rowsDeleted = db.delete("DeThi", "id_de=?", new String[]{id});
        return rowsDeleted > 0;
    }
    public ArrayList<BoDe> search(String keyword) {
        ArrayList<BoDe> boDeArrayList = new ArrayList<>();
        String query = "SELECT * FROM DeThi WHERE ten_de LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + keyword + "%"});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id_de"));
                @SuppressLint("Range") String ten = cursor.getString(cursor.getColumnIndex("ten_de"));

                // Tạo đối tượng BienBao và thêm vào danh sách bienBaoList
                BoDe boDe = new BoDe(id, ten);
                boDeArrayList.add(boDe);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return boDeArrayList;
    }
}
