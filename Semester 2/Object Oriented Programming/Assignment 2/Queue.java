/**
 * Assignment02 - Queue
 *
 * Compiles with JDK 1.7.0
 *
 * @author Orfeas-Ioannis Zafeiris (2250) <cs122250>
 */

class Person
{
    private String m_Name;
    private int m_ID;

    public Person(String p_Name, int p_ID)
    {
        m_Name = p_Name;
        m_ID = p_ID;
    }

    public String toString()
    {
        return m_Name + ": " + m_ID;
    }
}

class QueueElement<T>
{
    private T m_CurrentValue;
    private QueueElement<T> m_NextElement;

    QueueElement(T p_Value)
    {
        m_CurrentValue = p_Value;
        m_NextElement = null;
    }

    public void SetNextElement(QueueElement<T> p_Element)
    {
        m_NextElement = p_Element;
    }

    public T GetCurrentValue() { return m_CurrentValue; }

    public QueueElement<T> GetNextElement() { return m_NextElement; }
}

class Queue<T>
{
    private QueueElement<T> m_Head = null;

    private int m_ItemCount = 0;

    /**
     * Adds an object to the end of the Queue.
     * @param p_Item T
     */
    public void add(T p_Item)
    {
        // Create a new QueueElement and set the previous one as the next
        QueueElement<T> s_Element = new QueueElement<T>(p_Item);
        s_Element.SetNextElement(m_Head);

        // Set the new element as the head element and increment the count of items by one
        m_Head = s_Element;
        ++m_ItemCount;
    }

    /**
     * Removes and returns the object at the beginning of the Queue. If the Queue is empty it returns null.
     * @return T
     */
    public T remove()
    {
        // NOTE: This function *should* be called 'shift', 'pop_front' or 'dequeue' as 'remove' implies an index

        // Check if we have any items left
        if (isEmpty())
            return null;

        // Iterate through all the elements and find the one that was inserted first
        QueueElement<T> s_LastElement = null;
        QueueElement<T> s_CurrentElement = m_Head;

        while(s_CurrentElement.GetNextElement() != null)
        {
            s_LastElement = s_CurrentElement;
            s_CurrentElement = s_CurrentElement.GetNextElement();
        }

        // Get its value
        T s_Value = s_CurrentElement.GetCurrentValue();

        // Set it to null
        if (s_LastElement != null)
            s_LastElement.SetNextElement(null);
        else if (m_ItemCount == 1)
            m_Head = null;

        // And decrement the count of items by one
        --m_ItemCount;

        return s_Value;
    }

    /**
     * Checks if the Queue is empty.
     * @return boolean
     */
    public boolean isEmpty()
    {
        return m_ItemCount == 0 || m_Head == null;
    }
}

class QueueTest
{
    public static void main(String[] args)
    {
        Queue<Person> s_Queue = new Queue<Person>();

        Person s_Person01 = new Person("John", 123);
        Person s_Person02 = new Person("Mark", 345);
        Person s_Person03 = new Person("Stan", 567);
        Person s_Person04 = new Person("Jack", 789);

        s_Queue.add(s_Person01);

        System.out.println(s_Queue.remove());

        s_Queue.add(s_Person02);
        s_Queue.add(s_Person03);
        s_Queue.add(s_Person04);

        System.out.println(s_Queue.remove());
        System.out.println(s_Queue.remove());
        System.out.println(s_Queue.remove());

        System.out.println(s_Queue.remove());
    }
}
