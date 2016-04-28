# EC500D1-Project
This is a repository containint final project of the EC500D1 course. The project is called Enhanced BLAT. Team members are Rushi Patel and Arash Khoshparvar

## Synopsis
BLAT is an alignment tool, not to be confused with BLAST, BLAT offers faster alignment techniques with using less amount of computation resources. Like any other problem, there is a trade-off, so the performance is gained with the pprice of accuracy. BLAT is optimized for sequences of size 25bp or more. In order to overcome this shortcoming, biologists have come up with a solution. The mentioned solution expands the smaller sequence in an overlay and literally makes it larger, one that is compatible with BLAT requirements. With its integrated genome databases, BLAT not only improves performance, but also ease of use and user experience.

This project enhances BLAT towards performing batch queries without the need for constant user control. These queries can be independent of one another, or they can be related. An example of a related batch of queries is sequence expansion, which is query of a sequence encapsulated in a larger sequence, particularly its overlay in the genome. Since the encapsulation for the expansion is part of the selected genome, a first query is needed to attain the encapsulation itself. Using the Enhanced BLAT project user can throw in jobs and leave the desk while the time-consuming process of alignment is being performed. By running code on a powerful servers instead of the local machine, the user does not have to be worried about not being able to use the local machine.

## Code Example

The code in this repository consists of two different implementations of Enhanced BLAT. One is an integrated servlet based web application, and the other a CommandLine based application to be integrated into OWL Ecosystem. In the web based implementation, all attempts have been made for the user to have the same experience as BLAT, only with added functionality. The CommandLine implementation on the other hand is built in a way to be easily integrated into OWL which is a node.js application. To that end, all the results are parsed into JSON format and saved for any further use by OWL. An example use of the CommandLine version come as follows. This example queries the sequences "atggcttgatcaatgggact" and "tatccttgctgtaactgcaa". Default expansion values are 10, 5, and 2. Results are saved in the file ./output/EnhancedBLAT.json.
java -jar EC500ProjectCommandline-1.0-SNAPSHOT-jar-with-dependencies.jar -q "atggcttgatcaatgggact>tatccttgctgtaactgcaa"

## Motivation

Improve on the current work flow used with the BLAT functionality in the UCSC Genome Browser.
Results of this project can directly be used to enhance the Fluorescence in situ hybridization (FISH) probe design and verification. However, small changes in the design can comprise a larger set of problems.


## Installation

Projects are maven based. Maven is going to take care of all the dependencies. To run the web based implementation a server software like tomcat is needed.

