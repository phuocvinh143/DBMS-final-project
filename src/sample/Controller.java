package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

import db.db_connection;

import objects.Order;
import objects.Cart;
import objects.Customer;
import objects.Product;

public class Controller {

    Connection conn = db_connection.start_database();

    String check_don_hang;

    @FXML
    public ComboBox<String> tenkhachhang, thanhtoan_mahoadon, them_ten_mat_hang, tenmathang;

    @FXML
    TableView<Order> hoadon_dshd_table;
    private final ObservableList<Order> dataList = FXCollections.observableArrayList();
    private final ObservableList<Customer> dataList_dskh = FXCollections.observableArrayList();
    private final ObservableList<Product> dataList_dshh = FXCollections.observableArrayList();
    @FXML
    TableColumn<Order, Integer> stt_dshd_column, id_dshd_column;
    @FXML
    TableColumn<Order, String> khachhang_dshd_column, ngaylap_dshd_column, ghichu_column;
    @FXML
    TableColumn<Order, Float> tongtien_column;

    @FXML
    TableView<Cart> giohang_dshd_table;
    @FXML
    TableColumn<Cart, Integer> stt_dshd_column_1, soluong_dshd_column, thanhtien_dshd_column;
    @FXML
    TableColumn<Cart, String> tensanpham_dshd_column;
    @FXML
    TableColumn<Cart, Float> dongia_dshd_column;

    @FXML
    TableView<Customer> khachhang_dskh_table;
    @FXML
    TableColumn<Customer, Integer> stt_dskh_column, id_dskh_column;
    @FXML
    TableColumn<Customer, String> hoten_dskh_column, sdt_dskh_column, email_dskh_column, diachi_dskh_column;

    @FXML
    TableView<Product> hanghoa_dshh_table;
    @FXML
    TableColumn<Product, Integer> stt_dshh_column, id_dshh_column, soluong_dshh_column, dongia_dshh_column;
    @FXML
    TableColumn<Product, String> tenhang_dshh_column;
    @FXML
    TableColumn<Product, Float> sale_dshh_column;

    @FXML
    Label giohangid, tongtien_text;

    @FXML
    TextField timkiem_dshd, timkiem_dskh, timkiem_dshh, mahang_text, hoten_text, sdt_text, email_text, diachi_text, tenhang_text, soluong_text, dongia_text, sale_text, new_price;

    @FXML
    private Button hoadon_btn, khachhang_btn, hanghoa_btn, start_btn;

