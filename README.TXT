This project has some special building requirements.

The src directory contains both client and server code.
The src can be built normally to the bin/ directory (made in the root of this project by Eclipse).

BUT there must be a copy of the bin directory in BW4TServer/. 
If you change the bin (eg, by changing the source), you must (manually) 
copy the new bin into BW4TServer/

If you want to run the server with Eclipse, you need 

* main class: nl.tudelft.bw4t.server.BW4TEnvironment
* arguments: -scenario BW4TServer/BW4T.rs -map BW4TServer/maps/Map1 -serverip localhost -serverport 8000
* VM args: -Xmx500M -Xss20M


* class path set up as follows:

WITH SYMPHONY 2 BETA:
add repast.simphony.bin_and_src_2.0.0/repast.simphony.bin_and_src.jar

 WITH SYMPHONY 2 FINAL:
 * add the BW4TServer.jar
 * add all jars in /Volumes/apps/Repast-Simphony-2.0/eclipse/plugins/repast.simphony.runtime_2.0.1/lib
 * add Repast-Simphony-2.0/eclipse/plugins/repast.simphony.runtime_2.0.1/bin directory using the "advanced" button.
