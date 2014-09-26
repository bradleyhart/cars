package org.fazz.database

import groovy.sql.Sql

class Database {

    static Sql sqlInstance;

    static Sql sql() {
        if (!sqlInstance) {
            sqlInstance = Sql.newInstance("jdbc:h2:mem:cars;MODE=MySQL", "sa", "sa")
        }
        sqlInstance
    }

    static def initializeDatabase() {
        sql().execute(file("org/fazz/sql/schema.sql"))
    }

    private static String file(String classpath) {
        new File(this.getClassLoader().getResource(classpath).toURI()).text
    }

    static def isRunning(){
        initializeDatabase()
    }

    static def isEmpty(){
        sql().execute("DELETE FROM search")
    }

    static def searches(){
        sql().rows("SELECT * FROM search")
    }

}
