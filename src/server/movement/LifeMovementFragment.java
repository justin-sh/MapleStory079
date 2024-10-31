package server.movement;

import tools.data.output.LittleEndianWriter;

import java.awt.*;

public interface LifeMovementFragment {
    void serialize(final LittleEndianWriter p0);

    Point getPosition();
}
