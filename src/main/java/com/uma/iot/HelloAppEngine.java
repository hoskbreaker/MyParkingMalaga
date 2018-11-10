package com.uma.iot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.*;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


@WebServlet(
    name = "MyParkingMalaga",
    urlPatterns = {"/save"}
)
public class HelloAppEngine extends HttpServlet {


  int i = 0;
  int j = 0;
  CSVReaderInJava parking= new CSVReaderInJava();
  @Override
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
	  DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	  Entity ent=new Entity("localizacion");
	  ent.setProperty("id", i++);
	  ent.setProperty("lat", new Float(request.getParameter("lat")));
	  ent.setProperty("lon", new Float(request.getParameter("lon")));
	  ds.put(ent);
	  Entity entity=new Entity("aparcamientos");
	  //entity.setProperty("id",parking.getID());
	  entity.setProperty("nombre",parking.RowNames());
	  entity.setProperty("latitud", parking.RowLatitude());
	  entity.setProperty("longitud", parking.RowLongitude());
	  ds.put(entity);
	  
  }
}