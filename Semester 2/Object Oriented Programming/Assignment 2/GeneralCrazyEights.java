/**
 * Assignment02 - GeneralCrazyEights
 *
 * Compiles with JDK 1.7.0
 *
 * Note: This is meant to be played on Unix based systems (due to ANSI color code support)
 * Note: I just copy/pasted all classes because I was too lazy to split them into separate files
 *
 * @author Orfeas-Ioannis Zafeiris (2250) <cs122250>
 */

import java.util.*;
import java.lang.*;

// Optional class
class Utils
{
    public static boolean IsUnix()
    {
        String s_OSName = System.getProperty("os.name").toLowerCase();
        return s_OSName.contains("nix") || s_OSName.contains("nux") || s_OSName.contains("aix");
    }

    public static void ClearConsole()
    {
        if (IsUnix())
        {
            System.out.print("\033[2J");
            return;
        }

        // This is really hacky
        for(int i = 0; i < 200; ++i)
            System.out.println();
    }

    public static void Sleep(long p_Milliseconds)
    {
        try { Thread.sleep(p_Milliseconds); } catch (InterruptedException ignored) {}
    }
}

// Optional class
class ConsoleColor
{
    public static String Reset = "\u001B[0m";
    public static String Black = "\u001B[30m";
    public static String Red = "\u001B[31m";
    public static String Green = "\u001B[32m";
    public static String Yellow = "\u001B[33m";
    public static String Blue = "\u001B[34m";
    public static String Purple = "\u001B[35m";
    public static String Cyan = "\u001B[36m";
    public static String White = "\u001B[37m";
}

// Optional class
class CardPrinter
{
    private static String[][] m_CardPrints;

    private static char[] m_CardSuits = new char[] { 'S', 'H', 'C', 'D' };

    public static String[] GetCardPrint(Card p_Card)
    {
        String[] s_Print = new String[6];
        for(int i = 0; i < 6; ++i)
        {
            String s_Line = "";
            s_Line += (p_Card.GetColor() == Card.Color.Red ? ConsoleColor.Red : "");
            s_Line += m_CardPrints[p_Card.GetID()][i] + ConsoleColor.Reset + " ";

            // Hack for non-Unix systems
            if (!Utils.IsUnix())
                s_Line = s_Line.replace(ConsoleColor.Red, "").replace(ConsoleColor.Reset, "");

            s_Print[i] = s_Line;
        }

        int s_SuitValue = (p_Card.GetCustomSuit() == null ? p_Card.GetSuit().Value() : p_Card.GetCustomSuit().Value());

        System.out.println(s_SuitValue);
        StringBuilder s_Builder = new StringBuilder(s_Print[0]);
        s_Builder.setCharAt((Utils.IsUnix() && p_Card.GetColor() == Card.Color.Red ? 5 : 0), m_CardSuits[s_SuitValue]);
        s_Print[0] = s_Builder.toString();

        return s_Print;
    }

