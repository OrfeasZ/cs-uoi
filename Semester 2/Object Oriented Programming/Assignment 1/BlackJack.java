/**
* Assignment01 - BlackJack
* 
* Compiles with JDK 1.7.0
*
* @author: Orfeas-Ioannis Zafeiris (2250) <cs122250>
*/

import java.util.*;
import java.lang.*;

// Optional class
class Utils
{
    public static void ClearConsole()
    {
        String s_OSName = System.getProperty("os.name").toLowerCase();
        if (s_OSName.contains("nix") || s_OSName.contains("nux") || s_OSName.contains("aix"))
        {
            System.out.print("\033[2J");
            return;
        }

        // This is really hacky
        for(int i = 0; i < 200; ++i)
            System.out.println();
    }
}

// Optional class
class CardPrinter
{
    private static int[] m_Cards = new int[13];
    private static List<Integer> m_HandCards = new ArrayList<Integer>();

    private static String[][] m_CardPrints;

    public static void PrintCards()
    {
        List<String[]> s_CardPrints = new ArrayList<String[]>();
        for(int s_Card : m_HandCards)
            s_CardPrints.add(m_CardPrints[s_Card]);

        System.out.println();
        for(int i = 0; i < 6; ++i)
        {
            String s_Line = "\t";
            for(String[] s_Card : s_CardPrints)
                s_Line += s_Card[i] + " ";
            System.out.println(s_Line);
        }
        System.out.println();
    }

    public static void ResetDecks(int p_DeckCount)
    {
        for(int i = 0; i < m_Cards.length; ++i)
            m_Cards[i] = 4 * p_DeckCount;
    }

    public static void ResetCards()
    {
        m_HandCards = new ArrayList<Integer>();
    }

    public static void AddCard(int p_CardPoints)
    {
        if (p_CardPoints == 10)
        {
            Random s_Random = new Random();
            int s_RandInt = s_Random.nextInt(3);
            while(m_Cards[p_CardPoints - 1 + s_RandInt] <= 0)
                s_RandInt = s_Random.nextInt(3);
            p_CardPoints += s_RandInt;
        }

        --m_Cards[p_CardPoints - 1];

        m_HandCards.add(p_CardPoints - 1);
    }

    public static void InitCardPrints()
    {
        m_CardPrints = new String[13][6];
        m_CardPrints[1][0]=" _____ ";m_CardPrints[1][1]="|2    |";m_CardPrints[1][2]="|  &  |";m_CardPrints[1][3]="|     |";m_CardPrints[1][4]="|  &  |";m_CardPrints[1][5]="|____Z|";m_CardPrints[2][0]=" _____ ";m_CardPrints[2][1]="|3    |";m_CardPrints[2][2]="| & & |";m_CardPrints[2][3]="|     |";m_CardPrints[2][4]="|  &  |";m_CardPrints[2][5]="|____E|";m_CardPrints[3][0]=" _____ ";m_CardPrints[3][1]="|4    |";m_CardPrints[3][2]="| & & |";m_CardPrints[3][3]="|     |";m_CardPrints[3][4]="| & & |";m_CardPrints[3][5]="|____h|";m_CardPrints[4][0]=" _____ ";m_CardPrints[4][1]="|5    |";m_CardPrints[4][2]="| & & |";m_CardPrints[4][3]="|  &  |";m_CardPrints[4][4]="| & & |";m_CardPrints[4][5]="|____S|";m_CardPrints[5][0]=" _____ ";m_CardPrints[5][1]="|6    |";m_CardPrints[5][2]="| & & |";m_CardPrints[5][3]="| & & |";m_CardPrints[5][4]="| & & |";m_CardPrints[5][5]="|____9|";m_CardPrints[6][0]=" _____ ";m_CardPrints[6][1]="|7    |";m_CardPrints[6][2]="| & & |";m_CardPrints[6][3]="|& & &|";m_CardPrints[6][4]="| & & |";m_CardPrints[6][5]="|____L|";m_CardPrints[7][0]=" _____ ";m_CardPrints[7][1]="|8    |";m_CardPrints[7][2]="|& & &|";m_CardPrints[7][3]="| & & |";m_CardPrints[7][4]="|& & &|";m_CardPrints[7][5]="|____8|";m_CardPrints[8][0]=" _____ ";m_CardPrints[8][1]="|9    |";m_CardPrints[8][2]="|& & &|";m_CardPrints[8][3]="|& & &|";m_CardPrints[8][4]="|& & &|";m_CardPrints[8][5]="|____6|";m_CardPrints[9][0]=" _____ ";m_CardPrints[9][1]="|10 & |";m_CardPrints[9][2]="|& & &|";m_CardPrints[9][3]="|& & &|";m_CardPrints[9][4]="|& & &|";m_CardPrints[9][5]="|___0I|";m_CardPrints[10][0]=" _____ ";m_CardPrints[10][1]="|J  ww|";m_CardPrints[10][2]="| o {)|";m_CardPrints[10][3]="|o o% |";m_CardPrints[10][4]="| | % |";m_CardPrints[10][5]="|__%%[|";m_CardPrints[11][0]=" _____ ";m_CardPrints[11][1]="|Q  ww|";m_CardPrints[11][2]="| o {(|";m_CardPrints[11][3]="|o o%%|";m_CardPrints[11][4]="| |%%%|";m_CardPrints[11][5]="|_%%%O|";m_CardPrints[12][0]=" _____ ";m_CardPrints[12][1]="|K  WW|";m_CardPrints[12][2]="| o {)|";m_CardPrints[12][3]="|o o%%|";m_CardPrints[12][4]="| |%%%|";m_CardPrints[12][5]="|_%%%>|";m_CardPrints[0][0]=" _____ ";m_CardPrints[0][1]="|A _  |";m_CardPrints[0][2]="| ( ) |";m_CardPrints[0][3]="|(_'_)|";m_CardPrints[0][4]="|  |  |";m_CardPrints[0][5]="|____V|";
    }
}

