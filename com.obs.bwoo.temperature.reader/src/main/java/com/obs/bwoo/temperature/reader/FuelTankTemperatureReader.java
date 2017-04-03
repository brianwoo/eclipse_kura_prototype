/**
 *
 */
package com.obs.bwoo.temperature.reader;

import java.util.concurrent.ThreadLocalRandom;

import com.obs.bwoo.temperature.reader.interfaces.TemperatureReader;

/**
 * @author bwoo
 *
 */
public class FuelTankTemperatureReader implements TemperatureReader
{
    private static final int MIN_TEMPERATURE = 0;
    private static final int MAX_TEMPERATURE = 60;

    private static TemperatureReader reader;

    private FuelTankTemperatureReader()
    {
    }

    /*
     * (non-Javadoc)
     *
     * @see com.obs.temperature.reader.interfaces.TemperatureReader#getTemperature()
     */
    @Override
    public Float getTemperature()
    {
        int randValue = ThreadLocalRandom.current().nextInt(MIN_TEMPERATURE, MAX_TEMPERATURE + 1);

        return new Float(randValue);
    }

    /**
     * instantiate a singleton.
     *
     * @return
     */
    public static synchronized TemperatureReader getInstance()
    {
        if (null == reader)
        {
            reader = new FuelTankTemperatureReader();
        }

        return reader;
    }

}
