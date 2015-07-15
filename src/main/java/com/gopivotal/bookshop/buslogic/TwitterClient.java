package com.gopivotal.bookshop.buslogic;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.gemstone.gemfire.cache.execute.RegionFunctionContext;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.execute.Execution;
import com.gemstone.gemfire.cache.execute.FunctionService;
import com.gemstone.gemfire.cache.execute.ResultCollector;
import com.gemstone.gemfire.cache.partition.PartitionRegionHelper;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.pdx.PdxInstance;
import com.gemstone.org.jgroups.util.List;
import com.gopivotal.bookshop.domain.Post;
import com.gopivotal.bookshop.domain.User;

// TODO-09: Run this class to verify correct implementation (be sure to start server & locators using server-bootstrap starServers script)
public class TwitterClient {

	public static void main(String[] args) throws Exception {
		new TwitterClient().run();
	}

	public void run() throws Exception {

		ClientCache cache = new ClientCacheFactory()
				.set("name", "ClientWorker")
				.set("cache-xml-file", "xml/clientCache.xml").create();
		Region<Integer, Post> posts = cache.getRegion("Posts");
		Region<Integer, User> users = cache.getRegion("Users");
		// TODO-07: Add code to execute the function using the totalPrice field on the BookOrder object 
		
	     Scanner in = new Scanner(System.in);
     
	     System.out.println("1. Add Post");
	     System.out.println("2. Get All Posts for a given user");
	     System.out.println("3. Monitor Posts for a user");
         System.out.println("0. Quit");
         // handle user commands
         boolean quit = false;
         int menuItem;
         do {
               System.out.print("Choose menu item: ");
               menuItem = in.nextInt();
               switch (menuItem) {
               case 1:
            	   	User user = users.get(1);
                     System.out.println("You are logged in as  " + user.getName());
                     System.out.println("Please enter your post:");
                     String newPost = in.next();
                     System.out.println(newPost);
                     Integer postKey = posts.keySetOnServer().size()+1;
                     Post post = new Post(postKey, new Date(),newPost, user.getUserId());
                     posts.put(postKey, post);
                     user.addPost(postKey);
                     users.put(user.getUserId(),user);
                     
                     break;
               case 2:
                     System.out.println("What user would you like to see the posts for?");
                     Execution execution = FunctionService.onRegion(posts).withArgs("totalPrice")
             				.withCollector(new SummingResultCollector());

                     
                 
             		ResultCollector rc = execution.execute("com.gopivotal.bookshop.buslogic.GetAllPostsFunction");
                     ArrayList results = (ArrayList) rc.getResult();
                     System.out.println(results.get(0));
                   
//                     
//                     Iterator<Struct> itr = result.elements().;
//                     
//                     
//                     for(Post p: result){
//                    	 
//                     }
                     // do something...
                     break;
               case 3:
                     System.out.println("You've chosen item #3");
                     // do something...
                     break;
               case 0:
                     quit = true;
                     break;
               default:
                     System.out.println("Invalid choice.");
               }
         } while (!quit);
         System.out.println("Bye-bye!");
   
		
		
		
//		Execution execution = FunctionService.onRegion(books).withArgs("totalPrice")
//				.withCollector(new SummingResultCollector());
//
//		ResultCollector rc = execution.execute("com.gopivotal.bookshop.buslogic.GenericSumFunction");
//		
//		BigDecimal number = (BigDecimal) rc.getResult();
//		assertEquals(new BigDecimal("93.95"), number); // 40.98 + 52.97
	
		
		// TODO-08: Get result and assert that the two orders total $93.95

		
		
	
		cache.close();
	}

}
