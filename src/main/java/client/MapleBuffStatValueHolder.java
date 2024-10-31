package client;

import server.MapleStatEffect;

import java.util.concurrent.ScheduledFuture;

public class MapleBuffStatValueHolder {
    public MapleStatEffect effect;
    public long startTime;
    public int value;
    public ScheduledFuture<?> schedule;

    public MapleBuffStatValueHolder(final MapleStatEffect effect, final long startTime, final ScheduledFuture<?> schedule, final int value) {
        this.effect = effect;
        this.startTime = startTime;
        this.schedule = schedule;
        this.value = value;
    }
}
