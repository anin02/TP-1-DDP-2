package assignments.assignment2;
<<<<<<< HEAD

import java.util.ArrayList;
=======
>>>>>>> a3374bc0a7a254532ce81e5027b827e968778571

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
