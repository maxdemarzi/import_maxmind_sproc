package com.maxdemarzi.schema;

import com.maxdemarzi.results.StringResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.procedure.Context;
import org.neo4j.procedure.Description;
import org.neo4j.procedure.Mode;
import org.neo4j.procedure.Procedure;

import java.io.IOException;
import java.util.stream.Stream;

public class Schema {

    @Context
    public GraphDatabaseService db;


    @Procedure(name="com.maxdemarzi.schema.generate",mode= Mode.SCHEMA)
    @Description("CALL com.maxdemarzi.schema.generate() - generate schema")

    public Stream<StringResult> generate() throws IOException {
        org.neo4j.graphdb.schema.Schema schema = db.schema();
        if (!schema.getConstraints(Labels.Timezone).iterator().hasNext()) {
            schema.constraintFor(Labels.Timezone)
                    .assertPropertyIsUnique("name")
                    .create();
        }

        if (!schema.getConstraints(Labels.Continent).iterator().hasNext()) {
            schema.constraintFor(Labels.Continent)
                    .assertPropertyIsUnique("name")
                    .create();
        }

        if(!schema.getIndexes(Labels.State).iterator().hasNext()) {
            schema.constraintFor(Labels.State)
                    .assertPropertyIsUnique("code")
                    .create();
            schema.indexFor(Labels.State)
                    .on("name")
                    .create();
        }

        if (!schema.getConstraints(Labels.City).iterator().hasNext()) {
            schema.constraintFor(Labels.City)
                    .assertPropertyIsUnique("geoname_id")
                    .create();
            schema.indexFor(Labels.City)
                    .on("name")
                    .create();
        }
        return Stream.of(new StringResult("Schema Generated"));
    }

}
