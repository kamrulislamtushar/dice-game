package com.dice.game.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class NotFoundException extends AbstractThrowableProblem {
    private static final long serialVersionUID = 1L;

    public NotFoundException() {
        super(URI.create("/api/v1/"), "404 NOT FOUND", Status.NOT_FOUND);
    }
}
