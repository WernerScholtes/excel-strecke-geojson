package com.strecke.geojson;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GeoJsonLogicTest {
    @Test
    public void groupsByStrecke() {
        List<PointRecord> points = Arrays.asList(
                new PointRecord("A", "1", 50.1, 8.1, "A3"),
                new PointRecord("B", "2", 50.2, 8.2, "A5"),
                new PointRecord("C", "3", 50.3, 8.3, "A3"));
        Map<String, List<PointRecord>> grouped = ExcelStreckeGeoJsonApp.groupByStrecke(points);
        assertEquals(2, grouped.size());
        assertEquals(2, grouped.get("A3").size());
        assertEquals(1, grouped.get("A5").size());
    }

    @Test
    public void parsesGermanDecimal() {
        assertEquals(Double.valueOf(8.123), ExcelKonfigurationReader.parseNumber("8,123"));
    }

    @Test
    public void sanitizesFileName() {
        assertEquals("A3 Nord.geojson", GeoJsonWriter.toFileName("A3 Nord"));
        assertEquals("A5_Sued.geojson", GeoJsonWriter.toFileName("A5/Sued"));
    }
}
