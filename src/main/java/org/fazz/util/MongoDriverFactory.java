package org.fazz.util;

import com.mongodb.*;

import java.util.ArrayList;
import java.util.List;

public class MongoDriverFactory {

    public static final String EMPTY_REDUCE = "function(obj,prev) {}";
    public static final BasicDBObject EMPTY_INITIAL = dbObject("count", 0);
    public static final String EMPTY_FINIALIZE = null;

    public static BasicDBObject dbObject(String key, Object object){
        return new BasicDBObject(key, object);
    }

    public static BasicDBList dbList(BasicDBObject... objects){
        return new BasicDBList(){{
            for (BasicDBObject object : objects) {
                add(object);
            }
        }};
    }

    public static GroupCommand groupCommand(DBCollection inputCollection, DBObject keys, DBObject condition, DBObject initial, String reduce, String finalize){
        return new GroupCommand(inputCollection, keys, condition, initial, reduce, finalize);
    }

    public static <T> List<T> listResults(String field, BasicDBList results) {
        List<T> makes = new ArrayList<>();
        results.forEach((dbObject) -> makes.add((T) ((BasicDBObject) dbObject).get(field)));
        return makes;
    }

    public static BasicDBList group(DBCollection cars, GroupCommand groupCommand) {
        return (BasicDBList) cars.group(groupCommand);
    }

    public static BasicDBObject declareKey(String field) {
        return dbObject(field, 1);
    }

    public static BasicDBObject and(BasicDBObject... ands) {
        return dbObject("$and", dbList(ands));
    }
}
