/**
* Lab01 - Excersise 01
* 
* Compiles with JDK 1.7.0
*
* @author: Orfeas-Ioannis Zafeiris (2250) <cs122250>
*/

import java.util.Scanner;

class Excersise3
{
	private static double m_USD = 1.322d;
	private static double m_GBP = 0.876d;

	public static void main(String[] args)
	{
		Scanner s_Input = new Scanner(System.in);

		String s_InputStr;

		System.out.println("Please enter an ammount to convert (in EUR), or 'exit' to quit:");

		// Read data until the user enters 'exit'
		while(!(s_InputStr = s_Input.nextLine()).equalsIgnoreCase("exit"))
		{
			double s_Amount = 0.0d;

			// Try to parse the input as a double
			try
			{
				s_Amount = Double.parseDouble(s_InputStr);
			}
			catch (Exception e)
			{
				// Print an error and continue the loop if it fails
				System.out.println("Invalid input detected. Please try again.");
				continue;
			}

			System.out.println("Please select the currency to convert to (USD/GBP):");

			boolean s_ValidCurrency = false;
			double s_CurrencyRate = 1.0d;
			String s_Currency = "";

			do
			{
				s_Currency = s_Input.nextLine().toLowerCase();

				// Check if the selected currency is valid and set the rates
				switch(s_Currency)
				{
					case "usd":
						s_ValidCurrency = true;
						s_CurrencyRate = m_USD;
						s_Currency = "USD";
						break;
					case "gbp":
						s_ValidCurrency = true;
						s_CurrencyRate = m_GBP;
						s_Currency = "GBP";
						break;
					default:
						System.out.println("Invalid currency selected. Available currencies: USD, GBP.");
						break;
				}
			}
			while (!s_ValidCurrency);

			// Print out our converted ammount
			System.out.printf("%f EUR = %f %s\n", s_Amount, s_Amount * s_CurrencyRate, s_Currency);

			System.out.println("\nPlease enter an amount to convert (in EUR), or 'exit' to quit:");
		}
	}
}
