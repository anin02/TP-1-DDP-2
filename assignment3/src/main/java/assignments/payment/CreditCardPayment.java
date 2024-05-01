package assignments.payment;

public class CreditCardPayment implements DepeFoodPaymentSystem {

    // Properti
    private static final double TRANSACTION_FEE_PERCENTAGE = 0.02;

    // Method dari interface DepeFoodPaymentSystem
    @Override
    public long processPayment(long amount) {
        // Menghitung transaction fee
        long transactionFee = countTransactionFee(amount);
        // Mengurangkan transaction fee dari saldo
        long saldoSetelahPembayaran = amount - transactionFee;
        return saldoSetelahPembayaran;
    }

    // Method untuk menghitung transaction fee
    public long countTransactionFee(long amount) {
        return (long) (amount * TRANSACTION_FEE_PERCENTAGE);
    }
}
