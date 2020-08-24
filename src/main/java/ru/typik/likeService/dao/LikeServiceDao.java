package ru.typik.likeService.dao;

public interface LikeServiceDao {

    Long getCountLikesForPlayer(String playerId);

    boolean saveLikeForPlayer(String playerId);

}
