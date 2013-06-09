/**
 * Assignment03 - CreatureSim
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


abstract class Creature
{
    protected Zoo m_ZooRef;
    protected boolean m_Dead;
    protected boolean m_Moved;
    protected int m_Steps;

    protected Point m_Position;

    Creature(Zoo p_ZooRef, Point p_Position)
    {
        m_Position = p_Position;
        m_ZooRef = p_ZooRef;
        m_Dead = false;
        m_Moved = false;
        m_Steps = 0;
    }

    public void Kill()
    {
        // Set our state to dead
        m_Dead = true;

        // And set content of the cell we were to null
        m_ZooRef.SetCreatureAtCoordinates(null, m_Position.x, m_Position.y);
    }

    protected ArrayList<Integer> GetNearbyEmptyCells()
    {
        ArrayList<Integer> s_CellOptions = new ArrayList<Integer>();

        // Left
        if (m_Position.x > 0 && m_ZooRef.GetCreatureAtCoordinates(m_Position.x - 1, m_Position.y) == null)
            s_CellOptions.add(0);

        // Right
        if (m_Position.x < CreatureSim.GRID_SIZE - 1 && m_ZooRef.GetCreatureAtCoordinates(m_Position.x + 1, m_Position.y) == null)
            s_CellOptions.add(1);

        // Up
        if (m_Position.y > 0 && m_ZooRef.GetCreatureAtCoordinates(m_Position.x, m_Position.y - 1) == null)
            s_CellOptions.add(2);

        // Down
        if (m_Position.y < CreatureSim.GRID_SIZE - 1 && m_ZooRef.GetCreatureAtCoordinates(m_Position.x, m_Position.y + 1) == null)
            s_CellOptions.add(3);

        return s_CellOptions;
    }

    protected void TryMoveLeft()
    {
        if (GetNearbyEmptyCells().contains(0))
        {
            m_ZooRef.SetCreatureAtCoordinates(this, m_Position.x - 1, m_Position.y);
            m_ZooRef.SetCreatureAtCoordinates(null, m_Position.x, m_Position.y);
            --m_Position.x;
        }
    }

    protected void TryMoveRight()
    {
        if (GetNearbyEmptyCells().contains(1))
        {
            m_ZooRef.SetCreatureAtCoordinates(this, m_Position.x + 1, m_Position.y);
            m_ZooRef.SetCreatureAtCoordinates(null, m_Position.x, m_Position.y);
            ++m_Position.x;
        }
    }

    protected void TryMoveUp()
    {
        if (GetNearbyEmptyCells().contains(2))
        {
            m_ZooRef.SetCreatureAtCoordinates(this, m_Position.x, m_Position.y - 1);
            m_ZooRef.SetCreatureAtCoordinates(null, m_Position.x, m_Position.y);
            --m_Position.y;
        }
    }

    protected void TryMoveDown()
    {
        if (GetNearbyEmptyCells().contains(3))
        {
            m_ZooRef.SetCreatureAtCoordinates(this, m_Position.x, m_Position.y + 1);
            m_ZooRef.SetCreatureAtCoordinates(null, m_Position.x, m_Position.y);
            ++m_Position.y;
        }
    }

    public void SetHasMoved(boolean p_Moved)
    {
        m_Moved = p_Moved;
    }

    public abstract void Move();

    public boolean IsDead() { return m_Dead; }

    public boolean HasMoved() { return m_Moved; }

    public Point GetPosition() { return m_Position; }
}

class Ant extends Creature
{
    Ant(Zoo p_ZooRef, Point p_Position)
    {
        super(p_ZooRef, p_Position);
    }

    private void TryReproduce()
    {
        ArrayList<Integer> s_CellOptions = GetNearbyEmptyCells();

        // There aren't any nearby empty cells; don't reproduce
        if (s_CellOptions.isEmpty())
            return;

        switch(s_CellOptions.get((new Random()).nextInt(s_CellOptions.size())))
        {
            case 0:
                m_ZooRef.SpawnAnt(m_Position.x - 1, m_Position.y);
                break;
            case 1:
                m_ZooRef.SpawnAnt(m_Position.x + 1, m_Position.y);
                break;
            case 2:
                m_ZooRef.SpawnAnt(m_Position.x, m_Position.y - 1);
                break;
            case 3:
                m_ZooRef.SpawnAnt(m_Position.x, m_Position.y + 1);
                break;
        }
    }

    public void Move()
    {
        // We have already moved or we're dead; don't move
        if (m_Dead || m_Moved)
            return;

        // Select a random direction
        switch((new Random()).nextInt(4))
        {
            case 0:
                TryMoveLeft();
                break;
            case 1:
                TryMoveRight();
                break;
            case 2:
                TryMoveUp();
                break;
            case 3:
                TryMoveDown();
                break;
        }

        // Increment our steps
        ++m_Steps;
        m_Moved = true;

        // And reproduce if we have to
        if (m_Steps == 3)
        {
            TryReproduce();
            m_Steps = 0;
        }
    }

    public String toString() { return "o"; }
}

class Aardvark extends Creature
{
    private int m_LastAte;

    Aardvark(Zoo p_ZooRef, Point p_Position)
    {
        super(p_ZooRef, p_Position);
        m_LastAte = 0;
    }

    private void TryReproduce()
    {
        ArrayList<Integer> s_CellOptions = GetNearbyEmptyCells();

        // There aren't any nearby empty cells; don't reproduce
        if (s_CellOptions.isEmpty())
            return;

        switch(s_CellOptions.get((new Random()).nextInt(s_CellOptions.size())))
        {
            case 0:
                m_ZooRef.SpawnAardvark(m_Position.x - 1, m_Position.y);
                break;
            case 1:
                m_ZooRef.SpawnAardvark(m_Position.x + 1, m_Position.y);
                break;
            case 2:
                m_ZooRef.SpawnAardvark(m_Position.x, m_Position.y - 1);
                break;
            case 3:
                m_ZooRef.SpawnAardvark(m_Position.x, m_Position.y + 1);
                break;
        }
    }

    private ArrayList<Integer> GetNearbyAnts()
    {
        ArrayList<Integer> s_AntCellOptions = new ArrayList<Integer>();

        if (m_ZooRef.GetCreatureAtCoordinates(m_Position.x - 1, m_Position.y) instanceof Ant)
            s_AntCellOptions.add(0);

        if (m_ZooRef.GetCreatureAtCoordinates(m_Position.x + 1, m_Position.y) instanceof Ant)
            s_AntCellOptions.add(1);

        if (m_ZooRef.GetCreatureAtCoordinates(m_Position.x, m_Position.y - 1) instanceof Ant)
            s_AntCellOptions.add(2);

        if (m_ZooRef.GetCreatureAtCoordinates(m_Position.x, m_Position.y + 1) instanceof Ant)
            s_AntCellOptions.add(3);

        return s_AntCellOptions;
    }

    public void Move()
    {
        // We have already moved or we're dead; don't move
        if (m_Dead || m_Moved)
            return;

        // See if there are any ants in the nearby cells
        ArrayList<Integer> s_AntCellOptions = GetNearbyAnts();

        if (!s_AntCellOptions.isEmpty())
        {
            // If so, eat one of them and move to its position
            switch(s_AntCellOptions.get((new Random()).nextInt(s_AntCellOptions.size())))
            {
                case 0:
                    m_ZooRef.GetCreatureAtCoordinates(m_Position.x - 1, m_Position.y).Kill();
                    TryMoveLeft();
                    break;
                case 1:
                    m_ZooRef.GetCreatureAtCoordinates(m_Position.x + 1, m_Position.y).Kill();
                    TryMoveRight();
                    break;
                case 2:
                    m_ZooRef.GetCreatureAtCoordinates(m_Position.x, m_Position.y - 1).Kill();
                    TryMoveUp();
                    break;
                case 3:
                    m_ZooRef.GetCreatureAtCoordinates(m_Position.x, m_Position.y + 1).Kill();
                    TryMoveDown();
                    break;
            }

            m_LastAte = 0;
        }
        else
        {
            // Otherwise check if we've starved to death
            ++m_LastAte;

            if (m_LastAte == 3)
            {
                Kill();
                return;
            }

            // And if not, try moving to a random nearby cell
            switch((new Random()).nextInt(4))
            {
                case 0:
                    TryMoveLeft();
                    break;
                case 1:
                    TryMoveRight();
                    break;
                case 2:
                    TryMoveUp();
                    break;
                case 3:
                    TryMoveDown();
                    break;
            }
        }

        // Increment our steps
        ++m_Steps;
        m_Moved = true;

        // And reproduce if we have to
        if (m_Steps == 8)
        {
            TryReproduce();
            m_Steps = 0;
        }
    }

    public String toString() { return "X"; }
}

class Zoo
{
    private Creature[][] m_CreatureGrid;

    Zoo()
    {
        m_CreatureGrid = new Creature[CreatureSim.GRID_SIZE][CreatureSim.GRID_SIZE];

        // Spawn the ants
        for (int i = 0; i < CreatureSim.ANT_COUNT; ++i)
            SpawnAnt(GetRandomEmptyCell());

        // And the aardvarks
        for (int i = 0; i < CreatureSim.AARDVARK_COUNT; ++i)
            SpawnAardvark(GetRandomEmptyCell());
    }

    private Point GetRandomEmptyCell()
    {
        Random s_Random = new Random();

        int s_X = s_Random.nextInt(m_CreatureGrid.length), s_Y = s_Random.nextInt(m_CreatureGrid.length);

        while (m_CreatureGrid[s_X][s_Y] != null)
        {
            s_X = s_Random.nextInt(m_CreatureGrid.length);
            s_Y = s_Random.nextInt(m_CreatureGrid.length);
        }

        return new Point(s_X, s_Y);
    }

    public Creature GetCreatureAtCoordinates(int p_X, int p_Y)
    {
        if (p_X < 0 || p_X >= m_CreatureGrid.length || p_Y < 0 || p_Y >= m_CreatureGrid.length)
            return null;

        return m_CreatureGrid[p_Y][p_X];
    }

    public void SetCreatureAtCoordinates(Creature p_Creature, int p_X, int p_Y)
    {
        if (p_X < 0 || p_X >= m_CreatureGrid.length || p_Y < 0 || p_Y >= m_CreatureGrid.length)
            return;

        m_CreatureGrid[p_Y][p_X] = p_Creature;
    }

    public boolean SpawnAnt(Point p_Point)
    {
        return SpawnAnt(p_Point.x, p_Point.y);
    }

    public boolean SpawnAnt(int p_X, int p_Y)
    {
        if (p_X < 0 || p_X >= m_CreatureGrid.length || p_Y < 0 || p_Y >= m_CreatureGrid.length)
            return false;

        if (m_CreatureGrid[p_Y][p_X] != null)
            return false;

        m_CreatureGrid[p_Y][p_X] = new Ant(this, new Point(p_X, p_Y));
        return true;
    }

    public boolean SpawnAardvark(Point p_Point)
    {
        return SpawnAardvark(p_Point.x, p_Point.y);
    }

    public boolean SpawnAardvark(int p_X, int p_Y)
    {
        if (p_X < 0 || p_X >= m_CreatureGrid.length || p_Y < 0 || p_Y >= m_CreatureGrid.length)
            return false;

        if (m_CreatureGrid[p_Y][p_X] != null)
            return false;

        m_CreatureGrid[p_Y][p_X] = new Aardvark(this, new Point(p_X, p_Y));
        return true;
    }

    public void Print()
    {
        Utils.ClearConsole();
        String s_Border = "\t-";

        for (int i = 0; i < CreatureSim.GRID_SIZE; ++i)
            s_Border += "--";

        System.out.println(s_Border);

        for (int x = 0; x < CreatureSim.GRID_SIZE; ++x)
        {
            System.out.print("\t|");

            for (int y = 0; y < CreatureSim.GRID_SIZE; ++y)
            {
                System.out.print((GetCreatureAtCoordinates(x, y) == null ? " " : GetCreatureAtCoordinates(x, y)));
                System.out.print("|");
            }

            System.out.println();
        }

        System.out.println(s_Border);
    }

    public void Think()
    {
        // Perform actions for all alive Aardvarks
        for (int x = 0; x < CreatureSim.GRID_SIZE; ++x)
            for (int y = 0; y < CreatureSim.GRID_SIZE; ++y)
                if (GetCreatureAtCoordinates(x, y) instanceof Aardvark && !GetCreatureAtCoordinates(x, y).IsDead() && !GetCreatureAtCoordinates(x, y).HasMoved())
                    GetCreatureAtCoordinates(x, y).Move();

        // Perform actions for all alive Ants
        for (int x = 0; x < CreatureSim.GRID_SIZE; ++x)
            for (int y = 0; y < CreatureSim.GRID_SIZE; ++y)
                if (GetCreatureAtCoordinates(x, y) instanceof Ant && !GetCreatureAtCoordinates(x, y).IsDead() && !GetCreatureAtCoordinates(x, y).HasMoved())
                    GetCreatureAtCoordinates(x, y).Move();

        // Reset their 'HasMoved' state
        for (int x = 0; x < CreatureSim.GRID_SIZE; ++x)
            for (int y = 0; y < CreatureSim.GRID_SIZE; ++y)
                if (GetCreatureAtCoordinates(x, y) != null)
                    GetCreatureAtCoordinates(x, y).SetHasMoved(false);

        // Print the grid
        Print();
    }
}

public class CreatureSim
{
    public final static int GRID_SIZE = 20;
    public final static int ANT_COUNT = 100;
    public final static int AARDVARK_COUNT = 5;

    public static void main(String[] args)
    {
	    Zoo m_Zoo = new Zoo();
        m_Zoo.Print();

        Scanner s_Scanner = new Scanner(System.in);

        for(;;)
        {
            m_Zoo.Think();
            s_Scanner.nextLine();
        }
    }
}
