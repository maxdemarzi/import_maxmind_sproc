# Import Maxmind Stored Procedure

Import the MaxMind Cities Database into Neo4j using a Stored Procedure

This project requires Neo4j 3.3.x

Instructions
------------ 

This project uses maven, to build a jar-file with the procedure in this
project, simply package the project with maven:

    mvn clean package

This will produce a jar-file, `target/importer-1.0-SNAPSHOT.jar`,
that can be copied to the `plugin` directory of your Neo4j instance.

    cp target/importer-1.0-SNAPSHOT.jar neo4j-enterprise-3.3.1/plugins/.


Edit your Neo4j/conf/neo4j.conf file by adding this line:

    dbms.security.procedures.unrestricted=com.maxdemarzi.*    
    
(Re)start Neo4j

Create the schema:

    CALL com.maxdemarzi.schema.generate;

Import the data (ip4 data not included in repo):

    CALL com.maxdemarzi.import.locations("/Users/maxdemarzi/Projects/import_maxmind_sproc/src/main/resources/data/GeoLite2-City-Locations-en.csv");

    CALL com.maxdemarzi.import.ip4("/Users/maxdemarzi/Projects/import_maxmind_sproc/src/main/resources/data/GeoLite2-City-Blocks-IPv4.csv");
    
        
If using Windows you must escape the slashes like so:

    CALL com.maxdemarzi.import.locations('C:\\Users\\maxdemarzi\\Projects\\import_maxmind_sproc\\src\\main\\resources\\data\\GeoLite2-City-Locations-en.csv');    
    
    
If you are using Neo4j 3.4 or later you can all add a spatial index:

    CREATE INDEX ON :City(location);
    
and populate it with:

    MATCH (c:City)
    SET c.location = point({latitude: c.latitude, longitude: c.longitude, crs: 'WGS-84'});
    
Then you will be able to do spatial queries like:

    MATCH (c:City)-[:IN_LOCATION]->(s:State), (c2:City)
    WHERE c.name = "Union City"
      AND s.name = "California"
      AND distance(c.location, c2.location) <= 10000
    RETURN c2.name, c2.latitude, c2.longitude, distance(c.location, c2.location)    
        
    
    
This product includes GeoLite2 data created by MaxMind, available from <a href="http://www.maxmind.com">http://www.maxmind.com</a>.      