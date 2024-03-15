package assignments.assignment2;

public class User {
    private String namaUser;
    private String noTeleponUser;
    private String emailUser;
    private String lokasiUser;
    private String roleUser;
    private ArrayList<Order> orderHistory;

    public User(String nama, String nomorTelepon, String email, String lokasi, String role){
        this.namaUser = nama;
        this.noTeleponUser = nomorTelepon;
        this.emailUser = email;
        this.lokasiUser = lokasi;
        this.roleUser = role;
        this.orderHistory = null;
    }

    public String getNamaUser(){
        return namaUser;
    }

    public String getNoTelepon(){
        return noTeleponUser;
    }

    public String getEmailUser(){
        return emailUser;
    }

    public String getLokasiUser(){
        return lokasiUser;
    }

    public String getRoleUser(){
        return roleUser;
    }

    public Order getOrderHistory(){
        return orderHistory;
    }

    public void setOrderHistory(ArrayList<Order> orderHistory){
        this.orderHistory = orderHistory;
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
}
