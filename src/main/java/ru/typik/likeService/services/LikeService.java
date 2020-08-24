package ru.typik.likeService.services;

public interface LikeService {

    void like(String playerId);

    long getLikes(String playerId);

}
