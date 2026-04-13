/*
store player scores
sort highest to lowest
find a platers rand 
 */

import java.util.ArrayList;
import java.util.Scanner;


public class leaderboard
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);

        manager userPlayer = new manager();

        int choice = 0;

        while(choice !=4)
        {
            System.out.println("1. View Players");
            System.out.println("2. Add Player");
            System.out.println("3. Search Player");
            System.out.println("4. Exit");
            System.out.println("-----------------------");
        
            System.out.print("Enter your choice: ");
            choice = input.nextInt();

            switch (choice) 
            {
                case 1:
                    System.out.println("---List of Players---");
                    userPlayer.sortScore();
                    userPlayer.viewPlayers();
                    break;
                case 2:
                    userPlayer.addPlayer();
                    break;
                case 3:
                    System.out.print("Enter Player name: ");
                    input.nextLine();
                    String searchName = input.nextLine();

                    data foundPlayer = userPlayer.binarySearchName(searchName);

                    if(foundPlayer != null)
                    {
                        System.out.println(foundPlayer);
                        System.out.println("-----------------------");
                    }
                    else System.out.println("No Player found with name: " + searchName);
                    break;
                default:
                    throw new AssertionError();
            }
        }        
    }
}



class data
{
    private String name;
    private String position;
    private int goalsPerSeason;
    private int score;

    public data(String name, String position, int goalsPerSeason, int score)
    {
        this.name = name;
        this.position = position;
        this.goalsPerSeason = goalsPerSeason;
        this.score = score;
    }

    public String getName() {return name;}
    public String getPosition() {return position;}
    public int getGoalsPerSeason() {return goalsPerSeason;}
    public int getScore() {return score;}

    public String toString()
    {
        return("\n-----------------------------\nPlayer: " + name + "\nPosition: " + position + "\nGoals Per Season: " + goalsPerSeason + "\nScore (out of 10): " + score);
    }

    public boolean equals(Object obj)
    {
        if(this == obj)
        {
            return true;
        }

        if(obj == null || getClass() != obj.getClass())
        {
            return false;
        }

        data other = (data) obj;
        return this.name.equals(other.name);
    }
}

class manager
{
    private ArrayList<data> playerData;

    public manager()
    {
        playerData = new ArrayList<>();
    }
    
    public ArrayList<data> playerData()
    {
        return playerData;
    }

    public void setPlaterData(ArrayList<data> playerData)
    {
        this.playerData = playerData;
    }

    public void viewPlayers()
    {
        for(int i = 0; i < playerData.size(); i++)
        {
            System.out.println(playerData.get(i));
        }
    }

    public void addPlayer()
    {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter Player first and last name: ");
        String name = input.nextLine();

        System.out.print("Enter player position: ");
        String position = input.nextLine();

        System.out.print("Enter goals per season: ");
        int goalsPerSeason = input.nextInt();

        int score = 0;
        if(goalsPerSeason >= 100)
        {
            score = 10;
        }
        else if(goalsPerSeason < 100 && goalsPerSeason >= 70)
        {
            score = 8;
        }
        else if(goalsPerSeason < 70 && goalsPerSeason >= 40)
        {
            score = 6;
        }
        else if(goalsPerSeason < 40 && goalsPerSeason >= 20)
        {
            score = 4;
        }
        else if(goalsPerSeason < 20)
        {
            score = 2;
        }

        for(int i = 0; i < playerData.size(); i++)
        {
            if(playerData.get(i).getName().equals(name))
            {
                System.out.println("Player already exists with name: " + name);
                return;
            }
        }

        playerData.add(new data(name, position, goalsPerSeason, score));

    }

    public void sortScore()
    {

        for(int i = 0; i < playerData.size(); i++)
        {
            for(int j = 0; j < playerData.size() - 1; j++) // j is going to stay 1 block behind i so that it can compare
            {
                data playerBehind = playerData.get(j);
                data playerAhead = playerData.get(j + 1); // cant use i to compare 

                if(playerBehind.getScore() < playerAhead.getScore()) // < for highest first
                {
                    playerData.set(j, playerAhead); //puts playerAhead were playerBehind was
                    playerData.set(j + 1, playerBehind); //puts playerBehind where playerAhead was, swaps the two
                }
            }
        }
    }

    public void sortName()
    {
        for(int i = 0; i < playerData.size(); i++)
        {
            for(int j = 0; j < playerData.size() - 1; j++)
            {
                data playerBehind = playerData.get(j);
                data playerAhead = playerData.get(j + 1);

                if(playerBehind.getName().compareTo(playerAhead.getName()) > 0) // > 0 sorts A-Z || < 0 sorts Z-A
                {
                    playerData.set(j, playerAhead);
                    playerData.set(j + 1, playerBehind);
                }
            }
        }
    }

    public data binarySearchName(String name)
    {
        sortName();

        int low = 0; //lowest index of array
        int high = playerData.size() - 1; //highest index of array

        while(low <= high) //while low is less than or grater than high
        {
            int mid = (low + high) /2; //finds the middle index of the array 
            data midPlayer = playerData.get(mid); //sets midPlayer to the middle index of the name array by using mid as the .get index

            int result = midPlayer.getName().compareToIgnoreCase(name); // get the name of player object and compares to name ur searching for then stores the comparison as a number

            if(result == 0) //names match exactly
            {
                return midPlayer;
            }
            else if(result < 0) // midPlayer's name comes before the searched name, so + 1 to move it up and keep searching
            {
                low = mid + 1;
            }
            else // result > 0 midPlayer's name comes after the search name, so - 1 to move it down and keep searching
            {
                high = mid - 1;
            }
        }

        return null;
    } 


}

