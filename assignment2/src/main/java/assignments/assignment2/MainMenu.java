package assignments.assignment2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import assignments.assignment1.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList = new ArrayList<>(); // List Restoran
    private static ArrayList<User> userList; // List User
    private static ArrayList<Menu> menuList = new ArrayList<>(); // List Menu
    private static ArrayList<Order> orderList = new ArrayList<>(); // List Order

    public static void main(String[] args) {
        initUser(); // Inisialisasi userList

        boolean programRunning = true;
        printHeader();
        while(programRunning){
            startMenu();
            int command = input.nextInt();
            input.nextLine();

            if(command == 1){
                System.out.println("\nSilakan Login:");
                System.out.print("Nama: ");
                String nama = input.nextLine();
                System.out.print("Nomor Telepon: ");
                String noTelp = input.nextLine();


                User userLoggedIn; 
                boolean isLoggedIn = true;
                userLoggedIn = getUser(nama, noTelp); // Mencari User

                // Mengecek apakah User terdaftar
                if(userLoggedIn == null){
                    System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
                    continue;
                } else {
                    System.out.println("Selamat Datang " + userLoggedIn.getNamaUser() + "!");
                }

                // Log in sebagai customer
                if(userLoggedIn.getRoleUser() == "Customer"){
                    while (isLoggedIn){
                        menuCustomer();
                        int commandCust = input.nextInt();
                        input.nextLine();

                        switch(commandCust){
                            case 1 -> handleBuatPesanan(noTelp);
                            case 2 -> handleCetakBill(noTelp);
                            case 3 -> handleLihatMenu();
                            case 4 -> handleUpdateStatusPesanan();
                            case 5 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                // Log in sebagai admin
                } else{
                    while (isLoggedIn){
                        menuAdmin();
                        int commandAdmin = input.nextInt();
                        input.nextLine();

                        switch(commandAdmin){
                            case 1 -> handleTambahRestoran();
                            case 2 -> handleHapusRestoran();
                            case 3 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }
            }else if(command == 2){
                programRunning = false;
            }else{
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("Terima kasih telah menggunakan DepeFood ^___^");
    }

    // Method mencari user
    public static User getUser(String nama, String nomorTelepon){
        for(User user : userList){
            if(user.getNamaUser().equals(nama) && user.getNoTelepon().equals(nomorTelepon)){
                return user;
            }
        }
        return null;
    }

    // Method membuat pesanan
    public static void handleBuatPesanan(String nomorTelp){
        System.out.println("--------------Buat Pesanan--------------");
        while(true){
            System.out.print("Nama Restoran: ");
            String namaResto = input.nextLine();
            
            Restaurant restoranPilihan = null; // Data dari restoran yang dipilih
            boolean restoTerdaftar = false;
            for(Restaurant listResto:restoList){
                if(namaResto.equals(listResto.getNamaResto())){
                    restoTerdaftar = true; // Validasi input apakah restoran sudah terdaftar
                    restoranPilihan = listResto;
                    break;
                }
            }
        
            if(restoTerdaftar == false){ // Peringatan bahwa restoran tidak terdaftar
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }

            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
            String tglPemesanan = input.nextLine();

            // Validasi input tanggal pemesanan (harus sesuai format)
            if(tglPemesanan.charAt(2) != '/' || tglPemesanan.charAt(5) != '/' || tglPemesanan.length() != 10){
                System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY)!\n");
                continue;
            }

            System.out.print("Jumlah Pesanan: ");
            Integer jumlahPesanan = Integer.parseInt(input.nextLine());

            System.out.println("Order: ");
            ArrayList<String> daftarMenu = new ArrayList<>(); // Array berisi daftar menu yang diorder
            while(jumlahPesanan > 0){
                String menuMakanan = input.nextLine(); // Input menu yang di order berdasarkan jumlah pesanan
                daftarMenu.add(menuMakanan);
                jumlahPesanan--;
            }

            // Mengecek apakah semua menu yang diorder tersedia dalam restoran
            int menuFound = 0;
            for (String menu : daftarMenu) { // Iterasi setiap menu yang diorder
                for (Menu restoMenu : restoranPilihan.getDaftarMenu()) { // Mencari menu yang diorder dalam restoran 
                    if (restoMenu.getNamaMakanan().equalsIgnoreCase(menu)) {
                        menuFound += 1;
                        break;
                    }
                }
            }

            System.out.println(menuFound);
            // Apabila menu ada pada restoran, maka counter akan bertambah 1
            int jumlahOrder = daftarMenu.size();
            if (jumlahOrder != menuFound) { // counter harus sesuai dengan banyak menu yang dipesan
                System.out.println("Mohon memesan menu yang tersedia di restoran!\n");
                continue;
            }

            // Generate order ID
            String orderID = OrderGenerator.generateOrderID(namaResto, tglPemesanan, nomorTelp);

            // Mencari lokasi customer dari info nomor telepon
            String lokasi = "";
            for(User cust : userList){
                if(cust.getNoTelepon().equals(nomorTelp)){
                    lokasi = cust.getLokasiUser();
                    break;
                }
            }

            // Menetapkan biaya ongkir customer
            int biayaOngkir;
            if(lokasi.equals("P")){
                biayaOngkir = 10000;
            } else if(lokasi.equals("U")){
                biayaOngkir = 20000;
            } else if(lokasi.equals("T")){
                biayaOngkir = 35000;
            } else if(lokasi.equals("S")){
                biayaOngkir = 40000;
            } else {
                biayaOngkir = 60000;
            }

            // Menetapkan restoran
            Restaurant resto = null;
            for(Restaurant listResto:restoList){
                if(namaResto.equals(listResto.getNamaResto())){
                    resto = listResto;
                    break;
                }
            }

            // Menu pesanan
            Menu [] items = new Menu[100];
            int i = 0;
            for(String menuOrder : daftarMenu){ // Iterasi menu yang dipesan
                for(Menu menu : menuList){ // Mencari dalam list menu
                    if(menu.getNamaMakanan().equalsIgnoreCase(menuOrder)){
                        items[i] = menu; // Menu akan dimasukkan ke dalam menu pesanan
                        i++;
                    }
                }
            }

            // Membuat Order
            Order pesanan = new Order(orderID, tglPemesanan, biayaOngkir, resto, items);

            // Menambahkan order pada list order
            orderList.add(pesanan);

            // Menambahkan order pada order history user
            for(User cust : userList){
                if(cust.getNoTelepon().equals(nomorTelp)){
                    // Ambil orderHistory saat ini dari objek User
                    ArrayList<Order> orderHistory = cust.getOrderHistory();

                    // Tambahkan pesanan baru ke dalam orderHistory
                    orderHistory.add(pesanan);

                    // Set orderHistory yang telah ditambahkan pesanan baru kembali ke objek User
                    cust.setOrderHistory(orderHistory);
                }
            }
            System.out.println("Pesanan dengan ID " + orderID + " diterima!"); 
            break;
        }
    }


    public static void handleCetakBill(String nomorTelp){
        System.out.println("--------------Cetak Bill--------------");
        while(true){
            System.out.print("Masukkan Order ID: ");
            String orderID = input.nextLine();

            boolean statusPesanan = false;
            boolean orderIdTerdaftar = false;
            for(Order order : orderList){
                if(order.getOrderId().equals(orderID)){
                    orderIdTerdaftar = true; // Validasi input apakah order Id terdaftar
                    statusPesanan = order.getStatusPesanan(); // Mengambil status pesanan
                    break;
                }
            }

            if(orderIdTerdaftar == false){
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }

            ArrayList<Order> orderHistory = new ArrayList<>();
            String lokasi = null;
            for(User cust : userList){
                if(cust.getNoTelepon().equals(nomorTelp)){  
                    lokasi = cust.getLokasiUser(); // Mencari lokasi kirim customer
                    orderHistory = cust.getOrderHistory(); // Mengambil keterangan order history customer
                }
            } 

            String tglPemesanan = "";
            String namaResto = "";
            int biayaOngkir = 0;
            ArrayList<Menu> items = new ArrayList<>();

            // Mencari tanggal pemesanan, nama restoran, biaya ongkir, item menu pesanan
            for(Order order : orderHistory){
                if(orderID.equals(order.getOrderId())){
                    tglPemesanan = order.getTanggalPemesanan();
                    namaResto = order.getRestaurant().getNamaResto();
                    biayaOngkir = order.getBiayaOngkir();
                    items = order.getMenuPesanan();
                }
            }

            // Menetapkan status order
            String statusOrder = "";
            if(statusPesanan == true){
                statusOrder = "Selesai";
            } else {
                statusOrder = "Not Finished";
            }

            // Mencetak Bill
            System.out.println("\nBill:");
            System.out.println("Order ID: " + orderID);
            System.out.println("Tanggal Pemesanan: " + tglPemesanan);
            System.out.println("Restaurant: " + namaResto);
            System.out.println("Lokasi Pengiriman: " + lokasi);
            System.out.println("Status Pengiriman: " + statusOrder);
            System.out.println("Pesanan:");

            double totalHargaMenu = 0;
            for(Menu menu : items){
                if (menu != null) {
                    totalHargaMenu += menu.getHargaMakanan();
                    System.out.println("- " + menu.getNamaMakanan() + " " + (int) menu.getHargaMakanan());
                }
            }
            System.out.println("Biaya Ongkos Kirim: Rp " + biayaOngkir);
            System.out.println("Total Biaya: Rp " + (int)(totalHargaMenu + biayaOngkir));
            break;
        }
    }

    public static void handleLihatMenu(){
        System.out.println("--------------Lihat Menu--------------");
        while(true){
            System.out.print("Nama Restoran: ");
            String namaResto = input.nextLine();

            ArrayList<Menu> daftarMenu = new ArrayList<>();
            boolean restoTerdaftar = false;
            for(Restaurant listResto:restoList){
                if(namaResto.equalsIgnoreCase(listResto.getNamaResto())){
                    restoTerdaftar = true; // Validasi input apakah restoran terdaftar
                    daftarMenu = listResto.getDaftarMenu(); // Mengambil daftar menu pada restoran
                    break;
                }
            }
            
            if(restoTerdaftar == false){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }

            System.out.println("Menu: ");

            // Mengambil harga setiap makanan pada menu untuk diurutkan dari yang termurah
            double [] hargaMenu = new double [100];
            int i = 0;
            for(Menu menu : daftarMenu){
                hargaMenu[i] = menu.getHargaMakanan();
                i++;
            }

            // Mengurutkan dari yang termurah
            Arrays.sort(hargaMenu);

            // Mencetak Menu
            int nomor = 1;
            for(double harga : hargaMenu){
                for(Menu menu : daftarMenu){
                    if(harga == menu.getHargaMakanan()){
                        System.out.println(nomor + ". " + menu.getNamaMakanan() + " " + (int)menu.getHargaMakanan());
                        nomor++;
                        break;
                    }
                }
            }
            break;
        }
    }

    public static void handleUpdateStatusPesanan(){
        System.out.println("--------------Update Status Pesanan--------------");
        while(true){
            System.out.print("Order ID: ");
            String orderID = input.nextLine();

            boolean statusSebelum = false;
            boolean orderIdTerdaftar = false;
            for(Order order : orderList){
                if(order.getOrderId().equals(orderID)){
                    orderIdTerdaftar = true; // Validasi input apakah restoran terdaftar
                    statusSebelum = order.getStatusPesanan(); // Mengambil status order sebelumnya
                    break;
                }
            }

            if(orderIdTerdaftar == false){
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }

            System.out.print("Status: ");
            String statusUpdate = input.nextLine();

            // Menetapkan status order sebelumnya
            String statusOrder = "";
            if(statusSebelum == true){
                statusOrder = "Selesai";
            } else {
                statusOrder = "Not Finished";
            }

            // Apabila status yang akan diupdate sama dari sebelumnya (sudah diupdate), maka tidak akan ter update
            if(statusOrder.equalsIgnoreCase(statusUpdate) && statusUpdate.equalsIgnoreCase("Selesai")){
                System.out.println("Status pesanan dengan ID " + orderID + " tidak berhasil diupdate!\n");
                continue;
            // Apabila status yang akan diupdate berbeda dari sebelumnya, maka akan ter update
            }else{
                System.out.println("Status pesanan dengan ID " + orderID + " berhasil diupdate!");
                for(Order order : orderList){
                    if(order.getOrderId().equals(orderID)){
                        order.setStatusPesanan(true); // Memperbarui status pesanan
                        break;
                    }
                }
            }
            break;
        }
    }

    public static void handleTambahRestoran(){
        System.out.println("--------------Tambah Restoran--------------");
        while(true){
            System.out.print("Nama: ");
            String namaResto = input.nextLine();

            boolean restoTerdaftar = false;
            for(Restaurant listResto:restoList){
                if(namaResto.equals(listResto.getNamaResto())){
                    System.out.println("Restoran dengan nama " + namaResto + " sudah pernah terdaftar. Mohon masukkan nama yang berbeda!\n");
                    restoTerdaftar = true; // Validasi input apakah restoran terdaftar
                    break;
                }
            }
            
            if(restoTerdaftar){
                continue;
            }

            // Validasi input panjang nama restoran harus lebih dari 4
            if(namaResto.length() < 4){
                System.out.println("Nama Restoran tidak valid!\n");
                continue;
            }

            System.out.print("Jumlah Makanan: ");
            Integer jumlahMakanan = Integer.parseInt(input.nextLine());

            ArrayList<String> daftarMenu = new ArrayList<>();
            while(jumlahMakanan > 0){
                String menuMakanan = input.nextLine(); // Input makanan di restoran sesuai banyak makanannya
                daftarMenu.add(menuMakanan);
                jumlahMakanan--;
            }

            // Validasi input harga makanan harus bilangan bulat
            boolean hargaBilBulat = true;
            for(String menu:daftarMenu){
                String harga = menu.substring(menu.length()-1);

                if(harga.matches("\\d+") == false){ // cek apakah bilangan bulat
                    System.out.println("Harga menu harus bilangan bulat!\n");
                    hargaBilBulat = false;
                    break;
                }
            }

            if(hargaBilBulat == false){
                continue;
            }

            ArrayList<Menu> listItem = new ArrayList<>();
            for(String menu: daftarMenu){
                // Memisahkan nama makanan dan harga makanan
                String namaMakanan = menu.substring(0,menu.lastIndexOf(" "));
                double hargaMakanan = Double.parseDouble(menu.substring(menu.lastIndexOf(" ")));

                // Menyimpan nama dan harga makanan restoran pada list item
                Menu menuResto = new Menu(namaMakanan, hargaMakanan);
                listItem.add(menuResto);
            }

            // Menyimpan pada menuList 
            for(Menu menu : listItem){
                menuList.add(menu);
            }

            // Menyimpan data restoran dan menetapkan daftar menu pada restoran
            Restaurant restobaru = new Restaurant(namaResto);
            restobaru.setDaftarMenu(listItem);
            restoList.add(restobaru);

            System.out.println("Restaurant " + namaResto + " Berhasil terdaftar.");
            break;
        }
    }

    public static void handleHapusRestoran(){
        System.out.println("--------------Hapus Restoran--------------");
        while(true){
            System.out.print("Nama Restoran: ");
            String namaResto = input.nextLine();

            boolean restoTerdaftar = false;
            for(Restaurant listResto:restoList){
                if(namaResto.equalsIgnoreCase(listResto.getNamaResto())){
                    restoTerdaftar = true; // Validasi input apakah restoran terdaftar
                    break;
                }
            }
            
            if(restoTerdaftar == false){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }

            for (int i = 0; i < restoList.size(); i++) {
                if (restoList.get(i).getNamaResto().equalsIgnoreCase(namaResto)) {
                    restoList.remove(i); // Menghapus elemen restoran dari list restoran program
                    break; 
                }
            }
            System.out.println("Restoran berhasil dihapus.");
            break;
        }
    }

    public static void initUser(){
        // User-user terdaftar
       userList = new ArrayList<User>();
       userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer"));
       userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer"));
       userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer"));
       userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer"));
       userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer"));

       userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin"));
       userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin"));
    }

    public static void printHeader(){ // Header
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    public static void startMenu(){ // Menu Awal
        System.out.println("\nSelamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuAdmin(){ // Menu Admin
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuCustomer(){ // Menu Customer
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Update Status Pesanan");
        System.out.println("5. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }
}