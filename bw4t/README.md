#BW4T Main Maven Module
This is the directory contains the main maven module. BW4T is split up in six submodules which are explained below. Each submodule is compiled and tested separately by Maven. A good overview of exactly how Maven module organization works can be found at [Sonatype](http://books.sonatype.com/mvnex-book/reference/multimodule.html).

### bw4t-client 
The bw4t-client submodule is used either directly by a user or indirectly via GOAL. Basically this module allows you to connect an agent to the bw4t-server. If this is launched with the GUI enabled, the user is able to perform actions and see the percepts that the robot can perform and percept respectively.

### bw4t-core
The bw4t-core submodule basically contains lots of "model"-classes which are needed by multiple other modules. For example the AbstractMapController can be found in this class, because it is needed for the bw4t-client as well as the bw4t-server.

### bw4t-environment-store
The bw4t-environment-store allows you to configure all kinds of options regarding the map and/or environment.

### bw4t-integration-test
This module performs integration testing, by launching the server and the client and performing a small test.

### bw4t-scenario-editor
The scenario-editor allows you to configure connections options, epartners and the types of bots and their abilities/handicaps.

### bw4t-server
The bw4t-server basically runs the bw4t environment with a given configuration to which clients can connect.

