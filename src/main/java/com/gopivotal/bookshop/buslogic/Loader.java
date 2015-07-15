package com.gopivotal.bookshop.buslogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gopivotal.bookshop.domain.Post;
import com.gopivotal.bookshop.domain.User;


/*
 * This is a fallback class that was used early on to bootstrap the BookOrder data. It presumed you had a locator and server(s) started.
 * In general though, the preferred approach is to run the server-bootstrap start script to start the server processes. That way, this works
 * as a stand alone when a client-only course is defined.
 * 
 */
public class Loader
{
	
	private ClientCache cache;
	
	public static void main(String[] args)
	{
		Loader loader = new Loader();
		loader.getCache();
		loader.populateUsers();
		loader.closeCache();
	}
	
	public void setCache (ClientCache cache)
	{
		this.cache = cache;
	}
	

	public void populateUsers()
	{
		Region<Integer, Post> posts = cache.getRegion("Posts");
		Region<Integer, User> users = cache.getRegion("Users");
		

		User user1 = new User(new Integer(1),"Bob Odenkirk");
		users.put(user1.getUserId(), user1);
		User user2 = new User(new Integer(2),"Sarah Palin");
		users.put(user2.getUserId(), user2);
		User user3 = new User(new Integer(3),"Gustavo Fring");
		users.put(user3.getUserId(), user3);
		User user4 = new User(new Integer(4),"Hiro Nakumura");
		users.put(user4.getUserId(), user4);
		User user5 = new User(new Integer(5),"Betty-Sue Jenkins");
		users.put(user5.getUserId(), user5);
		
		for(Integer key: users.keySetOnServer()){
			System.out.println(key);
			populatePostsForUser(users.get(key),posts,users,users.get(key).getUserId()*5);
	    }
		

//			dropDatabase(posts,users);
		
		
	}
	
	public void dropDatabase(Region<Integer, Post> posts, Region<Integer, User> users){
		for(Integer key: posts.keySetOnServer()){
			System.out.println(key);
			posts.remove(key);
		}
		
		for(Integer key: users.keySetOnServer()){
			System.out.println(key);
			users.remove(key);
		}
		System.out.println("Dropping of database successful");
	}
	
	public void populatePostsForUser(User user, Region<Integer, Post> posts, Region<Integer, User> users,Integer postKey){
		
		String [] words= generateRandomWords(5);
		System.out.println("Current number of posts is " + posts.size());
	
		for(String word: words){
			
			System.out.println("Now creating Post" + word+ " with id" + postKey + 
					"For user with Name " + user.getName());
		
			Post post= new Post(postKey, new Date(), word, 
			user.getUserId() );
			posts.put(postKey, post);
			user.addPost(postKey);
			users.put(user.getUserId(), user);
			postKey +=1;
		}
		
	}
	
	public void closeCache()
	{
		cache.close();
	}
	
	public void getCache()
	{
		this.cache = new ClientCacheFactory()
        .set("name", "ClientWorker")
        .set("cache-xml-file", "xml/clientCache.xml")
        .create();
	}
	
	//Citing http://stackoverflow.com/questions/4951997/generating-random-words-in-java
	public static String[] generateRandomWords(int numberOfWords)
	{
	    String[] randomStrings = new String[numberOfWords];
	    Random random = new Random();
	    for(int i = 0; i < numberOfWords; i++)
	    {
	        char[] word = new char[random.nextInt(8)+3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
	        for(int j = 0; j < word.length; j++)
	        {
	            word[j] = (char)('a' + random.nextInt(26));
	        }
	        randomStrings[i] = new String(word);
	    }
	    return randomStrings;
	}
}
