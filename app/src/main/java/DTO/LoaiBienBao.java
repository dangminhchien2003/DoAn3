package DTO;

import android.widget.ImageView;

public class LoaiBienBao {
    String id, TenloaiBB, Mota, anh;

    public LoaiBienBao(String id, String tenloaiBB, String mota, String anh) {
        this.id = id;
        TenloaiBB = tenloaiBB;
        Mota = mota;
        this.anh = anh;
    }

    public LoaiBienBao() {

    }

    public LoaiBienBao(String id, String tenLoai) {
        this.id = id;
        this.TenloaiBB = tenLoai;
    }

    @Override
    public String toString() {
        return TenloaiBB;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenloaiBB() {
        return TenloaiBB;
    }

    public void setTenloaiBB(String tenloaiBB) {
        TenloaiBB = tenloaiBB;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
