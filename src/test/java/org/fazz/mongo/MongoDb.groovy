package org.fazz.mongo

class MongoDb {

    static boolean started = false

    static isRunning() {
        new Thread({
            String[] roots = ["script"]
            GroovyScriptEngine engine = new GroovyScriptEngine(roots)
            engine.run("StartMongoDb.groovy", new Binding())
        }).start()
        started = true
    }

}
