/**
 * Lab08 - PeopleTownReader
 *
 * Compiles with JDK 1.7.0
 *
 * @author Orfeas-Ioannis Zafeiris (2250) <cs122250>
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

class Person
{
    private String m_Name;
    private ArrayList<String> m_Towns;

    public String GetName() { return m_Name; }
    public ArrayList<String> GetTowns() { return m_Towns; }

    public Person(String p_Name)
    {
        m_Name = p_Name;
        m_Towns = new ArrayList<String>();
    }

    public void AddTown(String p_Town)
    {
        m_Towns.add(p_Town);
    }

    public String toString()
    {
        // Iterate through all towns and combine them using a comma as a separator
        StringBuilder s_Result = new StringBuilder();
        for(String s_Town : m_Towns)
        {
            s_Result.append(s_Town);
            s_Result.append(", ");
        }

        // And then trim the last comma (if needed)
        String s_Towns = s_Result.length() > 0 ? s_Result.substring(0, s_Result.length() - 2): "";

        return m_Name + "\t" + s_Towns;
    }
}

public class PeopleTownReader
{
    public static void main(String[] p_Args)
    {
        BufferedReader s_Reader;

        try
        {
            // Initialize our reader
            s_Reader = new BufferedReader(new FileReader("input.txt"));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Failed to open the specified file.");
            return;
        }

        HashMap<String, Person> s_People = new HashMap<String, Person>();

        try
        {
            String s_Line;
            while ((s_Line = s_Reader.readLine()) != null)
            {
                String[] s_Vars = s_Line.split("\\t");

                // Check if we've already created an object for this person
                if (s_People.containsKey(s_Vars[0].toLowerCase()))
                {
                    s_People.get(s_Vars[0].toLowerCase()).AddTown(s_Vars[1]);
                }
                else
                {
                    // If not, create a new one and add it to our map
                    Person s_Person = new Person(s_Vars[0]);
                    s_Person.AddTown(s_Vars[1]);
                    s_People.put(s_Person.GetName().toLowerCase(), s_Person);
                }
            }

            // Close the reader and print out everything
            s_Reader.close();

            for (String s_Key : s_People.keySet())
                System.out.println(s_People.get(s_Key));

        }
        catch (IOException e)
        {
            System.out.println("An error occured while reading the specified file.");
            return;
        }

        BufferedWriter s_Writer;

        try
        {
            // And finally, init a writer and write the output to that file
            s_Writer = new BufferedWriter(new FileWriter("output.txt", false));

            for (String s_Key : s_People.keySet())
                s_Writer.write(s_People.get(s_Key) + "\n");

            s_Writer.close();
        }
        catch (IOException e)
        {
            System.out.println("An error occured while writing to the specified output file.");
        }
    }
}
