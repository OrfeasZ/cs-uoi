/**
* Lab02 - Bubble Sort
* 
* Compiles with JDK 1.7.0
*
* @author: Orfeas-Ioannis Zafeiris (2250) <cs122250>
*/

import java.util.Scanner;

class BubbleSort
{
	private static int[] Sort(int[] p_Array)
	{
		for(int i = p_Array.length - 1; i >= 0; --i)
		{
			for(int j = 0; j < i; ++j)
			{
				if (p_Array[i] < p_Array[j])
				{
					int y = p_Array[i];
					p_Array[i] = p_Array[j];
					p_Array[j] = y;
				}
			}
		}
		return p_Array;
	}

	private static void PrintArray(int[] p_Array)
	{
		for(int i = 0; i < p_Array.length; ++i)
			System.out.printf("[%d] = %d\n", i, p_Array[i]);
		System.out.println("");
	}

	private static int[] ReadArray(int length)
	{
		System.out.printf("Please specify %d elements:\n", length);
		Scanner s_Input = new Scanner(System.in);
		int[] s_Array = new int[length];

		for(int i = 0; i < length; ++i)
			s_Array[i] = s_Input.nextInt();

		return s_Array;
	}

	public static void main(String[] args)
	{
		//int[] s_Array = new int[] { 9, 112, 2, 854, 6 };
		int[] s_Array = ReadArray(5);

		System.out.println("\nIn array:");
		PrintArray(s_Array);

		s_Array = Sort(s_Array);

		System.out.println("Out array:");
		PrintArray(s_Array);
	}
}