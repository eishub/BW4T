#!/bin/tcsh

set pathname=`dirname "$0"`
cd "$pathname"

setenv Repast /Applications/Repast-Simphony-2.0.0-beta
setenv RepastPlugins $Repast/eclipse/plugins/
setenv RepastRuntime $RepastPlugins/repast.simphony.runtime_2.0.0
setenv RepastBinSrc $RepastPlugins/repast.simphony.bin_and_src_2.0.0
setenv RepastCore $RepastPlugins/repast.simphony.core_2.0.0
java -cp BW4TServer.jar\:$RepastCore/lib/\*\:$RepastRuntime/lib/\*\:$RepastRuntime/bin\:$RepastBinSrc/repast.simphony.bin_and_src.jar nl.tudelft.bw4t.server.BW4TEnvironment -scenario ./BW4T.rs -map Random -serverip localhost -serverport 8000

