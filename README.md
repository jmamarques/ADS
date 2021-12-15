# ADS

#Project 3: Dynamic selection and invocation of scheduling/timetabling algorithms

- Flexible import Reading (from user and from schedule/timetable initial CSV, XML, Json, BD, Web Services data) and
  computing scheduling/timetabling problem properties
- Schedules/timetables problem representation
- Query the knowledge base for the selection of a set of the most suitable scheduling/timetabling algorithms which are
  able to solve a problem with those properties
- Information extraction and visualization of scheduling/timetabling algorithms (matching the set of the most suitable
  scheduling/timetabling algorithms) from a selected (Java/Python) library/API containing a large variety of algorithms
- Taking into account the information extracted from the (Java/Python) library/API, perform the dynamic invocation of
  the most suited algorithm present in the library, according to its availability in the library/API and its order in
  the most suited algorithms selection list
- Compute generated schedules/timetables quality metrics
- Export the generated (best) schedules/timetables (CSV, XML, Json, DB, Web Service)

#Problem Case
Present the user with room allocation strategies through:
  - Quality criteria;
  - One or more room allocation suggestions.

#Procedures
  - Import the file "ADS - Caracterizacao das salas.xls";
  - Consult the knowledge base and search for the most suitable algorithm (API SWRL) capable of solving the problem of room allocation;
  - Perform dynamic invocation of the most suitable algorithm, taking into account the extraction of the knowledge base;
  - Define quality metrics for allocations;
  - Compare the metrics with the result of allocations;
  - View more than one allocation suggestion, if the user wishes.

#Quality criteria:
  - Tolerance in the number of students allocated in the classrooms;
  - The smallest number of classes without allocated rooms;
  - The smallest number of room changes in sets of classes;
  - The least number of building changes in sets of classes;
  - Greater number of auditoriums, with several hours in a row, without class allocation;
  - Greater correspondence of the characteristics of the classrooms with the class.

The user will be able to choose the schedule he prefers according to the weight he attributes to each quality criterion.

#Programming language used: JAVA

#Backend support library: POI

#UI Interface: HTML and CSS

#Other APIs used: Angular and Docker

#Deploy : Google Cloud Plataform