package ru.typik.likeService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.typik.likeService.dao.LikeServiceDao;
import ru.typik.likeService.exceptions.PlayerNotFoundException;

@Service
@RequiredArgsConstructor
public class LikeServiceDB implements LikeService {

    private final @Autowired LikeServiceDao dao;

    @Override
    public void like(String playerId) throws PlayerNotFoundException {
        if (!this.dao.saveLikeForPlayer(playerId)) {
            throw new PlayerNotFoundException(playerId);
        }
    }

    @Override
    public long getLikes(String playerId) {
        Long result = this.dao.getCountLikesForPlayer(playerId);
        if (result == null) {
            throw new PlayerNotFoundException(playerId);
        }
        return result;
    }

}
