package com.jqc.tank.net;

import com.jqc.tank.common.Dir;
import com.jqc.tank.common.Group;

import java.util.UUID;

public class Player {
    public int x, y;
    private Dir dir;
    private boolean moving;
    private Group group;
    private String name;
    private UUID id;

    public Dir getDir() {
        return dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public Group getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }
}

