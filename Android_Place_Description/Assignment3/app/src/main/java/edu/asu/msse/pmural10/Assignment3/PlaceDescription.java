/*
* Created by Prashanth Murali on 2/09/17.
* Copyright © 2017 Prashanth Murali. All rights reserved.
* Right To Use for the instructor and the University to build and evaluate the software package
* @author Prashanth Murali mail to: pmurali10@asu.edu
* @version 1.0 February 9, 2017
 */
package edu.asu.msse.pmural10.Assignment3;

public class PlaceDescription {

    public String name;
    public String description;
    public String category;
    public String addressTitle;
    public String addressText;
    public String elevation;
    public String lat;
    public String lon;

    PlaceDescription() {
        //System.out.println("");
    }

    void placeDetails(String name, String description, String category, String addressTitle, String addressText, String elevation, String lat, String lon) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.addressTitle = addressTitle;
        this.addressText = addressText;
        this.elevation = elevation;
        this.lat = lat;
        this.lon = lon;
    }
}
