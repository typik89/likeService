package ru.typik.likeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;

@SpringBootApplication
public class LikeServiceApplication {

    @Autowired
    MongoClient mongoClient;

    public MongoTemplate mongoTemplate() {
        MongoTemplate mt = new MongoTemplate(this.mongoClient, "likeService");
        mt.setWriteConcern(WriteConcern.MAJORITY);
        return mt;
    }

    public static void main(String[] args) {
        SpringApplication.run(LikeServiceApplication.class, args);
    }

}