    public static void InitCardPrints()
    {
        m_CardPrints = new String[13][6];
        m_CardPrints[1][0]=" _____ ";m_CardPrints[1][1]="|2    |";m_CardPrints[1][2]="|  &  |";m_CardPrints[1][3]="|     |";m_CardPrints[1][4]="|  &  |";m_CardPrints[1][5]="|____Z|";m_CardPrints[2][0]=" _____ ";m_CardPrints[2][1]="|3    |";m_CardPrints[2][2]="| & & |";m_CardPrints[2][3]="|     |";m_CardPrints[2][4]="|  &  |";m_CardPrints[2][5]="|____E|";m_CardPrints[3][0]=" _____ ";m_CardPrints[3][1]="|4    |";m_CardPrints[3][2]="| & & |";m_CardPrints[3][3]="|     |";m_CardPrints[3][4]="| & & |";m_CardPrints[3][5]="|____h|";m_CardPrints[4][0]=" _____ ";m_CardPrints[4][1]="|5    |";m_CardPrints[4][2]="| & & |";m_CardPrints[4][3]="|  &  |";m_CardPrints[4][4]="| & & |";m_CardPrints[4][5]="|____S|";m_CardPrints[5][0]=" _____ ";m_CardPrints[5][1]="|6    |";m_CardPrints[5][2]="| & & |";m_CardPrints[5][3]="| & & |";m_CardPrints[5][4]="| & & |";m_CardPrints[5][5]="|____9|";m_CardPrints[6][0]=" _____ ";m_CardPrints[6][1]="|7    |";m_CardPrints[6][2]="| & & |";m_CardPrints[6][3]="|& & &|";m_CardPrints[6][4]="| & & |";m_CardPrints[6][5]="|____L|";m_CardPrints[7][0]=" _____ ";m_CardPrints[7][1]="|8    |";m_CardPrints[7][2]="|& & &|";m_CardPrints[7][3]="| & & |";m_CardPrints[7][4]="|& & &|";m_CardPrints[7][5]="|____8|";m_CardPrints[8][0]=" _____ ";m_CardPrints[8][1]="|9    |";m_CardPrints[8][2]="|& & &|";m_CardPrints[8][3]="|& & &|";m_CardPrints[8][4]="|& & &|";m_CardPrints[8][5]="|____6|";m_CardPrints[9][0]=" _____ ";m_CardPrints[9][1]="|10 & |";m_CardPrints[9][2]="|& & &|";m_CardPrints[9][3]="|& & &|";m_CardPrints[9][4]="|& & &|";m_CardPrints[9][5]="|___0I|";m_CardPrints[10][0]=" _____ ";m_CardPrints[10][1]="|J  ww|";m_CardPrints[10][2]="| o {)|";m_CardPrints[10][3]="|o o% |";m_CardPrints[10][4]="| | % |";m_CardPrints[10][5]="|__%%[|";m_CardPrints[11][0]=" _____ ";m_CardPrints[11][1]="|Q  ww|";m_CardPrints[11][2]="| o {(|";m_CardPrints[11][3]="|o o%%|";m_CardPrints[11][4]="| |%%%|";m_CardPrints[11][5]="|_%%%O|";m_CardPrints[12][0]=" _____ ";m_CardPrints[12][1]="|K  WW|";m_CardPrints[12][2]="| o {)|";m_CardPrints[12][3]="|o o%%|";m_CardPrints[12][4]="| |%%%|";m_CardPrints[12][5]="|_%%%>|";m_CardPrints[0][0]=" _____ ";m_CardPrints[0][1]="|A _  |";m_CardPrints[0][2]="| ( ) |";m_CardPrints[0][3]="|(_'_)|";m_CardPrints[0][4]="|  |  |";m_CardPrints[0][5]="|____V|";
    }

    public static void PrintCards(ArrayList<Card> p_Cards)
    {
        for (int i = 0; i < 6; ++i)
        {
            String s_Line = "\t";
            for(Card s_Card : p_Cards)
                s_Line += s_Card.GetPrint()[i];

            System.out.println(s_Line);
        }

        String s_IndexLine = "\t";
        for (int i = 1; i <= p_Cards.size(); ++i)
        {
            s_IndexLine += " (" + i + ")    ";
            if (i > 9)
                s_IndexLine = s_IndexLine.substring(0, s_IndexLine.length() - 1);
        }
        System.out.println(s_IndexLine);

        System.out.println();
    }
}

//

class Card
{
    public enum Color { Black, Red }

    public enum Suit {
        Spades(0), Hearts(1), Clubs(2), Diamonds(3);

        private final int m_Value;
        private Suit(int p_Value) { m_Value = p_Value; }
        public int Value() { return m_Value; }

        public String toString()
        {
            // Automatically casting enum values as Strings in a 'modern' language? That's completely unheard of!
            // Now excuse me while I throw my PC out the window due to the always increasing stupidity of Java...

            switch (m_Value)
            {
                case 0:
                    return "Spades";
                case 1:
                    return "Hearts";
                case 2:
                    return "Clubs";
                case 3:
                    return "Diamonds";
                default:
                    return "";
            }
        }
    }

