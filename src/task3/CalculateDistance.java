package task3;

public class CalculateDistance {
    private CalculateDistance(){}
    public static double calculateDistanceByCoordinates(final double latitude1, final double longitude1, final double latitude2, final double longitude2) {
        final double radiusOfEarth = 6371;

        final double latitude1Rad = Math.toRadians(latitude1);
        final double longitude1Rad = Math.toRadians(longitude1);
        final double latitude2Rad = Math.toRadians(latitude2);
        final double longitude2Rad = Math.toRadians(longitude2);

        final double differenceLatitude = latitude2Rad - latitude1Rad;
        final double differenceLongitude = longitude2Rad - longitude1Rad;

        final double a = Math.pow(Math.sin(differenceLatitude / 2), 2) + Math.cos(latitude1Rad)
                * Math.cos(latitude2Rad) * Math.pow(Math.sin(differenceLongitude / 2), 2);
        final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return radiusOfEarth * c;
    }
}
