package com.gopivotal.bookshop.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class User implements Serializable
{
	
	
	private static final long serialVersionUID = 7526471155622776147L;

	  
	private Integer id;
		
	private String name;

	private ArrayList <Integer> postIds;
	
	public User(){}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public User (int id, String name)
	{
		super();
		this.id = new Integer(id);
		this.name= name;
		this.postIds = new ArrayList();
		
	}
	
	
	public User(Integer id, String name, ArrayList <Integer> postIds)
	{
		super();
		this.id = id;
		this.name=name;
		this.postIds = postIds;
	
	}
	
	
	public void addPost(Integer postIdKey)
	{
		if (postIds == null)
		{
			postIds = new ArrayList<Integer>();
		}
		
		postIds.add(postIdKey);
	}

	public ArrayList <Integer> getPostIds()
	{
		//Implement query service for getting posts
		return postIds;
	}

	public void setPosts(ArrayList <Integer> postIds)
	{
		this.postIds = postIds;
	}

	public Integer getUserId()
	{
		return id;
	}
	public void setUserId(Integer userId)
	{
		this.id = userId;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((name == null) ? 0 : name.hashCode());
		
		result = prime * result
				+ ((postIds == null) ? 0 : postIds.hashCode());
		
		return result;
	}
	
	


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;

		if (postIds == null) {
			if (other.postIds != null)
				return false;
		} else if (!postIds.equals(other.postIds))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "User [UserId=" + id
				+ ", Name=" + name + " posts=" + postIds.toString() + "]";
	}
	

	
}
