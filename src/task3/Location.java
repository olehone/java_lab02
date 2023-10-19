package task3;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

//TODO: add weather
public class Location {
    private String country;
    private String city;
    private String address;
    private double latitude;
    private double longitude;
    private ZoneId zoneId;
    public Location(final String address, final String country, final String city, final double latitude, final double longitude, final ZoneId zoneId) {
        this.address = address;
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zoneId = zoneId;
    }
    public Location(final String address, final String country, final String city, final double latitude, final double longitude, final String utsOffset) {
        this.address = address;
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zoneId = ZoneOffset.of(utsOffset);
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(final double longitude) {
        this.longitude = longitude;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }
    public void setZoneId(final ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public String getFullAddress() {
        return address + ", " + city + "(" + country + ")";
    }

    public ZonedDateTime getLocalDateTime(final ZonedDateTime time) {
        return time.withZoneSameInstant(zoneId);
    }

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

    @Override
    public String toString() {
        return city + "/" + country;
    }
}
