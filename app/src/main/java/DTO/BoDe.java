package DTO;

public class BoDe {
    private String id_de;
    private String ten_de;

    public BoDe(String id_de, String ten_de) {
        this.id_de = id_de;
        this.ten_de = ten_de;
    }

    public BoDe() {

    }

    public String getId_de() {
        return id_de;
    }

    public void setId_de(String id_de) {
        this.id_de = id_de;
    }

    public String getTen_de() {
        return ten_de;
    }

    public void setTen_de(String ten_de) {
        this.ten_de = ten_de;
    }
}
