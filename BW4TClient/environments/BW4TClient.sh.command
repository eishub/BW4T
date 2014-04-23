#!/bin/tcsh

set pathname=`dirname "$0"`
cd "$pathname"
java -jar BW4TClient.jar -agentclass nl.tudelft.bw4t.agent.TestAgent -clientip localhost -serverip localhost -clientport 2000 -serverport 8000 -launchgui false -map maps/Map1 -agentcount 2 -humancount 0

