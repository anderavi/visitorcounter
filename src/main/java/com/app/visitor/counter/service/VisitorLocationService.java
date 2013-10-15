package com.app.visitor.counter.service;

import java.io.IOException;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.app.visitor.counter.service.bean.VisitorLocation;
import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.maxmind.geoip.regionName;

@Service
public class VisitorLocationService {

    public VisitorLocation getLocation(String ipAddress) {

        String dataFile = "location/GeoLiteCity.dat";
        return getLocation(ipAddress, dataFile);

    }

    private VisitorLocation getLocation(String ipAddress, String locationDataFile) {

        VisitorLocation serverLocation = null;

        URL url = getClass().getClassLoader().getResource(locationDataFile);

        if (url == null) {
            System.err.println("location database is not found - " + locationDataFile);
        } else {

            try {

                serverLocation = new VisitorLocation();

                LookupService lookup = new LookupService(url.getPath(), LookupService.GEOIP_MEMORY_CACHE);
                Location locationServices = lookup.getLocation(ipAddress);
                if (locationServices == null) {
                    serverLocation.setCountryCode("localhost");
                    serverLocation.setCountryName("localhost");
                    serverLocation.setRegion("localhost");
                    serverLocation.setRegionName("localhost");
                    serverLocation.setCity("localhost");
                    serverLocation.setPostalCode("localhost");
                    serverLocation.setLatitude("localhost");
                    serverLocation.setLongitude("localhost");
                } else {
                    serverLocation.setCountryCode(locationServices.countryCode);
                    serverLocation.setCountryName(locationServices.countryName);
                    serverLocation.setRegion(locationServices.region);
                    serverLocation.setRegionName(regionName.regionNameByCode(locationServices.countryCode,
                            locationServices.region));
                    serverLocation.setCity(locationServices.city);
                    serverLocation.setPostalCode(locationServices.postalCode);
                    serverLocation.setLatitude(String.valueOf(locationServices.latitude));
                    serverLocation.setLongitude(String.valueOf(locationServices.longitude));

                }
            } catch (IOException e) {

                System.err.println(e.getMessage());

            }

        }

        return serverLocation;

    }
}