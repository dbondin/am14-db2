package ru.applmath.valutes.export;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoDatabaseWriter implements DatabaseWriter {
	public void writeToDatabase(List<Valute> v) {
		try {
			Mongo mongoClient = new Mongo("127.0.0.1");
			
			DB db = mongoClient.getDB("forex");
			
			DBCollection col = db.getCollection("valutes");
			
			for(Valute _v : v) {
				BasicDBObject dbobj = new BasicDBObject();
				dbobj.append("valuteId", _v.getId());
				dbobj.append("valuteName", _v.getName());
				dbobj.append("valuteCharCode", _v.getCharCode());
				dbobj.append("valuteValue", _v.getValue());
				
				col.insert(dbobj);
			}
			
			mongoClient.close();
			
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
