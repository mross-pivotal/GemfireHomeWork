package com.gopivotal.bookshop.domain;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable
{
	private static final long serialVersionUID = 7526471155622776147L;

	private Integer postId;
	private Date createdAt;
	private Integer userId;
	private String text;
	
	public Post() {}




	public Post(Integer postId, Date createdAt, String text, 
			Integer userId)
	{
		super();
		
		this.postId= postId;
		this.createdAt= createdAt;
		this.userId = userId;
		this.text = text;
		
	}
	
	public float getPostId()
	{
		return postId;
	}



	public void setPostId(Integer postId)
	{
		this.postId = postId;
	}
	
	

	public Integer getUserId()
	{
		return userId;
	}



	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}


	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		this.text = text;
	}
	public Date getCreatedAt()
	{
		return createdAt;
	}
	public void setCreatedAt(Date createdAt)
	{
		this.createdAt = createdAt;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((postId == null) ? 0 : postId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (postId == null)
		{
			if (other.postId != null)
				return false;
		} else if (!postId.equals(other.postId))
			return false;
		return true;
	}
}
