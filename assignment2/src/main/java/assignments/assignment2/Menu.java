package assignments.assignment2;

public class Menu {
    private String namaMakanan;
    private double hargaMakanan;

    public Menu(String namaMakanan, double harga){
        this.namaMakanan = namaMakanan;
        this.hargaMakanan = harga;
    }

    public String getNamaMakanan(){
        return namaMakanan;
    }

    public double getHargaMakanan(){
        return hargaMakanan;
    }
}
