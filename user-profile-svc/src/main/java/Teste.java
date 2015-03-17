import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;


public class Teste {

	public static void main(String[] args) throws UnknownHostException {
		MongoClient mongoClient = new MongoClient( "ec2-54-94-201-218.sa-east-1.compute.amazonaws.com" , 27017 );

		DB db = mongoClient.getDB( "nutrieduc-chat" );
		Set<String> colls = db.getCollectionNames();

		for (String s : colls) {
		    System.out.println(s);
		}
		DBCollection coll = db.getCollection("historychatmessages");
		BasicDBObject doc = new BasicDBObject("name", "MongoDB")
        .append("type", "database")
        .append("count", 1)
        .append("info", new BasicDBObject("x", 203).append("y", 102));
		coll.insert(doc);
		
		System.out.println(coll.getCount());
		
		DBCursor cursor = coll.find();
		try {
		   while(cursor.hasNext()) {
		       System.out.println(cursor.next());
		   }
		} finally {
		   cursor.close();
		}
		
	}
}
