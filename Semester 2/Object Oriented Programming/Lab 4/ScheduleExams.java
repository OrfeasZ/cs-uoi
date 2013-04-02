/**
 * Lab04 - ScheduleExams
 *
 * Compiles with JDK 1.7.0
 *
 * @author: Orfeas-Ioannis Zafeiris (2250) <cs122250>
 */

import java.lang.String;
import java.lang.System;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Scanner;

class Date
{
	private int m_Day;
	private int m_Month;
	private int m_Year;

	Date(int p_Day, int p_Month, int p_Year)
	{
		m_Day = p_Day;
		m_Month = p_Month;
		m_Year = p_Year;
	}

	Date(Date p_Date)
	{
		m_Day = p_Date.getDay();
		m_Month = p_Date.getMonth();
		m_Year = p_Date.getYear();
	}

	public int getDay() { return m_Day; }
	public int getMonth() { return m_Month; }
	public int getYear() { return m_Year; }

    public void set(int p_Day, int p_Month, int p_Year)
    {
        m_Day = p_Day;
        m_Month = p_Month;
        m_Year = p_Year;
    }

    public void set(Date p_Date)
    {
        m_Day = p_Date.getDay();
        m_Month = p_Date.getMonth();
        m_Year = p_Date.getYear();
    }

    public void addDays(int p_Days)
    {
        Calendar s_Calendar = Calendar.getInstance();
        s_Calendar.set(m_Year, m_Month, m_Day);
        s_Calendar.add(Calendar.DAY_OF_YEAR, p_Days);
        m_Day = s_Calendar.get(Calendar.DAY_OF_MONTH);
        m_Month = s_Calendar.get(Calendar.MONTH);
        m_Year = s_Calendar.get(Calendar.YEAR);
    }

	public boolean equals(Date p_Date)
	{
        return m_Day == p_Date.getDay() &&
               m_Month == p_Date.getMonth() &&
               m_Year == p_Date.getYear();
    }

    public String toString()
    {
        return (new DateFormatSymbols().getMonths()[m_Month-1]) + " " + m_Day + ", " + m_Year;
    }

}

class Examination
{
    private String m_Examiner;
    private Date m_Date;

    Examination()
    {
        m_Examiner = "";
        m_Date = new Date(1, 1, 1970);
    }

    Examination(String p_Examiner)
    {
        m_Examiner = p_Examiner;
    }

    Examination(String p_Examiner, Date p_Date)
    {
        m_Examiner = p_Examiner;
        m_Date = new Date(p_Date);
    }

    Examination(Examination p_Examination)
    {
        m_Examiner = p_Examination.getExaminer();
        m_Date = new Date(p_Examination.getDate());
    }

    public void setDate(int p_Day, int p_Month, int p_Year)
    {
        m_Date.set(p_Day, p_Month, p_Year);
    }

    public void setDate(Date p_Date)
    {
        m_Date.set(p_Date);
    }

    public void setExaminer(String p_Examiner)
    {
        m_Examiner = p_Examiner;
    }

    public Date getDate() { return m_Date; }
    public String getExaminer() { return m_Examiner; }

    public boolean equals(Examination p_Examination)
    {
        return m_Examiner.equals(p_Examination.getExaminer()) &&
               m_Date.equals(p_Examination.getDate());
    }

    public String toString()
    {
        return m_Examiner + ": " + m_Date;
    }

    public void resolveConflict(Examination p_Examination)
    {
        Date s_Date = p_Examination.getDate();
        s_Date.addDays(1);
        p_Examination.setDate(s_Date);
    }
}

class ScheduleExams
{
    private static void UpdateExamination(Examination p_Examination)
    {
        Scanner s_Scanner = new Scanner(System.in);

        System.out.println("Enter an examiner name:");
        p_Examination.setExaminer(s_Scanner.nextLine());

        System.out.println("Enter the examination day:");
        int s_Day = s_Scanner.nextInt();

        System.out.println("Enter the examination month:");
        int s_Month = s_Scanner.nextInt();

        System.out.println("Enter the examination year:");
        int s_Year = s_Scanner.nextInt();

        p_Examination.setDate(s_Day, s_Month, s_Year);

        System.out.println();
    }

    public static void main(String[] p_Args)
    {
        Examination s_AliceExamination = new Examination("John",
                    new Date(1, 4, 2013));

        Examination s_BobExamination = new Examination(s_AliceExamination);

        System.out.println("Alice's examination is " + (!s_AliceExamination.equals(s_BobExamination) ? "not " : "") + "equal to Bob's.");
        System.out.println();
        System.out.println("Alice's examination: " + s_AliceExamination);
        System.out.println("Bob's examination: " + s_BobExamination);
        System.out.println();

        System.out.println("Updating Alice's examination.");
        UpdateExamination(s_AliceExamination);

        System.out.println("Updating Bob's examination.");
        UpdateExamination(s_BobExamination);

        System.out.println("Alice's examination is " + (!s_AliceExamination.equals(s_BobExamination) ? "not " : "") + "equal to Bob's.");

        if (s_AliceExamination.equals(s_BobExamination))
            s_AliceExamination.resolveConflict(s_BobExamination);

        System.out.println();

        System.out.println("Alice's examination: " + s_AliceExamination);
        System.out.println("Bob's examination: " + s_BobExamination);
    }
}