    public enum Type { Normal, Joker, GetTwo }

    private int m_ID;
    private Color m_Color;
    private Type m_Type;
    private String[] m_Print;

    private Suit m_CustomSuit = null;
    private Suit m_Suit;

    Card(int p_ID, Color p_Color, int p_Suit)
    {
        m_ID = p_ID;
        m_Color = p_Color;
        m_Type = Type.Normal;

        // Java is stupid like that and doesn't allow us to cast integers to enums and vice versa.
        // And then people ask me why I hate it...

        switch (p_Suit)
        {
            case 0: m_Suit = Suit.Spades; break;
            case 1: m_Suit = Suit.Hearts; break;
            case 2: m_Suit = Suit.Clubs; break;
            case 3: m_Suit = Suit.Diamonds; break;
        }

        m_Print = CardPrinter.GetCardPrint(this);

        if (m_ID == 7)
            m_Type = Type.Joker;
        else if (m_ID == 6)
            m_Type = Type.GetTwo;
    }

    public String toString()
    {
        String[] m_CardChars = new String[] { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
        return m_CardChars[m_ID];
    }

    public String GetArt()
    {
        String s_Print = "\n";
        for (String s_PrintLine : m_Print)
            s_Print += "\t" + s_PrintLine + "\n";
        return s_Print;
    }

    public void SetCustomSuit(int p_Suit)
    {
        // I would start bashing Java again but, heck, it's not even worth that.

        switch (p_Suit)
        {
            case 0: m_CustomSuit = Suit.Spades; break;
            case 1: m_CustomSuit = Suit.Hearts; break;
            case 2: m_CustomSuit = Suit.Clubs; break;
            case 3: m_CustomSuit = Suit.Diamonds; break;
        }

        if (m_CustomSuit == Suit.Hearts || m_CustomSuit == Suit.Diamonds)
            m_Color = Color.Red;
        else
            m_Color = Color.Black;

        // Recalculate our ~artistic~ print
        m_Print = CardPrinter.GetCardPrint(this);
    }

    public Suit GetCustomSuit() { return m_CustomSuit; }

    public int GetID() { return m_ID; }

    public Color GetColor() { return m_Color; }

    public String[] GetPrint() { return m_Print; }

    public Type GetType() { return m_Type; }

    public Suit GetSuit() { return m_Suit; }
}

class Deck
{
    ArrayList<ArrayList<Card>> m_Cards = new ArrayList<ArrayList<Card>>();

    Deck()
    {
        // Initialize the deck
        for(int i = 0; i < 13; ++i)
        {
            ArrayList<Card> s_Cards = new ArrayList<Card>();
            for(int j = 0; j < 4; ++j)
            {
                Card s_Card = new Card(i, (j % 2 == 0 ? Card.Color.Black : Card.Color.Red), j);
                s_Cards.add(s_Card);
            }
            m_Cards.add(s_Cards);
        }
    }

    public Card GetRandomCard()
    {
        Random s_Random = new Random();

        int s_CardIndex = s_Random.nextInt(13);
        while (m_Cards.get(s_CardIndex).size() == 0)
            s_CardIndex = s_Random.nextInt(13);

        int s_SpecCardIndex = s_Random.nextInt(m_Cards.get(s_CardIndex).size());
        Card s_Card = m_Cards.get(s_CardIndex).get(s_SpecCardIndex);
        m_Cards.get(s_CardIndex).remove(s_SpecCardIndex);

        return s_Card;
    }

    public void AddCards(ArrayList<Card> p_Cards)
    {
        for (Card s_Card : p_Cards)
        {
            m_Cards.get(s_Card.GetID()).add(s_Card);
        }
    }
}

class NormalPlayer extends GeneralPlayer
{
    NormalPlayer(String p_Name)
    {
        super(p_Name, Type.Normal);
    }

    public void PrintHand()
    {
        System.out.println("Your hand:");
        CardPrinter.PrintCards(m_HandCards);
        System.out.println();
    }

