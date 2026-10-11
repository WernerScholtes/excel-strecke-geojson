package com.strecke.geojson;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelStreckeGeoJsonApp {
    public static void main(String[] args) {
        try {
            AppConfig config = AppConfig.load(args);
            List<PointRecord> points = new ExcelKonfigurationReader(config).read();
            Map<String, List<PointRecord>> byStrecke = groupByStrecke(points);
            Path outputDir = resolveOutputDir(config);
            GeoJsonWriter writer = new GeoJsonWriter();
            for (Map.Entry<String, List<PointRecord>> entry : byStrecke.entrySet()) {
                Path file = outputDir.resolve(GeoJsonWriter.toFileName(entry.getKey()));
                writer.writeFeatureCollection(file, entry.getValue());
                System.out.println(file.toAbsolutePath() + " (" + entry.getValue().size() + " Punkte)");
            }
            System.out.println("Fertig. Dateien: " + byStrecke.size() + ", Punkte: " + points.size());
        } catch (Exception ex) {
            System.err.println("Fehler: " + ex.getMessage());
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }

    static Map<String, List<PointRecord>> groupByStrecke(List<PointRecord> points) {
        Map<String, List<PointRecord>> grouped = new LinkedHashMap<String, List<PointRecord>>();
        for (PointRecord point : points) {
            List<PointRecord> list = grouped.get(point.getStrecke());
            if (list == null) {
                list = new ArrayList<PointRecord>();
                grouped.put(point.getStrecke(), list);
            }
            list.add(point);
        }
        return grouped;
    }

    private static Path resolveOutputDir(AppConfig config) {
        String configured = config.get("excel.output.dir");
        if (!configured.isEmpty()) {
            return Paths.get(configured);
        }
        Path input = Paths.get(config.require("excel.input"));
        Path parent = input.getParent();
        return parent == null ? Paths.get("geojson") : parent.resolve("geojson");
    }
}
