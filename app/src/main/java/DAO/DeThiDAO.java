package DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import DTO.CauHoi;
import Database.DbHelper;

public class DeThiDAO {
    private SQLiteDatabase db;
    public DeThiDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        db = dbHelper.getReadableDatabase();
    }
    // Phương thức lấy danh sách câu hỏi theo idLoaiCauHoi
    public ArrayList<CauHoi> getCauHoiByLoaiDe(String idde) {
        ArrayList<CauHoi> listCH = new ArrayList<>();

        // Thực hiện truy vấn CSDL để lấy danh sách các cauhoi dựa trên idde
        Cursor cursor = db.rawQuery("SELECT * FROM CauHoi WHERE id_de = ?", new String[]{idde});

        // Duyệt qua kết quả trả về từ truy vấn và thêm vào danh sách CauhoiList
        if (cursor.moveToFirst()) {
            do {
                // Đọc thông tin của câu hỏi từ cursor và tạo đối tượng CauHoi tương ứng
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String id_loaiCH = cursor.getString(cursor.getColumnIndex("id_loaiCH"));
                @SuppressLint("Range") String Cau = cursor.getString(cursor.getColumnIndex("Cau"));
                @SuppressLint("Range") String noidung = cursor.getString(cursor.getColumnIndex("noi_dung"));
                @SuppressLint("Range") String dapanA = cursor.getString(cursor.getColumnIndex("dap_an_a"));
                @SuppressLint("Range") String dapanB = cursor.getString(cursor.getColumnIndex("dap_an_b"));
                @SuppressLint("Range") String dapanC = cursor.getString(cursor.getColumnIndex("dap_an_c"));
                @SuppressLint("Range") String dapanD = cursor.getString(cursor.getColumnIndex("dap_an_d"));
                @SuppressLint("Range") String dapandung = cursor.getString(cursor.getColumnIndex("dap_an_dung"));
                @SuppressLint("Range") String giaithich = cursor.getString(cursor.getColumnIndex("giai_thich"));
                @SuppressLint("Range") String id_de = cursor.getString(cursor.getColumnIndex("id_de"));
                @SuppressLint("Range") String anh = cursor.getString(cursor.getColumnIndex("anh"));

                CauHoi cauHoi = new CauHoi(id, id_loaiCH,Cau, noidung, dapanA, dapanB, dapanC, dapanD, dapandung,giaithich,id_de,anh);
                listCH.add(cauHoi);
            } while (cursor.moveToNext());
        }

        // Đóng cursor và trả về danh sách List
        cursor.close();
        return listCH;
    }

}
