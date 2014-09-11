#Blocks World for Teams - v3.5.0
Blocks World for Teams (**BW4T**) is a testbed EIS environment for team coordination. BW4T allows for games with human-human, agent-agent and human-agent teams of variable sizes. The goal is to jointly deliver a sequence of colored blocks in a particular order as fast as possible. A complicating factor is that the players cannot see each other.

This page descibes how users can install and use the Blocks World for Teams environment. 
For developer information please follow this [Link to Developer details](DEVELOPER.md)

Blocks World for Teams has a client-server architecture. This means that you need to install and run two parts to use it: a client and a server. The server is the Blocks World for Team Server where the real simulation takes place. The client is the agent programming environment, which communicates with the server to run the simulation. This architecture was chosen to allow multi-machine configurations, where multiple users can interact in the same Blocks World. 

This environment is a general EIS environment, you can use it from GOAL but also from other EIS-compatible systems or even stand alone. To install GOAL, please refer to [GOAL](http://ii.tudelft.nl/trac/goal)

We deliver a number of components to work with this environment:
 * client, server, mapeditor, scenarioeditor. All available at the [https://github.com/eishub/BW4T/releases](BW4T release page).
 * [https://github.com/eishub/BW4T/blob/master/doc/Manuals/BW4T3_instructions.pdf?raw=true](BW4T user manual).
 * [https://github.com/eishub/BW4T/blob/master/doc/Manuals/BW4T3%20Specification.pdf?raw=true](BW4T specification manual) explaining all percepts and actions that the BW4T environment provides.

To install BW4T and for all details on how to use the environment and related tools, please consult the instructions manual at [https://github.com/eishub/BW4T/blob/master/doc/Manuals/BW4T3_instructions.pdf?raw=true](BW4T user manual).

