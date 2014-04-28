set Repast=C:\RepastSimphony-2.1
set RepastPlugins=%Repast%\eclipse\plugins\
set RepastRuntime=%RepastPlugins%\repast.simphony.runtime_2.1.0
set RepastBinSrc=%RepastPlugins%\/repast.simphony.bin_and_src_2.1.0
set RepastCore=%RepastPlugins%\repast.simphony.core_2.1.0
java  -cp "BW4TServer.jar;%RepastCore%\lib\*;%RepastRuntime%\lib\*;%RepastRuntime%\bin;%RepastBinSrc%\repast.simphony.bin_and_src.jar" nl.tudelft.bw4t.server.BW4TEnvironment -scenario ./BW4T.rs -map Random -serverip localhost -serverport 8000
if errorlevel 1 pause
