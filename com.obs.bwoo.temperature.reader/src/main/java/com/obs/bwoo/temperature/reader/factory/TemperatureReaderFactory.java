/**
 *
 */
package com.obs.bwoo.temperature.reader.factory;

import com.obs.bwoo.temperature.reader.FuelTankTemperatureReader;
import com.obs.bwoo.temperature.reader.interfaces.TemperatureReader;

/**
 * @author bwoo
 *
 */
public class TemperatureReaderFactory
{
    public enum Type
    {
        FUEL_TANK
    }

    public TemperatureReader getTemperatureReader(Type type)
    {
        if (Type.FUEL_TANK == type)
        {
            return FuelTankTemperatureReader.getInstance();
        }
        else
        {
            throw new IllegalArgumentException("You did not implement this type of temperature reader: " + type);
        }
    }
}
