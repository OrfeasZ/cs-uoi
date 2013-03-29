/**
* Lab03 - Vector
* 
* Compiles with JDK 1.7.0
*
* @author: Orfeas-Ioannis Zafeiris (2250) <cs122250>
*/


class Vector
{
	private int[] m_Components;

	public int[] GetComponents() { return m_Components; }

	Vector(int p_ComponentCount)
	{
		m_Components = new int[p_ComponentCount];
	}

	public void set(int p_Index, int p_Value)
	{
		if (p_Index >= m_Components.length)
		{
			System.out.println("Index is out of bounds.");
			return;
		}

		m_Components[p_Index] = p_Value;
	}

	public void set(int[] p_Components)
	{
		if (p_Components.length == m_Components.length)
			m_Components = p_Components;
		else
			System.out.println("Input array is of invalid size.");
	}

	public void add(Vector p_Vector)
	{
		if (p_Vector.GetComponents().length != m_Components.length)
		{
			System.out.println("Input vector is of invalid size.");
			return;
		}

		for(int i = 0; i < m_Components.length; ++i)
			m_Components[i] += p_Vector.GetComponents()[i];
	}

	public int dot(Vector p_Vector)
	{
		if (p_Vector.GetComponents().length != m_Components.length)
		{
			System.out.println("Input vector is of invalid size.");
			return 0;
		}

		int s_FinalVal = 0;

		for(int i = 0; i < m_Components.length; ++i)
			s_FinalVal += (m_Components[i] * p_Vector.GetComponents()[i]);

		return s_FinalVal;
	}

	public boolean equals(Vector p_Vector)
	{
		if (p_Vector.GetComponents().length != m_Components.length)
			return false;

		for(int i = 0; i < m_Components.length; ++i)
			if (m_Components[i] != p_Vector.GetComponents()[i])
				return false;

		return true;
	}

	public String toString()
	{
		String s_Result = "(";

		boolean s_First = true;

		for(int s_Component : m_Components)
		{
			if (s_First)
			{
				s_First = false;
				s_Result += s_Component;
				continue;
			}

			s_Result += (", " + s_Component);
		}

		s_Result += ")";

		return s_Result;
	}
}

class VectorTest
{
	public static void main(String[] args)
	{
		Vector s_Vector01 = new Vector(3);
		Vector s_Vector02 = new Vector(3);

		s_Vector01.set(0, 6);
		s_Vector01.set(1, -5);
		s_Vector01.set(2, 3);

		s_Vector02.set(new int[] { 1, 5, 9 });


		System.out.printf("Vector01 is: %s\n", s_Vector01.toString());
		System.out.printf("Vector02 is: %s\n", s_Vector02.toString());

		System.out.printf("Vector01 is%s equal to Vector02.\n", (s_Vector01.equals(s_Vector02)) ? "" : " not");

		System.out.printf("Inner product is '%d'.\n", s_Vector01.dot(s_Vector02));

		s_Vector01.add(s_Vector02);
		System.out.printf("Final Vector: %s\n", s_Vector01.toString());
	}
}