    private int ReadOption(ArrayList<Integer> p_ViableOptions, Scanner p_Scanner)
    {
        String s_Option = p_Scanner.nextLine();

        int s_OptionVal = -1;

        try
        {
            s_OptionVal = Integer.parseInt(s_Option);
        }
        catch(NumberFormatException ignored) {}

        if (s_OptionVal == -1 || p_ViableOptions.indexOf(s_OptionVal - 1) == -1)
        {
            m_Table.PrintRoundInfo(this);
            PrintHand();
            PrintViableOptions(p_ViableOptions);
            return ReadOption(p_ViableOptions, p_Scanner);
        }

        return s_OptionVal - 1;
    }

    private int ReadSuit(Scanner p_Scanner)
    {
        String[] s_ValidSuits = new String[] { "s", "h", "c", "d" };
        String s_Option = p_Scanner.nextLine().toLowerCase();

        if (Arrays.asList(s_ValidSuits).indexOf(s_Option) == -1)
        {
            m_Table.PrintRoundInfo(this);
            PrintHand();
            System.out.println("You are throwing a Joker. Select a suit: (S)pades, (H)earts, (C)lubs or (D)iamonds");
            return ReadSuit(p_Scanner);
        }

        return Arrays.asList(s_ValidSuits).indexOf(s_Option);
    }

    private void PrintViableOptions(ArrayList<Integer> p_ViableOptions)
    {
        System.out.print("Please select an option. Viable options: ");

        for (int i = 0; i < p_ViableOptions.size(); ++i)
        {
            String s_Option = (i == 0 ? "" : ", ");
            s_Option += (p_ViableOptions.get(i) + 1);
            System.out.print(s_Option);
        }
        System.out.println();
    }

    public boolean PlayRound()
    {
        PrintHand();

        // Get the viable options for the cards we hold
        ArrayList<Integer> s_ViableOptions = GetViableOptions();

        if (m_Table.GetLastCard().GetType() == Card.Type.GetTwo)
        {
            // If the last card is a '7' and we have one just throw it
            if (FindCardByID(6) != null)
            {
                System.out.println("You have a 7; throwing it...");
                Utils.Sleep(3000);
                m_Table.AddCardToTable(FindCardByID(6));
                m_HandCards.remove(FindCardByID(6));
                return HasWon();
            }

            // Otherwise pull
            for (int i = 0; i < m_Table.GetGetTwoCount() * 2; ++i)
                PullCard();

            m_Table.PrintRoundInfo(this);
            PrintHand();

            // And recalculate our options
            s_ViableOptions = GetViableOptions();
        }

        // If we don't have any options just pull a card
        while(s_ViableOptions.size() == 0)
        {
            System.out.println("There are no viable options. Pulling a card...");
            Utils.Sleep(2000);
            PullCard();
            m_Table.PrintRoundInfo(this);
            PrintHand();
            s_ViableOptions = GetViableOptions();
        }

        // Print out the options we have
        PrintViableOptions(s_ViableOptions);

        Scanner s_Scanner = new Scanner(System.in);

        // And wait till we select one
        int s_Option = ReadOption(s_ViableOptions, s_Scanner);

        // If our selected option is a Joker then wait till we select a custom Suit.
        if (m_HandCards.get(s_Option).GetType() == Card.Type.Joker)
        {
            m_Table.PrintRoundInfo(this);
            PrintHand();
            System.out.println("You are throwing a Joker. Select a suit: (S)pades, (H)earts, (C)lubs or (D)iamonds");
            m_HandCards.get(s_Option).SetCustomSuit(ReadSuit(s_Scanner));
        }

        // And then just add the card to the table and remove it from our hand
        m_Table.AddCardToTable(m_HandCards.get(s_Option));
        m_HandCards.remove(s_Option);

        return HasWon();
    }

}

class ComputerPlayer extends GeneralPlayer
{
    ComputerPlayer(String p_Name)
    {
        super(p_Name, Type.AI);
    }

