package assignments.payment;

public class DebitPayment implements DepeFoodPaymentSystem {

    // Properti
    private static final double MINIMUM_TOTAL_PRICE = 50000; // Total harga pesanan minimal

    // Method dari interface DepeFoodPaymentSystem
    @Override
    public long processPayment(long amount) {
        // Cek apakah total harga pesanan memenuhi syarat minimal
        if (amount < MINIMUM_TOTAL_PRICE) {
            System.out.println(
                    "Total harga pesanan kurang dari minimum yang dibutuhkan untuk pembayaran dengan Debit Card.");
            return -1; // Pembayaran gagal
        }

        // Pembayaran berhasil
        System.out.println("Pembayaran dengan Debit Card berhasil dilakukan sejumlah: " + amount);
        return 0; // Kode berhasil
    }
}
