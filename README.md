# EC500D1-Project
This is a repository containing final project of the EC500D1 course. The project is called Enhanced BLAT. Team members are Rushi Patel and Arash Khoshparvar

## Synopsis
BLAT is a bioinformatics alignment tool. Not to be confused with BLAST, BLAT offers faster alignment techniques with using less number of computation resources. Like any other problem, there is a trade-off, so the performance is gained with the price of accuracy. BLAT is optimized for sequences of size 25bp or more. In order to overcome this shortcoming, biologists have come up with a solution. The mentioned solution expands the smaller sequence in an overlay and literally makes it larger, one that is compatible with BLAT requirements. With its integrated genome databases, BLAT not only improves performance, but also ease of use and user experience.

This project enhances BLAT towards performing batch queries without the need for constant user control. These queries can be independent of one another, or they can be related. An example of a related batch of queries is sequence expansion, which is query of a sequence encapsulated in a larger sequence, particularly its overlay in the genome. Since the encapsulation for the expansion is part of the selected genome, a first query is needed to attain the encapsulation itself. Using the Enhanced BLAT project user can throw in jobs and leave the desk while the time-consuming process of alignment is being performed. By running code on a powerful servers instead of the local machine, the user does not have to be worried about not being able to use the local machine.

## Code Example

The code in this repository consists of two different implementations of Enhanced BLAT. One is an integrated servlet based web application, and the other a CommandLine based application to be integrated into OWL Ecosystem. In the web based implementation, all attempts have been made for the user to have the same experience as BLAT, only with added functionality.

An example use of the CommandLine version come as follows. This example queries the sequences "atggcttgatcaatgggact" and "tatccttgctgtaactgcaa". As this example shows, the character '>' is the separation character for different jobs. All other characters other than alphabetic letters are ignored.

java -jar EC500ProjectCommandline-1.0-SNAPSHOT-jar-with-dependencies.jar -q "atggcttgatcaatgggact>tatccttgctgtaactgcaa"

The CommandLine implementation on the other hand is built in a way to be easily integrated into OWL which is a Node.js application. To that end, all the results are parsed into JSON format and saved for any further use by OWL. Results are saved in the file ./output/EnhancedBLAT.json. Node.js code is also available in the repository under EC500ProjectNode folder which is ready to be integrated into OWL or any other Node.js based application. 
## Motivation

Improve on the current work flow used with the BLAT functionality in the UCSC Genome Browser.
Results of this project can directly be used to enhance the Fluorescence in situ hybridization (FISH) probe design and verification. However, minor changes in the design can comprise a larger set of problems.


## Installation

All java implementations in this project are maven based. Maven is going to take care of all the dependencies. All you need is an internet connection and issuing a Maven build lifecycle. For a quick review of how to use maven you can go to [Maven in five minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)

To run the web based implementation a server software like tomcat is needed. After building the web application implementation with Maven, the target .war can be fed manually to any server software or Maven can be used to deploy it on tomcat or any other server software. To run the webapp on tomcat you just need to issue:
mvn tomcat:run

For the CommandLine implementation, POM has been updated to build a single jar file containing all the dependencies. In order to do so the following command needs to be issued:
mvn clean compile assembly:single
