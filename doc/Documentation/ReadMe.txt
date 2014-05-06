BlocksWorldForTeam2 v1.0

This version has been tested and been confirmed working with Repast Simphony 2.0 beta and GOAL version 3959


ESSENTIAL!!!!!!!!
BW4TServer directory has its OWN COPY OF THE BIN 
If it is not there yet you may have to copy it after compiling (which is done by default 
to the root of BW4T2)_.
YOU MUST COPY MANUALLY the bin directory if you fix anything in the source code
It is unclear why this is this way, Thomas said something about Repast needs it, but no details.




Eclipse Setup
To compile this project you need to set up several things first
1. Install Repast (http://repast.sourceforge.net/)
2. Add the eis library (GOAL/dep) to the project settings
3. Add all jars in dep/ directory to the project settings

Setting up Eclipse to run the BW4TServer
1. Create a new run configuration in Eclipse with as main method BW4TEnvironment
2. Add the following to the program arguments:
	-scenario .\BW4TServer\BW4T.rs -map .\BW4TServer\maps\ColorTestScenario -serverip localhost -serverport 8000
3. Add the following to the VM arguments:
	-Djava.security.policy=.\BW4TServer\server.policy -Xss10M -Xmx400M
4. Remove all user entries from the class path (INCLUDING the BlocksWorldForTeam2 folder)
5. Add jar:
	BW4TServer/BW4TServer.jar
6. Add external jars:
	<RepastInstallationDirectory>/eclipse/plugins/repast.simphony.runtime_2.0.0/lib (add all jars in this folder)
7. Add external folder:
	<RepastInstallationDirectory>/eclipse/plugins/repast.simphony.runtime_2.0.0/bin
	
Running the server
Before running the run configuration made for the server do the following to make sure that any updates are reflected in the server
1. Use the BuildServer.xml file to update the BW4TServer.jar
2. Copy the project bin folder to BW4TServer/bin and replace all items
3. Run the server

Setting up Eclipse to run the BW4TClient (Java Agents)
1. Create a new run configuration in Eclispe with as main method BW4TRemoteEnvironment
2. Add the following to the program arguments:
	-clientip localhost -serverip localhost -clientport 2000 -serverport 8000 -launchgui true -map .\BW4TClient\environments\maps\ColorTestScenario -agentcount 0 -humancount 2
3. Add the following to the VM arguments:
	-Djava.security.policy=../BlocksWorldForTeam2/BW4TClient/environments/client.policy
4. The classpath should be the default classpath

Setting up Eclipse to run the BW4TClient (GOAL)
1. Add the following to the VM arguments of the GOAL run configuration
	-Djava.security.policy=..\BlocksWorldForTeam2\BW4TClient\environments\client.policy
	
Using the environment with Java
1. Follow the instructions above to run the server
2. Run the client using the run configuration you made above, after the repast window has appeared.
	
Using the environment with GOAL
1. Follow the instructions above to run the server
2. Run the buildClient.xml file
3. Start GOAL
4. GOAL preferences/Environment/ and set "MAS Settings" to "Specific absolute path" and select
the BW4TClient/environments directory inside the BlocksWorldForTeam2 project folder.
5. copy BW4TClient/GOALagents/bw4t2.mas2g in the BlocksWorldForTeam2 project folder to the file "bw4t2.tmp.mas2g" in same directory.
6. Edit the map location intialization parameter in the temp file. 
	Currently it is assigned to "environments/maps/ColorTestScenario", for GOAL to find it in the current state it should be set to:
	"../BlocksWorldForTeam2/BW4TClient/environments/maps/ColorTestScenario"
	Also, change humancount="2" into agentcount="2". 
7. Select the new mas2g and run it.
8. Press the "power" button in the repast simulator. Goal entities should appear
9. Start the sim
10. Start GOAL mas (mas was paused so far).

Creating the release files
1. Copy the project bin folder to BW4TServer/bin and replace all items
2. Run the buildClient.xml file
3. Run the BuildServer.xml file
4. Make sure that the change made in "Using the environment with GOAL" above (step 6) was reverted in the BW4TClient/GOALAgents/bw4t2.mas2g file.
4. Run the CreateBW4TClientZip.xml file
5. Run the CreateBW4TServerZip.xml file
6. Release the zip files including the instructions in the doc folder