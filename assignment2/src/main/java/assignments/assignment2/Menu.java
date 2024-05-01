package assignments.assignment2;

public class Menu {
    private String namaMakanan;
    private double hargaMakanan;

    // data fields
    public Menu(String namaMakanan, double harga){
        this.namaMakanan = namaMakanan;
        this.hargaMakanan = harga;
    }

    // Setter dan Getter
    public String getNamaMakanan(){
        return namaMakanan;
    }

    public double getHargaMakanan(){
        return hargaMakanan;
    }
}