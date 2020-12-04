package objects;

import javafx.beans.property.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
    public SimpleIntegerProperty ID, stt;
    public SimpleStringProperty date, hoten, ghichu;
    public SimpleStringProperty tongtien;

    public Order(Integer stt, Integer ID, Date date, String hoten, float tongtien, String ghichu) {
        this.stt = new SimpleIntegerProperty(stt);
        this.ID = new SimpleIntegerProperty(ID);
        this.hoten = new SimpleStringProperty(hoten);
        this.tongtien = new SimpleStringProperty(String.format("%,.2f", tongtien));
        this.ghichu = new SimpleStringProperty(ghichu);

        SimpleDateFormat tmp = new SimpleDateFormat("dd-MM-yyyy");
        String _date = tmp.format(date);
        this.date = new SimpleStringProperty(_date);
    }

    // stt
    public int getStt() { return stt.get(); }
    public void setStt(int p_stt) { this.stt = new SimpleIntegerProperty(p_stt); }

    // ID
    public int getID() { return ID.get(); }
    public void setID(int p_ID) { this.ID = new SimpleIntegerProperty(p_ID); }

    // date
    public String getDate() { return date.get(); }
    public void setDate(Date p_date) {
        SimpleDateFormat tmp = new SimpleDateFormat("dd-MM-yyyy");
        String _date = tmp.format(p_date);
        this.date = new SimpleStringProperty(_date);
    }

    // hoten
    public String getHoten() { return hoten.get(); }
    public void setHoten(String p_hoten) { this.hoten = new SimpleStringProperty(p_hoten); }

    // ghichu
    public String getGhichu() { return ghichu.get(); }
    public void setGhichu(String ghichu) { this.ghichu = new SimpleStringProperty(ghichu); }

    // tongtien
    public String getTongtien() { return tongtien.get(); }
    public void setTongtien(float tongtien) { this.tongtien = new SimpleStringProperty(String.format("%,.2f", tongtien)); }
}
