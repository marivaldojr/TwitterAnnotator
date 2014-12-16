package jena.examples.rdf;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import twitter4j.HashtagEntity;
import twitter4j.PagableResponseList;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.api.FavoritesResources;
import twitter4j.api.TimelinesResources;


public class DataSetExtractor {
	
	private Twitter twitter;
	private List<String> twitterList;
	private long cursor;
    private User u;
    private PagableResponseList<User> users;
    private Iterator<User> it;
	private BufferedWriter out, outPages;
    
	public DataSetExtractor(){
		twitter = TwitterFactory.getSingleton();
	}
	
	public void extractPages(){
	    	    
	    twitterList = new ArrayList<String>();	    
	    
	    twitterList.add("ufba");
	    twitterList.add("dilmabr");
	    twitterList.add("lemondefr");
	    twitterList.add("deutschalemao");	    	    
	    
	    //-------------------------------- Printing Friends (following) Info -----------------------------------------------
	    
	    
	    try { 	
	    	out = new BufferedWriter(new FileWriter("dataset-twitter"));
			outPages = new BufferedWriter(new FileWriter("pages"));
	                	        
	        cursor = -1;
	        int countPages = 1, i = 0;
	        while(i < twitterList.size()){
	     
	        	users = twitter.getFriendsList(twitterList.get(i), cursor);
  	                
	   	        it = users.iterator();           
	   	       
	   	        String t = "http://www.twitter.com/";	   	         	        
	   	        while(it.hasNext())
	   	        {
	   	          	u = it.next();
	   	          	if (countPages <= 5){
	   	          		outPages.write(u.getScreenName() + "\n");
		   	         	out.write(twitterList.get(i) + "¬");	  
		   	          	out.write(u.getScreenName() + "¬");
		   	          	out.write(u.getName() + "¬");
		   	          	out.write(t + u.getScreenName() + "¬");
		   	          	if(u.getLocation().equals(""))
		   	          		out.write("null¬");
		   	          	else
		   	          		out.write(u.getLocation() + "¬");
		   	          	out.write(u.getFriendsCount() + "¬");	   	         
		   	          	out.write(u.getFollowersCount() + "\n");
	   	          	}else{
	   	          		break;
	   	          	}  	          		
	   	          
	   	          	countPages++;
	   	        }        	        
	        	
	   	        countPages = 1;
	   	        i++;
	        }	        
	      
	        out.close();  
	        outPages.close();
	            
	    } catch (TwitterException te) {
	        te.printStackTrace();
	        System.out.println("Failed to get friends' ids: " + te.getMessage());
	        System.exit(-1);
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	   
	}

	public void extractTweets(){		
		
		String line = "";
		twitterList = new ArrayList<String>();
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader("pages"));
			line = br.readLine(); 
			
			while(line != null){
				twitterList.add(line);
				line = br.readLine();
			}
				
			System.out.println("Size: " + twitterList.size());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		ResponseList<Status> users = null;
		Iterator<Status> it;
		Status u;
		cursor = -1;
		
		
	    try {
			out = new BufferedWriter(new FileWriter("tweets"));
			
			int tweetCount = 1, i = 0;
	        while(i < twitterList.size()){
	     
	        	users = twitter.getUserTimeline(twitterList.get(i));			
		                
	   	        it = users.iterator();	   	        
	   	        
	   	        System.out.println(i + ": ");
	   	        
	   	        while(it.hasNext()){
	   	        	
	   	        	u = it.next();
	   	        	if (tweetCount <= 1){	   	        		
	   	        		
	   	        		out.write(u.getId() + "¬");	   	        		
	   	        		out.write(u.getUser().getScreenName() + "¬");   	        		
	   	        		out.write(u.getText().replaceAll("\n", " ") + "¬");
	   	        		out.write(u.getCreatedAt() + "¬");   	   	        	
	   	   	        	
	   	   	        	if(u.getHashtagEntities().length > 0){
	   	   	        		for(HashtagEntity hashtag: u.getHashtagEntities()){
	   	   	        			out.write(hashtag.getText() + "¬");
	   	   	        		}
	   	   	        		
	   	   	        	}
	   	   	        	out.newLine();
	   	        	}else{
	   	        		break;
	   	        	}	        		
	   	        	
	   	        	tweetCount++;	   	        	
	   	        }       	
		        
		        tweetCount = 1;
	   	        i++;
			
	        }
			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	        
	    
	}
	
	public void extractTweetsMyTimeline(){

		ResponseList<Status> users = null;
		Iterator<Status> it;
		Status u;
		
		try {
			out = new BufferedWriter(new FileWriter("tweets", true));
			users = twitter.getUserTimeline("mitchbr91");	
			
			it = users.iterator();
			
			while(it.hasNext()){
				u = it.next();
				
				out.write(u.getId() + "¬");	   	        		
	        	out.write(u.getUser().getScreenName() + "¬");   	        		
	        	out.write(u.getText().replaceAll("\n", " ") + "¬");
	        	out.write(u.getCreatedAt() + "¬");   	   	        	
	   	        	
	   	        if(u.getHashtagEntities().length > 0){
	   	        	for(HashtagEntity hashtag: u.getHashtagEntities()){
	   	        		out.write(hashtag.getText() + "¬");
	   	        	}
	   	        	
	   	        }
	   	        out.newLine();
				
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		         
        	
	}
	
	public void extractFavorites(String screenname){		
		   	
		    try {
				out = new BufferedWriter(new FileWriter("favorites"));
				ResponseList<Status> favorites = twitter.getFavorites(screenname);
				
				for(Status s: favorites){
					out.write(screenname + "¬");
					out.write(s.getId() + "\n");					
				}				
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}