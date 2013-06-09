/**
 * Assignment03 - Pokegun
 *
 * Compiles with JDK 1.7.0
 *
 * @author Orfeas-Ioannis Zafeiris (2250) <cs122250>
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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

    public static boolean IsNumber(String p_String)
    {
        try
        {
            Integer.parseInt(p_String);
        }
        catch(NumberFormatException e)
        {
            return false;
        }

        return true;
    }

    public static void PrintDialog(String p_Message)
    {
        System.out.print(" ");
        for (int i = 0; i < 77; ++i)
            System.out.print("-");

        System.out.println();
        System.out.print("| " + p_Message);

        for (int i = 0; i < 76 - p_Message.length(); ++i)
            System.out.print(" ");
        System.out.println("|");

        System.out.print(" ");
        for (int i = 0; i < 77; ++i)
            System.out.print("-");

        System.out.println();
    }

    public static void PromptForEnter()
    {
        System.out.println("Press ENTER to continue...");
        (new Scanner(System.in)).nextLine();
    }

    public static void Sleep(long p_Milliseconds)
    {
        try { Thread.sleep(p_Milliseconds); } catch (InterruptedException ignored) {}
    }
}

interface TypeInfo
{
    public String GetType();
    public String GetIdentifier();
}

abstract class CellObject
{
    private Cell m_CellRef;

    CellObject(Cell p_CellRef)
    {
        m_CellRef = p_CellRef;
    }

    public void SetCell(Cell p_CellRef)
    {
        m_CellRef = p_CellRef;
    }

    public Cell GetCell() { return m_CellRef; }
}

abstract class Player extends CellObject implements TypeInfo
{
    private boolean m_Dead;
    private int m_MaxStrength;
    private int m_Strength;
    private GameGrid m_GameGridRef;

    protected Weapon m_Weapon;

    Player(Cell p_CellRef, GameGrid p_GameGridRef, int p_MaxStrength)
    {
        super(p_CellRef);

        m_GameGridRef = p_GameGridRef;

        m_Dead = false;

        // Add ourselves to the cell
        p_CellRef.AddPlayer(this);

        m_Weapon = null;

        // And generate a new strength
        m_Strength = (new Random()).nextInt(p_MaxStrength) + 1;
        m_MaxStrength = m_Strength;
    }

    public void MoveToCell(Cell p_NewCellRef)
    {
        GetCell().RemovePlayer(this);
        SetCell(p_NewCellRef);
        GetCell().AddPlayer(this);
    }

    public void Kill()
    {
        GetCell().RemovePlayer(this);
        m_Dead = true;
    }

    public void SetWeapon(Weapon p_Weapon)
    {
        m_Weapon = p_Weapon;
    }

    public boolean TryMoveLeft()
    {
        if (GetCell().GetCoordinates().y == 0)
            return false;

        MoveToCell(m_GameGridRef.GetCell(GetCell().GetCoordinates().x, GetCell().GetCoordinates().y - 1));
        return true;
    }

    public boolean TryMoveRight()
    {
        if (GetCell().GetCoordinates().x == Pokegun.GRID_SIZE - 1)
            return false;

        MoveToCell(m_GameGridRef.GetCell(GetCell().GetCoordinates().x + 1, GetCell().GetCoordinates().y));
        return true;
    }

    public boolean TryMoveUp()
    {
        if (GetCell().GetCoordinates().x == 0)
            return false;

        MoveToCell(m_GameGridRef.GetCell(GetCell().GetCoordinates().x - 1, GetCell().GetCoordinates().y));
        return true;
    }

    public boolean TryMoveDown()
    {
        if (GetCell().GetCoordinates().y == Pokegun.GRID_SIZE - 1)
            return false;

        MoveToCell(m_GameGridRef.GetCell(GetCell().GetCoordinates().x, GetCell().GetCoordinates().y + 1));
        return true;
    }

    public void InflictDamage(int p_Damage)
    {
        // Inflict damage to ourselves
        m_Strength -= p_Damage;

        // If our strength is below 0 it means we're dead
        if (m_Strength <= 0)
            Kill();
    }

    public void AddStrength(int p_Strength)
    {
        // Add strength
        m_Strength += p_Strength;

        // And make sure it's within bounds
        if (m_Strength > m_MaxStrength)
            m_Strength = m_MaxStrength;
    }

    public GameGrid GetGameGrid() { return m_GameGridRef; }

    public Weapon GetWeapon() { return m_Weapon; }

    public int GetMaxStrength() { return m_MaxStrength; }
    public int GetStrength() { return m_Strength; }

    public boolean IsDead() { return m_Dead; }

    abstract void Think();
}

class ComputerPlayer extends Player
{
    ComputerPlayer(Cell p_CellRef, GameGrid p_GameGridRef, int p_MaxStrength)
    {
        super(p_CellRef, p_GameGridRef, p_MaxStrength);
    }

    public void Think()
    {
        // Try moving to a random nearby cell
        boolean s_Result = false;
        switch((new Random()).nextInt(4))
        {
            case 0:
                s_Result = TryMoveLeft();
                break;
            case 1:
                s_Result = TryMoveRight();
                break;
            case 2:
                s_Result = TryMoveUp();
                break;
            case 3:
                s_Result = TryMoveDown();
                break;
        }

        // Check if we managed to move; if not, try again
        if (!s_Result)
            Think();
    }

    public String GetType() { return "ComputerPlayer"; }
    public String GetIdentifier() { return "c"; }
}

class UserPlayer extends Player
{
    UserPlayer(Cell p_CellRef, GameGrid p_GameGridRef, int p_MaxStrength)
    {
        super(p_CellRef, p_GameGridRef, p_MaxStrength);
    }

    private void PrintBattle(ComputerPlayer p_Attacker)
    {
        Utils.ClearConsole();
        System.out.println("                                                        XM;i    \n                                                       @   ,Z   \n                                                      @B  . qO  \n                                                     @kivJJFB   \n                                                      v  .:B    \n                                                     :@vB@BJLi7 \n                                                    G.::7B@ @,r7\n                                                    MrB7 .@vB@  \n                                                    E@ B B P@   \n                                                      :1..B 7   \n                                                      .B@ iB@   \n                  .@O@B@Zj.Y.                         BEB .@@M  \n                  @ .B@B@B@ @                                   \n               .;@@2B@@OB@@ B@.                                 \n               B@F@BP7LB N  @ .                                 \n                   @B@BS   :S                                   \n                v. r@   q:i                                     \n              ,@.      @BN .B1:                                 \n             B@1@@Or,:.@, @B B@                                 \n            Bi ii:1Mv7B  1k. 7j                                 ");

        Utils.PrintDialog(p_Attacker + " used 'punch' causing you " + p_Attacker.GetStrength() + " damage.");
        (new Scanner(System.in)).nextLine();
    }

    private void PrintAfterBattleWin(ComputerPlayer p_Attacker)
    {
        Utils.ClearConsole();
        System.out.println("                                                                \n                                                                \n                                                                \n                                                                \n                                                                \n                                                                \n                                                                \n                                                                \n                                                                \n                                                                \n                                                                \n                  .@O@B@EJ.Y.                                   \n                  @ .B@B@B@ @                                   \n               .;@B2B@BOB@B B@.                                 \n               B@F@BP7LB 0  @ .                                 \n                   @@@BS   :S                                   \n                7. r@   N::                                     \n              ,@.      @@N .B1:                                 \n             B@5@BMr,:.B, @B @@                                 \n            Bi ii:1MvrB  1k. ru                                 ");

        Utils.PrintDialog(p_Attacker + " fainted.");
        (new Scanner(System.in)).nextLine();
    }

    private void PrintAfterBattleLose(ComputerPlayer p_Attacker)
    {
        Utils.ClearConsole();
        System.out.println("                                                        XM;i    \n                                                       @   .E   \n                                                      @B  . qM  \n                                                     @kivYjFB   \n                                                      v  .:B    \n                                                     :BvB@BYLi7 \n                                                    Z.::7B@ @,r7\n                                                    MrB7  BvB@  \n                                                    EB B B P@   \n                                                      :2..@ 7   \n                                                      .B@ iB@   \n                                                      BEB .@BM  \n                                                                \n                                                                \n                                                                \n                                                                \n                                                                \n                                                                \n                                                                \n                                                                ");

        Utils.PrintDialog("You fainted.");
        (new Scanner(System.in)).nextLine();
    }

    private boolean Battle()
    {
        for (ComputerPlayer s_Bot : GetCell().GetBots())
        {
            // Bot attacked us; inflict damage to ourselves
            InflictDamage(s_Bot.GetStrength());

            PrintBattle(s_Bot);
            
            // If we're dead print the losing art and return
            if (GetStrength() <= 0)
            {
                PrintAfterBattleLose(s_Bot);
                Kill();
                return false;
            }

            // Otherwise, print the winning art and kill the bot that attacked us
            PrintAfterBattleWin(s_Bot);
            s_Bot.Kill();
        }

        return true;
    }

    private void TryDropWeapon()
    {
        // We can't drop a weapon on a cell that already has one
        if (GetCell().HasWeapon())
            return;

        GetCell().SetWeapon(m_Weapon);
        SetWeapon(null);
    }

    private void TrySwapWeapon()
    {
        // Swap our current weapon with the weapon that's on the cell we're currently on (if any) 
        ArrayList<Weapon> s_CellWeapon =  new ArrayList<Weapon>() {{ add(GetCell().GetWeapon()); }};
        GetCell().SetWeapon(GetWeapon());
        SetWeapon(s_CellWeapon.get(0));
    }

    private void TryFireWeapon()
    {
        // We can't fire a weapon if we don't have one
        if (GetWeapon() == null)
            return;

        // Request firing coordinates
        RequestFireInput();
    }

    private void RequestFireInput()
    {
        GetGameGrid().Print("Enter the coordinates you want to fire your weapon at (eg. 2, 5)");

        String s_Coords = (new Scanner(System.in)).nextLine();

        // Check if there is a separator
        if (!s_Coords.contains(","))
            RequestFireInput();

        // Split the input
        String[] s_CoordParts = s_Coords.split(",");

        // Check if two coordinates were given
        if (s_CoordParts.length < 2)
            RequestFireInput();

        // Check if they are numbers
        if (!Utils.IsNumber(s_CoordParts[0].trim()) || !Utils.IsNumber(s_CoordParts[1].trim()))
            RequestFireInput();

        int s_Y = Integer.parseInt(s_CoordParts[0].trim());
        int s_X = Integer.parseInt(s_CoordParts[1].trim());

        // Check if we are in bounds
        if (s_X >= Pokegun.GRID_SIZE || s_Y >= Pokegun.GRID_SIZE)
            RequestFireInput();

        // Check if we are firing on ourselves
        if (s_Y == GetCell().GetCoordinates().y && s_X == GetCell().GetCoordinates().x)
            RequestFireInput();

        // Otherwise fire!
        int m_WeaponResult = m_Weapon.Use(GetGameGrid().GetCell(s_X, s_Y));
        GetGameGrid().Print("You used '" + m_Weapon.GetType() + "'...");
        (new Scanner(System.in)).nextLine();

        // Check if firing the weapon succeeded
        if (m_WeaponResult == -1)
            GetGameGrid().Print("But it misfired...");
        else
        {
            if (GetWeapon() instanceof Virus)
                AddStrength(m_WeaponResult);
            GetGameGrid().Print("It's super effective!");
        }

        (new Scanner(System.in)).nextLine();

        // Remove the weapon
        SetWeapon(null);
    }

    private void RequestInput()
    {
        GetGameGrid().Print();

        String s_Option = (new Scanner(System.in)).nextLine();

        // Request an option from the player
        if (s_Option.equalsIgnoreCase("w"))
        {
            if (!TryMoveUp())
                RequestInput();
        }
        else if (s_Option.equalsIgnoreCase("a"))
        {
            if (!TryMoveLeft())
                RequestInput();
        }
        else if (s_Option.equalsIgnoreCase("s"))
        {
            if (!TryMoveRight())
                RequestInput();
        }
        else if (s_Option.equalsIgnoreCase("d"))
        {
            if (!TryMoveDown())
                RequestInput();
        }
        else if (s_Option.equalsIgnoreCase("f"))
        {
            TryFireWeapon();
            RequestInput();
        }
        else if (s_Option.equalsIgnoreCase("e"))
        {
            TryDropWeapon();
            RequestInput();
        }
        else if (s_Option.equalsIgnoreCase("r"))
        {
            TrySwapWeapon();
            RequestInput();
        }
        else
        {
            RequestInput();
        }
    }

    public void Think()
    {
        // We're dead; we shouldn't be thinking
        if (IsDead())
            return;

        // Check if we are in the same cell with a bot
        if (GetCell().PlayerCount() > 1)
        {
            // If so start a battle right away
            if (!Battle())
                return;
        }

        // Check if the game has ended
        if (GetGameGrid().HasEnded())
            return;

        // Request an action from the player
        RequestInput();

        // See if there are any bots in the cell we moved into;
        if (GetCell().PlayerCount() > 1)
            Battle();
    }

    public String GetType() { return "UserPlayer"; }
    public String GetIdentifier() { return "u"; }
}

abstract class Weapon extends CellObject implements TypeInfo
{
    private boolean m_Functional;

    Weapon(Cell p_CellRef, boolean p_Functional)
    {
        super(p_CellRef);
        m_Functional = p_Functional;
        GetCell().SetWeapon(this);
    }

    public int Use(Cell p_TargetCell)
    {
        // Weapon is malfunctional; firing failed
        if (!m_Functional)
            return -1;

        return UseInternal(p_TargetCell);
    }

    abstract int UseInternal(Cell p_TargetCell);
}

class Grenade extends Weapon
{
    Grenade(Cell p_CellRef, boolean p_Functional)
    {
        super(p_CellRef, p_Functional);
    }

    int UseInternal(Cell p_TargetCell)
    {
        // Kill all bots that are on the target cell
        for (ComputerPlayer s_Bot : p_TargetCell.GetBots())
            s_Bot.Kill();

        return 0;
    }

    public String GetType() { return "Grenade"; }
    public String GetIdentifier() { return "g"; }
}

class Teargas extends Weapon
{
    Teargas(Cell p_CellRef, boolean p_Functional)
    {
        super(p_CellRef, p_Functional);
    }

    int UseInternal(Cell p_TargetCell)
    {
        // Make all bots that are on that target cell move
        for (ComputerPlayer s_Bot : p_TargetCell.GetBots())
            s_Bot.Think();

        return 0;
    }

    public String GetType() { return "Teargas"; }
    public String GetIdentifier() { return "t"; }
}

class Virus extends Weapon
{
    Virus(Cell p_CellRef, boolean p_Functional)
    {
        super(p_CellRef, p_Functional);
    }

    int UseInternal(Cell p_TargetCell)
    {
        int s_AddedStrength = 0;

        for (ComputerPlayer s_Bot : p_TargetCell.GetBots())
        {
            // Inflict damage to the bot
            int s_BotStrength = s_Bot.GetStrength();
            s_Bot.InflictDamage(10);

            // And add its strength to ourselves
            if (s_BotStrength < 10)
                s_AddedStrength += 10 - s_BotStrength;
        }

        return s_AddedStrength;
    }

    public String GetType() { return "Virus"; }
    public String GetIdentifier() { return "v"; }
}

class Cell
{
    private Point m_Coordinates;
    private ArrayList<Player> m_Players;
    private Weapon m_Weapon;

    Cell(Point p_Coordinates)
    {
        m_Coordinates = p_Coordinates;
        m_Players = new ArrayList<Player>();
        m_Weapon = null;
    }

    public void AddPlayer(Player p_Player)
    {
        m_Players.add(p_Player);
    }

    public void RemovePlayer(Player p_Player)
    {
        m_Players.remove(p_Player);
    }

    public void SetWeapon(Weapon p_Weapon)
    {
        m_Weapon = p_Weapon;
    }

    public void RemoveWeapon()
    {
        m_Weapon = null;
    }

    public String toString()
    {
        if (HasPlayers())
        {
            if (HasUserPlayer())
                return "Y";
            else
                return "X";
        }
        else if (HasWeapon())
        {
            return "o";
        }

        return " ";
    }

    public boolean HasUserPlayer()
    {
        // Check to see if the cell has the UserPlayer on it
        for (Player s_Player : m_Players)
            if (s_Player instanceof UserPlayer)
                return true;

        return false;
    }

    public ArrayList<ComputerPlayer> GetBots()
    {
        // Get all bots that are on that cell
        ArrayList<ComputerPlayer> s_Bots = new ArrayList<ComputerPlayer>();

        for (Player s_Player : m_Players)
            if (s_Player instanceof ComputerPlayer)
                s_Bots.add((ComputerPlayer)s_Player);

        return s_Bots;
    }

    public Weapon GetWeapon() { return m_Weapon; }

    public int PlayerCount() { return m_Players.size(); }

    public boolean HasPlayers() { return !m_Players.isEmpty(); }

    public boolean HasWeapon() { return m_Weapon != null; }

    public Point GetCoordinates() { return m_Coordinates; }
}

class GameGrid
{
    private Cell[][] m_Cells;
    private ArrayList<ComputerPlayer> m_Bots;
    private ArrayList<Weapon> m_Weapons;
    private UserPlayer m_Player;

    GameGrid()
    {
        m_Cells = new Cell[Pokegun.GRID_SIZE][Pokegun.GRID_SIZE];
        m_Bots = new ArrayList<ComputerPlayer>();
        m_Weapons = new ArrayList<Weapon>();

        CreateCells();
        SpawnBots();
        SpawnWeapons();

        // Spawn the player
        m_Player = new UserPlayer(m_Cells[0][0], this, Pokegun.MAX_USER_STRENGTH);
    }

    private void CreateCells()
    {
        for (int x = 0; x < Pokegun.GRID_SIZE; ++x)
            for (int y = 0; y < Pokegun.GRID_SIZE; ++y)
                m_Cells[x][y] = new Cell(new Point(x, y));
    }

    private void SpawnBots()
    {
        Random s_Random = new Random();

        // Never spawn on (0, 0)
        for (int i = 0; i < Pokegun.COMPUTER_PLAYERS; ++i)
        {
            Cell m_Cell = m_Cells[s_Random.nextInt(Pokegun.GRID_SIZE - 1) + 1][s_Random.nextInt(Pokegun.GRID_SIZE - 1) + 1];
            System.out.println("Adding bot to cell " + m_Cell.GetCoordinates().x + ", " + m_Cell.GetCoordinates().y);
            m_Bots.add(new ComputerPlayer(m_Cell, this, Pokegun.MAX_COMPUTER_STRENGTH));
        }
    }

    public Cell GetRandomEmptyCell()
    {
        // This will never return (0, 0)

        Random s_Random = new Random();
        Cell s_CellRef = m_Cells[s_Random.nextInt(Pokegun.GRID_SIZE - 1) + 1][s_Random.nextInt(Pokegun.GRID_SIZE - 1) + 1];

        // This can be optimized (may try same coordinates and will iterate indefinitely if all cells are used)
        while(s_CellRef.HasPlayers() || s_CellRef.HasWeapon())
            s_CellRef = m_Cells[s_Random.nextInt(Pokegun.GRID_SIZE - 1) + 1][s_Random.nextInt(Pokegun.GRID_SIZE - 1) + 1];

        return s_CellRef;
    }

    private void SpawnWeapons()
    {
        Random s_Random = new Random();
        m_Weapons.add(new Grenade(GetRandomEmptyCell(), (s_Random.nextInt(100) + 1) > 5));
        m_Weapons.add(new Teargas(GetRandomEmptyCell(), (s_Random.nextInt(100) + 1) > 5));
        m_Weapons.add(new Virus(GetRandomEmptyCell(), (s_Random.nextInt(100) + 1) > 5));
    }

    public Cell GetCell(int p_X, int p_Y)
    {
        if (p_X > Pokegun.GRID_SIZE || p_Y > Pokegun.GRID_SIZE)
            return null;

        return m_Cells[p_X][p_Y];
    }

    public void Print()
    {
        Print(null);
    }

    public void Print(String p_Message)
    {
        Utils.ClearConsole();

        // Print user health, weapon and current position
        String s_HealthBar = "[";
        for (int i = 0; i < Math.round(((float)m_Player.GetStrength() / (float)m_Player.GetMaxStrength()) * 20.f); ++i)
            s_HealthBar += "=";
        for (int i = 0; i <  Math.round(20.f - (((float)m_Player.GetStrength() / (float)m_Player.GetMaxStrength()) * 20.f)); ++i)
            s_HealthBar += " ";
        s_HealthBar += "]";

        System.out.println("\n HP: " + s_HealthBar + " (" + m_Player.GetStrength() + "/" + m_Player.GetMaxStrength() + ")");
        System.out.println(" Weapon: " + (m_Player.GetWeapon() == null ? "None" : m_Player.GetWeapon().GetType()));
        System.out.println(" Position: (" + m_Player.GetCell().GetCoordinates().y + "," + m_Player.GetCell().GetCoordinates().x + ")");

        // Print the grid
        if (Pokegun.GRID_SIZE < 24)
            for (int i = 0; i < ((24 - Pokegun.GRID_SIZE - 2) / 2) - 5; ++i)
                System.out.println();

        String s_Spacing = "";

        if (Pokegun.GRID_SIZE < 80)
            for (int i = 0; i < ((80 - Pokegun.GRID_SIZE) / 2) - 1; ++i)
                s_Spacing += " ";

        System.out.print(s_Spacing + "  ");

        for (int i = 0; i < Pokegun.GRID_SIZE; ++i)
            System.out.print(i);

        System.out.println();

        String s_Border = s_Spacing + "  ";
        for (int i = 0; i < Pokegun.GRID_SIZE; ++i)
            s_Border += "-";

        System.out.println(s_Border);
        for (int x = 0; x < Pokegun.GRID_SIZE; ++x)
        {
            System.out.print(s_Spacing + x + "|");

            for (int y = 0; y < Pokegun.GRID_SIZE; ++y)
            {
                System.out.print((GetCell(x, y) == null ? " " : GetCell(x, y)));
                //System.out.print("|");
            }

            System.out.println("|");
        }
        System.out.println(s_Border);

        if (Pokegun.GRID_SIZE < 24)
            for (int i = 0; i < ((24 - Pokegun.GRID_SIZE - 1) / 2) - 3; ++i)
                System.out.println();

        // Print the instructional message
        if (p_Message != null)
        {
            Utils.PrintDialog(p_Message);
        }
        else if (m_Player.GetCell().HasWeapon())
        {
            if (m_Player.GetWeapon() != null)
                Utils.PrintDialog("You're standing on a weapon. Press R to swap it with your current weapon.");
            else
                Utils.PrintDialog("You're standing on a weapon. Press R to pick it up.");
        }
        else
        {
            if (m_Player.GetWeapon() != null)
                Utils.PrintDialog("Use WASD to move, F to fire your weapon or E to drop it.");
            else
                Utils.PrintDialog("Use WASD to move.");
        }
    }

    public boolean HasEnded()
    {
        // Check if the player died
        if (m_Player.IsDead())
            return true;

        // Or if all bots are dead
        for (ComputerPlayer s_Bot : m_Bots)
            if (!s_Bot.IsDead())
                return false;

        return true;
    }


    public boolean Think()
    {
        // Print the grid
        Print();

        // Make the UserPlayer think
        m_Player.Think();

        if (HasEnded())
            return false;

        // Make the Bots think
        for (ComputerPlayer s_Bot : m_Bots)
            if (!s_Bot.IsDead())
                s_Bot.Think();

        return !HasEnded();
    }

    public Player GetPlayer() { return m_Player; }
}

public class Pokegun
{
    public static final int COMPUTER_PLAYERS = 10;
    public static final int MAX_USER_STRENGTH = 100;
    public static final int MAX_COMPUTER_STRENGTH = 20;
    public static final int GRID_SIZE = 5;

    private static void PrintIntro()
    {
        Utils.ClearConsole();
        System.out.println("                                                                               \n                                          5@@                                  \n                                         B@  @;   .k@B@Xr                      \n                             .:.:P     5Bi  7@B  @@kii7XB@BN                   \n      ,q@B@B@@@B@        i@@@B@B@B@7  @B@Y@BJ  0@         .B@   : .            \n   .B@B8i      .Z@B    @B@    B@,  @@ B@j7 :B@B@    .@G:    q@ M@@0@B@B. .     \n U@B.             @B   B@X@   @     @B.   .   8B   B@@@B@O@Bq  N@B    BB@B@B@M:\n ,@@B       r@@U   @  :LM@B       .@B  JBLB  @B   U@    @@Bu:.  B@B    @B    @ \n   B@BrB    .@ BL .B@PUj::@N     B@ @  X@Y q@L@B  7B  @BuB.,rBBBk @B   B@   @B \n    @@B@1    ZB2  BM  M    @2    Ju@BP     i   X@v GB@B, @   @J@   @   @L  ,B  \n      @B@       jB@  B@B@B  @  J     @@i    .O@B         B   B@B   B :     B2  \n       @@@    ,@B@    .Z7  @B ,@B@r   .@B@B@B2.@B@r      @B   1   BL @    1@   \n        @@@    @MB@       EB   BiB@B@5   BN     rB@B@B@B  @B    .BF 1B    @    \n        L@B0   ZBNB@U,.LB@B@ .X@   :B@B@X@L          OB  B@@B@B@BL  @M   @B    \n         B@B.   @  B@B@B:.@B@B@:       P@Br         BB   B     B@B@B@i   B     \n          B@@ rBB@                                 iB@B@B7         @B@7 B@     \n           BZBNi                                                    .7@BO      \n");
        System.out.println("                              Press ENTER to play!\n\n\n");
        (new Scanner(System.in)).nextLine();
    }

    private static void PrintInstructions()
    {
        Utils.ClearConsole();
        System.out.println(" Use");
        System.out.println();
        System.out.println("         ____       ");
        System.out.println("        ||W ||      ");
        System.out.println("        ||__||      ");
        System.out.println("        |/__\\|      ");
        System.out.println("   ____  ____  ____ ");
        System.out.println("  ||A ||||S ||||D ||");
        System.out.println("  ||__||||__||||__||");
        System.out.println("  |/__\\||/__\\||/__\\|");
        System.out.println();
        System.out.println();
        System.out.println(" and ENTER to move.");
        System.out.println(" [w = up, a = left, s = down, d = right]");
        System.out.println("\n\n\n\n");

        System.out.print(" ");
        Utils.PromptForEnter();

        Utils.ClearConsole();
        System.out.println(" Use");
        System.out.println();
        System.out.println("   ____  ____ ");
        System.out.println("  ||E ||||R ||");
        System.out.println("  ||__||||__||");
        System.out.println("  |/__\\||/__\\|");
        System.out.println("         ____       ");
        System.out.println("        ||F ||      ");
        System.out.println("        ||__||      ");
        System.out.println("        |/__\\|     ");
        System.out.println();
        System.out.println();
        System.out.println(" and ENTER to perform weapon actions.");
        System.out.println(" [e = drop current weapon, r = swap weapons, f = fire current weapon]");
        System.out.println("\n\n\n\n");

        System.out.print(" ");
        Utils.PromptForEnter();

        Utils.ClearConsole();
        System.out.println(" Use");
        System.out.println();
        System.out.println("   ________________");
        System.out.println("  ||ENTER         ||");
        System.out.println("  ||______________||");
        System.out.println("  |/______________\\|");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(" to confirm actions or to advance a text dialog.");
        System.out.println();
        System.out.println("\n\n\n\n");

        System.out.print(" ");
        Utils.PromptForEnter();
    }

    private static void PrintLoser()
    {
        Utils.ClearConsole();
        System.out.println("                            ---------------------\n                            | YOU LOST THE GAME |\n                            ---------------------");
        System.out.println("\n\n\n\n\n\n\n\n\n");
    }

    private static void PrintWinner()
    {
        Utils.ClearConsole();
        System.out.println("                             --------------------\n                             | A WINRAR IS YOU! |\n                             --------------------");
        System.out.println("\n\n\n\n\n\n\n\n\n");
    }

    public static void main(String[] args)
    {
	    PrintIntro();
        PrintInstructions();

        GameGrid s_GameGrid = new GameGrid();

        while(s_GameGrid.Think())
            ;

        if (s_GameGrid.GetPlayer().IsDead())
            PrintLoser();
        else
            PrintWinner();
   }
}
