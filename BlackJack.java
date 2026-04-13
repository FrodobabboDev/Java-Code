/*
class Card
 - suit
 - rank (hierarchy)
 - value
class Deck
 - ArrayList of Cards
 - Shuffle
 - deal
class Player
 - name
 - hand
 - chip balance
 - bet
class Dealer
 - extends Player
 - card logic
class Hand
 - calculates total
 - Ace flipping (1 vs 11)
class Main (BlackJack)
 - entry point
 - setup

 */

import java.util.*;

public class BlackJack
{
    Deck deck;
    Dealer dealer;
    private ArrayList<Player> players;

    public BlackJack() //constructor
    {
        Scanner input = new Scanner(System.in);
        dealer = new Dealer("Dealer"); //create a new dealer

        players = new ArrayList<>(); //creates a new empty ArrayList for players

        System.out.print("How many players: ");
        int playerAmount = input.nextInt();
        input.nextLine(); //gets rid of buffer

        for(int i = 0; i < playerAmount; i ++)
        {
            System.out.print("Enter Player Name: ");
            String name = input.nextLine();
            System.out.print("Starting Chip Balance: ");
            int chipBalance = input.nextInt();
            input.nextLine();
            players.add(new Player(name, chipBalance));
        }

        deck = new Deck();
        deck.shuffle();
    }

    public void startRound()
    {
        // ask each player to bet before cards are dealt
        for(int i = 0; i < players.size(); i++)
        {
            Scanner input = new Scanner(System.in);
            Player current = players.get(i);
            System.out.println("-------------\n" + current.getName() + " - Chip Balance: " + current.getChipBalance());
            System.out.print("\n-------------\nPlace your bet: ");
            int bet = input.nextInt();
            current.placeBet(bet);
        }

        //clears everyones hand dealer + player(s)
        dealer.clearHand();
        for(int i = 0; i < players.size(); i ++)
        {
            players.get(i).clearHand();
        }

        //deals 2 cards to each player
        for(int i = 0; i < players.size(); i++)
        {
            players.get(i).addCard(deck.deal()); //deck.deal() calls the deal method ( deal method returns only the top card and that is it)
            players.get(i).addCard(deck.deal()); //calls deal again to return the next top card giving the player two cards from the same deck
        }

        //deals two cards to the dealer
        dealer.addCard(deck.deal());
        dealer.addCard(deck.deal());

        //show dealers hand with first card hidden
        dealer.displayHand();

        //each player rotates turn
        for(int i = 0; i < players.size(); i++)
        {
            playerTurn(players.get(i));
        }

        //dealer plays
        dealerTurn();

        //figures out who won
        determineWinner();
    }

    public void playerTurn(Player player)
    {
        Scanner input = new Scanner(System.in);
        //gets current players hand
        player.displayHand();

        boolean playerStand = false;

        while(playerStand == false && player.getHandTotal() <= 21)
        {
            System.out.println("1. Hit");
            System.out.println("2. Stand");
            System.out.println("\nEnter choice: ");
            int choice = input.nextInt();

            if(choice == 1) //hit
            {
                player.addCard(deck.deal()); //adds a card to the player
                player.displayHand();

                if(player.getHandTotal() > 21)
                {
                    System.out.println("-------------");
                    System.out.println("BUST!");
                    break;
                }
            }
            else if(choice == 2) //stand
            {
                playerStand = true;
            }
        }
    }

    public void dealerTurn()
    {
        dealer.revealCard(); //reveals the hidden card
        dealer.displayHand();

        while(dealer.dealerAI())
        {
            dealer.addCard(deck.deal());
            dealer.displayHand();

            if(dealer.getHandTotal() > 21)
            {
                System.out.println("\n-------------");
                System.out.println("Dealer Busts!");
                break;
            }
        }
    }

    public void determineWinner()
    {
        int dealerTotal = dealer.getHandTotal();

        for(int i = 0; i < players.size(); i++) //loops through each player
        {
            Player current = players.get(i);
            int playerTotal = players.get(i).getHandTotal();

            System.out.println("\n-------------");
            System.out.println("\nName: " + current.getName() + "\nTotal: " + current.getHandTotal()); //prints players name and their total
            System.out.println("Dealer Total: " + dealer.getHandTotal());
            System.out.println("\n-------------");

            if(current.getHandTotal() > 21)
            {
                System.out.println("\n" +current.getName() + " Busts! " + current.getName() + " loses");
            }
            else if(dealer.getHandTotal() > 21)
            {
                System.out.println( "\nDealer Busts! " + current.getName() + " wins!");
                current.addChips(current.getCurrentBet() * 2);
            }
            else if(current.getHandTotal() > dealer.getHandTotal())
            {
                System.out.println("\n" + current.getName() + " Wins!");
                current.addChips(current.getCurrentBet() * 2);
            }
            else if(current.getHandTotal() < dealer.getHandTotal())
            {
                System.out.println("\nDealer Wins! " + current.getName() + " loses");
            }
            else 
            {
                System.out.println("\nPush, its a tie!");
                current.addChips(current.getCurrentBet());
            }
        }
    }


    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);

        int choice = 0;

        while(choice != 2)
        {
            System.out.println("\n--BlackJack--");
            System.out.println("1. Play");
            System.out.println("2. Quit");
            System.out.print("\nEnter Choice: ");
            choice = input.nextInt();
            System.out.println("-------------");

            switch (choice) 
            {
                case 1:
                    BlackJack game = new BlackJack();
                    game.startRound();
                    break;
                case 2:
                    System.out.println("Goodbye!");
                    break;
                default:
                    throw new AssertionError();
            }


            

        }
        
        
        System.out.println("Goodbye!");
    }
}

