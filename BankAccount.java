
import java.time.*;
import java.util.*;

public class BankAccount
{
    public static void main(String[] args)
    {

        Bank bank = new Bank();

        Scanner input = new Scanner(System.in);

        int choice = 0;

        while(choice != 6)
        {
            System.out.println("-----------------");
            System.out.println("--     BANK    --");
            System.out.println("-----------------");
            System.out.println("1. Create user");
            System.out.println("2. Add Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer");
            System.out.println("6. Quit");
            System.out.println("-----------------");
            System.out.print("\nEnter Choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = input.nextLine();

                    System.out.print("Create ID: ");
                    int id = input.nextInt();

                    Customer customer = new Customer(name, id); //creates a new customer
                    bank.addCustomer(customer); //adds customer to bank
                    System.out.println("User " + name + " created");
                    break;
                case 2:
                    System.out.print("Enter user ID: ");
                    int userId = input.nextInt();
                    Customer found = bank.findCustomer(userId);

                    if(found != null)
                    {
                        System.out.println("1. Savings Account");
                        System.out.println("2. Checkings Account");
                        System.out.print(": ");
                        int accountChoice = input.nextInt();

                        System.out.print("Enter account number: ");
                        int accountNumber = input.nextInt();
                        System.out.print("Enter starting balance: ");
                        double startingBalance = input.nextDouble();

                        if(accountChoice == 1)
                        {
                            System.out.print("Enter interest rate (e.g. 0.03): ");
                            double rate = input.nextDouble();
                            found.addAccount(new SavingsAccount(accountNumber, startingBalance, rate));
                        }
                        else if(accountChoice == 2)
                        {
                            System.out.print("Enter checkings minimum balance ( will get charged if you go below): ");
                            double limit = input.nextDouble();
                            System.out.print("Enter the dept fee: ");
                            double fee = input.nextDouble();
                            found.addAccount(new CheckingsAccount(accountNumber, startingBalance, fee, limit));
                        }
                        System.out.println("Account added!");
                    }
                    break;
                case 3: 
                    System.out.print("Enter user ID: ");
                    int depositID = input.nextInt();
                    Customer depositCustomer = bank.findCustomer(depositID); //create a customer called depositCustomer and equal it to the user that we found in array that had matching id as the id user entered

                    if(depositCustomer != null) //if when finding the matching id isnt not null (if there is a matching id)
                    {
                        depositCustomer.printAccounts(); //shows all accounts for the user
                        System.out.print("Enter account number: ");
                        int accountNumber = input.nextInt();
                        Account depositAccount = depositCustomer.getAccount(accountNumber); //finds the specific account

                        if(depositAccount != null)
                        {
                            System.out.print("Enter deposit amount: ");
                            double amount = input.nextDouble();
                            depositAccount.deposit(amount);
                        }
                    }
                    break;
                case 4:
                    System.out.print("Enter user ID: ");
                    int withdrawID = input.nextInt();
                    Customer withdrawCustomer = bank.findCustomer(withdrawID);

                    if(withdrawCustomer != null)
                    {
                        withdrawCustomer.printAccounts();
                        System.out.print("Enter account number: ");
                        int withdrawAccountNumber = input.nextInt();
                        Account withdrawAccount = withdrawCustomer.getAccount(withdrawAccountNumber);

                        if(withdrawAccount != null)
                        {
                            System.out.print("Enter withdraw amount: ");
                            double withdrawAmount = input.nextDouble();
                            withdrawAccount.withdraw(withdrawAmount);
                        }
                    }
                    break;
                case 5:
                    System.out.print("Enter user ID: ");
                    int transferID = input.nextInt();
                    Customer transferCustomer = bank.findCustomer(transferID);

                    if(transferCustomer != null)
                    {
                        System.out.print("Enter account to transfer FROM: ");
                        int fromNumber = input.nextInt();
                        Account fromAccount = transferCustomer.getAccount(fromNumber);

                        System.out.print("Enter account number to transfer TO: ");
                        int toNumber = input.nextInt();
                        Account toAccount = transferCustomer.getAccount(toNumber);

                        if(fromAccount != null && toAccount != null)
                        {
                            System.out.print("Enter transfer amount: ");
                            double transferAmount = input.nextDouble();
                            bank.transfer(fromAccount, toAccount, transferAmount);
                        }
                    }
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }
}

enum TransactionType
{
    DEPOSIT, WITHDRAWAL, TRANSFER
}

class Transaction
{
    private double amount;
    private LocalDate date; //LocalDate is built into java.time package, handles date formatting automatically
    private TransactionType type;

    public Transaction(double amount, LocalDate date, TransactionType type)
    {
        this.amount = amount;
        this.date = LocalDate.now(); //auto stamps the current date
        this.type = type;
    }

    public double getAmount() { return amount;}
    public LocalDate getDate() { return date;}
    public TransactionType getType() {return type;}

    public String toString()
    {
        return date + ": \n" + type + ": \n" + "$" + amount + ": ";
    }

}

class Account
{
    private int accountNumber;
    private double balance;

    private ArrayList<Transaction> transactions = new ArrayList<>(); //since transaction happen under account, we make a transactions array within account that stores transaction history


    public Account(int accountNumber, double  balance)
    {
        this.balance = balance;
        this.accountNumber = accountNumber;
    }

    public int getAccountNumber() { return accountNumber;}
    public double getBalance() { return balance;}

    public String toString()
    {
        return "----------\n" + accountNumber + "\nBalance: " + balance + "\n----------";
    }

    public void deposit(double amount) //void because it doesnt need to return anything since its modifying the instance variable directly
    {
        Scanner input = new Scanner(System.in);

        System.out.println("\nBalance: " + balance);
        System.out.println("Enter amount to deposit: ");
        double depositAmount = input.nextDouble();

        balance += depositAmount;
        transactions.add(new Transaction(depositAmount, null, TransactionType.DEPOSIT)); //adds deposit to transaction array
        System.out.println("New balance: " + balance);

    }

    public void withdraw(double withdrawAmount)
    {
        Scanner input = new Scanner(System.in);

        System.out.println("\n----------");
        System.out.println("Balance: " + balance);
        System.out.print("Enter amount to withdraw: ");
        withdrawAmount = input.nextDouble();

        if(withdrawAmount <= balance)
        {
            balance -= withdrawAmount;
            transactions.add(new Transaction(withdrawAmount, null, TransactionType.WITHDRAWAL)); // adds withdraw to transaction array
            System.out.println("New balance: " + balance);
        }
        else System.out.println("Insufficient funds.");
    }

    public void printHistory()
    {
        System.out.println("--Account History--");
        System.out.println("\nAccount: " + accountNumber);

        if(transactions.isEmpty())
        {
            System.out.println("No Transaction History");
            return;
        }

        for(int i = 0; i <transactions.size(); i++)
        {
            System.out.println(transactions.get(i)); //grabs the toString method and returns the string that prints the info
        }

        System.out.println("----------");
        System.out.println("Balance: " + balance);
        System.out.println("----------");
    }
}

class SavingsAccount extends Account
{
    private double interestRate;

    public SavingsAccount(int accountNumber, double balance, double interestRate)
    {
        super(accountNumber, balance); //passes up to Account constructor
        this.interestRate = interestRate;
    }

    public double getInterestRate() { return interestRate;}

    public void applyInterest()
    {
        double interest = getBalance() * interestRate; //gets balance and multiplies by interest to calculate how much interest is owed
        deposit(interest); //calls deposit() to add the interest into the balance
        System.out.println("Interest of $" + interest + " applied.");
    }
}

class CheckingsAccount extends Account
{
    private double overdraftLimit;
    private double overdraftFee; 

    public CheckingsAccount(int accountNumber, double balance, double overdraftFee, double overdraftLimit)
    {
        super(accountNumber, balance);
        this.overdraftFee = overdraftFee;
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() { return overdraftLimit;}
    public double getOverdraftFee() { return overdraftFee;}

    public void withdraw(double withdrawAmount)
    {
        if(withdrawAmount <= getBalance() + overdraftLimit)
        {

            if(withdrawAmount <= getBalance())
            {
                //allowed - either normal or overdraft
                super.withdraw(withdrawAmount); //normal withdraw, no overdraft
            }
            else 
        {
                //allowed - add fee
                super.withdraw(withdrawAmount); //withdraw normal amount
                super.withdraw(overdraftFee); // then withdraw the fee
                System.out.println("Fee of $" + overdraftFee + " charged");
            }
        }
        else 
        {
            //completely blocked - too far over the overdraft limit
            System.out.println("Exceeds overdraft limit. Transaction denied.");
        }
    }
}

class Customer
{
    private String name;
    private int id;

    private ArrayList<Account> accounts = new ArrayList<>();

    public Customer(String name, int id)
    {
        this.name = name;
        this.id = id;
    }

    public String getName() { return name;}
    public int getId() { return id;}

    public void addAccount(Account account)
    {
        accounts.add(account);
        System.out.println("Account added for " + name);
    }

    public void printAccounts()
    {
        System.out.println("--Accounts--");
        System.out.println("User: " + name + "\nID: " + id);

        if(accounts.isEmpty()) //if the accounts ArrayList is empty
        {
            System.out.println("No accounts found.");
            {
                return;
            }
        }

        for(int i = 0; i <accounts.size(); i++) //since all accounts are stored in accounts ArrayList, we need to loop through it and print each one
        {
            System.out.println(accounts.get(i)); //calls toString from account
        }
    }

    public Account getAccount(int accountNumber)
    {
        for(int i = 0; i < accounts.size(); i++)
        {
            if(accounts.get(i).getAccountNumber() == accountNumber)
            {
                return accounts.get(i);
            }
        }
        System.out.println("Account not found.");
        return null;
    }
}

class Bank
{
    private ArrayList<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer customer)
    {
        customers.add(customer);
    }

    public Customer findCustomer(int id)
    {
        for(int i = 0; i < customers.size(); i++)
        {
            if(id == customers.get(i).getId())
            {
                System.out.println(id + " found.");
                return customers.get(i);
            }
        }
        System.out.println("User not found.");
        return null;
    }

    public void printCustomers()
    {
        System.out.println("\n--Customers--");

        if(customers.isEmpty())
        {
            System.out.println("No users found.");
            return;
        }

        for(int i = 0; i < customers.size(); i++)
        {
            System.out.println(customers.get(i)); //print the toString in customer
        }
    }

    public void transfer(Account from, Account to, double amount)
    {
        if(amount <= from.getBalance())
        {
            from.withdraw(amount); //take money out of one account,  call withdraw() on the "from" account
            to.deposit(amount); //put it into the other, call deposit() on the "to" account
            System.out.println("Transferred $" + amount + " successfully");
        }
        else
        {
            System.out.println("Insufficient funds for transfer");
        }
    }
}