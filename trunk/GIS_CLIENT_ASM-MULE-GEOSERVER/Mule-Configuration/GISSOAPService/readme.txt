Fast Run Guide:

Requirements:
1. Maven 2 (2.0.8) installed and configured.
2. Mule (1.4.2) installed and configured (environment variable MULE_HOME must point to Mule's installation directory)

To run it on your machine you must change some IP addresses in conf/mule-config.xml.
- line 60: the host, port and service name that point to the Axis endpoint address.
- line 67 and 69: the host, port and service that point to the GeoServer services


To compile:
execute "compile.bat" and pray for no errors ;)

To run:
execute "run.bat" and wait till "Ready, set...go!!! (Please enter something)" prompt appears

>>>===-*-*-*-*- End of Fast Run Guide -*-*-*-*-===<<<

Some additional info:

1. What will it do?
The compile.bat file will invoke:
"mvn package"
witch will compile project, create an .jar archive and place it into the "%MULE_HOME%\lib\user" directory.

The run.bat will invoke:
"mule -config conf\mule-config.xml"
witch will start mule server with our application.

2. Eclipse can't compile the project.
Yes, that's true. Try this (tested on eclipse without plugins for integration with maven (or rather with disabled plugins)) 
from the command line, being in project's directory, invoke:
mvn eclipse:eclipse

!!!WARNING!!! Action above will change output folder for project binaries to "target/classes/", so if you don't want eclipse and maven to share the same output folder, change it. 
Refresh project in eclipse, clean it (if .xml-s still don't validate) and now it should work.

  