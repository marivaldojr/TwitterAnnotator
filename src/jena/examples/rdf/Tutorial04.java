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



/** Tutorial 4 - create a model and write it in XML form to standard out
 */
public class Tutorial04 extends Object {
    
   
	public static void main (String args[]) {

		/*
		DataSetExtractor ex = new DataSetExtractor();
		ex.extractPages();
		ex.extractTweets();
		ex.extractTweetsMyTimeline();
		ex.extractFavorites("mitchbr91");
		*/
			
		SocialNetworkInference s = new SocialNetworkInference();
		//s.createPages();
		//s.createTweets();
		//s.createFavorites();
		
		s.popularUsers();		       
    }
}