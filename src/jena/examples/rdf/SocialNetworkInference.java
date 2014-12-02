package jena.examples.rdf;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.XSD;



public class SocialNetworkInference {
	
	private String ontNamespace;
	private OntModel ontologyModel;
	private BufferedReader br = null;
	private StringTokenizer st;
	private String line;
	private String fileName;
	private FileWriter out;
	private String ontologyFileName;
	private File file;
	private InputStream in;
	
	public SocialNetworkInference(){
		ontNamespace = "http://www.semanticweb.org/michel/ontologies/2014/6/TwitterOntology#";
		fileName = "TwitterOntologyPopulated.owl";		
		ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
	}
	
	public void createPages(){				
		
		file = new File("TwitterOntology.owl");		
		ontologyFileName = file.getAbsolutePath();		
				
		in = FileManager.get().open(ontologyFileName);		
		
		if (in == null)
		    throw new IllegalArgumentException("File: " + ontologyFileName + " not found");		
		
		ontologyModel.read(in, ontNamespace);				
	
		String pageFollowing = "", screenname = "", pagename = "",
				urlpage = "", location = "", numFollowing = "", numFollowers = "";
		
	    	    
	    Individual pFollowing, newPage;
	    Property follows, sName, urlP, loc, nFollowing, nFollowers;
	    DatatypeProperty pName;
		
	    
		try {
			br = new BufferedReader(new FileReader("dataset-twitter"));
		    line = br.readLine();  
		  	
		    int i = 1;
		    while (line != null) {
		    	System.out.println(i++);
		    	st = new StringTokenizer(line, "¬");
		    	pageFollowing = (String) st.nextElement();
		    	screenname = (String) st.nextElement();
		    	pagename = (String) st.nextElement();
		    	urlpage = (String) st.nextElement();
		    	location = (String) st.nextElement();
		    	numFollowing =  (String) st.nextElement();
		    	numFollowers = (String) st.nextElement();
		    	
		    	System.out.println("Page Following: " + pageFollowing);
		    	System.out.println("Screenname: " + screenname);
		    	System.out.println("Pagename: " + pagename);
		    	System.out.println("Urlpage: " + urlpage);
		    	System.out.println("Location: " + location);
		    	System.out.println("NumFollowing: " + numFollowing);
		    	System.out.println("NumFollowers: " + numFollowers);
		    	System.out.println("---------------------------------------------");
		    	
		    	pFollowing = ontologyModel.getIndividual(ontNamespace + pageFollowing);		    	
		    	follows = ontologyModel.getProperty(ontNamespace + "follows");
		    	newPage = ontologyModel.createIndividual(ontNamespace + screenname, ontologyModel.getOntClass(ontNamespace + "TwitterAccount"));
		    	sName = ontologyModel.getProperty(ontNamespace + "screenname");		    	
		    	pName = ontologyModel.getDatatypeProperty(ontNamespace + "pagename");
		    	
		    	urlP = ontologyModel.getProperty(ontNamespace + "urlpage");
		    	nFollowing = ontologyModel.getProperty(ontNamespace + "numPagesFollowing");
		    	nFollowers = ontologyModel.getProperty(ontNamespace + "numFollowers");
		    	
		    	ontologyModel.add(pFollowing, follows, newPage);
		    	ontologyModel.add(newPage, pName, pagename, XSDDatatype.XSDstring);
		    	ontologyModel.add(newPage, urlP, urlpage, XSDDatatype.XSDstring);
		    	ontologyModel.add(newPage, nFollowing, numFollowing, XSDDatatype.XSDint);
		    	ontologyModel.add(newPage, nFollowers, numFollowers, XSDDatatype.XSDint);
		    	
		    	if(!location.equals("null")){
		    		loc = ontologyModel.getProperty(ontNamespace + "location");
		    		ontologyModel.add(newPage, loc, location, XSDDatatype.XSDstring);
		    	}  	
		    	
		    	

		    	line = br.readLine();		    	
		    }
		        				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      
	   	
		
		try {
			out = new FileWriter(fileName);
			ontologyModel.write(out, "RDF/XML" );
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		Reasoner reasoner = PelletReasonerFactory.theInstance().create();		
//		reasoner = reasoner.bindSchema(ontologyModel);
//		
//		//Bind the reasoner to the data model into a new Inferred model
//		Model infModel = ModelFactory.createInfModel(reasoner,ontologyModel);
				
	}

	public void createTweets(){
		file = new File("TwitterOntologyPopulated.owl");
		ontologyFileName = file.getAbsolutePath();		
		
		in = FileManager.get().open(ontologyFileName);		
		
		if (in == null)
		    throw new IllegalArgumentException("File: " + ontologyFileName + " not found");		
		
		ontologyModel.read(in, ontNamespace);
		
		String id, screenname, text, createdAt;		
		Individual tweet, twitterAccount;
		Property posts, tex, cAt, hashtag;
		
		int numHashtags;
		try {
			br = new BufferedReader(new FileReader("tweets"));			
			line = br.readLine(); 
			int i = 1;
			while(line != null){				
				
				System.out.println(i++);
				st = new StringTokenizer(line, "¬");
				numHashtags = st.countTokens();
				id = (String) st.nextElement();
				screenname = (String) st.nextElement();
				text = (String) st.nextElement();
				createdAt = (String) st.nextElement();
				
							
				tweet = ontologyModel.createIndividual(ontNamespace + id, ontologyModel.getOntClass(ontNamespace + "Tweet"));
				twitterAccount = ontologyModel.getIndividual(ontNamespace + screenname);
				tex = ontologyModel.getProperty(ontNamespace + "text");
				cAt = ontologyModel.getProperty(ontNamespace + "createdAt");
				posts = ontologyModel.getProperty(ontNamespace + "posts"); 
				
				ontologyModel.add(twitterAccount, posts, tweet);
				ontologyModel.add(tweet, tex, text, XSDDatatype.XSDstring);
				ontologyModel.add(tweet, cAt, createdAt, XSDDatatype.XSDstring);
				
				if(numHashtags > 4){
					hashtag = ontologyModel.getProperty(ontNamespace + "hashtag");
					while(st.hasMoreElements()){
						ontologyModel.add(tweet, hashtag, (String) st.nextElement(), XSDDatatype.XSDstring);
					}
				}	
								
				line = br.readLine();
				//break;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		
		
		try {
			out = new FileWriter(fileName);
			ontologyModel.write(out, "RDF/XML");
			System.out.println("BARRIL");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void createFavorites(){
		
		file = new File("TwitterOntologyPopulated.owl");
		ontologyFileName = file.getAbsolutePath();		
		
		in = FileManager.get().open(ontologyFileName);		
		
		if (in == null)
		    throw new IllegalArgumentException("File: " + ontologyFileName + " not found");		
		
		ontologyModel.read(in, ontNamespace);
		
		Individual tweet, twitterAccount;
		Property liked;
		
		String screenname = "", id = "";
		try {
			br = new BufferedReader(new FileReader("favorites"));
			line = br.readLine();
			
			while(line != null){
				st = new StringTokenizer(line, "¬");
				
				screenname = (String) st.nextElement();
				id = (String) st.nextElement();
				
				
				tweet = ontologyModel.getIndividual(ontNamespace + id);
				twitterAccount = ontologyModel.getIndividual(ontNamespace + screenname);
				liked = ontologyModel.getProperty(ontNamespace + "liked");
			
				ontologyModel.add(twitterAccount, liked, tweet);
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		 
		try {
			out = new FileWriter(fileName);
			ontologyModel.write(out, "RDF/XML");
			System.out.println("BARRIL");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
