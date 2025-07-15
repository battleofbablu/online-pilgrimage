package com.example.demo.Model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Booking 
{
 @Id
 @GeneratedValue(generator = "UUID")
 @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private String booking_id;
  private String temple_id;
  private String temple_name;
  private String name;
  private String address;
  private String contact;
  private int pilgrims;
  private int children;
  private String date;
  private String time;
public String getBooking_id() {
	return booking_id;
}
public void setBooking_id(String booking_id) {
	this.booking_id = booking_id;
}
public String getTemple_id() {
	return temple_id;
}
public void setTemple_id(String temple_id) {
	this.temple_id = temple_id;
}
public String getTemple_name() {
	return temple_name;
}
public void setTemple_name(String temple_name) {
	this.temple_name = temple_name;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getContact() {
	return contact;
}
public void setContact(String contact) {
	this.contact = contact;
}
public int getPilgrims() {
	return pilgrims;
}
public void setPilgrims(int pilgrims) {
	this.pilgrims = pilgrims;
}
public int getChildren() {
	return children;
}
public void setChildren(int children) {
	this.children = children;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}
@Override
public String toString() {
	return "Booking [booking_id=" + booking_id + ", temple_id=" + temple_id + ", temple_name=" + temple_name + ", name="
			+ name + ", address=" + address + ", contact=" + contact + ", pilgrims=" + pilgrims + ", children="
			+ children + ", date=" + date + ", time=" + time + "]";
}
  
}
