import java.util.Scanner;

public class BankAccount {
    private String owner;
    private double balance;

    public BankAccount(String owner, double initialBalance) {
        this.owner = owner;
        if (initialBalance >= 0) {
            this.balance = initialBalance;
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getOwner() {
        return owner;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew: " + amount);
        } else {
            System.out.println("Invalid or insufficient funds.");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the owner's name?");
        String owner = sc.nextLine();
        System.out.println("Enter the initial balance?");
        double init_bal=sc.nextDouble();
        System.out.println("Enter the amount to be deposited?");
        double amt_dep= sc.nextDouble();
        System.out.println("Enter the amount to be withdrawed?");
        double amt_wit= sc.nextDouble();
        BankAccount acc = new BankAccount(owner, init_bal);
        acc.deposit(amt_dep);
        acc.withdraw(amt_wit);
        System.out.println("Current Balance: " + acc.getBalance());

    }
}
