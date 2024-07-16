package com.minsproject.league.util;

public class RangeCalculator {

    private RangeCalculator() {}

    private static final double EARTH_RADIUS = 6371;

    public static class LatLonRange {
        public final double minLat;
        public final double maxLat;
        public final double minLon;
        public final double maxLon;

        public LatLonRange(double minLat, double maxLat, double minLon, double maxLon) {
            this.minLat = minLat;
            this.maxLat = maxLat;
            this.minLon = minLon;
            this.maxLon = maxLon;
        }
    }

    public static LatLonRange calculateRange(double lat, double lon, double radius) {
        double latChange = Math.toDegrees(radius / EARTH_RADIUS);

        double lonChange = Math.toDegrees(
                Math.asin(Math.sin(radius / EARTH_RADIUS) / Math.cos(Math.toRadians(lat)))
        );

        return new LatLonRange(
                lat - latChange,
                lat + latChange,
                lon - lonChange,
                lon + lonChange
        );
    }

    public static double calculateDistance(double latFrom, double lonFrom, double latTo, double lonTo) {
        double dLat = Math.toRadians(latTo - latFrom);
        double dLon = Math.toRadians(lonTo - lonFrom);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(latFrom)) * Math.cos(Math.toRadians(latTo)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}