//

class River
{
    public int[] m_Cards;
    private int[] m_CardValues = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 4 };
    private Random m_Random;
    private int m_DeckCount;

    River()
    {
        m_DeckCount = 1;
        start();
    }

    River(int p_DeckCount)
    {
        m_DeckCount = (p_DeckCount <= 1) ? 1 : p_DeckCount;
        start();
    }

    public void start()
    {
        CardPrinter.ResetDecks(m_DeckCount);

        m_Random = new Random();
        m_Cards = new int[10];

        for(int i = 0; i < m_CardValues.length; ++i)
            m_Cards[i] = (i <= 0 ? 0 : m_Cards[i-1]) + (4 * m_DeckCount * m_CardValues[i]);
    }

    public int nextCard()
    {
        // Check if we need to shuffle the deck(s)
        if (m_Cards[9] < 11 * m_DeckCount)
            start();

        int x = m_Random.nextInt(m_Cards[9]) + 1;

        int s_Pos = 9;
        for(int i = 0; i < m_Cards.length; ++i)
            if (m_Cards[i] > 0 && m_Cards[i] <= x)
                s_Pos = i;

        for(int i = s_Pos; i < m_Cards.length; ++i)
            --m_Cards[i];

        return s_Pos + 1;
    }
}

class Hand
{
    private List<Integer> m_Points = new ArrayList<Integer>();
    private boolean m_DealerHand;

    Hand(boolean p_Dealer)
    {
        m_DealerHand = p_Dealer;
    }

    public int getPoints()
    {
        // Soft points

        int s_Points = 0;
        for(int s_Card : m_Points)
            s_Points += s_Card;

        if (m_Points.contains(1) && (s_Points + 10) <= 21)
            s_Points += 10;

        return s_Points;
    }

    public int getRealPoints()
    {
        int s_Points = 0;
        for(int s_Card : m_Points)
            s_Points += s_Card;

        return s_Points;
    }

    public void printPoints()
    {
        CardPrinter.PrintCards();
        System.out.println("Points: " + getPoints() + (getPoints() != getRealPoints() ? " or " + getRealPoints() : ""));
    }

    public void addPoints(int p_Points)
    {
        if (!m_DealerHand)
            CardPrinter.AddCard(p_Points);
        m_Points.add(p_Points);
    }

    public void resetPoints()
    {
        if (!m_DealerHand)
            CardPrinter.ResetCards();
        m_Points.clear();
    }
}

class Player
{
    private int m_InitialAmount = 0;
    private int m_CurrentAmount = 0;
    private int m_CurrentBet = 0;
    private Hand m_Hand = new Hand(false);
    private boolean m_FirstTime = true;

    Player()
    {
        Scanner s_Scanner = new Scanner(System.in);
        System.out.println("Enter your initial amount of cash:");

        while((m_InitialAmount = s_Scanner.nextInt()) <= 0)
        {
            System.out.println("Invalid amount.\n");
            System.out.println("Enter your initial amount of cash:");
        }

        m_CurrentAmount = m_InitialAmount;
    }

    public void placeBet()
    {
        Scanner s_Scanner = new Scanner(System.in);

        System.out.println("Cash: " + m_CurrentAmount + "EUR");
        System.out.println((m_CurrentAmount >= m_InitialAmount) ? "Earnings: " + (m_CurrentAmount - m_InitialAmount) + "EUR" : "Losses: " + (m_InitialAmount - m_CurrentAmount) + "EUR");
        System.out.println("Enter your bet:");

        int s_Bet;
        while ((s_Bet = s_Scanner.nextInt()) <= 0 || s_Bet > m_CurrentAmount)
        {
            System.out.println("Your bet needs to be at least 1EUR and less or equal to your remaining cash.\n");
            System.out.println("Enter your bet:");
        }

        m_CurrentBet = s_Bet;
        m_CurrentAmount -= s_Bet;
    }

    public void endRound(boolean p_Won)
    {
        m_Hand.resetPoints();

        if (p_Won)
        {
            System.out.println("You win!");
            m_CurrentAmount += (2 * m_CurrentBet);
            m_CurrentBet = 0;
            return;
        }

        System.out.println("You lose.");
        m_CurrentBet = 0;
    }

