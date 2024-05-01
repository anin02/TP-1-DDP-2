package assignments.SolusiTP3;

import java.util.ArrayList;
import java.util.Scanner;

import assignments.assignment2.Menu;
import assignments.assignment2.Restaurant;
import assignments.assignment2.User;
import assignments.payment.CreditCardPayment;
import assignments.payment.DebitPayment;
import assignments.systemCLI.AdminSystemCLI;
import assignments.systemCLI.CustomerSystemCLI;
import assignments.systemCLI.UserSystemCLI;

public class MainMenu {
    private final Scanner input;
    public final LoginManager loginManager;
    public static ArrayList<Restaurant> restoList;
    public static ArrayList<User> userList;
    public static User userLoggedIn;

    public static ArrayList<Restaurant> getRestoList() {
        return restoList;
    }

    public static ArrayList<User> getUserList() {
        return userList;
    }

    public static User getUserLoggedIn() {
        return userLoggedIn;
    }

    public MainMenu(Scanner in, LoginManager loginManager) {
        this.input = in;
        this.loginManager = loginManager;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        userLoggedIn = null;
        initUser(); // Initialize user list
        restoList = new ArrayList<Restaurant>();
        // initResto(); // Initialize restaurant list
        MainMenu mainMenu = new MainMenu(new Scanner(System.in),
                new LoginManager(new AdminSystemCLI(), new CustomerSystemCLI()));
        mainMenu.run();
    }

    public void run() {
        printHeader();
        boolean exit = false;
        while (!exit) {
            startMenu();
            int choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1 -> login();
                case 2 -> exit = true;
                default -> System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }
        }

        input.close();
    }

    private void login() {
        System.out.println("\nSilakan Login:");
        System.out.print("Nama: ");
        String nama = input.nextLine();
        System.out.print("Nomor Telepon: ");
        String noTelp = input.nextLine();

        userLoggedIn = getUser(nama, noTelp); // Retrieve the user
        System.out.printf("USER LOGIN", userLoggedIn);

        if (userLoggedIn == null) {
            System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
            return;
        }

        System.out.printf("Selamat Datang %s!\n", nama);

        // Retrieve the corresponding UserSystemCLI instance based on user's role
        UserSystemCLI userSystem = loginManager.getSystem(userLoggedIn.getRole());

        // Set the logged-in user in the appropriate UserSystemCLI instance
        if (userSystem instanceof CustomerSystemCLI) {
            ((CustomerSystemCLI) userSystem).setUserLoggedIn(userLoggedIn);
        } else if (userSystem instanceof AdminSystemCLI) {
            ((AdminSystemCLI) userSystem).setUserLoggedIn(userLoggedIn);
        }

        // Run the selected UserSystemCLI
        userSystem.run();
    }

    private static void printHeader() {
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    private static void startMenu() {
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static User getUser(String nama, String nomorTelepon) {

        for (User user : userList) {
            if (user.getNama().equals(nama.trim()) && user.getNomorTelepon().equals(nomorTelepon.trim())) {
                return user;
            }
        }
        return null;
    }

    public static void initUser() {
        userList = new ArrayList<User>();

        userList.add(
                new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer", new DebitPayment(), 500000));
        userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer",
                new CreditCardPayment(), 2000000));
        userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer",
                new DebitPayment(), 750000));
        userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer",
                new CreditCardPayment(), 1800000));
        userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer", new DebitPayment(),
                650000));

        userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin", new CreditCardPayment(), 0));
        userList.add(
                new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin", new CreditCardPayment(), 0));
    }

    public static void initResto() {
        restoList = new ArrayList<Restaurant>();

        Restaurant holyCow = new Restaurant("HolyCow!");
        holyCow.addMenu(new Menu("Tenderloin Steak", 20000.0));
        holyCow.addMenu(new Menu("Sirloin Steak", 300000.0));
        holyCow.addMenu(new Menu("Apel", 100000.0));
        restoList.add(holyCow);
    }
}
