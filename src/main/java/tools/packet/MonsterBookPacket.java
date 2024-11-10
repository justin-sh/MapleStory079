package tools.packet;

import constants.ServerConstants;
import handling.MaplePacket;
import handling.SendPacketOpcode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.data.output.MaplePacketLittleEndianWriter;

public class MonsterBookPacket {
    private static final Logger logger = LoggerFactory.getLogger(MonsterBookPacket.class);
    public static MaplePacket addCard(final boolean full, final int cardid, final int level) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.isDebugPacket) {
            logger.info("addCard--------------------");
        }
        mplew.writeShort(SendPacketOpcode.MONSTERBOOK_ADD.getValue());
        if (!full) {
            mplew.write(1);
            mplew.writeInt(cardid);
            mplew.writeInt(level);
        } else {
            mplew.write(0);
        }
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }

    public static MaplePacket showGainCard(final int itemid) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.isDebugPacket) {
            logger.info("showGainCard--------------------");
        }
        mplew.writeShort(SendPacketOpcode.SHOW_ITEM_GAIN_INCHAT.getValue());
        mplew.write(15);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }

    public static MaplePacket showForeginCardEffect(final int id) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.isDebugPacket) {
            logger.info("showForeginCardEffect--------------------");
        }
        mplew.writeShort(SendPacketOpcode.SHOW_FOREIGN_EFFECT.getValue());
        mplew.writeInt(id);
        mplew.write(13);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }

    public static MaplePacket changeCover(final int cardid) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.isDebugPacket) {
            logger.info("changeCover--------------------");
        }
        mplew.writeShort(SendPacketOpcode.MONSTERBOOK_CHANGE_COVER.getValue());
        mplew.writeInt(cardid);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
}