    private int GetCardCountForSuit(Card.Suit p_Suit)
    {
        int s_Count = 0;

        for (Card s_Card : m_HandCards)
            if (s_Card.GetSuit() == p_Suit)
                ++s_Count;

        return s_Count;
    }

    private int GetCardCountForID(int p_ID)
    {
        int s_Count = 0;

        for (Card s_Card : m_HandCards)
            if (s_Card.GetID() == p_ID)
                ++s_Count;

        return s_Count;
    }

    private void PrintAILines()
    {
        for (int i = 0; i < 10; ++i)
            System.out.println();
    }

    public boolean PlayRound()
    {
        PrintAILines();

        // Get the viable options for the cards we hold
        ArrayList<Integer> s_ViableOptions = GetViableOptions();

        if (m_Table.GetLastCard().GetType() == Card.Type.GetTwo)
        {
            // If the last card is a '7' and we have one just throw it
            if (FindCardByID(6) != null)
            {
                System.out.println(m_Name + " is throwing a 7 of " + FindCardByID(6).GetSuit() + "...");
                Utils.Sleep(3000);
                m_Table.AddCardToTable(FindCardByID(6));
                m_HandCards.remove(FindCardByID(6));
                return HasWon();
            }

            // Otherwise pull
            for (int i = 0; i < m_Table.GetGetTwoCount() * 2; ++i)
                PullCard();

            // And recalculate our options
            s_ViableOptions = GetViableOptions();
        }

        // If we don't have any options just pull a card
        while(s_ViableOptions.size() == 0)
        {
            System.out.println(m_Name + " is pulling a card...");
            Utils.Sleep(2000);
            PullCard();
            m_Table.PrintRoundInfo(this);
            PrintAILines();
            s_ViableOptions = GetViableOptions();
        }

        // If we have just one option throw that
        if (s_ViableOptions.size() == 1)
        {
            System.out.println(m_Name + " is throwing a " + m_HandCards.get(s_ViableOptions.get(0)) + " of " + m_HandCards.get(s_ViableOptions.get(0)).GetSuit() + "...");
            Utils.Sleep(3000);
            m_Table.AddCardToTable(m_HandCards.get(s_ViableOptions.get(0)));
            m_HandCards.remove(m_HandCards.get(s_ViableOptions.get(0)));
            return HasWon();
        }

        // Figure out what's the best option
        ArrayList<Integer> s_CardChances = new ArrayList<Integer>();

        for (int p_Option : s_ViableOptions)
        {
            Card s_Card = m_HandCards.get(p_Option);
            s_CardChances.add(GetCardCountForSuit(s_Card.GetSuit()) >= GetCardCountForID(s_Card.GetID()) ? GetCardCountForSuit(s_Card.GetSuit()) : GetCardCountForID(s_Card.GetID()));
        }

        int m_BestOption = 0;
        for (int i = 1; i < s_CardChances.size(); ++i)
            if (s_CardChances.get(i) > s_CardChances.get(i - 1))
                m_BestOption = i;

        int s_Option = s_ViableOptions.get(m_BestOption);

        // And then throw it
        System.out.println(m_Name + " is throwing a " + m_HandCards.get(s_Option) + " of " + m_HandCards.get(s_Option).GetSuit() + "...");
        Utils.Sleep(3000);
        m_Table.AddCardToTable(m_HandCards.get(s_Option));
        m_HandCards.remove(m_HandCards.get(s_Option));

        return HasWon();
    }

}

class GeneralPlayer
{
    public enum Type { Normal, AI }

    protected String m_Name;
    protected Table m_Table;
    protected Type m_Type;

    ArrayList<Card> m_HandCards = new ArrayList<Card>();

    GeneralPlayer(String p_Name, Type p_Type)
    {
        m_Name = p_Name;
        m_Type = p_Type;
    }

    public void GiveCard(Card p_Card)
    {
        m_HandCards.add(p_Card);
    }

    public void PullCard()
    {
        GiveCard(m_Table.GetDeck().GetRandomCard());
    }

