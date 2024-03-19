package assignments.assignment2;

import java.util.ArrayList;

public class Restaurant {
    private String namaResto;
    private ArrayList<Menu> daftarMenu;

    public Restaurant(String nama){
        this.namaResto = nama;
    }
    
    // Setter dan Getter
    public String getNamaResto(){
        return namaResto;
    }

    public ArrayList<Menu> getDaftarMenu(){
        return daftarMenu;
    }
 
    public void setDaftarMenu(ArrayList<Menu> daftarMenu){
        this.daftarMenu = daftarMenu;
    }
}
