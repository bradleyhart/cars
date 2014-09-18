package org.fazz.mongo

import org.fazz.config.MongoConfig
import org.fazz.model.Car
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.data.mongodb.core.MongoTemplate

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

    static AnnotationConfigApplicationContext mongoContext
    static def isEmpty() {
        if(!mongoContext){
            mongoContext = new AnnotationConfigApplicationContext(MongoConfig)
        }
        def mongoTemplate = mongoContext.getBean(MongoTemplate)
        mongoTemplate.dropCollection(Car)
    }
}
