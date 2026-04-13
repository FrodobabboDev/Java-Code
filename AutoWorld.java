import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Inventory
{
    private String company;
    private String model;
    private int miles;
    private int price;
    private String carType;
    private boolean inStock;

    public Inventory(String company, String model, int miles, String carType, int price)
    {
        this.company = company;
        this.model = model;
        this.miles = miles;
        this.price = price;
        this.carType = carType;
        this.inStock = Math.random() < 0.5;
    }

    public boolean inStock()
    {
        return inStock; 
    }

    public String getCompany() {return company;}
    public String getModel() {return model;}
    public int getMiles() {return miles;}
    public int getPrice() {return price;}
    public String getCarType() {return carType;}

    public String toString()
    {
        return("Brand: " + company + "\nModel: " + model + "\nCar Type: " + carType + "\nMiles: " + miles + "\nPrice: " + price);
    }

    public int compareTo(Inventory other)
    {
        return this.company.compareTo(other.company);
    }


}

class MenuNavigation
{
    private ArrayList<Inventory> cars;

    public MenuNavigation()
    {
        cars = new ArrayList<>();
    }

    //gets user input and pulls info from the Inventory class and puts the corresponding user info into those instance variables
    public void addCar() 
    {
        int price = 0;

        Scanner input = new Scanner(System.in);

        System.out.print("Enter brand: ");
        String brand = input.nextLine();

        System.out.print("Enter Model: ");
        String model = input.nextLine();

        System.out.print("Enter miles: ");
        int miles = input.nextInt();
        input.nextLine();

        Random rand = new Random();

        if(miles >= 100000 && miles < 120000)
        {
            price = rand.nextInt(8000,15000);
        }
        else if(miles > 120000)
        {
            price = rand.nextInt(2000, 8000);
        }
        else if(miles <= 100000 && miles >= 70000)
        {
            price = rand.nextInt(13000, 20000);
        }
        else if(miles < 70000 && miles >= 40000)
        {
            price = rand.nextInt(20000, 30000);
        }
        else if(miles < 40000 && miles >= 20000)
        {
            price = rand.nextInt(30000, 45000);
        }
        else if(miles < 20000 && miles >= 0)
        {
            price = rand.nextInt(45000, 100000);
        }    

        System.out.print("Enter car Type (Truck, SUV, Sedan, Hatchback): ");
        String carType = input.nextLine();

        cars.add(new Inventory(brand, model, miles, carType, price));
    }

    public void viewInventory()
    {
        if(cars.isEmpty())
        {
            System.out.println("No Cars Found.");
        }

        for(int i = 0; i < cars.size(); i++)
        {
            System.out.println((i + 1) + ".\n" + cars.get(i));
            System.out.println("---------------------------");
        }
    }

    public void removeCar(int index)
    {
        if(index >= 0 && index < cars.size())
        {
            cars.remove(index);
        }
        else System.out.println("Invalid Car Number.");
    }

    public void sortByCompany()
    {
        int n = cars.size();

        for(int i = 0; i < cars.size(); i++)
        {
            int minIndex = i;

            for(int j = i + 1; j < n; j++)
            {
                Inventory a = cars.get(j);
                Inventory min = cars.get(minIndex);

                if(a.compareTo(min) < 0)
                {
                    minIndex = j;
                }
            }

            if(minIndex != i)
            {
                Inventory temp = cars.get(i);
                cars.set(i, cars.get(minIndex));
                cars.set(minIndex, temp);
            }
        }
    }

    public ArrayList<Inventory> searchByCompany(String company)
    {
        ArrayList<Inventory> results = new ArrayList<>();

        for(Inventory car : cars)
        {
            if(car.getCompany().equalsIgnoreCase(company))
            {
                results.add(car);
            }
        }
        return results;
    }

}

public class AutoWorld
{

    public static void main(String[] args)
    {

    

        Scanner input = new Scanner(System.in);
        MenuNavigation menu = new MenuNavigation();

        int userChoice = 0;

        while(userChoice != 5)
        {
            System.out.println("--Welcome to the AutoWorld--");
            System.out.println("1. Add a car");
            System.out.println("2. Remove a car");
            System.out.println("3. View Inventory");
            System.out.println("4. Search by brand");
            System.out.println("5. Exit");

            System.out.print("Choose Option: ");
            userChoice = input.nextInt();
            input.nextLine();

            String header = stupidHeader();

            System.out.println("----------------------------");
            switch(userChoice) {
                case 1:
                    menu.addCar();
                    System.out.println(header);
                    break;
                case 2:
                    menu.viewInventory();
                    System.out.print("\nEnter a car to remove: ");
                    int index = input.nextInt() - 1;
                    menu.removeCar(index);
                    System.out.println(header);
                    break;
                case 3:
                    menu.viewInventory();
                    break;
                case 4:
                    System.out.print("\nEnter a brand to search: ");
                    String brand = input.nextLine();
                    ArrayList<Inventory> results = menu.searchByCompany(brand);

                    if(results.isEmpty())
                    {
                        System.out.print("\nNo cars found for brand: " + brand);
                    }
                    else
                    {
                        System.out.println("----------------------------");
                        System.out.print("Cars found: ");
                        System.out.println("\n----------------------------");
                        for(Inventory car : results)
                        {
                            System.out.println("\n" + car);
                            System.out.println("--------");
                        }
                    }
                    System.out.println(header);
                    break;
                default:
                    break;
            }


        }
        System.out.println("Thank you for coming to AutoWorld!");
        

    }

    public static String stupidHeader()
    {
        return"----------------------------\n-      *               *   -\n-   *          *           -\n-  *     *        *        -\n----------------------------";
    }

}
