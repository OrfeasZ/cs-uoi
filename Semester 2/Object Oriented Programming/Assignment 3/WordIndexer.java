/**
 * Assignment03 - WordIndexer
 *
 * Compiles with JDK 1.7.0
 *
 * @author Orfeas-Ioannis Zafeiris (2250) <cs122250>
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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


class InternalWordIndexer
{
    private ArrayList<String> m_StopWords;
    private HashMap<String, ArrayList<Integer>> m_WordArticles;
    private HashMap<String, HashMap<Integer, Integer>> m_WordOccurrences;

    InternalWordIndexer() throws Exception
    {
        m_StopWords = new ArrayList<String>();
        m_WordArticles = new HashMap<String, ArrayList<Integer>>();
        m_WordOccurrences = new HashMap<String, HashMap<Integer, Integer>>();

        LoadStopWords();
        LoadReviews();
    }

    private void ParseReview(int p_ReviewIndex, String p_Review)
    {
        // Splt the review on any whitespace character
        for (String s_Word : p_Review.split("\\s+"))
        {
            // The trim it and convert it to lower-case
            s_Word = s_Word.trim().toLowerCase();

            // Check if it's a stop word
            boolean s_Valid = true;
            for (String s_StopWord : m_StopWords)
            {
                if (s_Word.equalsIgnoreCase(s_StopWord))
                {
                    s_Valid = false;
                    break;
                }
            }

            // And if it is, skip it
            if (!s_Valid)
                continue;

            // Remove punctuation
            s_Word = s_Word.replaceAll("\\W", "");

            // Check if the word is empty
            if (s_Word.length() == 0)
                continue;

            if (!m_WordArticles.containsKey(s_Word))
                m_WordArticles.put(s_Word, new ArrayList<Integer>());

            if (!m_WordArticles.get(s_Word).contains(p_ReviewIndex))
                m_WordArticles.get(s_Word).add(p_ReviewIndex);

            if (!m_WordOccurrences.containsKey(s_Word))
                m_WordOccurrences.put(s_Word, new HashMap<Integer, Integer>());

            if (!m_WordOccurrences.get(s_Word).containsKey(p_ReviewIndex))
                m_WordOccurrences.get(s_Word).put(p_ReviewIndex, 0);

            m_WordOccurrences.get(s_Word).put(p_ReviewIndex, m_WordOccurrences.get(s_Word).get(p_ReviewIndex) + 1);
        }
    }

    private void LoadReviews() throws Exception
    {
        BufferedReader s_Reader;
        try
        {
            s_Reader = new BufferedReader(new FileReader(WordIndexer.REVIEWS_FILE));
        }
        catch (FileNotFoundException e)
        {
            throw new Exception("An error occurred while opening the reviews file.");
        }

        String s_Review;

        try
        {
            // Read the reviews file line by line
            int s_Index = 0;
            while ((s_Review = s_Reader.readLine()) != null)
            {
                ParseReview(s_Index, s_Review);
                ++s_Index;
            }

            s_Reader.close();
        }
        catch (IOException e)
        {
            throw new Exception("An error occurred while reading the reviews file.");
        }
    }

    private void LoadStopWords() throws Exception
    {
        BufferedReader s_Reader;
        try
        {
            s_Reader = new BufferedReader(new FileReader(WordIndexer.STOPWORDS_FILE));
        }
        catch (FileNotFoundException e)
        {
            throw new Exception("An error occurred while opening the stopwords file.");
        }

        String s_StopWord;

        try
        {
            // Read the stopwords file line by line
            while ((s_StopWord = s_Reader.readLine()) != null)
                m_StopWords.add(s_StopWord.trim().toLowerCase());

            s_Reader.close();
        }
        catch (IOException e)
        {
            throw new Exception("An error occurred while reading the stopwords file.");
        }
    }

    public void Query(String p_QueryString)
    {
        Utils.ClearConsole();

        p_QueryString = p_QueryString.trim().toLowerCase();

        // Check if the word is a stopword
        for (String s_StopWord : m_StopWords)
        {
            if (p_QueryString.equalsIgnoreCase(s_StopWord))
            {
                System.out.println("No matches were found for the word '" + p_QueryString + "'.");
                System.out.println();
                return;
            }
        }

        // Remove punctuation
        p_QueryString = p_QueryString.replaceAll("\\W", "");

        // Check if we have that word in our map
        if (!m_WordArticles.containsKey(p_QueryString))
        {
            System.out.println("No matches were found for the word '" + p_QueryString + "'.");
            System.out.println();
            return;
        }

        System.out.println("The word '" + p_QueryString + "' was found in the following articles:");
        System.out.println("----------------------------------------------------");

        for (Integer s_ArticleIndex : m_WordArticles.get(p_QueryString))
            System.out.println("Article #" + (s_ArticleIndex + 1) + " (" + m_WordOccurrences.get(p_QueryString).get(s_ArticleIndex) + " occurrences)");

        System.out.println("----------------------------------------------------");
        System.out.println();
    }

    public void SaveOutput() throws Exception
    {
        BufferedWriter s_Writer;

        try
        {
            s_Writer = new BufferedWriter(new FileWriter(WordIndexer.OUTPUT_FILE));

            for (String s_Word : m_WordArticles.keySet())
            {
                StringBuilder s_Builder = new StringBuilder();
                for(Integer s_ArticleIndex : m_WordArticles.get(s_Word))
                    s_Builder.append((s_ArticleIndex + 1)).append(", ");

                s_Writer.write(s_Word + "\t" + (s_Builder.length() > 1 ? s_Builder.substring(0, s_Builder.length() - 2) : "") + "\n");
            }

            s_Writer.close();
        }
        catch (IOException e)
        {
            throw new Exception("An error occurred while writing to the output file.");
        }
    }
}

public class WordIndexer
{
    public final static String STOPWORDS_FILE = "stopwords.txt";
    public final static String REVIEWS_FILE = "reviews.txt";
    public final static String OUTPUT_FILE = "inverted_index.txt";

    public static void main(String[] args)
    {
        InternalWordIndexer s_Indexer;

        try
        {
            s_Indexer = new InternalWordIndexer();
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        Scanner s_Scanner = new Scanner(System.in);

        Utils.ClearConsole();

        for(;;)
        {
            System.out.println("Enter a search query or 'x' to exit.");

            String s_Query = s_Scanner.nextLine();

            if (s_Query.equalsIgnoreCase("x"))
                break;
            
            s_Indexer.Query(s_Query);
        }

        try
        {
            s_Indexer.SaveOutput();
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
