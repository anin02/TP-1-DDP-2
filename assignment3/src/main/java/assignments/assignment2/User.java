package assignments.assignment2;

import java.util.ArrayList;

import assignments.payment.DepeFoodPaymentSystem;

public class User {

    private String nama;
    private String nomorTelepon;
    private String email;
    private ArrayList<Order> orderHistory;
    public String role;
    private String lokasi;
    private long saldo; // Atribut saldo
    private DepeFoodPaymentSystem paymentSystem; // Atribut untuk sistem pembayaran

    public User(String nama, String nomorTelepon, String email, String lokasi, String role,
            DepeFoodPaymentSystem paymentSystem, long saldo) {
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
        this.paymentSystem = paymentSystem; // Inisialisasi sistem pembayaran
        this.saldo = saldo; // Inisialisasi saldo
        orderHistory = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public String getNama() {
        return nama;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public long getSaldo() { // Method untuk mengambil saldo
        return saldo;
    }

    public void setSaldo(long saldo) { // Method untuk mengatur saldo
        this.saldo = saldo;
    }

    public void addOrderHistory(Order order) {
        orderHistory.add(order);
    }

    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

    public String getRole() {
        return role;
    }

    public boolean isOrderBelongsToUser(String orderId) {
        for (Order order : orderHistory) {
            if (order.getOrderId().equals(orderId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("User dengan nama %s dan nomor telepon %s", nama, nomorTelepon);
    }

}
