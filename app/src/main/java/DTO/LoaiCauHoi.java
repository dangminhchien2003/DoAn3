package DTO;

public class LoaiCauHoi {
    String id, tenloai, mota, anh;

    public  LoaiCauHoi(){

    }
    public LoaiCauHoi(String id, String tenloai, String mota, String anh) {
        this.id = id;
        this.tenloai = tenloai;
        this.mota = mota;
        this.anh = anh;
    }

    public LoaiCauHoi(String id, String tenLoai) {
        this.id = id;
        this.tenloai = tenLoai;
    }
    @Override
    public String toString() {
        return tenloai;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
