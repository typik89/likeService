package ru.typik.likeService.dao.mongo;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import lombok.RequiredArgsConstructor;
import ru.typik.likeService.dao.LikeServiceDao;

@Repository
@RequiredArgsConstructor
public class LikeServiceDaoMongo implements LikeServiceDao {

    private static final String FIELD_ID = "_id";
    private static final String COLLECTION_PLAYERS = "players";
    private static final String FIELD_LIKES_COUNT = "likesCount";

    private final MongoTemplate mongoTemplate;

    @Override
    public Long getCountLikesForPlayer(String playerId) {
        return this.mongoTemplate.getCollection(COLLECTION_PLAYERS).find(Filters.eq(FIELD_ID, playerId))
                .map(el -> el.get(FIELD_LIKES_COUNT) == null ? 0 : el.getLong(FIELD_LIKES_COUNT)).first();
    }

    @Override
    public boolean saveLikeForPlayer(String playerId) {
        return this.mongoTemplate.getCollection(COLLECTION_PLAYERS).findOneAndUpdate(Filters.eq(FIELD_ID, playerId),
                Updates.inc(FIELD_LIKES_COUNT, 1l)) != null;
    }

}
