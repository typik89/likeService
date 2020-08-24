package ru.typik.likeService.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.typik.likeService.dao.LikeServiceDao;
import ru.typik.likeService.exceptions.PlayerNotFoundException;

@ExtendWith(MockitoExtension.class)
public class LikeServiceDBTest {

    @Mock
    LikeServiceDao dao;

    @InjectMocks
    private LikeServiceDB likeService;

    @Test
    public void testNotExistedPlayer() {
        Mockito.when(this.dao.getCountLikesForPlayer(ArgumentMatchers.eq("notExisted"))).thenReturn(null);
        Mockito.when(this.dao.saveLikeForPlayer(ArgumentMatchers.eq("notExisted"))).thenReturn(false);

        assertThrows(PlayerNotFoundException.class, () -> this.likeService.getLikes("notExisted"));
        assertThrows(PlayerNotFoundException.class, () -> this.likeService.like("notExisted"));
    }

    @Test
    public void testSaveExisted() {
        Mockito.when(this.dao.getCountLikesForPlayer(ArgumentMatchers.eq("existed"))).thenReturn(1l);
        Mockito.when(this.dao.saveLikeForPlayer(ArgumentMatchers.eq("existed"))).thenReturn(true);

        assertEquals(1l, this.likeService.getLikes("existed"));
        this.likeService.like("existed");
        Mockito.verify(this.dao, times(1)).saveLikeForPlayer("existed");
    }

}
