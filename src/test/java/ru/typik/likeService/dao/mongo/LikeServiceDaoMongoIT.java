package ru.typik.likeService.dao.mongo;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.mongodb.client.MongoClients;

import ru.typik.likeService.dao.LikeServiceDao;

@Testcontainers
public class LikeServiceDaoMongoIT {

    @Container
    MongoDBContainer mongoDBContainer = new MongoDBContainer();

    LikeServiceDao dao;

    @BeforeEach
    public void beforeEach() {
        MongoTemplate mongoTemplate = new MongoTemplate(MongoClients.create(this.mongoDBContainer.getReplicaSetUrl()),
                "likeService");
        this.dao = new LikeServiceDaoMongo(mongoTemplate);

        mongoTemplate.dropCollection("players");
        mongoTemplate.getCollection("players").insertOne(new Document().append("_id", "1").append("likesCount", 1L));
        mongoTemplate.getCollection("players").insertOne(new Document().append("_id", "2").append("likesCount", 0L));
        mongoTemplate.getCollection("players").insertOne(new Document().append("_id", "3"));
    }

    @Test
    public void testGetCountLikes() {
        assertEquals(1, this.dao.getCountLikesForPlayer("1"));
        assertEquals(0, this.dao.getCountLikesForPlayer("2"));
        assertEquals(0, this.dao.getCountLikesForPlayer("3"));
        assertNull(this.dao.getCountLikesForPlayer("4"));
    }

    @Test
    public void testIncrementLikes() {
        assertTrue(this.dao.saveLikeForPlayer("1"));
        assertEquals(2l, this.dao.getCountLikesForPlayer("1"));

        assertTrue(this.dao.saveLikeForPlayer("2"));
        assertEquals(1l, this.dao.getCountLikesForPlayer("2"));

        assertTrue(this.dao.saveLikeForPlayer("3"));
        assertEquals(1l, this.dao.getCountLikesForPlayer("3"));

        assertFalse(this.dao.saveLikeForPlayer("4"));
        assertNull(this.dao.getCountLikesForPlayer("4"));
    }
}
