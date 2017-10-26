package com.maxdemarzi.imports;

import com.maxdemarzi.results.StringResult;
import org.neo4j.kernel.internal.GraphDatabaseAPI;
import org.neo4j.logging.Log;
import org.neo4j.procedure.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


public class Imports {
    @Context
    public GraphDatabaseAPI db;

    @Context
    public Log log;

    @Procedure(name = "com.maxdemarzi.import.locations", mode = Mode.WRITE)
    @Description("CALL com.maxdemarzi.import.locations(file)")
    public Stream<StringResult> importLocations(@Name("file") String file) throws InterruptedException {
        long start = System.nanoTime();

        Thread t1 = new Thread(new ImportLocationsRunnable(file, db, log));
        t1.start();
        t1.join();

        return Stream.of(new StringResult("Locations imported in " + TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start) + " seconds"));
    }

}
