package DTO;

import android.os.Parcel;
import android.os.Parcelable;

public class CauHoi implements Parcelable {
    private String id;
    private String idLoaiCH;
    private String Cau;
    private String noidung;
    private String dapanA;
    private String dapanB;
    private String dapanC;
    private String dapanD;
    private String dapandung;
    private String giaithich;
    private String id_de;
    private String anh;
    private String selectedAnswer;

    public CauHoi() {}

    public CauHoi(String id, String idLoaiCH, String Cau, String noidung, String dapanA, String dapanB, String dapanC, String dapanD, String dapandung, String giaithich, String id_de, String anh) {
        this.id = id;
        this.idLoaiCH = idLoaiCH;
        this.Cau = Cau;
        this.noidung = noidung;
        this.dapanA = dapanA;
        this.dapanB = dapanB;
        this.dapanC = dapanC;
        this.dapanD = dapanD;
        this.dapandung = dapandung;
        this.giaithich = giaithich;
        this.id_de = id_de;
        this.anh = anh;
    }

    // Getters and setters...

    public String getId_de() {
        return id_de;
    }

    public void setId_de(String id_de) {
        this.id_de = id_de;
    }

    public String getCau() {
        return Cau;
    }

    public void setCau(String Cau) {
        this.Cau = Cau;
    }

    public String getGiaithich() {
        return giaithich;
    }

    public void setGiaithich(String giaithich) {
        this.giaithich = giaithich;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public String getIdLoaiCH() {
        return idLoaiCH;
    }

    public void setIdLoaiCH(String idLoaiCH) {
        this.idLoaiCH = idLoaiCH;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_loaiCH() {
        return idLoaiCH;
    }

    public void setId_loaiCH(String idLoaiCH) {
        this.idLoaiCH = idLoaiCH;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getDapanA() {
        return dapanA;
    }

    public void setDapanA(String dapanA) {
        this.dapanA = dapanA;
    }

    public String getDapanB() {
        return dapanB;
    }

    public void setDapanB(String dapanB) {
        this.dapanB = dapanB;
    }

    public String getDapanC() {
        return dapanC;
    }

    public void setDapanC(String dapanC) {
        this.dapanC = dapanC;
    }

    public String getDapanD() {
        return dapanD;
    }

    public void setDapanD(String dapanD) {
        this.dapanD = dapanD;
    }

    public String getDapandung() {
        return dapandung;
    }

    public void setDapandung(String dapandung) {
        this.dapandung = dapandung;
    }

    // Parcelable implementation
    protected CauHoi(Parcel in) {
        id = in.readString();
        idLoaiCH = in.readString();
        Cau = in.readString();
        noidung = in.readString();
        dapanA = in.readString();
        dapanB = in.readString();
        dapanC = in.readString();
        dapanD = in.readString();
        dapandung = in.readString();
        giaithich = in.readString();
        id_de = in.readString();
        anh = in.readString();
        selectedAnswer = in.readString();
    }

    public static final Creator<CauHoi> CREATOR = new Creator<CauHoi>() {
        @Override
        public CauHoi createFromParcel(Parcel in) {
            return new CauHoi(in);
        }

        @Override
        public CauHoi[] newArray(int size) {
            return new CauHoi[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(idLoaiCH);
        dest.writeString(Cau);
        dest.writeString(noidung);
        dest.writeString(dapanA);
        dest.writeString(dapanB);
        dest.writeString(dapanC);
        dest.writeString(dapanD);
        dest.writeString(dapandung);
        dest.writeString(giaithich);
        dest.writeString(id_de);
        dest.writeString(anh);
        dest.writeString(selectedAnswer);
    }
}
