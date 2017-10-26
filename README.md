# Import Maxmind Stored Procedure

Import the MaxMind Cities Database into Neo4j using a Stored Procedure

This project requires Neo4j 3.2.x

Instructions
------------ 

This project uses maven, to build a jar-file with the procedure in this
project, simply package the project with maven:

    mvn clean package

This will produce a jar-file, `target/importer-1.0-SNAPSHOT.jar`,
that can be copied to the `plugin` directory of your Neo4j instance.

    cp target/importer-1.0-SNAPSHOT.jar neo4j-enterprise-3.2.6/plugins/.


Edit your Neo4j/conf/neo4j.conf file by adding this line:

    dbms.security.procedures.unrestricted=com.maxdemarzi.*    
    
(Re)start Neo4j

Create the schema:

    CALL com.maxdemarzi.schema.generate;

Import the data:

    CALL com.maxdemarzi.import.locations("/Users/maxdemarzi/Projects/import_maxmind_sproc/src/main/resources/data/GeoLite2-City-Locations-en.csv");
    
If using Windows you must escape the slashes like so:

    CALL com.maxdemarzi.import.locations('C:\\Users\\maxdemarzi\\Projects\\import_maxmind_sproc\\src\\main\\resources\\data\\GeoLite2-City-Locations-en.csv');    
    
    
This product includes GeoLite2 data created by MaxMind, available from <a href="http://www.maxmind.com">http://www.maxmind.com</a>.      