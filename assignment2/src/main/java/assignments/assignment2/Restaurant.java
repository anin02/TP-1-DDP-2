package assignments.assignment2;

import java.util.ArrayList;

public class Restaurant {
    private String namaResto;
    private ArrayList<Menu> daftarMenu;

    public Restaurant(String nama){
        this.namaResto = nama;
    }
    
    public String getNamaResto(){
        return namaResto;
    }

    public Menu getDaftarMenu(){
        return daftarMenu;
    }
 
    public void setDaftarMenu(ArrayList<Menu> daftarMenu){
        this.daftarMenu = daftarMenu;
    }
    // TODO: tambahkan methods yang diperlukan untuk class ini
}
