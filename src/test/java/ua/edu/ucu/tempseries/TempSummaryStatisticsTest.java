package ua.edu.ucu.tempseries;

import junit.framework.TestCase;

public class TempSummaryStatisticsTest extends TestCase {
    TempSummaryStatistics stats;
    public void setUp() throws Exception {
        super.setUp();
        TemperatureSeriesAnalysis serAn = new TemperatureSeriesAnalysis(new double[]{1.0, 1.0, 1.0});
        stats = serAn.summaryStatistics();
    }

    public void testGetAvgTemp() {
        assertEquals(1.0, stats.getAvgTemp(), 0.00001);
    }

    public void testGetDevTemp() {
        assertEquals(0.0, stats.getDevTemp(), 0.00001);
    }

    public void testGetMinTemp() {
        assertEquals(1.0, stats.getMinTemp(), 0.00001);
    }

    public void testGetMaxTemp() {
        assertEquals(1.0, stats.getMaxTemp(), 0.00001);
    }
}