//makes suit set only to these values, impossible to change
enum Suit
{
    HEARTS, DIAMONDS, CLUBS, SPADES
}

//makes rank set only to these values, impossible to change
enum Rank 
{
    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
}

class Card
{
    private Suit suit;
    private Rank rank;
    private int value;

    public Card(Suit suit, Rank rank) // constructor
    {
        this.suit = suit;
        this.rank = rank;
        this.value = assignValue(rank); //assigns value to the switch statement assignment value in assignRank
    }

    private int assignValue(Rank rank) //sets rank to its corresponding value using switch statement to filter through
    {
        switch(rank)
        {
            case TWO: return 2;
            case THREE: return 3;
            case FOUR: return 4;
            case FIVE: return 5;
            case SIX: return 6;
            case SEVEN: return 7;
            case EIGHT: return 8;
            case NINE: return 9;
            case TEN:
            case JACK:
            case QUEEN:
            case KING: return 10;
            case ACE: return 11; // default ace to 11 flip to 1 later
            default: return 0;
        }
    }

    public Suit getSuit() { return suit;}
    public Rank getRank() { return rank;}
    public int getValue() { return value;}
    public void setValue(int value) { this.value = value;} //needed for ace flipping

    //toString 
    public String toString()
    {
        return rank + " of " + suit;
    }

}

class Deck
{
    private ArrayList<Card> deckOfCards; //arraylist of cards

    public Deck() //constructor
    {
        deckOfCards = new ArrayList<>();

        //loops through HEARTS, DIAMONDS, CLUBS, SPADES
        for(Suit suit : Suit.values()) //.values() returns an array of every constant in the enum, and pulls them out one by one
        {
            //loops through ONE,TWO,THREE...JACK,QUEEN etc. 
            for(Rank rank : Rank.values())
            {
                deckOfCards.add(new Card(suit, rank)); // create and add card to deckOfCards
            }
        }
    }

    public void shuffle() //shuffles dec
    {
        Collections.shuffle(deckOfCards); //built in the class
    }

    public Card deal() //set to Card ( not void) because the method sends back a Card
    {
        return deckOfCards.remove(0); //removes card at index 0 and returns it to whoever called the method
    }
}

class Player
{
    private String name;
    private ArrayList<Card> hand; //creates array using Card info called hand
    private int chipBalance;
    private int currentBet;

    public Player(String name, int chipBalance) //constructor
    {
        this.name = name;
        this.chipBalance = chipBalance;
        hand = new ArrayList<>(); //initializes hand as empty ArrayList
        this.currentBet = 0;
    }

    public String getName() { return name;}
    public int getChipBalance() { return chipBalance;}
    public int getCurrentBet() { return currentBet;}
    public ArrayList<Card> getHand() { return hand;}

    public void placeBet(int amount)
    {

        if(amount > chipBalance)
        {
            System.out.println("Not enough credits");
            return;
        }

        currentBet = amount;
        chipBalance -= amount;
    }

    public void addCard(Card card) //adds a new card to the deck
    {
        hand.add(card);
    }

    public void clearHand()
    {
        hand.clear();
    }

    public int getHandTotal() //int because it returns an int 
    {
        int total = 0; //start with a total of 0 cards
        for(int i = 0; i < hand.size(); i++)
        {
            total += hand.get(i).getValue(); //total += hand.get card at i and .get its value
        }

        if(total > 21)
        {
            for(int i = 0; i < hand.size(); i++)
            {
                if(hand.get(i).getRank() == Rank.ACE && hand.get(i).getValue() == 11)
                {
                    hand.get(i).setValue(1); //flips ace from 11 to 1
                    total -= 10; // adjusts the total by 10
                    break; //only flip one ace at a time and the recheck
                }
            }
        }
        return total; //return the total to sender
    }

    public void addChips(int amount)
    {
        chipBalance += amount;
    }


    public void displayHand()
    {
        System.out.println("\n -- " + name + " -- ");

        for(int i =0; i < hand.size(); i++)
        {
            System.out.println("- " + hand.get(i)); //you want to call the object not the class
        }
        System.out.println("\n-------------");
        System.out.println("Total: " + getHandTotal());

    }
}

class Dealer extends Player // "Dealer is a player", extends: inherits behavior and data
{
    private boolean hideFirstCard;

    public Dealer(String name) //constuctor
    {
        super(name, 1000000); // super: calls the player constructor and passes the name and chip balance up to it HAS TO BE FIRST LINE OF CONSTRUCTOR
        hideFirstCard = true;
    }

    public void revealCard() //reveals the dealer first card
    {
        hideFirstCard = false;
    }

    public void displayHand() // displays the hound but doesnt show the hidden card unless the boolean value is false
    {
        System.out.println("\n -- " + getName() + " -- "); //gets dealer name

        if(hideFirstCard == true)
        {
            System.out.println("- Hidden Card");

            for(int i = 1; i < getHand().size(); i ++)
            {
                System.out.println("- " + getHand().get(i));
            }
        }
        else
        {
            for(int i = 0; i < getHand().size(); i ++)
            {
                System.out.println("- " + getHand().get(i));
            }
        }
        
    }

    public boolean  dealerAI()
    {
        if(getHandTotal() < 17)
        {
            return true; //signals to the game loop that the dealer wants to hit
        }
        return false; // signals to stand
    }

    
}

