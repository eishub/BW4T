# Blocks World for Teams
Blocks World for Teams (**BW4T**) is a testbed EIS environment for team coordination. BW4T allows for games with human-human, agent-agent and human-agent teams of variable sizes. The goal is to jointly deliver a sequence of colored blocks in a particular order as fast as possible. A complicating factor is that the players cannot see each other.

This page descibes how users can install and use the Blocks World for Teams environment. 
For developer information please follow this [Link to Developer details](DEVELOPER.md)

Blocks World for Teams has a client-server architecture. This means that you need to install and run two parts to use it: a client and a server. The server is the Blocks World for Team Server where the real simulation takes place. The client is the agent programming environment, which communicates with the server to run the simulation. This architecture was chosen to allow multi-machine configurations, where multiple users can interact in the same Blocks World. 

This environment is a general EIS environment, you can use it from GOAL but also from other EIS-compatible systems or even stand alone. To install GOAL, please refer to [GOAL](https://goalapl.atlassian.net/wiki/spaces/GOAL/pages/33041/Download+and+Install+GOAL)

We deliver a number of components to work with this environment:
 * The [BW4T user manual](https://github.com/eishub/BW4T/blob/master/doc/src/main/resources/BW4T3%20Instructions.pdf).
 * The [BW4T specification manual](https://github.com/eishub/BW4T/blob/master/doc/src/main/resources/BW4T3%20Specification.pdf). explaining all percepts and actions that the BW4T environment provides.
 * client, server, mapeditor, scenarioeditor. All available at the [BW4T release page](https://github.com/eishub/BW4T/releases).

To install BW4T and for all details on how to use the environment and related tools, please consult the [BW4T user manual](https://github.com/eishub/BW4T/blob/master/doc/Manuals/BW4T3_instructions.pdf?raw=true).

**WARNING** The collisions and e-partner options are not completely ready for use.

**WARNING** To do mvn install, use "mvn install -DskipTests". Test with "mvn package".

# BW4T Main Maven Module
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

Dependency information 
=====================


```
<repository>
 <id>eishub-mvn-repo</id>
 <url>https://raw.github.com/eishub/mvn-repo/master</url>
</repository>
```

The client artefact:

```	
<dependency>
	<groupId>eishub.bw4t</groupId>
	<artifactId>bw4t-client</artifactId>
	<version>3.9.1</version>
</dependency>
```

The server artefact:

```	
<dependency>
	<groupId>eishub.bw4t</groupId>
	<artifactId>bw4t-server</artifactId>
	<version>3.9.1</version>
</dependency>
```