    public void AddToTable(Table p_Table)
    {
        m_Table = p_Table;
    }

    protected ArrayList<Integer> GetViableOptions()
    {
        ArrayList<Integer> s_ViableOptions = new ArrayList<Integer>();

        Card.Suit s_CardSuit = (m_Table.GetLastCard().GetCustomSuit() == null ? m_Table.GetLastCard().GetSuit() :m_Table.GetLastCard().GetCustomSuit());

        for (int i = 0; i < m_HandCards.size(); ++i)
        {
            if (m_HandCards.get(i).GetID() == m_Table.GetLastCard().GetID() ||
                m_HandCards.get(i).GetSuit() == s_CardSuit ||
                m_HandCards.get(i).GetType() == Card.Type.Joker)
                s_ViableOptions.add(i);
        }

        return s_ViableOptions;
    }

    protected Card FindCardByID(int p_ID)
    {
        for (Card s_Card : m_HandCards)
            if (s_Card.GetID() == p_ID)
                return s_Card;
        return null;
    }

    protected boolean HasWon()
    {
        return (m_HandCards.isEmpty());
    }

    public boolean PlayRound()
    {
        return false;
    }

    public String GetName() { return m_Name; }

    public Type GetType() { return m_Type; }

    public int GetHandCardCount() { return m_HandCards.size(); }
}

class Table
{
    ArrayList<GeneralPlayer> m_Players = new ArrayList<GeneralPlayer>();
    Deck m_Deck = new Deck();
    ArrayList<Card> m_TableCards = new ArrayList<Card>();
    int m_GetTwoCount = 0;
    GeneralPlayer m_Winner;

    Table(ArrayList<GeneralPlayer> p_Players)
    {
        m_Players = p_Players;

        // Add all the players to our table
        for (GeneralPlayer s_Player : m_Players)
            s_Player.AddToTable(this);
    }

    public boolean PlayRound()
    {
        // Make each player play a round (unless the player before him wins)
        for (GeneralPlayer s_Player : m_Players)
        {
           PrintRoundInfo(s_Player);
            if (s_Player.PlayRound())
            {
                m_Winner = s_Player;
                return true;
            }
        }

        return false;
    }

    public void PrintRoundInfo(GeneralPlayer p_Player)
    {
        Utils.ClearConsole();

        System.out.println("Player '" + p_Player.GetName() + "'" + (p_Player.GetType() == GeneralPlayer.Type.AI ? " [AI]" : "") + " is now playing (Cards: " + p_Player.GetHandCardCount() + ").\n\n");
        System.out.println("Last Card: " + GetLastCard().GetArt());
        System.out.println();
    }

    public void PerformCardChecks()
    {
        // Put all cards (other than the last one) back in the deck (if needed)
        if (m_TableCards.size() >= (52 - (m_Players.size() * 3)))
        {
            m_Deck.AddCards(new ArrayList<Card>(m_TableCards.subList(0, m_TableCards.size() - 1)));
            m_TableCards = new ArrayList<Card>(m_TableCards.subList(m_TableCards.size() - 1, m_TableCards.size()));
        }
    }

    public void AddCardToTable(Card p_Card)
    {
        m_TableCards.add(p_Card);

        // If the card is a 'GetTwo' card then increment the count of consecutive 'GetTwo' cards
        if (p_Card.GetType() == Card.Type.GetTwo)
            ++m_GetTwoCount;
        else
            m_GetTwoCount = 0;
    }

    public int GetGetTwoCount() { return m_GetTwoCount; }

    public Card GetLastCard() { return m_TableCards.get(m_TableCards.size() - 1); }

    public ArrayList<GeneralPlayer> GetPlayers() { return m_Players; }

    public Deck GetDeck() { return m_Deck; }

