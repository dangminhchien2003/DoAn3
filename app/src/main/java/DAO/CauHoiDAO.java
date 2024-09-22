package DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import DTO.BienBao;
import DTO.CauHoi;
import Database.DbHelper;

public class CauHoiDAO {
    private SQLiteDatabase db;
    public CauHoiDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        db = dbHelper.getReadableDatabase();
    }
    // Phương thức lấy danh sách câu hỏi theo idLoaiCauHoi
    public ArrayList<CauHoi> getCauHoiByLoai(String idLoaiCauHoi) {
        ArrayList<CauHoi> listCH = new ArrayList<>();

        // Thực hiện truy vấn CSDL để lấy danh sách các cauhoi dựa trên idLoaiCauhoi
        Cursor cursor = db.rawQuery("SELECT * FROM CauHoi WHERE id_loaiCH = ?", new String[]{idLoaiCauHoi});

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

    // Phương thức lấy tất cả các câu hỏi
    public ArrayList<CauHoi> getAllCauHoi() {
        ArrayList<CauHoi> listCH = new ArrayList<>();

        // Thực hiện truy vấn CSDL để lấy tất cả các cauhoi
        Cursor cursor = db.rawQuery("SELECT * FROM CauHoi", null);

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
    public ArrayList<CauHoi> searchQuestions(String keyword) {
        ArrayList<CauHoi> cauHoiList = new ArrayList<>();
        String query = "SELECT * FROM CauHoi WHERE noi_dung LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + keyword + "%"});

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
                cauHoiList.add(cauHoi);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cauHoiList;
    }

    public void ThemCauHoi(CauHoi cauHoi) {
        ContentValues values = new ContentValues();
        values.put("id", cauHoi.getId());
        values.put("id_loaiCH", cauHoi.getId_loaiCH());
        values.put("Cau", cauHoi.getCau());
        values.put("noi_dung", cauHoi.getNoidung());
        values.put("dap_an_a", cauHoi.getDapanA());
        values.put("dap_an_b", cauHoi.getDapanB());
        values.put("dap_an_c", cauHoi.getDapanC());
        values.put("dap_an_d", cauHoi.getDapanD());
        values.put("dap_an_dung", cauHoi.getDapandung());
        values.put("giai_thich", cauHoi.getGiaithich());
        values.put("id_de", cauHoi.getId_de());
        values.put("anh", cauHoi.getAnh());

        db.insert("CauHoi", null, values);
        db.close();
    }

    public boolean Kiemtramacauhoi(String idloai) {
        String sql = "SELECT * FROM CauHoi WHERE id=?";
        Cursor cursor = db.rawQuery(sql, new String[]{idloai});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean delete(String id) {
        int rowsDeleted = db.delete("CauHoi", "id=?", new String[]{id});
        return rowsDeleted > 0;
    }

    public boolean Suacauhoi(String id, String idloai, String cau, String noidung, String dapanA, String dapanB, String dapanC, String dapanD, String dapanDung, String giaithich, String made, String anh) {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("id_loaiCH",idloai);
        values.put("Cau", cau);
        values.put("noi_dung", noidung);
        values.put("dap_an_a", dapanA);
        values.put("dap_an_b", dapanB);
        values.put("dap_an_c", dapanC);
        values.put("dap_an_d", dapanD);
        values.put("dap_an_dung", dapanDung);
        values.put("giai_thich", giaithich);
        values.put("id_de", made);
        values.put("anh", anh);

        int rowsAffected = db.update("CauHoi", values, "id=?", new String[]{id});
        return rowsAffected > 0;
    }
}
