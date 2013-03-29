/**
* Lab02 - Car
* 
* Compiles with JDK 1.7.0
*
* @author: Orfeas-Ioannis Zafeiris (2250) <cs122250>
*/

import java.util.Scanner;
import java.awt.Point;
import java.io.*;  

class Car
{
	private Point m_Position;

	public Car()
	{
		m_Position = new Point(0, 0);
	}

	public Car(int x, int y)
	{
		m_Position = new Point(x, y);
	}

	public void PrintPosition()
	{
		System.out.printf("Position: (%d, %d).\n\n", m_Position.x, m_Position.y);
	}

	public boolean Move(int x, int y)
	{
		if ((m_Position.x + x < -10 || m_Position.x + x > 10) ||
			(m_Position.y + y < -10 || m_Position.y + y > 10))
		{
			System.out.println("Input is out-of-bounds ([-10, 10]).");
			return false;
		}

		m_Position.x += x;
		m_Position.y += y;

		PrintPosition();

		return true;
	}

	public Point GetPosition() { return m_Position; }
}

class Car2D
{

	public static void main(String[] args)
	{
		Car s_Car = new Car();

		Scanner s_Input = new Scanner(System.in);

		boolean s_Valid = true;

		while(s_Valid)
		{
			System.out.println("Enter x step:");
			int x = s_Input.nextInt();

			System.out.println("Enter y step:");
			int y = s_Input.nextInt();

			System.out.println("");

			s_Valid = s_Car.Move(x, y);
		}
	}
	
}