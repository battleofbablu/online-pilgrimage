package com.example.demo.Model;

import java.util.UUID;

import jdk.jfr.DataAmount;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Data
@Entity
public class Temple 
{
	@Id
	@GeneratedValue(generator = "UUID")
	 @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "temple_id", nullable = false)
	private String temple_id;
	private String temple_name;
	private String location;
	private String details;
	private String image_url;
	private String video_url;
	private String opening_time;
	private String closing_time;
	
	private String image1;  // additional images
    private String image2;
    private String image3;
    
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getVideo_url() {
		return video_url;
	}
	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}
	public String getOpening_time() {
		return opening_time;
	}
	public void setOpening_time(String opening_time) {
		this.opening_time = opening_time;
	}
	public String getClosing_time() {
		return closing_time;
	}
	public void setClosing_time(String closing_time) {
		this.closing_time = closing_time;
	}
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	public String getImage3() {
		return image3;
	}
	public void setImage3(String image3) {
		this.image3 = image3;
	}
	@Override
	public String toString() {
		return "Temple [temple_id=" + temple_id + ", temple_name=" + temple_name + ", location=" + location
				+ ", details=" + details + ", image_url=" + image_url + ", video_url=" + video_url + ", opening_time="
				+ opening_time + ", closing_time=" + closing_time + ", image1=" + image1 + ", image2=" + image2
				+ ", image3=" + image3 + "]";
	}
	
	

}
