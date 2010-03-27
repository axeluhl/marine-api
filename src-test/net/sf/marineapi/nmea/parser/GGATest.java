package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.util.Datum;
import net.sf.marineapi.nmea.util.Direction;
import net.sf.marineapi.nmea.util.GpsFixQuality;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.SentenceId;
import net.sf.marineapi.nmea.util.Units;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the GGA sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
public class GGATest {

    public static final String EXAMPLE = "$GPGGA,120044,6011.552,N,02501.941,E,1,00,2.0,28.0,M,19.6,M,,*79";

    private GGAParser gga;

    /**
     * setUp
     */
    @Before
    public void setUp() {
        try {
            gga = new GGAParser(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * 
     */
    @Test
    public void testGGAParser() {
        GGAParser instance = new GGAParser(EXAMPLE);
        assertEquals(SentenceId.GGA, instance.getSentenceId());
    }

    @Test
    public void testGetAltitude() {
        assertEquals(28.0, gga.getAltitude(), 0.001);
    }

    @Test
    public void testGetAltitudeUnits() {
        assertEquals(Units.METER, gga.getAltitudeUnits());
    }

    @Test
    public void testGetDgpsAge() {
        try {
            gga.getDgpsAge();
            fail("Did not throw ParseException");
        } catch (DataNotAvailableException e) {
            // ok
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetDgpsStationId() {
        try {
            gga.getDgpsStationId();
            fail("Did not throw ParseException");
        } catch (DataNotAvailableException e) {
            // ok
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetFixQuality() {
        assertEquals(GpsFixQuality.NORMAL, gga.getFixQuality());
    }

    @Test
    public void testGetGeoidalHeight() {
        assertEquals(19.6, gga.getGeoidalHeight(), 0.001);
    }

    @Test
    public void testGetGeoidalHeightUnits() {
        assertEquals(Units.METER, gga.getGeoidalHeightUnits());
    }

    @Test
    public void testGetHorizontalDOP() {
        assertEquals(2.0, gga.getHorizontalDOP(), 0.001);
    }

    @Test
    public void testGetNumberOfSatellites() {
        assertEquals(0, gga.getSatelliteCount());
    }

    @Test
    public void testGetPosition() {
        // expected lat/lon values
        final double lat = 60 + (11.552 / 60);
        final double lon = 25 + (1.941 / 60);

        Position p = gga.getPosition();
        assertNotNull(p);
        assertEquals(lat, p.getLatitude(), 0.0000001);
        assertEquals(Direction.NORTH, p.getLatHemisphere());
        assertEquals(lon, p.getLongitude(), 0.0000001);
        assertEquals(Direction.EAST, p.getLonHemisphere());
        assertEquals(Datum.WGS84, p.getDatum());
    }

    @Test
    public void testGetUtcTime() {
        assertEquals("120044", gga.getUtcTime());
    }

    @Test
    public void testGetUtcHours() {
        assertEquals(12, gga.getUtcHours());
    }

    @Test
    public void testGetUtcMinutes() {
        assertEquals(0, gga.getUtcMinutes());
    }

    @Test
    public void testGetUtcSeconds() {
        assertEquals(44, gga.getUtcSeconds(), 0.001);
    }

    @Test
    public void testSetAltitude() {
        final double alt = 11.111;
        gga.setAltitude(alt);
        assertEquals(alt, gga.getAltitude(), 0.0001);
    }

    @Test
    public void testSetAltitudeUnits() {
        assertEquals(Units.METER, gga.getAltitudeUnits());
        gga.setAltitudeUnits(Units.FEET);
        assertEquals(Units.FEET, gga.getAltitudeUnits());
    }

    @Test
    public void testSetDgpsAge() {
        final double age = 33.3;
        gga.setDgpsAge(age);
        assertEquals(age, gga.getDgpsAge(), 0.001);
    }

    @Test
    public void testSetDgpsStationId() {
        gga.setDgpsStationId("0001");
        assertEquals("0001", gga.getDgpsStationId());
    }

    @Test
    public void testSetFixQuality() {
        assertEquals(GpsFixQuality.NORMAL, gga.getFixQuality());
        gga.setFixQuality(GpsFixQuality.INVALID);
        assertEquals(GpsFixQuality.INVALID, gga.getFixQuality());
    }

    @Test
    public void testSetGeoidalHeight() {
        gga.setGeoidalHeight(3.14);
        assertEquals(3.14, gga.getGeoidalHeight(), 0.0001);

    }

    @Test
    public void testSetGeoidalHeightUnits() {
        assertEquals(Units.METER, gga.getGeoidalHeightUnits());
        gga.setGeoidalHeightUnits(Units.FEET);
        assertEquals(Units.FEET, gga.getGeoidalHeightUnits());
    }

    @Test
    public void testSetHorizontalDOP() {
        assertEquals(2.0, gga.getHorizontalDOP(), 0.001);
        gga.setHorizontalDOP(0.025);
        assertEquals(0.025, gga.getHorizontalDOP(), 0.001);
    }

}