    private void printBet()
    {
        System.out.println("Current bet: " + m_CurrentBet + "EUR");
    }

    public boolean play(River p_River)
    {
        if (m_CurrentAmount <= 0)
            return false;

        Scanner s_Scanner = new Scanner(System.in);

        if (!m_FirstTime)
        {
            System.out.println();
            System.out.println("What do you want to do? (P)lay or (S)top?");

            String s_FirstChoice;
            while(!(s_FirstChoice = s_Scanner.nextLine()).equalsIgnoreCase("p") && !s_FirstChoice.equalsIgnoreCase("s"))
            {
                System.out.println("Invalid option.\n");
                System.out.println("What do you want to do? (P)lay or (S)top?");
            }

            if (s_FirstChoice.equalsIgnoreCase("s"))
                return false;
        }
        else
        {
            m_FirstTime = false;
        }

        Utils.ClearConsole();

        // Place a bet
        placeBet();

        Utils.ClearConsole();

        // Get a card
        m_Hand.addPoints(p_River.nextCard());

        // Give the dealer a card
        BlackJack.initDealer();

        // And get a second card
        m_Hand.addPoints(p_River.nextCard());

        m_Hand.printPoints();
        printBet();

        // Check if we did a BlackJack
        if (m_Hand.getPoints() == 21)
        {
            endRound(true);
            return true;
        }

        boolean s_CanDouble = m_CurrentAmount >= m_CurrentBet;
        System.out.println("What do you want to do?" + (s_CanDouble ? " (D)ouble," : "") + " (H)it or (S)tand?");

        String s_Choice;
        while(!(s_Choice = s_Scanner.nextLine()).equalsIgnoreCase("h") &&
                !s_Choice.equalsIgnoreCase("s") &&
                ((!s_CanDouble && s_Choice.equalsIgnoreCase("d")) ||
                (s_CanDouble && !s_Choice.equalsIgnoreCase("d"))))
        {
            Utils.ClearConsole();

            m_Hand.printPoints();
            printBet();

            System.out.println("Invalid option.\n");
            System.out.println("What do you want to do?" + (s_CanDouble ? " (D)ouble," : "") + " (H)it or (S)tand?");
        }

        if (s_Choice.equalsIgnoreCase("d"))
        {
            // Double the bet and only give one more card
            m_CurrentAmount -= m_CurrentBet;
            m_CurrentBet *= 2;
            m_Hand.addPoints(p_River.nextCard());

            Utils.ClearConsole();

            m_Hand.printPoints();
            printBet();
        }
        else if (s_Choice.equalsIgnoreCase("h"))
        {
            // Keep playing
            while(true)
            {
                m_Hand.addPoints(p_River.nextCard());

                Utils.ClearConsole();

                m_Hand.printPoints();
                printBet();

                if (m_Hand.getPoints() >= 21)
                    break;

                System.out.println("What do you want to do? (H)it or (S)tand?");

                while(!(s_Choice = s_Scanner.nextLine()).equalsIgnoreCase("h") && !s_Choice.equalsIgnoreCase("s"))
                {
                    Utils.ClearConsole();

                    m_Hand.printPoints();
                    printBet();

                    System.out.println("Invalid option.\n");
                    System.out.println("What do you want to do? (H)it or (S)tand?");
                }

                if (s_Choice.equalsIgnoreCase("s"))
                    break;
            }
        }

        if (m_Hand.getPoints() > 21)
        {
            endRound(false);
            return true;
        }

        // Dealer plays
        int s_DealerPoints = BlackJack.playDealer();

        System.out.println();
        System.out.println("Dealer got '" + s_DealerPoints + "'.");

        if (s_DealerPoints > 21) {
            endRound(true);
            return true;
        }

        if (m_Hand.getPoints() <= s_DealerPoints) {
            endRound(false);
            return true;
        }

        endRound(true);
        return true;
    }

    public int getPayout()
    {
        return m_CurrentAmount - m_InitialAmount;
    }
}

class BlackJack
{
    private static River m_River;
    private static Hand m_DealerHand = new Hand(true);

    public static void initDealer()
    {
        m_DealerHand.resetPoints();
        m_DealerHand.addPoints(m_River.nextCard());
    }

    public static int playDealer()
    {
        while(m_DealerHand.getPoints() < 17) {
            m_DealerHand.addPoints(m_River.nextCard());
        }

        return m_DealerHand.getPoints();
    }

    public static void main(String[] args)
    {
        CardPrinter.InitCardPrints();

        Utils.ClearConsole();

        Scanner s_Scanner = new Scanner(System.in);

        System.out.println("Enter the desired amount of decks:");

        int s_Decks;
        while((s_Decks = s_Scanner.nextInt()) <= 0)
        {
            System.out.println("The amount of decks must be a positive number.\n");
            System.out.println("Enter the desired amount of decks:");
        }

        System.out.println();

        m_River = new River(s_Decks);

        Player s_Player = new Player();

        while(s_Player.play(m_River)) {
            // Keep playing
        }

        Utils.ClearConsole();
        System.out.println("Payout: " + s_Player.getPayout() + "EUR");
        System.out.println("Thank you for playing!");
    }
}
