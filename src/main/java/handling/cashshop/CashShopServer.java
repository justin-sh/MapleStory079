package handling.cashshop;

import handling.MapleServerHandler;
import handling.channel.PlayerStorage;
import handling.mina.MapleCodecFactory;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.ServerProperties;

import java.io.IOException;
import java.net.InetSocketAddress;

public class CashShopServer {

    private static final Logger logger = LoggerFactory.getLogger(CashShopServer.class);

    private static String ip;
    private static InetSocketAddress InetSocketadd;
    private static int PORT;
    private static final short DEFAULT_PORT = 5200;
    private static IoAcceptor acceptor;
    private static PlayerStorage players;
    private static PlayerStorage playersMTS;
    private static boolean finishedShutdown;

    public static void run_startup_configurations() {
        CashShopServer.PORT = Short.parseShort(ServerProperties.getProperty("RoyMS.CSPort", String.valueOf(DEFAULT_PORT)));
        CashShopServer.ip = ServerProperties.getProperty("RoyMS.IP") + ":" + CashShopServer.PORT;
        IoBuffer.setUseDirectBuffer(false);
        IoBuffer.setAllocator(new SimpleBufferAllocator());
        CashShopServer.acceptor = new NioSocketAcceptor();
        CashShopServer.acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MapleCodecFactory()));
        ((SocketSessionConfig) CashShopServer.acceptor.getSessionConfig()).setTcpNoDelay(true);
        CashShopServer.players = new PlayerStorage(-10);
        CashShopServer.playersMTS = new PlayerStorage(-20);
        try {
            CashShopServer.acceptor.setHandler(new MapleServerHandler(-1, true));
            CashShopServer.acceptor.bind(new InetSocketAddress(CashShopServer.PORT));
            logger.info("商城    1: 启动端口 " + CashShopServer.PORT);
        } catch (IOException e) {
//            logger.error("Binding to port " + CashShopServer.PORT + " failed");
            throw new RuntimeException("Binding to port " + CashShopServer.PORT + " failed", e);
        }
    }

    public static String getIP() {
        return CashShopServer.ip;
    }

    public static PlayerStorage getPlayerStorage() {
        return CashShopServer.players;
    }

    public static PlayerStorage getPlayerStorageMTS() {
        return CashShopServer.playersMTS;
    }

    public static void shutdown() {
        if (CashShopServer.finishedShutdown) {
            return;
        }
        logger.info("正在断开商城内玩家...");
        CashShopServer.players.disconnectAll();
        CashShopServer.playersMTS.disconnectAll();
        logger.info("正在关闭商城伺服器...");
        CashShopServer.finishedShutdown = true;
    }

    public static boolean isShutdown() {
        return CashShopServer.finishedShutdown;
    }

    static {
        CashShopServer.finishedShutdown = false;
    }
}
