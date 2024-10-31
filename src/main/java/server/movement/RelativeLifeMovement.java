package server.movement;

import tools.data.output.LittleEndianWriter;

import java.awt.*;

public class RelativeLifeMovement extends AbstractLifeMovement {
    public RelativeLifeMovement(final int type, final Point position, final int duration, final int newstate) {
        super(type, position, duration, newstate);
    }

    @Override
    public void serialize(final LittleEndianWriter lew) {
        lew.write(this.getType());
        lew.writeShort(this.getPosition().x);
        lew.writeShort(this.getPosition().y);
        lew.write(this.getNewstate());
        lew.writeShort(this.getDuration());
    }
}
