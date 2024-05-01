package assignments.systemCLI;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import assignments.assignment1.OrderGenerator;
import assignments.assignment2.Menu;
import assignments.assignment2.Order;
import assignments.assignment2.Restaurant;
import assignments.assignment2.User;
import assignments.payment.CreditCardPayment;
import assignments.payment.DebitPayment;
import assignments.payment.DepeFoodPaymentSystem;
import assignments.SolusiTP3.MainMenu;

public class CustomerSystemCLI extends UserSystemCLI {

    public CustomerSystemCLI() {
        input = new Scanner(System.in);
    }

    private static Scanner input = new Scanner(System.in);
    private User userLoggedIn;

    public void setUserLoggedIn(User userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
    }

    @Override
    public void displayMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Bayar Bill");
        System.out.println("5. Cek Saldo");
        System.out.println("6. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    @Override
    public boolean handleMenu(int choice) {
        switch (choice) {
            case 1:
                handleBuatPesanan();
                break;
            case 2:
                handleCetakBill();
                break;
            case 3:
                handleLihatMenu();
                break;
            case 4:
                handleBayarBill();
                break;
            case 5:
                handleCekSaldo();
                break;
            case 6:
                return false;
            default:
                System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    public static void handleBuatPesanan() {
        System.out.println("--------------Buat Pesanan----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String restaurantName = input.nextLine().trim();
            Restaurant restaurant = getRestaurantByName(restaurantName);
            if (restaurant == null) {
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
            String tanggalPemesanan = input.nextLine().trim();
            if (!OrderGenerator.validateDate(tanggalPemesanan)) {
                System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY)");
                System.out.println();
                continue;
            }
            System.out.print("Jumlah Pesanan: ");
            int jumlahPesanan = Integer.parseInt(input.nextLine().trim());
            System.out.println("Order: ");
            List<String> listMenuPesananRequest = new ArrayList<>();
            for (int i = 0; i < jumlahPesanan; i++) {
                listMenuPesananRequest.add(input.nextLine().trim());
            }
            if (!validateRequestPesanan(restaurant, listMenuPesananRequest)) {
                System.out.println("Mohon memesan menu yang tersedia di Restoran!");
                continue;
            }

            // Accessing userLoggedIn here from MainMenu
            User userLoggedIn = MainMenu.getUserLoggedIn();

            Order order = new Order(
                    OrderGenerator.generateOrderID(restaurantName, tanggalPemesanan, userLoggedIn.getNomorTelepon()),
                    tanggalPemesanan,
                    OrderGenerator.calculateDeliveryCost(userLoggedIn.getLokasi()),
                    restaurant,
                    getMenuRequest(restaurant, listMenuPesananRequest));

            System.out.printf("Pesanan dengan ID %s diterima!", order.getOrderId());
            userLoggedIn.addOrderHistory(order); // Adding order to user's order history
            return;
        }
    }

    public static Menu[] getMenuRequest(Restaurant restaurant, List<String> listMenuPesananRequest) {
        Menu[] menu = new Menu[listMenuPesananRequest.size()];
        for (int i = 0; i < menu.length; i++) {
            for (Menu existMenu : restaurant.getMenu()) {
                if (existMenu.getNamaMakanan().equals(listMenuPesananRequest.get(i))) {
                    menu[i] = existMenu;
                }
            }
        }
        return menu;
    }

    public static boolean validateRequestPesanan(Restaurant restaurant, List<String> listMenuPesananRequest) {
        return listMenuPesananRequest.stream().allMatch(
                pesanan -> restaurant.getMenu().stream().anyMatch(menu -> menu.getNamaMakanan().equals(pesanan)));
    }

    public static Restaurant getRestaurantByName(String name) {
        Optional<Restaurant> restaurantMatched = MainMenu.restoList.stream()
                .filter(restoran -> restoran.getNama().toLowerCase().equals(name.toLowerCase())).findFirst();
        if (restaurantMatched.isPresent()) {
            return restaurantMatched.get();
        }
        return null;
    }

    public static void handleCetakBill() {
        System.out.println("--------------Cetak Bill----------------");
        while (true) {
            System.out.print("Masukkan Order ID: ");
            String orderId = input.nextLine().trim();
            Order order = getOrderOrNull(orderId);
            if (order == null) {
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            System.out.println("");
            System.out.print(outputBillPesanan(order));
            return;
        }
    }

    public static String outputBillPesanan(Order order) {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(symbols);
        return String.format("Bill:%n" +
                "Order ID: %s%n" +
                "Tanggal Pemesanan: %s%n" +
                "Lokasi Pengiriman: %s%n" +
                "Status Pengiriman: %s%n" +
                "Pesanan:%n%s%n" +
                "Biaya Ongkos Kirim: Rp %s%n" +
                "Total Biaya: Rp %s%n",
                order.getOrderId(),
                order.getTanggal(),
                MainMenu.userLoggedIn.getLokasi(),
                !order.getOrderFinished() ? "Not Finished" : "Finished",
                getMenuPesananOutput(order),
                decimalFormat.format(order.getOngkir()),
                decimalFormat.format(order.getTotalHarga()));
    }

    public static String getMenuPesananOutput(Order order) {
        StringBuilder pesananBuilder = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('\u0000');
        decimalFormat.setDecimalFormatSymbols(symbols);
        for (Menu menu : order.getSortedMenu()) {
            pesananBuilder.append("- ").append(menu.getNamaMakanan()).append(" ")
                    .append(decimalFormat.format(menu.getHarga())).append("\n");
        }
        if (pesananBuilder.length() > 0) {
            pesananBuilder.deleteCharAt(pesananBuilder.length() - 1);
        }
        return pesananBuilder.toString();
    }

    public static Order getOrderOrNull(String orderId) {
        for (User user : MainMenu.getUserList()) {
            for (Order order : user.getOrderHistory()) {
                if (order.getOrderId().equals(orderId)) {
                    return order;
                }
            }
        }
        return null;
    }

    public static void handleLihatMenu() {
        System.out.println("--------------Lihat Menu----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String restaurantName = input.nextLine().trim();
            Restaurant restaurant = getRestaurantByName(restaurantName);
            if (restaurant == null) {
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            System.out.print(restaurant.printMenu());
            return;
        }
    }

    public void handleBayarBill() {
        System.out.println("--------------Bayar Bill----------------");
        System.out.print("Masukkan Order ID: ");
        String orderId = input.nextLine().trim();
        Order order = getOrderOrNull(orderId);
        if (order == null) {
            System.out.println("Order ID tidak dapat ditemukan.\n");
            return;
        }

        System.out.println("Bill:");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Tanggal Pemesanan: " + order.getTanggal());
        System.out.println("Restaurant: " + order.getRestaurant().getNama());
        System.out.println("Lokasi Pengiriman: " + MainMenu.userLoggedIn.getLokasi());
        System.out.println("Status Pengiriman: " + (!order.getOrderFinished() ? "Not Finished" : "Finished"));
        System.out.println("Pesanan:");
        for (Menu menu : order.getSortedMenu()) {
            System.out.println("- " + menu.getNamaMakanan() + " " + menu.getHarga());
        }
        System.out.println("Biaya Ongkos Kirim: Rp " + order.getOngkir());
        System.out.println("Total Biaya: Rp " + order.getTotalHarga());

        DepeFoodPaymentSystem paymentSystem = selectPaymentMethod(); // Panggil method selectPaymentMethod()
        if (paymentSystem != null) {
            double totalBiaya = order.getTotalHarga();
            long biayaTransaksi = (long) (totalBiaya * 0.02); // Biaya transaksi 2% dari total biaya
            double totalPembayaran = totalBiaya + biayaTransaksi;
            double saldoUser = userLoggedIn.getSaldo();
            double sisaSaldo = saldoUser - totalPembayaran;

            System.out.println("\nBerhasil Membayar Bill sebesar Rp " + totalBiaya +
                    " dengan biaya transaksi sebesar Rp " + biayaTransaksi);

            // Update saldo userLoggedIn (assuming saldo is a field in the User class)
            userLoggedIn.setSaldo((long) sisaSaldo); // Misalnya, update saldo dengan totalBiaya
        }
    }

    public void handleCekSaldo() {
        System.out.println("--------------Cek Saldo----------------");
        System.out.println("Sisa Saldo sebesar Rp " + userLoggedIn.getSaldo());
    }

    private DepeFoodPaymentSystem selectPaymentMethod() {
        System.out.println("Pilih metode pembayaran:");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");
        System.out.print("Pilihan Anda: ");
        int choice = input.nextInt();
        input.nextLine(); // Consume newline character
        switch (choice) {
            case 1:
                return new CreditCardPayment();
            case 2:
                return new DebitPayment();
            default:
                System.out.println("Pilihan tidak valid.");
                return null;
        }
    }
}
