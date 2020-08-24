package ru.typik.likeService.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final @Getter String playerId;

}
