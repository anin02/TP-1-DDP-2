package assignments.assignment2;
<<<<<<< HEAD

import java.util.ArrayList;
import java.util.Arrays;
=======
>>>>>>> a3374bc0a7a254532ce81e5027b827e968778571

public class Order {
    private String orderId;
    private String tanggalPemesanan;
    private int biayaOngkir;
    private Restaurant restaurant;
    private ArrayList<Menu> menuPesanan;
    private boolean statusPesanan;

    public Order(String orderId, String tanggal, int ongkir, Restaurant resto, Menu[] items){
        this.orderId = orderId;
        this.tanggalPemesanan = tanggal;
        this.biayaOngkir = ongkir;
        this.restaurant = resto;
        this.menuPesanan = new ArrayList<Menu>(Arrays.asList(items)); // Mengubah input Array menjadi ArrayList
    }
    
    // Setter dan Getter
    public String getOrderId(){
        return orderId;
    }

    public String getTanggalPemesanan(){
        return tanggalPemesanan;
    }

    public int getBiayaOngkir(){
        return biayaOngkir;
    }

    public Restaurant getRestaurant(){
        return restaurant;
    }

    public ArrayList<Menu> getMenuPesanan(){
        return menuPesanan;
    }

    public boolean getStatusPesanan(){
        return statusPesanan;
    }

    public void setStatusPesanan(boolean statusPesanan){
        this.statusPesanan = statusPesanan;
    }
}
