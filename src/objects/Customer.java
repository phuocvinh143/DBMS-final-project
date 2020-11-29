package objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
        private SimpleIntegerProperty ID, stt;
        private SimpleStringProperty hoten, email, diachi, sdt;

        public Customer(Integer stt, Integer ID, String hoten, String sdt, String email, String diachi) {
            this.stt = new SimpleIntegerProperty(stt);
            this.ID = new SimpleIntegerProperty(ID);
            this.hoten = new SimpleStringProperty(hoten);
            this.sdt = new SimpleStringProperty(sdt);
            this.email = new SimpleStringProperty(email);
            this.diachi = new SimpleStringProperty(diachi);
        }

        // stt
        public int getStt() { return stt.get(); }
        public void setStt(int p_stt) { this.stt = new SimpleIntegerProperty(p_stt); }

        // ID
        public int getID() { return ID.get(); }
        public void setID(int p_ID) { this.ID = new SimpleIntegerProperty(p_ID); }

        // hoten
        public String getHoten() { return hoten.get(); }
        public void setHoten(String p_hoten) { this.hoten = new SimpleStringProperty(p_hoten); }

        // sdt
        public String getSdt() { return sdt.get(); }
        public void setGhichu(String sdt) { this.sdt = new SimpleStringProperty(sdt); }

        // email
        public String getEmail() { return email.get(); }
        public void setEmail(String email) { this.email = new SimpleStringProperty(email); }

        // diachi
        public String getDiachi() { return diachi.get(); }
        public void setDiachi(String diachi) { this.diachi = new SimpleStringProperty(diachi); }
}
