/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jena.examples.rdf ;

import java.util.Scanner;



/** Tutorial 4 - create a model and write it in XML form to standard out
 */
public class Tutorial04 extends Object {
    
   
	public static void main (String args[]) {

		Scanner scanner = new Scanner(System.in);
		while(true){
		System.out.println("\n\n1. Popular\n"
				+ "2. Post Curtido\n"
				+ "3. Mesma Localização\n"
				+ "4. Mesma Hashtag\n"
				+ "5. Seguem paginas em comum\n"
				+ "6. Totas Regras\n");
		int key = scanner.nextInt(); 
		
		SocialNetworkInference s = new SocialNetworkInference();
		s.loadOWLFile();
			switch (key) {
			case 1:
				s.popularUsers();
				break;
			
			case 2:
				s.likedPage();
				break;
			

			case 3:
				s.sameLocation();
				break;
			
			case 4: 
				s.sameHashtag();
				break;
			case 5: 
				s.seguemComum();
				break;	
			case 6:
				s.allRules();
				break;
			default: 
				break;
			}
		}
	
    }
}