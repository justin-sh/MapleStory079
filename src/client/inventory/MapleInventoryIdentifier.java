package client.inventory;

import database.DatabaseConnection;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MapleInventoryIdentifier implements Serializable {
    private static final long serialVersionUID = 21830921831301L;
    private static final MapleInventoryIdentifier instance;
    private final AtomicInteger runningUID;
    private final ReentrantReadWriteLock rwl;
    private final Lock readLock;
    private final Lock writeLock;

    public static int getInstance() {
        return MapleInventoryIdentifier.instance.getNextUniqueId();
    }

    public MapleInventoryIdentifier() {
        this.rwl = new ReentrantReadWriteLock();
        this.readLock = this.rwl.readLock();
        this.writeLock = this.rwl.writeLock();
        this.runningUID = new AtomicInteger(0);
        this.getNextUniqueId();
    }

    public int getNextUniqueId() {
        if (this.grabRunningUID() <= 0) {
            this.setRunningUID(this.initUID());
        }
        this.incrementRunningUID();
        return this.grabRunningUID();
    }

    public int grabRunningUID() {
        this.readLock.lock();
        try {
            return this.runningUID.get();
        } finally {
            this.readLock.unlock();
        }
    }

    public void incrementRunningUID() {
        this.setRunningUID(this.grabRunningUID() + 1);
    }

    public void setRunningUID(final int rUID) {
        if (rUID < this.grabRunningUID()) {
            return;
        }
        this.writeLock.lock();
        try {
            this.runningUID.set(rUID);
        } finally {
            this.writeLock.unlock();
        }
    }

    public int initUID() {
        int ret = 0;
        if (this.grabRunningUID() > 0) {
            return this.grabRunningUID();
        }
        try {
            final int[] ids = new int[4];
            final Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT MAX(uniqueid) FROM inventoryitems");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ids[0] = rs.getInt(1) + 1;
            }
            rs.close();
            ps.close();
            ps = con.prepareStatement("SELECT MAX(petid) FROM pets");
            rs = ps.executeQuery();
            if (rs.next()) {
                ids[1] = rs.getInt(1) + 1;
            }
            rs.close();
            ps.close();
            ps = con.prepareStatement("SELECT MAX(ringid) FROM rings");
            rs = ps.executeQuery();
            if (rs.next()) {
                ids[2] = rs.getInt(1) + 1;
            }
            rs.close();
            ps.close();
            ps = con.prepareStatement("SELECT MAX(partnerringid) FROM rings");
            rs = ps.executeQuery();
            if (rs.next()) {
                ids[3] = rs.getInt(1) + 1;
            }
            rs.close();
            ps.close();
            for (int i = 0; i < 4; ++i) {
                if (ids[i] > ret) {
                    ret = ids[i];
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    static {
        instance = new MapleInventoryIdentifier();
    }
}
