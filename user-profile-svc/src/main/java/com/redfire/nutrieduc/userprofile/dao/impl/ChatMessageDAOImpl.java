package com.redfire.nutrieduc.userprofile.dao.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.redfire.nutrieduc.chat.ChatMessage;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceCollectionResponse;
import com.redfire.nutrieduc.commonsvc.svc.io.ServiceRequest;
import com.redfire.nutrieduc.userprofile.dao.ChatMessageDAO;

@Repository
public class ChatMessageDAOImpl implements ChatMessageDAO {
	@Value("${mongodb.host}" )
	private String mongodbHost;
	
	@Value("${mongodb.port}" )
	private Integer mongoDbPort;
	
	@Override
	public void insert(ChatMessage chatMessage) {
		try {
			DBCollection coll = getCollection();
			BasicDBObject doc = new BasicDBObject()
	        .append("userA", chatMessage.getUserA())
	        .append("userB", chatMessage.getUserB())
	        .append("sender", chatMessage.getSender())
	        .append("messageContent", chatMessage.getValue());
			coll.insert(doc);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ServiceCollectionResponse<ChatMessage> allHistory(ServiceRequest<ChatMessage> message) {
		ServiceCollectionResponse<ChatMessage> response = new ServiceCollectionResponse<>(); 
		try {
			DBCollection coll = getCollection();
			ChatMessage entity = message.getEntity();
			
			BasicDBObject andQuery1 = whereClause1(entity);
			BasicDBObject andQuery2 = whereClause2(entity);
			
			DBObject query = new BasicDBObject().append("$or", Arrays.asList(andQuery1, andQuery2));
			
			DBCursor myCursor = coll.find(query);
			while(myCursor.hasNext()) {
				DBObject obj = myCursor.next();
				ChatMessage item = new ChatMessage();
				item.setSender((String) obj.get("sender"));
				item.setUserA(obj.get("userA").toString());
				item.setUserB(obj.get("userB").toString());
				item.setValue(obj.get("messageContent").toString());
				response.getDataList().add(item);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	private BasicDBObject whereClause1(ChatMessage entity) {
		BasicDBObject andQuery1 = new BasicDBObject();
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		obj.add(new BasicDBObject("userA", entity.getUserA()));
		obj.add(new BasicDBObject("userB", entity.getUserB()));
		andQuery1.put("$and", obj);
		return andQuery1;
	}

	private BasicDBObject whereClause2(ChatMessage entity) {
		BasicDBObject andQuery2 = new BasicDBObject();
		List<BasicDBObject> obj2 = new ArrayList<BasicDBObject>();
		obj2.add(new BasicDBObject("userA", entity.getUserB()));
		obj2.add(new BasicDBObject("userB", entity.getUserA()));
		andQuery2.put("$and", obj2);
		return andQuery2;
	}

	private DBCollection getCollection() throws UnknownHostException {
		MongoClient mongoClient = new MongoClient( mongodbHost , mongoDbPort );

		DB db = mongoClient.getDB( "nutrieduc-chat" );
		return db.getCollection("historychatmessages");
	}
}
