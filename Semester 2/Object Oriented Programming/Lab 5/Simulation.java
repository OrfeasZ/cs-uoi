/**
 * Lab05 - Simulation
 *
 * Compiles with JDK 1.7.0
 *
 * @author: Orfeas-Ioannis Zafeiris (2250) <cs122250>
 */

import java.util.Random;
import java.util.LinkedList;

class Engine
{
    public enum OilType
    {
        l4, l6
    }

    private OilType m_EngineOilType;
    private double m_Consumption;
    private double m_CurrentOil;

    Engine(OilType p_EngineOilType)
    {
        m_EngineOilType = p_EngineOilType;
        m_Consumption = (new Random()).nextDouble();

        // Fill the engine with oil depending on the oil type
        m_CurrentOil = (m_EngineOilType == OilType.l4 ? 4.0 : 6.0);
    }

    Engine(OilType p_EngineOilType, double p_Oil)
    {
        m_EngineOilType = p_EngineOilType;
        m_Consumption = (new Random()).nextDouble();
        m_CurrentOil = p_Oil;
    }

    public OilType GetOilType() { return m_EngineOilType; }

    public double GetConsumption() { return m_Consumption; }

    public double GetCurrentOil() { return m_CurrentOil; }

    public void SetCurrentOil(double p_Value)
    {
        m_CurrentOil = p_Value;
    }

    public void ConsumeOilFor(int m_Kilometers)
    {
        // Consume oil for the given amount of km
        m_CurrentOil -= (m_Consumption * (m_Kilometers / 1000));
    }

    public void RefillOil()
    {
        // Refill our engine with oil
        m_CurrentOil = (m_EngineOilType == OilType.l4 ? 4.0 : 6.0);
    }
}

class Driver
{
    private String m_Name;

    private int m_LicenseID;

    Driver(String p_Name, int p_LicenseID)
    {
        m_Name = p_Name;
        m_LicenseID = p_LicenseID;
    }

    public String GetName() { return m_Name; }

    public int GetLicenseID() { return m_LicenseID; }

    public String toString()
    {
        return "Name: " + m_Name + ", License ID: " + m_LicenseID;
    }
}

class Car
{
    private String m_LicensePlate;

    private String m_Brand;

    private Engine m_Engine;

    private Driver m_Driver;

    private boolean m_DriversExchanged = false;

    private boolean m_OilRefilled = false;

    private int m_Position = 0;

    Car(String p_Brand, String p_LicensePlates, Engine.OilType p_EngineOilType)
    {
        m_Brand = p_Brand;
        m_LicensePlate = p_LicensePlates;
        m_Engine = new Engine(p_EngineOilType);
    }

    Car(String p_Brand, String p_LicensePlates, Engine.OilType p_EngineOilType, Driver p_Driver)
    {
        m_Brand = p_Brand;
        m_LicensePlate = p_LicensePlates;
        m_Engine = new Engine(p_EngineOilType);
        m_Driver = p_Driver;
    }

    Car(String p_Brand, String p_LicensePlates, Engine.OilType p_EngineOilType, double p_Oil, Driver p_Driver)
    {
        m_Brand = p_Brand;
        m_LicensePlate = p_LicensePlates;
        m_Engine = new Engine(p_EngineOilType, p_Oil);
        m_Driver = p_Driver;
    }

    public void Cruise()
    {
        // Move either one step forwards or backwards
        m_Position += ((new Random()).nextInt() % 2 == 0 ? 1 : -1);

        // Consume oil for 1000km
        m_Engine.ConsumeOilFor(1000);

        // And if our oil is below the predefined limit, refill it
        if (m_Engine.GetCurrentOil() < 1.0)
        {
            m_OilRefilled = true;
            m_Engine.RefillOil();
        }
    }

    public void SetDriver(Driver p_DriverRef)
    {
        m_Driver = p_DriverRef;
    }

    public void ExchangeDrivers(Car p_Car)
    {
        m_DriversExchanged = true;
        p_Car.SetDriversExchanged();

        // Make a new list that holds all the drivers
        LinkedList<Driver> s_Drivers = new LinkedList<Driver>();
        s_Drivers.add(m_Driver);
        s_Drivers.add(p_Car.GetDriver());

        // And the swap them
        m_Driver = s_Drivers.get(1);
        p_Car.SetDriver(s_Drivers.get(0));
    }

    public void SetDriversExchanged() { m_DriversExchanged = true; }

    public int GetPosition() { return m_Position; }

    public Driver GetDriver() { return m_Driver; }

    public String GetLicensePlate() { return m_LicensePlate; }

    public String GetBrand() { return m_Brand; }

    public String toString()
    {
        String s_RetStr = "Brand: " + m_Brand + "\n";

        s_RetStr += "Position: " + m_Position + "\n";

        s_RetStr += "Oil Level: " + m_Engine.GetCurrentOil() + "\n";

        s_RetStr += "Driver: " + m_Driver + "\n";

        s_RetStr += "Exchanged Drivers: " + m_DriversExchanged + "\n";

        s_RetStr += "Oil Refilled: " + m_OilRefilled;

        return s_RetStr;
    }
}


class Simulation
{
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Invalid Arguments.");
            return;
        }

        int s_Steps = Integer.parseInt(args[0]);

        Driver s_Driver01 = new Driver("John", 123456789);
        Driver s_Driver02 = new Driver("Mark", 987654321);

        Car s_Car01 = new Car("Toyota", "MNK2122", Engine.OilType.l4, s_Driver01);
        Car s_Car02 = new Car("Suzuki", "ABX1234", Engine.OilType.l6, s_Driver02);

        for (int i = 0; i < s_Steps; ++i)
        {
            s_Car01.Cruise();
            s_Car02.Cruise();

            System.out.println("\nCar 01:");
            System.out.println(s_Car01);

            System.out.println("\nCar 02:");
            System.out.println(s_Car02);

            System.out.println("\n--------------");

            if (s_Car01.GetPosition() == s_Car02.GetPosition())
                s_Car01.ExchangeDrivers(s_Car02);
        }
    }
}
