package DTO;

public class BienBao {
    String id, id_loai, tenbien,sobien, mota, anh;


    public BienBao(String id, String id_loai, String tenbien, String sobien, String mota, String anh) {
        this.id = id;
        this.id_loai = id_loai;
        this.tenbien = tenbien;
        this.sobien = sobien;
        this.mota = mota;
        this.anh = anh;
    }

    public BienBao() {

    }

    public String getSobien() {
        return sobien;
    }

    public void setSobien(String sobien) {
        this.sobien = sobien;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_loai() {
        return id_loai;
    }

    public void setId_loai(String id_loai) {
        this.id_loai = id_loai;
    }

    public String getTenbien() {
        return tenbien;
    }

    public void setTenbien(String tenbien) {
        this.tenbien = tenbien;
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
