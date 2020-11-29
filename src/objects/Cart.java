package objects;

import javafx.beans.property.*;

public class Cart {
    private SimpleIntegerProperty stt;
    private SimpleIntegerProperty soluong;
    private SimpleStringProperty tensanpham, dongia;
    private String thanhtien;

    public Cart(Integer stt, String tensanpham, Integer soluong, float dongia) {
        this.stt = new SimpleIntegerProperty(stt);
        this.tensanpham = new SimpleStringProperty(tensanpham);
        this.dongia = new SimpleStringProperty(String.format("%,.2f", dongia));
        this.soluong = new SimpleIntegerProperty(soluong);
        this.thanhtien = String.format("%,.2f", (float)soluong * dongia);
    }

    // stt
    public int getStt() { return stt.get(); }
    public void setStt(int p_stt) { this.stt = new SimpleIntegerProperty(p_stt); }

    // tensanpham
    public String getTensanpham() { return tensanpham.get(); }
    public void setTensanpham(String p_tensanpham) { this.tensanpham = new SimpleStringProperty(p_tensanpham); }

    // dongia
    public String getDongia() { return dongia.get(); }
    public void setDongia(float dongia) { this.dongia = new SimpleStringProperty(String.format("%,.2f", dongia)); }

    // soluong
    public int getSoluong() { return soluong.get(); }
    public void setSoluong(int soluong) { this.soluong = new SimpleIntegerProperty(soluong); }

    // thanhtien
    public String getThanhtien() { return thanhtien; }
    public void setThanhtien(String thanhtien) { this.thanhtien = thanhtien; }
}
