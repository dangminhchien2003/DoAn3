package DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import DTO.LoaiBienBao;
import DTO.LoaiCauHoi;
import Database.DbHelper;

public class LoaiCauHoiDAO {
    private SQLiteDatabase db;
    public LoaiCauHoiDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<LoaiCauHoi> getAll(){
        String sql="SELECT * FROM Loaicauhoi ";
        return (ArrayList<LoaiCauHoi>) getData(sql);
    }
    @SuppressLint("Range")
    private ArrayList<LoaiCauHoi> getData(String sql, String... selectionArgs) {
        ArrayList<LoaiCauHoi> listLCH = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            LoaiCauHoi obj = new LoaiCauHoi();
            obj.setId(c.getString(c.getColumnIndexOrThrow("id")));
            obj.setTenloai(c.getString(c.getColumnIndexOrThrow("tenloai")));
            obj.setMota(c.getString(c.getColumnIndexOrThrow("mota")));
            obj.setAnh(c.getString(c.getColumnIndexOrThrow("anh")));

            listLCH.add(obj);
            Log.e("Loaicauhoi", obj.getTenloai());
        }
        return listLCH;
    }

    public void ThemLoaiCauHoi(LoaiCauHoi loaiCauHoi) {
        ContentValues values = new ContentValues();
        values.put("id", loaiCauHoi.getId());
        values.put("tenloai", loaiCauHoi.getTenloai());
        values.put("mota", loaiCauHoi.getMota());
        values.put("anh", loaiCauHoi.getAnh());

        db.insert("Loaicauhoi", null, values);
        db.close();
    }

    public boolean Kiemtramaloaicauhoi(String idloai) {
        String sql = "SELECT * FROM Loaicauhoi WHERE id=?";
        Cursor cursor = db.rawQuery(sql, new String[]{idloai});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean SuaLoaiCauHoi(String maLoai, String tenLoai, String moTa, String anh) {
        ContentValues values = new ContentValues();
        values.put("tenloai", tenLoai);
        values.put("mota", moTa);
        values.put("anh", anh); // Add image name to the ContentValues

        int rowsAffected = db.update("Loaicauhoi", values, "id=?", new String[]{maLoai});
        return rowsAffected > 0;
    }

    public boolean delete(String id) {
        int rowsDeleted = db.delete("Loaicauhoi", "id=?", new String[]{id});
        return rowsDeleted > 0;
    }
    @SuppressLint("Range")
    public ArrayList<LoaiCauHoi> getAlltenloai() {
        ArrayList<LoaiCauHoi> loaiCauHoiArrayList = new ArrayList<>();
        String sql = "SELECT id, tenloai FROM Loaicauhoi";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String tenLoai = cursor.getString(cursor.getColumnIndexOrThrow("tenloai"));
                LoaiCauHoi loaiCauHoi = new LoaiCauHoi(id, tenLoai);
                loaiCauHoiArrayList.add(loaiCauHoi);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return loaiCauHoiArrayList;
    }

    public ArrayList<LoaiCauHoi> search(String keyword) {
        ArrayList<LoaiCauHoi> loaiCauHoiArrayList = new ArrayList<>();
        String query = "SELECT * FROM Loaicauhoi WHERE tenloai LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + keyword + "%"});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String ten = cursor.getString(cursor.getColumnIndex("tenloai"));
                @SuppressLint("Range") String mota = cursor.getString(cursor.getColumnIndex("mota"));
                @SuppressLint("Range") String anh = cursor.getString(cursor.getColumnIndex("anh"));

                LoaiCauHoi loaiCauHoi = new LoaiCauHoi(id, ten,mota,anh);
                loaiCauHoiArrayList.add(loaiCauHoi);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return loaiCauHoiArrayList;
    }
}
