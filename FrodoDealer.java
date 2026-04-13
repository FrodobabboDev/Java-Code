import java.util.ArrayList;
import javax.sound.midi.SysexMessage;


class Inventory
{
    private String company;
    private String model;
    private int miles;
    private String carType;
    private boolean inStock;

    public Inventory(String company, String model, int miles, String carType)
    {
        this.company = company;
        this.model = model;
        this.miles = miles;
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
    public String getCarType() {return carType;}

    public String toString()
    {
        return("Body Style: " + carType + "Brand: " + company + "\nModel: " + model + "\nMiles: " + miles);
    }
}

class MenuNavigation
{
    private ArrayList<Inventory> cars;

    public MenuNavigation()
    {
        cars = new ArrayList<>();
    }

    public void addCar(String brand, String model, int miles, String carType)
    {
        cars.add(new Inventory(brand, model, miles, carType));
    }

    public void viewInventory()
    {
        if(cars.isEmpty())
        {
            System.out.println("No Cars Found.");
        }

        for(int i = 0; i < cars.size(); i++)
        {
            System.out.println((i + 1) + ". " + cars.get(i));
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
}


class AutoWorld
{

    Scanner input = new Scanner(System.in);
}
