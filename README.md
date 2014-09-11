#Blocks World for Teams - v3.5.0
Blocks World for Teams (**BW4T**) is a testbed for team coordination. BW4T allows for games with human-human, agent-agent and human-agent teams of variable sizes. The goal is to jointly deliver a sequence of colored blocks in a particular order as fast as possible. A complicating factor is that the players cannot see each other.

This page descibes how users can install and use the Blocks World for Teams environment. 
For developer information please follow this [Link to Developer details](DEVELOPER.md)

We assume that you already installed the agent programming language environment of your choice, for example [GOAL](http://ii.tudelft.nl/trac/goal)

### Client-Server systems
Blocks World for Teams has a client-server architecture. This means that you need to install and run two parts to use it: a client and a server. The server is the Blocks World for Team Server where the real simulation takes place. The client is the agent programming environment, which communicates with the server to run the simulation. This architecture was chosen to allow multi-machine configurations, where multiple users can interact in the same Blocks World. 

#Installation
## Installing client software
If you installed a recent release of GOAL, a BW4T sample project is provided to you with the name 'BW4T3'. This client is ready to run. But you do need to install and run the server first

For other agent environments, you need to install the BW4T Client Environment. [Download here](FIXME). You also will need an agent program that runs in your agent environment using the BW4T environment.

## Installing server software
The server software is a single jar file. [Download latest version here](FIXME). 

## Installing editors
There are two editors:
 1. The Map editor to create new maps. [Download latest version here](FIXME). 
 2. The Scenario editor to create new agent configuration files. [Download latest version here](FIXME). 
 
## Documentation
We have the following documentation
 BW4T3 manual with general installation instructions
