/**
* Assignment01 - Array
* 
* Compiles with JDK 1.7.0
*
* @author: Orfeas-Ioannis Zafeiris (2250) <cs122250>
*/

import java.util.Random;

class Array
{
	private final int C = 4;

	private int m_Capacity;
	private int m_CurrentIndex = 0;

	private int[] m_Items;

	Array()
	{
		m_Capacity = C;
		m_Items = new int[m_Capacity];
	}

	Array(int p_Cap)
	{
		// Make sure that the capacity is not less than C
		m_Capacity = (p_Cap <= C) ? C : p_Cap;
		m_Items = new int[m_Capacity];
	}

	public void add(int p_Value)
	{
		// Check to see if we're overflowing
		if (m_CurrentIndex > m_Capacity - 1)
		{
			// Allocate a new array with double the capacity
			m_Capacity *= 2;
			int[] s_NewItemArray = new int[m_Capacity];
			for(int i = 0; i < m_Items.length; ++i)
				s_NewItemArray[i] = m_Items[i];
			m_Items = s_NewItemArray;
		}

		// Assing our value
		m_Items[m_CurrentIndex] = p_Value;

		// And increment our current index
		++m_CurrentIndex;
	}

	public void delete()
	{
		// Check if there's anything to delete
		if (m_CurrentIndex == 0)
			return;

		// Just null out the value
		m_Items[m_CurrentIndex - 1] = 0;

		// And check if we need to resize the array
		if (((((float)(m_CurrentIndex - 1.0) / (float)m_Capacity) <= 0.25)) && m_Capacity >= (C * 2))
		{
			// Allocate a new array with half the capacity
			m_Capacity /= 2;
			int[] s_NewItemArray = new int[m_Capacity];
			for(int i = 0; i < m_Capacity; ++i)
				s_NewItemArray[i] = m_Items[i];
			m_Items = s_NewItemArray;
		}

		--m_CurrentIndex;
	}

	public int get(int p_Index)
	{
		// Check if we have an item at the requested index
		if (p_Index >= m_CurrentIndex)
			throw new ArrayIndexOutOfBoundsException("Could not get value. Specified index is out of bounds.");

		return m_Items[p_Index];
	}

	public void set(int p_Index, int p_Value)
	{
		// Check if we have an item at the requested index
		if (p_Index >= m_CurrentIndex)
			throw new ArrayIndexOutOfBoundsException("Could not assign new value. Specified index is out of bounds.");

		// And only if we do, assign the new value to it
		m_Items[p_Index] = p_Value;
	}

	public int getSize() { return m_CurrentIndex; }

	public int getCapacity() { return m_Capacity; }
}

class ArrayTest
{
	private static void PrintArrayInfo(Array p_Array)
	{
		System.out.printf("Size: %d. Capacity: %d.\n", p_Array.getSize(), p_Array.getCapacity());
	}

	public static void main(String[] args)
	{
		Array s_Array = new Array();

		Random s_Random = new Random();

		PrintArrayInfo(s_Array);

		s_Array.add(s_Random.nextInt());
		s_Array.add(s_Random.nextInt());

		PrintArrayInfo(s_Array);

		s_Array.delete();

		PrintArrayInfo(s_Array);

		for(int i = 0; i < 9; ++i)
			s_Array.add(s_Random.nextInt());

		PrintArrayInfo(s_Array);

		for (int i = 0; i < 8; ++i)
			s_Array.delete();

		PrintArrayInfo(s_Array);

		int s_Index = 1;
		System.out.printf("Got item value '%d' at index '%d'.\n", s_Array.get(s_Index), s_Index);

		s_Array.set(s_Index, 0);

		System.out.printf("New value for item at index '%d' is '%d'.\n", s_Index, s_Array.get(s_Index));

	}
}