    public GeneralPlayer GetWinner() { return m_Winner; }
}

class GeneralCrazyEights
{
    private static void PrintLogo()
    {
        System.out.println("\n\n   ______                          _______       __    __      \n  / ____/________ _____  __  __   / ____(_)___ _/ /_  / /______\n / /   / ___/ __ `/_  / / / / /  / __/ / / __ `/ __ \\/ __/ ___/\n/ /___/ /  / /_/ / / /_/ /_/ /  / /___/ / /_/ / / / / /_(__  ) \n\\____/_/   \\__,_/ /___/\\__, /  /_____/_/\\__, /_/ /_/\\__/____/  \n                      /____/           /____/                  \n");

        for (int i = 0; i < 12; ++i)
            System.out.println();
    }

    private static GeneralPlayer ReadNewPlayerInfo(Scanner p_Scanner, int p_Index)
    {
        Utils.ClearConsole();
        PrintLogo();

        // Read the name of the Player
        System.out.println("Enter a name for player on slot " + (p_Index + 1) + ":");
        String s_PlayerName = p_Scanner.nextLine();

        Utils.ClearConsole();
        PrintLogo();

        // Read the type of the Player (normal or AI)
        System.out.println("Select the player type for '" + s_PlayerName + "': (N)ormal or (A)I.");

        String s_PlayerType = p_Scanner.nextLine();

        while (!s_PlayerType.equalsIgnoreCase("n") && !s_PlayerType.equalsIgnoreCase("a"))
        {
            Utils.ClearConsole();
            PrintLogo();
            System.out.println("Select the player type for '" + s_PlayerName + "': (N)ormal or (A)I.");
            s_PlayerType = p_Scanner.nextLine();
        }

        // And create a new Player instance based on that data
        return (s_PlayerType.equalsIgnoreCase("n") ? new NormalPlayer(s_PlayerName) : new ComputerPlayer(s_PlayerName));
    }

    private static int ReadPlayerCount(Scanner p_Scanner)
    {
        Utils.ClearConsole();
        PrintLogo();

        // Read the count of players
        System.out.println("Enter the amount of players (2-5):");
        String s_StringAmount = p_Scanner.nextLine();

        int s_Amount;

        try
        {
            s_Amount = Integer.parseInt(s_StringAmount);
        }
        catch (NumberFormatException ignored) { return ReadPlayerCount(p_Scanner); }

        if (s_Amount < 2 || s_Amount > 5)
            return ReadPlayerCount(p_Scanner);

        return s_Amount;
    }

    private static void PrintOSWarning(Scanner p_Scanner)
    {
        // Check if our OS is supported
        String s_OSName = System.getProperty("os.name").toLowerCase();
        if (Utils.IsUnix())
            return;

        // If not print a warning
        Utils.ClearConsole();
        PrintLogo();

        System.out.println("WARNING: This game only looks good on *nix flavors. Press ENTER to continue...");
        p_Scanner.nextLine();
    }

    public static void main(String[] args)
    {
        CardPrinter.InitCardPrints();

        ArrayList<GeneralPlayer> s_Players = new ArrayList<GeneralPlayer>();

        Scanner s_Scanner = new Scanner(System.in);

        // Print an OS Warning (if needed)
        PrintOSWarning(s_Scanner);

        // Read the count of players
        int s_PlayerAmount = ReadPlayerCount(s_Scanner);

        // And then get info for every players
        for (int i = 0; i < s_PlayerAmount; ++i)
            s_Players.add(ReadNewPlayerInfo(s_Scanner, i));

        // Create a new table and make 'em sit at it
        Table s_Table = new Table(s_Players);

        // Give 5 cards to each players
        for (int i = 0; i < 5; ++i)
            for (GeneralPlayer s_Player : s_Players)
                s_Player.PullCard();

        // And throw one at the table
        s_Table.AddCardToTable(s_Table.GetDeck().GetRandomCard());

        // Keep playing rounds and checking if the deck needs cards until a player wins
        while(!s_Table.PlayRound())
            s_Table.PerformCardChecks();

        Utils.ClearConsole();

        PrintLogo();
        System.out.println("Player '" + s_Table.GetWinner().GetName() + "' won the game! Thank you for playing.");
    }
}
