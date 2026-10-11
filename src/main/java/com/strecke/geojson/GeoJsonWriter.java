package com.strecke.geojson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GeoJsonWriter {
    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public void writeFeatureCollection(Path file, List<PointRecord> points) throws IOException {
        ObjectNode collection = mapper.createObjectNode();
        collection.put("type", "FeatureCollection");
        ArrayNode features = collection.putArray("features");
        for (PointRecord point : points) {
            ObjectNode feature = features.addObject();
            feature.put("type", "Feature");
            ObjectNode geometry = feature.putObject("geometry");
            geometry.put("type", "Point");
            ArrayNode coordinates = geometry.putArray("coordinates");
            coordinates.add(point.getLongitude());
            coordinates.add(point.getLatitude());
            ObjectNode properties = feature.putObject("properties");
            properties.put("Standort", point.getStandort());
            properties.put("PermanentId", point.getPermanentId());
        }
        Path parent = file.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        mapper.writeValue(file.toFile(), collection);
    }

    public static String toFileName(String strecke) {
        String name = strecke.trim().replaceAll("[\\\\/:*?\"<>|]", "_");
        if (name.isEmpty()) {
            name = "strecke";
        }
        return name + ".geojson";
    }
}
