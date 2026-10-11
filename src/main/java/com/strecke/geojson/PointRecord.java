package com.strecke.geojson;

public class PointRecord {
    private final String standort;
    private final String permanentId;
    private final double latitude;
    private final double longitude;
    private final String strecke;

    public PointRecord(String standort, String permanentId, double latitude, double longitude, String strecke) {
        this.standort = standort;
        this.permanentId = permanentId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.strecke = strecke;
    }

    public String getStandort() {
        return standort;
    }

    public String getPermanentId() {
        return permanentId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getStrecke() {
        return strecke;
    }
}
