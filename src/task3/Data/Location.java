package task3.Data;

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

    public void setZoneId(final ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


    public String getFullAddress() {
        return address + ", " + city + "(" + country + ")";
    }

    public ZonedDateTime getLocalDateTime(final ZonedDateTime time) {
        return time.withZoneSameInstant(zoneId);
    }

    @Override
    public String toString() {
        return city + "/" + country;
    }
}
