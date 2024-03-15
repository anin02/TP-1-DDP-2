package assignments.assignment2;

import java.util.ArrayList;

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
        this.menuPesanan = items;
    }
    
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

    public Menu getMenuPesanan(){
        return menuPesanan;
    }

    public boolean getStatusPesanan(){
        return statusPesanan;
    }

    public void setStatusPesanan(boolean statusPesanan){
        this.statusPesanan = statusPesanan;
    }
    // TODO: tambahkan methods yang diperlukan untuk class ini
}
