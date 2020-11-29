package objects;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product {
    private SimpleIntegerProperty ID, stt, soluong;
    private SimpleStringProperty tenhang, dongia;
    private SimpleFloatProperty sale;

    public Product(Integer stt, Integer ID, String tenhang, Integer soluong, Integer dongia, Float sale) {
        this.stt = new SimpleIntegerProperty(stt);
        this.ID = new SimpleIntegerProperty(ID);
        this.tenhang = new SimpleStringProperty(tenhang);
        this.soluong = new SimpleIntegerProperty(soluong);
        this.dongia = new SimpleStringProperty(String.format("%,.2f", (float)dongia));
        this.sale = new SimpleFloatProperty(sale);
    }

    // stt
    public int getStt() { return stt.get(); }
    public void setStt(int p_stt) { this.stt = new SimpleIntegerProperty(p_stt); }

    // ID
    public int getID() { return ID.get(); }
    public void setID(int p_ID) { this.ID = new SimpleIntegerProperty(p_ID); }

    // tenhang
    public String getTenhang() { return tenhang.get(); }
    public void setTenhang(String tenhang) { this.tenhang = new SimpleStringProperty(tenhang); }

    // soluong
    public int getSoluong() { return soluong.get(); }
    public void setSoluong(int soluong) { this.soluong = new SimpleIntegerProperty(soluong); }

    // dongia
    public String getDongia() { return dongia.get(); }
    public void setDongia(int dongia) { this.dongia = new SimpleStringProperty(String.format("%,.2f", (float)dongia)); }

    // sale
    public Float getSale() { return sale.get(); }
    public void setSale(Float sale) { this.sale = new SimpleFloatProperty(sale); }
}
