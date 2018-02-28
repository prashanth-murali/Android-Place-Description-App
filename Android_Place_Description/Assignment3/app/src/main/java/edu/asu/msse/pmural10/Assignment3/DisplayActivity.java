/*
* Created by Prashanth Murali on 2/09/17.
* Copyright © 2017 Prashanth Murali. All rights reserved.
* Right To Use for the instructor and the University to build and evaluate the software package
* @author Prashanth Murali mail to: pmurali10@asu.edu
* @version 1.0 February 9, 2017
 */
package edu.asu.msse.pmural10.Assignment3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayActivity extends AppCompatActivity {

    public EditText nametext, description, categorytext, addressline1, addressline2, elevationtext, latdata, longdata;
    public TextView namelabel, categorylabel, addresslabel, elevationlabel, latlabel, longlabel;
    public String name1, description1, category1, address1, address2, elevation1, latitude1, longitude1;
    public static PlaceLibrary p = new PlaceLibrary();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        nametext = (EditText) findViewById(R.id.namedata);
        description = (EditText) findViewById(R.id.description);
        categorytext = (EditText) findViewById(R.id.categorydata);
        addressline1 = (EditText) findViewById(R.id.addressline1);
        addressline2 = (EditText) findViewById(R.id.addressline2);
        elevationtext = (EditText) findViewById(R.id.elevationdata);
        latdata = (EditText) findViewById(R.id.latdata);
        longdata = (EditText) findViewById(R.id.longdata);

        namelabel = (TextView) findViewById(R.id.namelabel);
        categorylabel = (TextView) findViewById(R.id.categorylabel);
        addresslabel = (TextView) findViewById(R.id.addresslabel);
        elevationlabel = (TextView) findViewById(R.id.elevationlabel);
        latlabel = (TextView) findViewById(R.id.latlabel);
        longlabel = (TextView) findViewById(R.id.longlabel);
        //parseJson("places.json");


        if (MainActivity.city != "") {
            Log.v("city name", MainActivity.city);
            getdata(MainActivity.city);

            /*PlaceDescription place = new PlaceDescription();
            place.placeDetails(name1, description1, category1, address1, address2, elevation1, latitude1, longitude1);


            nametext.setText(place.name);
            description.setText(place.description);
            categorytext.setText(place.category);
            addressline1.setText(place.addressTitle);
            addressline2.setText(place.addressText);
            elevationtext.setText(place.elevation);
            latdata.setText(place.lat);
            longdata.setText(place.lon);*/
        } else {
            PlaceDescription place = new PlaceDescription();
            place.placeDetails("", "", "", "", "", "", "", "");


        }

    }

    public void getdata(String city) {


            if (city.equals("")) {
                p.search(MainActivity.city);
            } else {

                if (!p.ifExists(city)) {
                    p.addDescription("","", "", "", "","","", "");

                }

                else
                {
                    PlaceDescription places=p.search(city);

                    if(places!=null)
                    {
                        nametext.setText(places.name);
                        description.setText(places.description);
                        categorytext.setText(places.category);
                        addressline1.setText(places.addressTitle);
                        addressline2.setText(places.addressText);
                        elevationtext.setText(places.elevation);
                        latdata.setText(places.lat);
                        longdata.setText(places.lon);
                    }
                }

            }

    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save) {

            if (MainActivity.flag1 == 1) {
                name1 = nametext.getText().toString();
                description1 = description.getText().toString();
                category1 = categorytext.getText().toString();
                address1 = addressline1.getText().toString();
                address2 = addressline2.getText().toString();
                elevation1 = elevationtext.getText().toString();
                latitude1 = latdata.getText().toString();
                longitude1 = longdata.getText().toString();

                if (!p.ifExists(name1)) {

                    if(latitude1.equals("") && longitude1.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Enter Latitude and Longitude",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    p.addDescription(name1, description1, category1, address1, address2, elevation1, latitude1, longitude1);
                    MainActivity.cities.add(name1);
                    MainActivity.cities1.add(name1);
                } else {
                    if (p.search(name1) != null) {
                        Log.v("ALREADY EXISTS","EXISTSSS");
                        Toast.makeText(getApplicationContext(),"Place Exists Already",Toast.LENGTH_SHORT).show();
                        return false;
                        //p.modify(name1, description1, category1, address1, address2, elevation1, latitude1, longitude1);
                    }
                }


                Intent intent = new Intent(DisplayActivity.this, MainActivity.class);
                startActivity(intent);

            }

            if(MainActivity.flag1==0)
            {
                name1 = MainActivity.city;
                description1 = description.getText().toString();
                category1 = categorytext.getText().toString();
                address1 = addressline1.getText().toString();
                address2 = addressline2.getText().toString();
                elevation1 = elevationtext.getText().toString();
                latitude1 = latdata.getText().toString();
                longitude1 = longdata.getText().toString();

                p.modify(name1, description1, category1, address1, address2, elevation1, latitude1, longitude1);

                Intent intent = new Intent(DisplayActivity.this, MainActivity.class);
                startActivity(intent);
            }

            //MainActivity.flag1=0;
        }

        if(id==R.id.delete)
        {
            name1 = nametext.getText().toString();
            Log.v("HELLO",name1);
            p.delete(name1);

            Intent intent = new Intent(DisplayActivity.this, MainActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

}