    public void handleBtnClick(javafx.event.ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == hoadon_btn){
            stage = (Stage) hoadon_btn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("hoadon_content.fxml"));
        }
        else if (event.getSource() == khachhang_btn) {
            stage = (Stage) khachhang_btn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("khachhang_content.fxml"));
        }
        else if (event.getSource() == hanghoa_btn) {
            stage = (Stage) hanghoa_btn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("hanghoa_content.fxml"));
        }
        else {
            stage = (Stage) start_btn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("hoadon_content.fxml"));
        }


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void timkiem_dshd_typing() {
        FilteredList<Order> filteredData = new FilteredList<>(dataList, b -> true);

        filteredData.setPredicate(order -> {
            String lowerCaseFilter = timkiem_dshd.getText().toLowerCase();
            return order.getHoten().toLowerCase().contains(lowerCaseFilter);
        });

        SortedList<Order> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(hoadon_dshd_table.comparatorProperty());
        hoadon_dshd_table.setItems(sortedData);
    }

    @FXML
    public void themhoadon_btn_clicked() {
        CallableStatement cStmt = null;
        String query = "{call p_createOrder('" + tenkhachhang.getValue() + "')}";
        try {
            cStmt = conn.prepareCall(query);
            cStmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        load_hoadon();
    }

    @FXML
    public void tinhtien_btn_clicked() {
        CallableStatement cStmt = null;

        if (check_don_hang.equals("Đã thanh toán")) {
            _alert("Đơn hàng đã thanh toán rồi!");
            return;
        }

        String query = "{call p_checkOut(" + thanhtoan_mahoadon.getValue() + ")}";
        try {
            cStmt = conn.prepareCall(query);
            cStmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        // In hoa don
        GregorianCalendar gg = new GregorianCalendar();
        SimpleDateFormat dd = new SimpleDateFormat("dd/MM/YYYY");
        SimpleDateFormat ddd = new SimpleDateFormat("HH:mm");

        String Header =
                          "*************************************  CTY TNHH 1 thành viên  *************************************;" +
                          "*************************************       TÍNH TIỀN         *************************************;"
                        + "Ngày: "+dd.format(gg.getTime()) + "Thời gian: " + ddd.format(gg.getTime()) + "\n\n\n;"
                        + "                                             HÓA ĐƠN                                               \n;"
                        + "---------------------------------------------------------------------------------------------------\n;"
                        + "Tên x Số lượng                                                                               Đơn giá\n;"
                        + "----------------------------------------------------------------------------------------------------\n;";

        String a = new String();

        cStmt = null;
        ResultSet rs = null, rs1 = null;
        query = "{call p_listProducts(" + thanhtoan_mahoadon.getValue() + ")}";
        Integer cnt = new Integer(1);

        try {
            cStmt = conn.prepareCall(query);
            rs = cStmt.executeQuery();
            while (rs.next()) {
                query = "{call p_decrease_product_instock('" + rs.getString("productName") + "'," + rs.getInt("quantity") + ")}";
                cStmt = conn.prepareCall(query);
                cStmt.executeQuery();

                String tmp = rs.getString("productName") + " x " + rs.getInt("quantity");
                String tmp1 = String.format("%,.2f", rs.getFloat("price"));
                String space = "";
                for (int i = 0; i < 10 - tmp1.length() + (90 - tmp.length()); ++i) space = space + " ";
                a = a + tmp + space + tmp1 + "\n;";
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        String amt  =
                          "\n;----------------------------------------------------------------------------------------------------\n;"
                        + "                                                             Tổng cộng:              " + tongtien_text.getText() + "\n;"
                        + "----------------------------------------------------------------------------------------------------\n;"
                        + "----------------------------------------------------------------------------------------------------\n;"
                        + "----------------------------------------------------------------------------------------------------\n;"
                        + "                                        Software Developed by                                       \n;"
                        + "                                            Vinh  Mai                                               \n;"
                        + "----------------------------------------------------------------------------------------------------\n;"
                        + "                                            Thank You                                               \n;"
                        + "----------------------------------------------------------------------------------------------------\n;";

        PrintReciept p = new PrintReciept();
        String billz = Header + a + amt;
        PrintReciept.printcard(billz);

        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("D:/Study_At_Uni/NLHQTCSDL/Easiest_java/reciepts/1.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }

        load_hoadon();
    }

    @FXML
    public void xoahoadon_btn_clicked() {
        CallableStatement cStmt = null;
        String query = "{call p_deleteOrder(" + thanhtoan_mahoadon.getValue() + ")}";
        try {
            cStmt = conn.prepareCall(query);
            cStmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        load_hoadon();
    }

    @FXML
    public void themvaogiohang_btn_clicked() {
        CallableStatement cStmt = null;
        ResultSet rs = null;
        String query = "{call p_add2Cart(" + thanhtoan_mahoadon.getValue() + "," + mahang_text.getText() + ")}";

        if (check_don_hang.equals("Đã thanh toán")) {
            alertThemHang();
            return;
        }

        try {
            cStmt = conn.prepareCall(query);
            cStmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        load_cart();
    }

    @FXML
    public void xoakhoigiohang_btn_clicked() {
        CallableStatement cStmt = null;
        ResultSet rs = null;
        String query = "{call p_popFromCart(" + thanhtoan_mahoadon.getValue() + "," + mahang_text.getText() + ")}";
        if (check_don_hang.equals("Đã thanh toán")) {
            alertThemHang();
            return;
        }

        try {
            cStmt = conn.prepareCall(query);
            cStmt.executeQuery();
        } catch (SQLException e) {
            _alert(e.getMessage());
        }
        load_cart();
    }

    @FXML
    public void timkiem_dskh_typing() {
        FilteredList<Customer> filteredData = new FilteredList<>(dataList_dskh, b -> true);

        filteredData.setPredicate(customer -> {
            String lowerCaseFilter = timkiem_dskh.getText().toLowerCase();
            return customer.getHoten().toLowerCase().contains(lowerCaseFilter);
        });

        SortedList<Customer> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(khachhang_dskh_table.comparatorProperty());
        khachhang_dskh_table.setItems(sortedData);
    }

    @FXML
    public void themkhachhang_btn_clicked() {
        CallableStatement cStmt = null;
        ResultSet rs = null;
        String query = "{call p_createCustomer('" + hoten_text.getText() + "','" + sdt_text.getText() + "','" + email_text.getText() + "','" + diachi_text.getText() + "'" + ")}";
        try {
            cStmt = conn.prepareCall(query);
            cStmt.executeQuery();
        } catch (SQLException e) {
            _alert(e.getMessage());
        }
        load_khachhang();
    }

    @FXML
    public void timkiem_dshh_typing() {
        FilteredList<Product> filteredData = new FilteredList<>(dataList_dshh, b -> true);

        filteredData.setPredicate(product -> {
            String lowerCaseFilter = timkiem_dshh.getText().toLowerCase();
            return product.getTenhang().toLowerCase().contains(lowerCaseFilter);
        });

        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(hanghoa_dshh_table.comparatorProperty());
        hanghoa_dshh_table.setItems(sortedData);
    }

    @FXML
    public void themhanghoa_btn_clicked() {
        CallableStatement cStmt = null;
        ResultSet rs = null;
        String query = "{call p_addProduct('" + tenhang_text.getText() + "'," + soluong_text.getText() + "," + dongia_text.getText().replaceAll(",", "") + "," + sale_text.getText() + ")}";
        try {
            cStmt = conn.prepareCall(query);
            cStmt.executeQuery();
        } catch (SQLException e) {
            _alert(e.getMessage());
        }
        load_hanghoa();
    }

    @FXML
    public void capnhat_btn_clicked() {
        CallableStatement cStmt = null;
        ResultSet rs = null;
        Product _product = hanghoa_dshh_table.getSelectionModel().getSelectedItem();
        String query = "{call p_modifyPrice(" + _product.getID() + "," + new_price.getText() + ")}";
        try {
            cStmt = conn.prepareCall(query);
            cStmt.executeQuery();
        } catch (SQLException e) {
            _alert(e.getMessage());
        }
        load_hanghoa();
    }

    @FXML
    public void alertThemHang() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Thông báo giỏ hàng");

        // Header Text: null
        alert.setHeaderText(null);
        if (!thanhtoan_mahoadon.getValue().isEmpty())
            alert.setContentText("Không thể thao tác vào giỏ hàng ID: " + thanhtoan_mahoadon.getValue() + " do đơn hàng đã thanh toán!");
        else {
            alert.setContentText("Bạn chưa chọn giỏ hàng!");
        }

        alert.showAndWait();
    }

    public void _alert(String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Thông báo");

        // Header Text: null
        alert.setHeaderText(null);

        alert.setContentText(content);

        alert.showAndWait();
    }

    @FXML
    public void load_cart() {

        giohang_dshd_table.getItems().clear();
        stt_dshd_column_1.setCellValueFactory(new PropertyValueFactory<>("stt"));
        tensanpham_dshd_column.setCellValueFactory(new PropertyValueFactory<>("tensanpham"));
        soluong_dshd_column.setCellValueFactory(new PropertyValueFactory<>("soluong"));
        dongia_dshd_column.setCellValueFactory(new PropertyValueFactory<>("dongia"));
        thanhtien_dshd_column.setCellValueFactory(new PropertyValueFactory<>("thanhtien"));

        CallableStatement cStmt = null;
        ResultSet rs = null;
        String query = "{call p_listProducts(" + thanhtoan_mahoadon.getValue() + ")}";
        Integer cnt = new Integer(1);

        try {
            cStmt = conn.prepareCall(query);
            rs = cStmt.executeQuery();
            while (rs.next()) {
                Cart _cart = new Cart(
                        cnt,
                        rs.getString("productName"),
                        rs.getInt("quantity"),
                        rs.getFloat("price"));
                giohang_dshd_table.getItems().add(_cart);
                cnt = cnt + 1;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        query = "{call p_listCheckOut(" + thanhtoan_mahoadon.getValue() + ")}";
        try {
            cStmt = conn.prepareCall(query);
            rs = cStmt.executeQuery();
            while (rs.next())
                check_don_hang = rs.getString("checkout");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        Statement stmt = null;
        query = "select f_sumCart(" + thanhtoan_mahoadon.getValue() + ")" + "as ans";
        try {
            stmt = conn.createStatement();
            if (stmt.execute(query)) {
                rs = stmt.getResultSet();
            }
            while (rs.next()) {
                tongtien_text.setText(String.format("%,.2f", rs.getFloat("ans")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        giohangid.setText(thanhtoan_mahoadon.getValue());
    }

    @FXML
    public void load_cart_by_clicked_row() {
        giohang_dshd_table.getItems().clear();

        Order _order = hoadon_dshd_table.getSelectionModel().getSelectedItem();
        thanhtoan_mahoadon.setValue(_order.getID() + "");
    }

    @FXML
    public void load_productName_by_clicked_row() {
        Product _product = hanghoa_dshh_table.getSelectionModel().getSelectedItem();
        tenmathang.setValue(_product.getTenhang());
    }

    @FXML
    public void load_mahang() {

        CallableStatement cStmt = null;
        ResultSet rs = null;
        String query = "{call p_getIDbyProductName('" + them_ten_mat_hang.getValue() + "')}";


        int tmp = 0;
        try {
            cStmt = conn.prepareCall(query);
            rs = cStmt.executeQuery();
            while (rs.next()) {
                tmp = rs.getInt("ID");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        mahang_text.setText(tmp + "");
    }

    @FXML
    public void load_hoadon() {

        hoadon_dshd_table.getItems().clear();
        thanhtoan_mahoadon.getItems().clear();
        tenkhachhang.getItems().clear();
        them_ten_mat_hang.getItems().clear();

        // setup render hoadon_table
        stt_dshd_column.setCellValueFactory(new PropertyValueFactory<>("stt"));
        id_dshd_column.setCellValueFactory(new PropertyValueFactory<>("ID"));
        khachhang_dshd_column.setCellValueFactory(new PropertyValueFactory<>("hoten"));
        ngaylap_dshd_column.setCellValueFactory(new PropertyValueFactory<>("date"));
        tongtien_column.setCellValueFactory(new PropertyValueFactory<>("tongtien"));
        ghichu_column.setCellValueFactory(new PropertyValueFactory<>("ghichu"));

        CallableStatement cStmt = null;
        ResultSet rs = null;
        String query = "{call p_listsomething('Orders')}";
        Integer cnt = new Integer(1);

        try {
            cStmt = conn.prepareCall(query);
            rs = cStmt.executeQuery();
            while (rs.next()) {
                thanhtoan_mahoadon.getItems().add(rs.getString("o.ID"));
                Order _order = new Order(
                        cnt,
                        rs.getInt("o.ID"),
                        rs.getDate("o.dateCreate"),
                        rs.getString("customerName"),
                        rs.getFloat("sumCart"),
                        rs.getString("checkout"));
                dataList.add(_order);
                hoadon_dshd_table.getItems().add(_order);
                cnt = cnt + 1;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        query = "{call p_listsomething('Customers')}";
        try {
            cStmt = conn.prepareCall(query);
            rs = cStmt.executeQuery();
            while (rs.next()) {
                tenkhachhang.getItems().add(rs.getString("fullName"));
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        query = "{call p_listsomething('Products')}";
        try {
            cStmt = conn.prepareCall(query);
            rs = cStmt.executeQuery();
            while (rs.next()) {
                them_ten_mat_hang.getItems().add(rs.getString("productName"));
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    @FXML
    public void load_khachhang() {

        khachhang_dskh_table.getItems().clear();

        // setup render khachhang_table
        stt_dskh_column.setCellValueFactory(new PropertyValueFactory<>("stt"));
        id_dskh_column.setCellValueFactory(new PropertyValueFactory<>("ID"));
        hoten_dskh_column.setCellValueFactory(new PropertyValueFactory<>("hoten"));
        sdt_dskh_column.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        email_dskh_column.setCellValueFactory(new PropertyValueFactory<>("email"));
        diachi_dskh_column.setCellValueFactory(new PropertyValueFactory<>("diachi"));

        CallableStatement cStmt = null;
        ResultSet rs = null;
        String query = "{call p_listsomething('Customers')}";
        Integer cnt = new Integer(1);

        try {
            cStmt = conn.prepareCall(query);
            rs = cStmt.executeQuery();
            while (rs.next()) {
                Customer _customer = new Customer(
                        cnt,
                        rs.getInt("ID"),
                        rs.getString("fullName"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getString("address"));
                dataList_dskh.add(_customer);
                khachhang_dskh_table.getItems().add(_customer);
                cnt = cnt + 1;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    @FXML
    public void load_hanghoa() {
        hanghoa_dshh_table.getItems().clear();
        // setup render hanghoa_table
        stt_dshh_column.setCellValueFactory(new PropertyValueFactory<>("stt"));
        id_dshh_column.setCellValueFactory(new PropertyValueFactory<>("ID"));
        tenhang_dshh_column.setCellValueFactory(new PropertyValueFactory<>("tenhang"));
        soluong_dshh_column.setCellValueFactory(new PropertyValueFactory<>("soluong"));
        dongia_dshh_column.setCellValueFactory(new PropertyValueFactory<>("dongia"));
        sale_dshh_column.setCellValueFactory(new PropertyValueFactory<>("sale"));

        CallableStatement cStmt = null;
        ResultSet rs = null;
        String query = "{call p_listsomething('Products')}";
        Integer cnt = new Integer(1);

        final char seperatorChar = ',';
        final Pattern p = Pattern.compile("[0-9" + seperatorChar + "]*");

        dongia_text.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.isContentChange()) {
                return c; // no need for modification, if only the selection changes
            }
            String newText = c.getControlNewText();
            if (newText.isEmpty()) {
                return c;
            }
            if (!p.matcher(newText).matches()) {
                return null; // invalid change
            }

            // invert everything before the range
            int suffixCount = c.getControlText().length() - c.getRangeEnd();
            int digits = suffixCount - suffixCount / 4;
            StringBuilder sb = new StringBuilder();

            // insert seperator just before caret, if necessary
            if (digits % 3 == 0 && digits > 0 && suffixCount % 4 != 0) {
                sb.append(seperatorChar);
            }

            // add the rest of the digits in reversed order
            for (int i = c.getRangeStart() + c.getText().length() - 1; i >= 0; i--) {
                char letter = newText.charAt(i);
                if (Character.isDigit(letter)) {
                    sb.append(letter);
                    digits++;
                    if (digits % 3 == 0) {
                        sb.append(seperatorChar);
                    }
                }
            }

            // remove seperator char, if added as last char
            if (digits % 3 == 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.reverse();
            int length = sb.length();

            // replace with modified text
            c.setRange(0, c.getRangeEnd());
            c.setText(sb.toString());
            c.setCaretPosition(length);
            c.setAnchor(length);

            return c;
        }));
        new_price.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.isContentChange()) {
                return c; // no need for modification, if only the selection changes
            }
            String newText = c.getControlNewText();
            if (newText.isEmpty()) {
                return c;
            }
            if (!p.matcher(newText).matches()) {
                return null; // invalid change
            }

            // invert everything before the range
            int suffixCount = c.getControlText().length() - c.getRangeEnd();
            int digits = suffixCount - suffixCount / 4;
            StringBuilder sb = new StringBuilder();

            // insert seperator just before caret, if necessary
            if (digits % 3 == 0 && digits > 0 && suffixCount % 4 != 0) {
                sb.append(seperatorChar);
            }

            // add the rest of the digits in reversed order
            for (int i = c.getRangeStart() + c.getText().length() - 1; i >= 0; i--) {
                char letter = newText.charAt(i);
                if (Character.isDigit(letter)) {
                    sb.append(letter);
                    digits++;
                    if (digits % 3 == 0) {
                        sb.append(seperatorChar);
                    }
                }
            }

            // remove seperator char, if added as last char
            if (digits % 3 == 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.reverse();
            int length = sb.length();

            // replace with modified text
            c.setRange(0, c.getRangeEnd());
            c.setText(sb.toString());
            c.setCaretPosition(length);
            c.setAnchor(length);

            return c;
        }));

        try {
            cStmt = conn.prepareCall(query);
            rs = cStmt.executeQuery();
            while (rs.next()) {
                Product _product = new Product(
                        cnt,
                        rs.getInt("ID"),
                        rs.getString("productName"),
                        rs.getInt("inStock"),
                        rs.getInt("unitPrice"),
                        rs.getFloat("sale"));
                tenmathang.getItems().add(rs.getString("productName"));
                dataList_dshh.add(_product);
                hanghoa_dshh_table.getItems().add(_product);
                cnt = cnt + 1;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
}