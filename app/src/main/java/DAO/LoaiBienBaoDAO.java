package DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import DTO.BoDe;
import DTO.LoaiBienBao;
import DTO.LoaiCauHoi;
import Database.DbHelper;

public class LoaiBienBaoDAO {
    private SQLiteDatabase db;
    public LoaiBienBaoDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public ArrayList<LoaiBienBao> getAll(){
        String sql="SELECT * FROM LoaiBienBao";
        return (ArrayList<LoaiBienBao>) getData(sql);
    }
    @SuppressLint("Range")
    private ArrayList<LoaiBienBao> getData(String sql, String... selectionArgs) {
        ArrayList<LoaiBienBao> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            LoaiBienBao obj = new LoaiBienBao();
            obj.setId(c.getString(c.getColumnIndexOrThrow("id")));
            obj.setTenloaiBB(c.getString(c.getColumnIndexOrThrow("tenloai")));
            obj.setMota(c.getString(c.getColumnIndexOrThrow("mota")));
            obj.setAnh(c.getString(c.getColumnIndexOrThrow("anh")));

            list.add(obj);
            Log.e("LoaiBienBao", obj.getTenloaiBB());
        }
        return list;
    }
    @SuppressLint("Range")
    public ArrayList<LoaiBienBao> getAlltenloai() {
        ArrayList<LoaiBienBao> loaiBienBaoList = new ArrayList<>();
        String sql = "SELECT id, tenloai FROM LoaiBienBao";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String tenLoai = cursor.getString(cursor.getColumnIndexOrThrow("tenloai"));
                LoaiBienBao loaiBienBao = new LoaiBienBao(id, tenLoai);
                loaiBienBaoList.add(loaiBienBao);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return loaiBienBaoList;
    }

    public void ThemLoaiBienBao(LoaiBienBao loaiBienBao) {
        ContentValues values = new ContentValues();
        values.put("id", loaiBienBao.getId());
        values.put("tenloai", loaiBienBao.getTenloaiBB());
        values.put("mota", loaiBienBao.getMota());
        values.put("anh", loaiBienBao.getAnh());

        db.insert("LoaiBienBao", null, values);
        db.close();
    }

    public boolean Kiemtramaloaibienbao(String idloai) {
        String sql = "SELECT * FROM LoaiBienBao WHERE id=?";
        Cursor cursor = db.rawQuery(sql, new String[]{idloai});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean SuaLoaibienbao(String maLoai, String tenLoai, String moTa, String anh) {
        ContentValues values = new ContentValues();
        values.put("tenloai", tenLoai);
        values.put("mota", moTa);
        values.put("anh", anh); // Add image name to the ContentValues

        int rowsAffected = db.update("LoaiBienBao", values, "id=?", new String[]{maLoai});
        return rowsAffected > 0;
    }

    public boolean delete(String id) {
        int rowsDeleted = db.delete("LoaiBienBao", "id=?", new String[]{id});
        return rowsDeleted > 0;
    }

    public ArrayList<LoaiBienBao> search(String keyword) {
        ArrayList<LoaiBienBao> loaiBienBaoArrayList = new ArrayList<>();
        String query = "SELECT * FROM LoaiBienBao WHERE tenloai LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + keyword + "%"});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String ten = cursor.getString(cursor.getColumnIndex("tenloai"));
                @SuppressLint("Range") String mota = cursor.getString(cursor.getColumnIndex("mota"));
                @SuppressLint("Range") String anh = cursor.getString(cursor.getColumnIndex("anh"));

                // Tạo đối tượng BienBao và thêm vào danh sách bienBaoList
                LoaiBienBao loaiBienBao = new LoaiBienBao(id, ten,mota,anh);
                loaiBienBaoArrayList.add(loaiBienBao);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return loaiBienBaoArrayList;
    }
}
