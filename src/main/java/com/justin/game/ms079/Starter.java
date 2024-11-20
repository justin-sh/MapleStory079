package com.justin.game.ms079;

import client.MapleCharacter;
import client.MapleClient;
import client.SkillFactory;
import constants.GameConstants;
import constants.OtherSettings;
import constants.ServerConstants;
import database.DatabaseConnection;
import gui.RoyMS;
import handling.MapleServerHandler;
import handling.cashshop.CashShopServer;
import handling.channel.ChannelServer;
import handling.channel.MapleGuildRanking;
import handling.login.LoginInformationProvider;
import handling.login.LoginServer;
import handling.world.World;
import handling.world.family.MapleFamilyBuff;
import handling.world.guild.MapleGuild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import server.*;
import server.Timer;
import server.events.MapleOxQuizFactory;
import server.life.MapleLifeFactory;
import server.life.MapleMonsterInformationProvider;
import server.life.MobSkillFactory;
import server.maps.MapleMapFactory;
import server.quest.MapleQuest;
import tools.FileoutputUtil;
import tools.StringUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class Starter {

    private static final Logger logger = LoggerFactory.getLogger(Starter.class);

    public static boolean init;
    private static RoyMS CashGui;
    public static Starter instance;
    private static int maxUsers;
    private static final int srvPort = 6350;
    private MapleClient c;

    public static void main(final String[] args) throws InterruptedException {
        Starter.instance.init();
        Starter.instance.run();
        boolean loadGui = Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.loadGui", "false"));
        if (loadGui) {
            CashGui();
        }
    }

    private void init(){
        if(Starter.init){
            return;
        }
        Starter.init = true;

        String homePath = System.getProperty("homePath", "./config/");
        String scriptsPath = System.getProperty("scriptsPath", "./scripts/");
        String wzPath = System.getProperty("wzPath", "./scripts/wz");
        System.setProperty("server_property_file_path", homePath + "server.properties");
        System.setProperty("server_property_db_path", homePath + "db.properties");
        System.setProperty("server_property_shop_path", homePath + "shop.properties");
        System.setProperty("server_property_fish_path", homePath + "fish.properties");
        System.setProperty("wzPath", wzPath);
        System.setProperty("scripts_path", scriptsPath);
        System.setProperty("server_name", "冒险岛");

        OtherSettings.getInstance();
    }

    public void run() {
        final long start = System.currentTimeMillis();
        checkSingleInstance();
        if (Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.Admin"))) {
            logger.info(sectionString("[!!! 已开启只能管理员登录模式 !!!]"));

        }
        if (Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.AutoRegister"))) {
            logger.info("加载 自动注册完成 :::");
        }
        try {
            try (final PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("UPDATE accounts SET loggedin = 0, lastGainHM = 0")) {
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("[数据库异常] 请检查数据库链接。目前无法连接到MySQL数据库.");
        }
        logger.info("服务端 开始启动...");
        logger.info("当前操作系统: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
        logger.info("服务器地址: " + ServerProperties.getProperty("RoyMS.IP") + ":" + LoginServer.PORT);
        logger.info("游戏版本: " + ServerConstants.MAPLE_TYPE + " v." + ServerConstants.MAPLE_VERSION + "." + ServerConstants.MAPLE_PATCH);
        logger.info("主服务器: 蓝蜗牛");

        Runtime.getRuntime().addShutdownHook(new Thread(new Shutdown()));

        World.init();
        runThread();
        loadData();
        logger.info("加载\"登入\"服务...");
        LoginServer.run_startup_configurations();
        logger.info("正在加载频道...");
        ChannelServer.startChannel_Main();
        logger.info("频道加载完成!");
        logger.info("正在加载商城...");
        CashShopServer.run_startup_configurations();
        logger.info(sectionString("刷怪线程"));
        World.registerRespawn();
        server.Timer.CheatTimer.getInstance().register(AutobanManager.getInstance(), 60000L);
        onlineTime(1);
        memoryRecical(10);
        MapleServerHandler.registerMBean();
        LoginServer.setOn();
        logger.info("经验倍率：" + Integer.parseInt(ServerProperties.getProperty("RoyMS.Exp")) + "  物品倍率：" + Integer.parseInt(ServerProperties.getProperty("RoyMS.Drop")) + "  金币倍率：" + Integer.parseInt(ServerProperties.getProperty("RoyMS.Meso")) + "  BOSS爆率：" + Integer.parseInt(ServerProperties.getProperty("RoyMS.BDrop")));
        if (Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.检测复制装备", "false"))) {
            checkCopyItemFromSql();
        }
        if (Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.防万能检测", "false"))) {
            logger.info("启动防万能检测");
            startCheck();
        }
        final long now = System.currentTimeMillis() - start;
        final long seconds = now / 1000L;
        final long ms = now % 1000L;
        logger.info("加载完成, 耗时: " + seconds + "秒" + ms + "毫秒.");

    }

    public static void runThread() {
        logger.info("正在加载线程");
        server.Timer.WorldTimer.getInstance().start();
        server.Timer.EtcTimer.getInstance().start();
        server.Timer.MapTimer.getInstance().start();
        server.Timer.MobTimer.getInstance().start();
        server.Timer.CloneTimer.getInstance().start();
        server.Timer.CheatTimer.getInstance().start();
        logger.info("............");
        server.Timer.EventTimer.getInstance().start();
        server.Timer.BuffTimer.getInstance().start();
        server.Timer.TimerManager.getInstance().start();
        server.Timer.PingTimer.getInstance().start();
        server.Timer.PGTimer.getInstance().start();
        logger.info("正在加载线程完成.");
    }

    public static void loadData() {
        logger.info("载入数据(因为数据量大可能比较久而且内存消耗会飙升)");
        logger.info("加载等级经验数据");
        GameConstants.LoadExp();
        logger.info("加载排名信息数据");
        MapleGuildRanking.getInstance().RankingUpdate();
        logger.info("加载公会数据并清理不存在公会");
        MapleGuild.loadAll();
        logger.info("加载任务数据");
        MapleQuest.initQuests();
        MapleLifeFactory.loadQuestCounts();
        logger.info("加载爆物数据");
        MapleMonsterInformationProvider.getInstance().retrieveGlobal();
        logger.info("加载脏话检测系统");
        LoginInformationProvider.getInstance();
        logger.info("加载道具数据");
        ItemMakerFactory.getInstance();
        MapleItemInformationProvider.getInstance().load();
        logger.info("加载技能数据");
        SkillFactory.getSkill(99999999);
        MobSkillFactory.getInstance();
        MapleFamilyBuff.getBuffEntry();
        logger.info("加载SpeedRunner");
        try {
            SpeedRunner.getInstance().loadSpeedRuns();
        } catch (SQLException e) {
            logger.error("SpeedRunner错误.", e);
        }
        logger.info("加载随机奖励系统");
        RandomRewards.getInstance();
        logger.info("加载0X问答系统");
        MapleOxQuizFactory.getInstance().initialize();
        logger.info("加载嘉年华数据");
        MapleCarnivalFactory.getInstance();
        logger.info("加载角色类排名数据");
        logger.info("加载商城道具数据，数据较为庞大，请耐心等待");
        CashItemFactory.getInstance().initialize();
        MapleMapFactory.loadCustomLife();
    }

    public static void 自动存档(final int time) {
        logger.info("服务端启用自动存档." + time + "分钟自动执行数据存档.");
        server.Timer.WorldTimer.getInstance().register(() -> {
            try {
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        if (chr == null) {
                            continue;
                        }
                        chr.saveToDB(false, false);
                    }
                }
            } catch (Exception ex) {
            }
        }, 60000 * time);
    }

    //在线时间
    public static void onlineTime(final int time) {
        logger.info("服务端启用在线时间统计." + time + "分钟记录一次在线时间.");
        server.Timer.WorldTimer.getInstance().register(() -> {
            try {
                for (final ChannelServer chan : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : chan.getPlayerStorage().getAllCharacters()) {
                        if (chr == null) {
                            continue;
                        }
                        chr.gainGamePoints(1);
                        if (chr.getGamePoints() >= 5) {
                            continue;
                        }
                        chr.resetFBRW();
                        chr.resetFBRWA();
                        chr.resetSBOSSRW();
                        chr.resetSBOSSRWA();
                        chr.resetSGRW();
                        chr.resetSGRWA();
                        chr.resetSJRW();
                        chr.resetlb();
                        chr.setmrsjrw(0);
                        chr.setmrfbrw(0);
                        chr.setmrsgrw(0);
                        chr.setmrsbossrw(0);
                        chr.setmrfbrwa(0);
                        chr.setmrsgrwa(0);
                        chr.setmrsbossrwa(0);
                        chr.setmrfbrwas(0);
                        chr.setmrsgrwas(0);
                        chr.setmrsbossrwas(0);
                        chr.setmrfbrws(0);
                        chr.setmrsgrws(0);
                        chr.setmrsbossrws(0);
                        chr.resetGamePointsPS();
                        chr.resetGamePointsPD();
                    }
                }
            } catch (Exception ex) {
            }
        }, 60000 * time);
    }

    private static void checkSingleInstance() {
        try {
            new ServerSocket(srvPort);
        } catch (IOException ex) {
            if (ex.getMessage().contains("Address already in use: JVM_Bind")) {
                logger.error("在一台主机上同时只能启动一个进程(Only one instance allowed)。");
            }
            throw new RuntimeException("在一台主机上同时只能启动一个进程(Only one instance allowed)。", ex);
        }
    }

    protected static void checkCopyItemFromSql() {
        logger.info("服务端启用 防复制系统，发现复制装备.进行删除处理功能");
        final List<Integer> equipOnlyIds = new ArrayList<Integer>();
        final Map<Integer, Integer> checkItems = new HashMap<Integer, Integer>();
        try {
            final Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE equipOnlyId > 0");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final int itemId = rs.getInt("itemId");
                final int equipOnlyId = rs.getInt("equipOnlyId");
                if (equipOnlyId > 0) {
                    if (checkItems.containsKey(equipOnlyId)) {
                        if (checkItems.get(equipOnlyId) != itemId) {
                            continue;
                        }
                        equipOnlyIds.add(equipOnlyId);
                    } else {
                        checkItems.put(equipOnlyId, itemId);
                    }
                }
            }
            rs.close();
            ps.close();
            Collections.sort(equipOnlyIds);
            for (final int i : equipOnlyIds) {
                ps = con.prepareStatement("DELETE FROM inventoryitems WHERE equipOnlyId = ?");
                ps.setInt(1, i);
                ps.executeUpdate();
                ps.close();
                logger.info("发现复制装备 该装备的唯一ID: " + i + " 已进行删除处理..");
                FileoutputUtil.log("装备复制.txt", "发现复制装备 该装备的唯一ID: " + i + " 已进行删除处理..");
            }
        } catch (SQLException ex) {
            logger.info("[EXCEPTION] 清理复制装备出现错误." + ex);
        }
    }

    public void startServer() throws InterruptedException {
        this.init();
        this.run();
    }

    public static void CashGui() {
        logger.info("加载GUI工具");
        if (Starter.CashGui != null) {
            Starter.CashGui.dispose();
        }
        (Starter.CashGui = new RoyMS()).setVisible(true);
    }

    //在线统计
    public static void onlineStatistics(final int time) {
        logger.info("服务端启用在线统计." + time + "分钟统计一次在线的人数信息.");
        server.Timer.WorldTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
                final Map<Integer, Integer> connected = World.getConnected();
                final StringBuilder conStr = new StringBuilder(FileoutputUtil.CurrentReadable_Time() + " 在线人数: ");
                for (final int i : connected.keySet()) {
                    if (i == 0) {
                        final int users = connected.get(i);
                        conStr.append(StringUtil.padRight(String.valueOf(users), ' ', 3));
                        if (users > Starter.maxUsers) {
                            Starter.maxUsers = users;
                        }
                        conStr.append(" 最高在线: ");
                        conStr.append(Starter.maxUsers);
                        break;
                    }
                }
                logger.info(conStr.toString());
                if (Starter.maxUsers > 0) {
                    FileoutputUtil.log("logs/在线统计.log", conStr.toString());
                }
            }
        }, 60000 * time);
    }

    private static String sectionString(String s) {
        int padding = 79 / 2 - s.getBytes(StandardCharsets.UTF_8).length / 2;
        return "=".repeat(padding) + s + "=".repeat(padding);

    }

    public static void startCheck() {
        logger.info("服务端启用检测.30秒检测一次角色是否与登录器断开连接.");
        server.Timer.WorldTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
                for (final ChannelServer cserv_ : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv_.getPlayerStorage().getAllCharacters()) {
                        if (chr != null) {
                            chr.startCheck();
                        }
                    }
                }
            }
        }, 30000L);
    }

    //内存回收
    public static void memoryRecical(final int time) {
        Timer.WorldTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
                System.gc();
            }
        }, 60000 * time);
    }

    static {
        Starter.instance = new Starter();
        Starter.maxUsers = 0;
    }

    public static class Shutdown implements Runnable {
        @Override
        public void run() {
            new Thread(ShutdownServer.getInstance()).start();
        }
    }
}
