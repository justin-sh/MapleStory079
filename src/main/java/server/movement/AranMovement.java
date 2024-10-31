package server.movement;

import tools.data.output.LittleEndianWriter;

import java.awt.*;

public class AranMovement extends AbstractLifeMovement {
    public AranMovement(final int type, final Point position, final int duration, final int newstate) {
        super(type, position, duration, newstate);
    }

    @Override
    public void serialize(final LittleEndianWriter lew) {
        lew.write(this.getType());
        lew.write(this.getNewstate());
        lew.writeShort(this.getDuration());
    }
}
