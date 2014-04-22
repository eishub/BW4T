# BlocksWorld for Teams (BW4T)
### Table of Contents  
[Building](#building)  
[Running the server in Eclipse](#running-the-server-in-eclipse)  
[Setting up the Class Path](#setting-up-the-class-path)  
[Download Notice](#download-notice)  

### Building
This project has a few special building requirements.

The `src` directory contains both client and server code.
The source can be built normally to the `bin/` directory (made in the root of this project by Eclipse).

A copy of the bin directory in `BW4TServer/` is *required*. If you change the bin (e.g., by changing the source), you must (manually) copy the new bin into `BW4TServer/`

### Running the server in Eclipse

**Main Class**: `nl.tudelft.bw4t.server.BW4TEnvironment`

**Arguments**: `-scenario BW4TServer/BW4T.rs -map BW4TServer/maps/Map1 -serverip localhost -serverport 8000`

**VM Args**: `-Xmx500M -Xss20M`

### Setting up the Class Path

#### For Repast Symphony 2 Beta:
 * Add `repast.simphony.bin_and_src_2.0.0/repast.simphony.bin_and_src.jar`

#### For Repast Symphony 2 Final:
 * Add the `BW4TServer.jar`
 * Add all jars in `/Volumes/apps/Repast-Simphony-2.0/eclipse/plugins/repast.simphony.runtime_2.0.1/lib`
 * Add `Repast-Simphony-2.0/eclipse/plugins/repast.simphony.runtime_2.0.1/bin` directory using the *"advanced"* button.

### Download Notice
Originally there was approximately 2.3GB in executables in the repository under a directory called `downloads`. In order to avoid this, I will simply post the links here. Download the ones which are suited to you operating system.

#### Repast Symphony 2 Beta
http://sourceforge.net/projects/repast/files/Repast%20Simphony/Repast%20Simphony%202.0%20beta/

#### Repast Symphony 2 Final
http://sourceforge.net/projects/repast/files/Repast%20Simphony/Repast%20Simphony%202.0/

