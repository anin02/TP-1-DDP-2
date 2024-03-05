package assignments.assignment1;

import java.util.Scanner;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);

    // Menampilkan tampilan menu
    public static void showMenu(){
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
        System.out.println("Pilih menu:");
        System.out.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
    }

    // Menghitung Check Sum Order ID
    // Parameter method ini berupa kode order sebelum ditambah CheckSum (Nama Resto (4 karakter) + Tanggal Order (8 karakter) + hasil No Telepon (2 karakter))
    public static String hitungCheckSum(String kodeOrder){
        String[] listCharset = new String[]{"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"}; 
        
        String[] listKode = new String[14];
        for (int j=0; j < kodeOrder.length(); j++){
            char kodeAwal = kodeOrder.charAt(j); // Mengambil setiap karakter dalam kode order sementara
            String kodeProses = Character.toString(kodeAwal); // Karakter yang diambil diubah ke String
            listKode[j] = kodeProses; // Setiap karakter kode order yang sudah diubah ke string, akan dimasukkan ke array listkode
        }
        
        int hasilHitungGenap = 0;
        int hasilHitungGanjil = 0;
        for (int n=0; n < 14; n++){ // iterasi setiap karakter dalam listkode (setiap karakter kode order sementara)
            for (int k=0; k < 35; k++){ // iterasi listCharset (karakter dalam charset 39)
                if (listKode[n].equals(listCharset[k])){
                    if (n%2==0){
                        hasilHitungGenap += k; // Apabila karakter kode order berada di index genap maka charset dari kode order tersebut digunakan untuk Check Sum 1 (genap)
                    } else {
                        hasilHitungGanjil += k; // Apabila karakter kode order berada di index ganjil maka charset dari kode order tersebut digunakan untuk Check Sum 2 (ganjil)
                    }

                }
            }
        }

        hasilHitungGenap = hasilHitungGenap%36;
        hasilHitungGanjil = hasilHitungGanjil%36;
        String hasilChecksum = "";

        // hasil hitung genap dan hasil hitung ganjil diubah lagi dari nilai numerik ke value charset
        for (int k=0; k < 35; k++){
            if (hasilHitungGenap == k){
                hasilChecksum += listCharset[k];
            }
        }

        for (int k=0; k < 35; k++){
            if(hasilHitungGanjil == k){
                hasilChecksum += listCharset[k];
            }
        }
    
        return hasilChecksum;
    }

    // Membuat Order ID
    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        // 4 Karakter nama restoran (capslock)
        namaRestoran = (namaRestoran.replace(" ","")).substring(0, 4);
        namaRestoran = namaRestoran.toUpperCase();

        // 8 Karakter digit (tanggal,bulan,tahun)
        tanggalOrder = tanggalOrder.replace("/", "");

        // Setiap digit dalam nomor telepon di jumlahkan
        int i = 0;
        int noTelpBaru = 0;
        while(i < noTelepon.length()){
            int noHp = Character.getNumericValue(noTelepon.charAt(i));
            noTelpBaru += noHp;
            i++;
        }

        // hasil penjumlahan setiap digit no telepon di modulo 100, apabila hasilnya hanya 1 digit, maka tambahkan "0" didepan
        noTelpBaru = noTelpBaru%100;
        String hasilNoTelp = Integer.toString(noTelpBaru);
        if (hasilNoTelp.length() != 2){
            hasilNoTelp = "0" + hasilNoTelp;
        }

        // Kode order sementara (sebelum ditambah Check Sum)
        String kodeOrder = namaRestoran + tanggalOrder + hasilNoTelp;

        // Hitung Check Sum dengan pemanggilan hitungCheckSum
        String hasilChecksum = hitungCheckSum(kodeOrder);
    
        // Me-return Order ID
        return kodeOrder + hasilChecksum;
    }
    
    // Membuat Bill
    public static String generateBill(String OrderID, String lokasi){
        // lokasi (capslock)
        lokasi = lokasi.toUpperCase();
        
        // Menentukan biaya Ongkir sesuai tujuan lokasi
        String biayaOngkir;
        if(lokasi.equals("P")){
            biayaOngkir = "10.000";
        } else if(lokasi.equals("U")){
            biayaOngkir = "20.000";
        } else if(lokasi.equals("T")){
            biayaOngkir = "35.000";
        } else if(lokasi.equals("S")){
            biayaOngkir = "40.000";
        } else {
            biayaOngkir = "60.000";
        }

        String tanggalPemesanan;
        tanggalPemesanan = OrderID.substring(4,6) + "/" + OrderID.substring(6, 8) + "/" + OrderID.substring(8, 12);

        // Mencetak Bill
        String hasilGenerateBill = "Bill:\nOrder ID: " + OrderID + "\nTanggal Pemesanan: " + tanggalPemesanan + "\nLokasi Pengiriman: " + lokasi + "\nBiaya Ongkos Kirim: Rp " + biayaOngkir;

        return hasilGenerateBill;
    }


    public static void main(String[] args) {
        showMenu();

        while(true){
            System.out.println("--------------------------------------------------");
            System.out.print("pilihan menu: ");
            String pilihAksi = input.nextLine();

            // Generate Order ID
            if (pilihAksi.equals("1")){

                while(true){
                    // Input dan Validasi input nama restoran
                    System.out.println(" ");
                    System.out.print("Nama restoran: ");
                    String namaRestoran = input.nextLine();
                
                    if(namaRestoran.length() < 4){
                        System.out.println("Nama restoran tidak valid!");
                        continue;
                    }
                
                    // Input dan Validasi input tanggal pemesanan
                    System.out.print("Tanggal pemesanan: ");
                    String tanggalOrder = input.nextLine();

                    if(tanggalOrder.charAt(2) != '/' || tanggalOrder.charAt(5) != '/' || tanggalOrder.length() != 10){
                        System.out.println("Tanggal pemesanan dalam format DD/MM/YYYY!");
                        continue;
                    }

                    // Input dan Validasi input No Telepon
                    System.out.print("No.Telpon: ");
                    String noTelepon = input.nextLine();

                    boolean cekDigitHp = false;
                    for(int z = 0; z < noTelepon.length(); z++){
                        char nomorHPcek = noTelepon.charAt(z);
                        if(!Character.isDigit(nomorHPcek)){
                            cekDigitHp = true;
                            break;
                        }
                    }

                    if(cekDigitHp){
                        System.out.println("Harap masukkan nomor telepon dalam bentuk bilangan bulat positif.");
                        continue;
                    }

                    // Membuat dan mencetak Order ID
                    String hasilGenerateID = generateOrderID(namaRestoran, tanggalOrder, noTelepon);
                    System.out.println("Order ID " + hasilGenerateID + " diterima!");

                    break;
                }

                System.out.println("--------------------------------------------------");
                System.out.println("Pilih menu:");
                System.out.println("1. Generate Order ID");
                System.out.println("2. Generate Bill");
                System.out.println("3. Keluar");
                continue;
            }
        
            // Generate Bill
            else if(pilihAksi.equals("2")){
                
                while(true){
                    // Input dan Validasi input Order ID
                    System.out.println(" ");
                    System.out.print("Order ID: ");
                    String orderId = input.nextLine();

                    if(orderId.length() < 16){
                        System.out.println("Order ID minimal 16 karakter");
                        continue;
                    }

                    String cekKodeOrder = hitungCheckSum(orderId.substring(0, 14)); // Menghitung Check Sum untuk Input Order ID

                    boolean cekOrderId = false;
                    if(orderId.substring(14).equals(cekKodeOrder)){ // Cek apakah hasil Check Sum sudah sesuai dengan Check Sum input
                        cekOrderId = true;
                    }

                    if(cekOrderId == false){
                        System.out.println("Silahkan masukkan Order ID yang valid");
                        continue;
                    }

                    // Input dan Validasi input lokasi pengiriman
                    System.out.print("Lokasi Pengiriman: ");
                    String lokasiKirim = input.nextLine();

                    boolean cekLokasiKirim = false;
                    String [] lokasi = new String[]{"P","U","T","S","B","p","u","t","s","b"}; // lokasi pengiriman yang tersedia
                    for(String element:lokasi){
                        if(lokasiKirim.equals(element)){
                            cekLokasiKirim = true;
                        }
                    }

                    if(cekLokasiKirim == false){
                        System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan!");
                        continue;
                    }

                    // Membuat dan mencetak Bill
                    String hasilGenerateBill = generateBill(orderId, lokasiKirim);
                    System.out.println(" ");
                    System.out.println(hasilGenerateBill);
                    break;
                }

                System.out.println("--------------------------------------------------");
                System.out.println("Pilih menu:");
                System.out.println("1. Generate Order ID");
                System.out.println("2. Generate Bill");
                System.out.println("3. Keluar");
                continue; 
            }

            // Keluar dari sistem
            else {
                System.out.println("Terima kasih telah menggunakan DepeFood!");
                break;
            }
        }
    }
}