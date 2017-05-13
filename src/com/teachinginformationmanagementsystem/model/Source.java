package com.teachinginformationmanagementsystem.model;

public class Source {
	private int sou_id;
	private int sou_courseid;
	private String sou_name;
	private String sou_date;
	
	public Source(int id, int courseid, String name, String date){
		sou_id = id;
		sou_courseid = courseid;
		sou_name = name;
		sou_date = date;
	}
	
	public int getId(){
		return sou_id;
	}
	
	public int getCourseId(){
		return sou_courseid;
	}
	
	public String getName(){
		return sou_name;
	}
	public String getDate()
	{
		return sou_date;
	}
}
