package com.uma.iot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

class Book { 
	private String nombre,latitud,longitud;
	
	public Book(String nombre, String latitude, String longitude) {
		this.nombre = nombre;
		this.latitud= latitude;
		this.longitud = longitude;
	} 
	
	public String getName() {
		return nombre;
	} 
//	public void setName(String name) {
//		this.nombre = name;
//	}
	public String getLatitude() {
		return latitud; 
	} 
//	public void setLatitude(float latitude) {
//		this.latitud = latitude;
//	}
	public String getLongitude() {
		return longitud; 
	} 
//	public void setLongitude(float longitude) {
//		this.longitud = longitude;
//	}
//	@Override
//	public String toString() {
//		return "Book [name=" + name + ", price=" + price + ", author=" + author + "]";
//	}
}

public class CSVReaderInJava{
	URL url;
	URLConnection urlConn;
	InputStreamReader inputCSV;
	BufferedReader in;
	String nextLine;
	int i =0;
	
	@SuppressWarnings("null")
	public String[] RowNames()
	{
		List<Book> books = ReadCSV();
		String[] aux = null;
		int i = 0;
		// let's print all the person read from CSV file
		for (Book b : books) {
			aux[i] = b.getName();
			//System.out.println(b);
			i++;
		}
		return aux;
	}
	
	@SuppressWarnings("null")
	public String[] RowLatitude()
	{
		String[] aux = null;
		int i =0;
		List<Book> books = ReadCSV();
		// let's print all the person read from CSV file
		for (Book b : books) {
			aux[i] = String.valueOf(b.getLatitude());
			//System.out.println(b);
			i++;
		}
		return aux;

	}
	
	@SuppressWarnings("null")
	public String[] RowLongitude()
	{
		int i = 0;
		String[] aux = null;
		List<Book> books = ReadCSV();
		// let's print all the person read from CSV file
		for (Book b : books) {
			aux[i] = String.valueOf(b.getLongitude());
			//System.out.println(b);
			i++;
		}
		return aux;

	}
	
	public List<Book> ReadCSV() {
		 List<Book> books = new ArrayList<>();
		try {
			url = new URL("https://datosabiertos.malaga.eu/recursos/aparcamientos/ocupappublicosmun/ocupappublicosmun.csv");
			urlConn = url.openConnection();
			inputCSV = new InputStreamReader(
			          ((URLConnection) urlConn).getInputStream());
			
			in = new BufferedReader(inputCSV);
			
			while ((nextLine = in.readLine()) != null) {
				
				String line = in.readLine(); // loop until all lines are read
				while (line != null) { 
					// use string.split to load a string array with the values from
					// each line of 
					// the file, using a comma as the delimiter 
					String[] attributes = line.split(","); 
					Book book = createBook(attributes); 
					// adding book into 0
					books.add(book); 
					// read next line before looping 
					// if end of file reached, line would be null 
					line = in.readLine(); 
				}
			  }
		}catch	(Exception e) {
		  	System.out.println("Exception occurred");
		  	
		}
		return books;
	}
	
	private static Book createBook (String[] metadata) {
		String name = metadata[0];
		String latitude = metadata[1];
		String longitude = metadata[2];
		// create and return book of this metadata 
		return new Book(name, latitude, longitude);
	}

}