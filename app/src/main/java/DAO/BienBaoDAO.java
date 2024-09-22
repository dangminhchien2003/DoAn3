package DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import DTO.BienBao;
import DTO.CauHoi;
import DTO.LoaiBienBao;
import Database.DbHelper;

public class BienBaoDAO {
    private SQLiteDatabase db;
    public BienBaoDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        db = dbHelper.getReadableDatabase();
    }
    public ArrayList<BienBao> getBienBaoByLoai(String idLoaiBienBao) {
        ArrayList<BienBao> listbb = new ArrayList<>();

        // Thực hiện truy vấn CSDL để lấy danh sách các biển báo dựa trên idLoaiBienBao
        Cursor cursor = db.rawQuery("SELECT * FROM BienBao WHERE id_loai = ?", new String[]{idLoaiBienBao});

        // Duyệt qua kết quả trả về từ truy vấn và thêm vào danh sách bienBaoList
        if (cursor.moveToFirst()) {
            do {
                // Đọc thông tin của biển báo từ cursor và tạo đối tượng BienBao tương ứng
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String tenBienBao = cursor.getString(cursor.getColumnIndex("tenbien"));
                @SuppressLint("Range") String sobien = cursor.getString(cursor.getColumnIndex("sobien"));
                @SuppressLint("Range") String moTa = cursor.getString(cursor.getColumnIndex("mota"));
                @SuppressLint("Range") String anh = cursor.getString(cursor.getColumnIndex("anh"));

                // Tạo đối tượng BienBao và thêm vào danh sách bienBaoList
                BienBao bienBao = new BienBao(id, idLoaiBienBao, tenBienBao, sobien, moTa, anh);
                listbb.add(bienBao);
            } while (cursor.moveToNext());
        }

        // Đóng cursor và trả về danh sách bienBaoList
        cursor.close();
        return listbb;
    }


    public ArrayList<BienBao> getid(){
        String sql="SELECT * FROM BienBao WHERE id_loai = 1";
        return (ArrayList<BienBao>) getDatabb(sql);
    }

    @SuppressLint("Range")
    private ArrayList<BienBao> getDatabb(String sql, String... selectionArgs) {
        ArrayList<BienBao> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            BienBao obj = new BienBao();
            obj.setId(c.getString(c.getColumnIndexOrThrow("id")));
            obj.setSobien(c.getString(c.getColumnIndexOrThrow("sobien")));
            obj.setId_loai(c.getString(c.getColumnIndexOrThrow("id_loai")));
            obj.setTenbien(c.getString(c.getColumnIndexOrThrow("tenbien")));
            obj.setMota(c.getString(c.getColumnIndexOrThrow("mota")));
            obj.setAnh(c.getString(c.getColumnIndexOrThrow("anh")));

            list.add(obj);
            Log.e("BienBao", obj.getTenbien());
        }
        return list;
    }

    public ArrayList<BienBao> getAll(){
        String sql="SELECT * FROM BienBao";
        return (ArrayList<BienBao>) getData(sql);
    }
    @SuppressLint("Range")
    private ArrayList<BienBao> getData(String sql, String... selectionArgs) {
        ArrayList<BienBao> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            BienBao obj = new BienBao();
            obj.setId(c.getString(c.getColumnIndexOrThrow("id")));
            obj.setId_loai(c.getString(c.getColumnIndexOrThrow("id_loai")));
            obj.setSobien(c.getString(c.getColumnIndexOrThrow("sobien")));
            obj.setTenbien(c.getString(c.getColumnIndexOrThrow("tenbien")));
            obj.setMota(c.getString(c.getColumnIndexOrThrow("mota")));
            obj.setAnh(c.getString(c.getColumnIndexOrThrow("anh")));

            list.add(obj);
            Log.e("BienBao", obj.getTenbien());
        }
        return list;
    }
    public void ThemBienBao(BienBao bienBao) {
        ContentValues values = new ContentValues();
        values.put("id", bienBao.getId());
        values.put("id_loai", bienBao.getId_loai());
        values.put("sobien", bienBao.getSobien());
        values.put("tenbien", bienBao.getTenbien());
        values.put("mota", bienBao.getMota());
        values.put("anh", bienBao.getAnh());

        db.insert("BienBao", null, values);
        db.close();
    }

    public boolean Kiemtramabienbao(String idloai) {
        String sql = "SELECT * FROM BienBao WHERE id=?";
        Cursor cursor = db.rawQuery(sql, new String[]{idloai});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean Suabienbao(String ma,String maloai,String sobien, String ten, String moTa, String anh) {
        ContentValues values = new ContentValues();
        values.put("id", ma);
        values.put("id_loai",maloai);
        values.put("sobien", sobien);
        values.put("tenbien", ten);
        values.put("mota", moTa);
        values.put("anh", anh);

        int rowsAffected = db.update("BienBao", values, "id=?", new String[]{ma});
        return rowsAffected > 0;
    }

    public boolean delete(String id) {
        int rowsDeleted = db.delete("BienBao", "id=?", new String[]{id});
        return rowsDeleted > 0;
    }

    public ArrayList<BienBao> search(String keyword) {
        ArrayList<BienBao> bienBaoArrayList = new ArrayList<>();
        String query = "SELECT * FROM BienBao WHERE tenbien LIKE ? OR sobien LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + keyword + "%", "%" + keyword + "%"});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String idLoaiBienBao = cursor.getString(cursor.getColumnIndex("id_loai"));
                @SuppressLint("Range") String tenBienBao = cursor.getString(cursor.getColumnIndex("tenbien"));
                @SuppressLint("Range") String sobien = cursor.getString(cursor.getColumnIndex("sobien"));
                @SuppressLint("Range") String moTa = cursor.getString(cursor.getColumnIndex("mota"));
                @SuppressLint("Range") String anh = cursor.getString(cursor.getColumnIndex("anh"));

                // Tạo đối tượng BienBao và thêm vào danh sách bienBaoList
                BienBao bienBao = new BienBao(id, idLoaiBienBao, tenBienBao, sobien, moTa, anh);
                bienBaoArrayList.add(bienBao);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bienBaoArrayList;
    }

}
