package gui;

import client.LoginCrypto;
import client.MapleCharacter;
import client.inventory.Equip;
import client.inventory.ItemFlag;
import client.inventory.MapleInventoryType;
import constants.GameConstants;
import constants.ServerConstants;
import database.DatabaseConnection;
import handling.RecvPacketOpcode;
import handling.SendPacketOpcode;
import handling.channel.ChannelServer;
import handling.login.handler.AutoRegister;
import handling.world.World;
import org.slf4j.LoggerFactory;
import scripting.PortalScriptManager;
import scripting.ReactorScriptManager;
import server.Timer;
import server.*;
import server.life.MapleMonsterInformationProvider;
import server.quest.MapleQuest;
import tools.MaplePacketCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class RoyMS extends JFrame {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RoyMS.class);

    private static RoyMS instance;
    private static ScheduledFuture<?> ts;
    private int minutesLeft;
    private static Thread t;
    private JTextPane chatLog;
    private Checkbox checkbox1;
    private JTextField txtCharToDisconnect;
    private JTextField jTextField10;
    private JTextField jTextField11;
    private JTextField jTextField12;
    private JTextField jTextField13;
    private JTextField jTextField14;
    private JTextField jTextField15;
    private JTextField jTextField16;
    private JTextField jTextField17;
    private JTextField jTextField18;
    private JTextField jTextField19;
    private JTextField txtNotice;
    private JTextField txtGiftAmt;
    private JTextField txtGiftType;
    private JTextField txtShutdonwServerMinutes;
    private JTextField txtAccToDisconnect;
    private JTextField txtAccUpdated;
    private JTextField txtPasswd;
    private JTextField jTextField26;
    private JTextField txtAcc;
    private JTextField txtItemId;
    private JTextField txtAmt;
    private JTextField jTextField6;
    private JTextField jTextField7;
    private JTextField jTextField8;
    private JTextField jTextField9;

    public static RoyMS getInstance() {
        return RoyMS.instance;
    }

    public RoyMS() {
        this.minutesLeft = 0;
        URL iconUrl = RoyMS.class.getClassLoader().getResource("gui/Icon.png");
        assert iconUrl != null;
        final ImageIcon icon = new ImageIcon(iconUrl);
        this.setIconImage(icon.getImage());
        if (GameConstants.game == 0) {
            this.setTitle("服务端-079V6控制台");
        } else {
            this.setTitle("服务端-控制台");
        }
        this.initComponents();
    }

    private void initComponents() {
        Canvas canvas1 = new Canvas();
        JScrollPane jScrollPane1 = new JScrollPane();
        this.chatLog = new JTextPane();
        JTabbedPane tPane = new JTabbedPane();
        JPanel panelServerConf = new JPanel();
        JButton btnStartServer = new JButton();
        this.txtShutdonwServerMinutes = new JTextField();
        JButton btnShutdownServer = new JButton();
        JButton btnQueryOnlineUserCnt = new JButton();
        JButton btnDisconnectAll = new JButton();
        JScrollPane sPaneServerInfo = new JScrollPane();
        JPanel panelSaveData = new JPanel();
        JButton btnSaveData = new JButton();
        JButton jButton8 = new JButton();
        JLabel jLabel2 = new JLabel();
        JPanel panelReloadData = new JPanel();
        JButton btnReloadQuests = new JButton();
        JButton btnReloadEvents = new JButton();
        JButton btnReloadDrops = new JButton();
        JButton btnReloadShops = new JButton();
        JButton btnReloadPortalScripts = new JButton();
        JButton btnReloadReactorScripts = new JButton();
        JLabel jLabel1 = new JLabel();
        JButton btnReloadPacketOpcode = new JButton();
        JButton btnReloadCashShops = new JButton();
        JButton btnReloadDbConnections = new JButton();
        JPanel panelAccountChars = new JPanel();
        JButton btnDisconnectChar = new JButton();
        this.txtCharToDisconnect = new JTextField();
        this.txtAccToDisconnect = new JTextField();
        JButton btnDisconnectAccount = new JButton();
        JPanel panelNotice = new JPanel();
        this.txtNotice = new JTextField();
        JButton btnReleaseNotices = new JButton();
        this.txtAcc = new JTextField();
        this.txtItemId = new JTextField();
        JButton btnGiftItems = new JButton();
        this.txtAmt = new JTextField();
        this.jTextField6 = new JTextField();
        this.jTextField7 = new JTextField();
        this.jTextField8 = new JTextField();
        this.jTextField9 = new JTextField();
        this.jTextField10 = new JTextField();
        this.jTextField11 = new JTextField();
        this.jTextField12 = new JTextField();
        this.jTextField13 = new JTextField();
        this.jTextField14 = new JTextField();
        this.jTextField15 = new JTextField();
        this.jTextField16 = new JTextField();
        this.jTextField17 = new JTextField();
        this.jTextField18 = new JTextField();
        this.jTextField19 = new JTextField();
        JPanel panelRewards = new JPanel();
        this.txtGiftAmt = new JTextField();
        this.txtGiftType = new JTextField();
        JButton btnGift = new JButton();
        JPanel jPanel3 = new JPanel();
        this.txtAccUpdated = new JTextField();
        this.txtPasswd = new JTextField();
        JButton btnUpdatePwd = new JButton();
        this.jTextField26 = new JTextField();
        this.checkbox1 = new Checkbox();
        JButton jButton20 = new JButton();
        JButton jButton21 = new JButton();
        JTabbedPane jTabbedPane1 = new JTabbedPane();
        JLabel jLabel3 = new JLabel();
        JLabel jLabel4 = new JLabel();
        JLabel jLabel5 = new JLabel();
        JLabel jLabel6 = new JLabel();
        JLabel jLabel7 = new JLabel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jScrollPane1.setViewportView(this.chatLog);

        btnStartServer.setText("启动服务端");
        btnStartServer.addActionListener(RoyMS.this::btnStartServerActionPerformed);
        this.txtShutdonwServerMinutes.setText("关闭服务器倒数时间");
        btnShutdownServer.setText("关闭服务器");
        btnShutdownServer.addActionListener(RoyMS.this::btnShutdownServerActionPerformed);
        btnQueryOnlineUserCnt.setText("查询总计在线人数");
        btnQueryOnlineUserCnt.addActionListener(RoyMS.this::jButton22ActionPerformed);
        btnDisconnectAll.setText("断开全服玩家");
        btnDisconnectAll.addActionListener(RoyMS.this::btnDisconnectAllActionPerformed);

        JTextArea txtAreaServerInfo = new JTextArea();
        txtAreaServerInfo.setColumns(20);
        txtAreaServerInfo.setRows(5);
        txtAreaServerInfo.setText("冒险岛079怀旧版");
        sPaneServerInfo.setViewportView(txtAreaServerInfo);
        final GroupLayout layoutSC = new GroupLayout(panelServerConf);
        panelServerConf.setLayout(layoutSC);
        layoutSC.setHorizontalGroup(layoutSC.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layoutSC.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layoutSC.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layoutSC.createSequentialGroup()
                                        .addComponent(btnQueryOnlineUserCnt, -2, 134, -2)
                                        .addGap(18, 18, 18)
                                        .addComponent(this.txtShutdonwServerMinutes, -2, -1, -2)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 26, 32767)
                                        .addComponent(btnDisconnectAll, -2, 134, -2)
                                ).addGroup(layoutSC.createSequentialGroup()
                                        .addGroup(layoutSC.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(btnShutdownServer, -2, 134, -2)
                                                .addComponent(btnStartServer, -2, 134, -2)
                                        ).addGap(18, 18, 18)
                                        .addComponent(sPaneServerInfo)
                                )
                        )
                        .addGap(24, 24, 24)
                )
        );
        layoutSC.setVerticalGroup(layoutSC.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layoutSC.createSequentialGroup()
                        .addContainerGap(-1, 32767)
                        .addGroup(layoutSC.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(GroupLayout.Alignment.TRAILING,
                                        layoutSC.createSequentialGroup()
                                                .addComponent(btnStartServer, -2, 49, -2)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnShutdownServer, -2, 49, -2)
                                ).addComponent(sPaneServerInfo, GroupLayout.Alignment.TRAILING, -2, -1, -2)
                        )
                        .addGap(18, 18, 18)
                        .addGroup(layoutSC.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btnQueryOnlineUserCnt)
                                .addComponent(this.txtShutdonwServerMinutes, -2, -1, -2)
                                .addComponent(btnDisconnectAll)
                        ).addGap(8, 8, 8)
                )
        );
        tPane.addTab("服务器配置", panelServerConf);

        btnSaveData.setText("保存数据");
        btnSaveData.addActionListener(RoyMS.this::btnSaveDataActionPerformed);
        jButton8.setText("保存雇佣");
        jButton8.addActionListener(RoyMS.this::jButton8ActionPerformed);
        jLabel2.setText("保存系列：");
        final GroupLayout layoutSave = new GroupLayout(panelSaveData);
        panelSaveData.setLayout(layoutSave);
        layoutSave.setHorizontalGroup(layoutSave.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layoutSave.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layoutSave.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addGroup(layoutSave.createSequentialGroup()
                                        .addComponent(btnSaveData)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton8)
                                )
                        )
                        .addContainerGap(295, 32767)
                )
        );
        layoutSave.setVerticalGroup(layoutSave.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layoutSave.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layoutSave.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btnSaveData)
                                .addComponent(jButton8)
                        )
                        .addContainerGap(125, 32767)
                )
        );
        tPane.addTab("保存数据", panelSaveData);

        btnReloadQuests.setText("重载任务");
        btnReloadQuests.addActionListener(RoyMS.this::btnReloadQuestsActionPerformed);
        btnReloadEvents.setText("重载副本");
        btnReloadEvents.addActionListener(RoyMS.this::btnReloadEventsActionPerformed);
        btnReloadDrops.setText("重载爆率");
        btnReloadDrops.addActionListener(RoyMS.this::btnReloadDropsActionPerformed);
        btnReloadShops.setText("重载商店");
        btnReloadShops.addActionListener(RoyMS.this::btnReloadShopsActionPerformed);
        btnReloadPortalScripts.setText("重载传送门");
        btnReloadPortalScripts.addActionListener(RoyMS.this::btnReloadPortalScriptsActionPerformed);
        btnReloadReactorScripts.setText("重载反应堆");
        btnReloadReactorScripts.addActionListener(RoyMS.this::btnReloadReactorScriptsActionPerformed);

        jLabel1.setText("重载系列：");
        btnReloadPacketOpcode.setText("重载包头");
        btnReloadPacketOpcode.addActionListener(RoyMS.this::btnReloadPacketOpcodeActionPerformed);
        btnReloadCashShops.setText("重载商城");
        btnReloadCashShops.addActionListener(RoyMS.this::btnReloadCashShopsActionPerformed);
        btnReloadDbConnections.setText("清除Sql連線");
        btnReloadDbConnections.addActionListener(RoyMS.this::btnReloadDbConnectionsActionPerformed);
        final GroupLayout layoutReload = new GroupLayout(panelReloadData);
        panelReloadData.setLayout(layoutReload);
        layoutReload.setHorizontalGroup(layoutReload.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layoutReload.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layoutReload.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addGroup(layoutReload.createSequentialGroup()
                                        .addComponent(btnReloadPacketOpcode)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnReloadCashShops)
                                ).addGroup(layoutReload.createSequentialGroup()
                                        .addComponent(btnReloadEvents)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnReloadDrops)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnReloadReactorScripts)
                                        .addGap(12, 12, 12)
                                        .addComponent(btnReloadPortalScripts)
                                ).addGroup(layoutReload.createSequentialGroup()
                                        .addComponent(btnReloadQuests)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnReloadShops)
                                ).addComponent(btnReloadDbConnections)
                        ).addContainerGap(91, 32767)
                )
        );
        layoutReload.setVerticalGroup(layoutReload.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layoutReload.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGroup(layoutReload.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layoutReload.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnReloadPortalScripts)
                                        .addComponent(btnReloadReactorScripts)
                                ).addGroup(layoutReload.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnReloadEvents)
                                        .addComponent(btnReloadDrops)
                                )
                        ).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layoutReload.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btnReloadQuests)
                                .addComponent(btnReloadShops)
                        ).addGap(10, 10, 10)
                        .addGroup(layoutReload.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btnReloadPacketOpcode)
                                .addComponent(btnReloadCashShops)
                        ).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReloadDbConnections)
                        .addContainerGap(32, 32767)
                )
        );
        tPane.addTab("重载系列", panelReloadData);

        btnDisconnectChar.setText("解卡玩家");
        btnDisconnectChar.addActionListener(RoyMS.this::btnDisconnectCharActionPerformed);
        this.txtCharToDisconnect.setText("输入玩家名字");
        this.txtAccToDisconnect.setText("输入账号");
        btnDisconnectAccount.setText("解卡账号");
        btnDisconnectAccount.addActionListener(RoyMS.this::btnDisconnectAccountActionPerformed);
        final GroupLayout layoutAccNChar = new GroupLayout(panelAccountChars);
        panelAccountChars.setLayout(layoutAccNChar);
        layoutAccNChar.setHorizontalGroup(layoutAccNChar.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layoutAccNChar.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layoutAccNChar.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layoutAccNChar.createSequentialGroup()
                                        .addComponent(this.txtCharToDisconnect, -2, 124, -2)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnDisconnectChar)
                                )
                                .addGroup(layoutAccNChar.createSequentialGroup()
                                        .addComponent(this.txtAccToDisconnect, -2, 124, -2)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnDisconnectAccount)
                                )
                        )
                        .addContainerGap(252, 32767)
                )
        );
        layoutAccNChar.setVerticalGroup(layoutAccNChar.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layoutAccNChar.createSequentialGroup().addContainerGap().addGroup(layoutAccNChar.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.txtCharToDisconnect, -2, -1, -2).addComponent(btnDisconnectChar)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layoutAccNChar.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.txtAccToDisconnect, -2, -1, -2).addComponent(btnDisconnectAccount)).addContainerGap(117, 32767)));
        tPane.addTab("卡号处理", panelAccountChars);


        btnReleaseNotices.setText("公告发布");
        btnReleaseNotices.addActionListener(RoyMS.this::btnReleaseNoticesActionPerformed);

        this.txtAcc.setText("玩家名字");
        this.txtItemId.setText("物品ID");
        btnGiftItems.setText("给予物品");
        btnGiftItems.addActionListener(RoyMS.this::btnGiftItemsActionPerformed);
        this.txtAmt.setText("数量");
        this.jTextField6.setText("力量");
        this.jTextField7.setText("敏捷");
        this.jTextField8.setText("智力");
        this.jTextField9.setText("运气");
        this.jTextField10.setText("HP设置");
        this.jTextField11.setText("MP设置");
        this.jTextField12.setText("加卷次数");
        this.jTextField13.setText("制作人");
        this.jTextField14.setText("给予物品时间");
        this.jTextField15.setText("可以交易");
        this.jTextField16.setText("攻击力");
        this.jTextField17.setText("魔法力");
        this.jTextField18.setText("物理防御");
        this.jTextField19.setText("魔法防御");
        final GroupLayout layoutNotice = new GroupLayout(panelNotice);
        panelNotice.setLayout(layoutNotice);
        layoutNotice.setHorizontalGroup(layoutNotice.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layoutNotice.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layoutNotice.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layoutNotice.createSequentialGroup()
                                        .addComponent(this.txtNotice, -1, 500, 32767)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnReleaseNotices)
                                )
                                .addGroup(layoutNotice.createSequentialGroup()
                                        .addComponent(this.txtAcc, -2, 92, -2)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(this.txtItemId, -2, 77, -2)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(this.txtAmt, -2, 52, -2)
                                )
                                .addGroup(layoutNotice.createSequentialGroup()
                                        .addGroup(
                                                layoutNotice.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(layoutNotice.createSequentialGroup()
                                                                .addComponent(this.jTextField9, -2, 58, -2)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(this.jTextField13)
                                                        ).addGroup(GroupLayout.Alignment.TRAILING,
                                                                layoutNotice.createSequentialGroup()
                                                                        .addGroup(layoutNotice.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                                .addComponent(this.jTextField8)
                                                                                .addComponent(this.jTextField7)
                                                                        ).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addGroup(layoutNotice.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                                .addComponent(this.jTextField11, -2, 79, -2).addComponent(this.jTextField12, -2, 79, -2))).addGroup(layoutNotice.createSequentialGroup().addComponent(this.jTextField6, -2, 58, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextField10, -2, 79, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layoutNotice.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jTextField16).addComponent(this.jTextField15).addComponent(this.jTextField14).addComponent(this.jTextField17)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layoutNotice.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(btnGiftItems, -1, -1, 32767).addComponent(this.jTextField18).addComponent(this.jTextField19)))).addContainerGap()));
        layoutNotice.setVerticalGroup(layoutNotice.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layoutNotice.createSequentialGroup().addContainerGap().addGroup(layoutNotice.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.txtNotice, -2, -1, -2).addComponent(btnReleaseNotices)).addGap(18, 18, 18).addGroup(layoutNotice.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.txtAcc, -2, -1, -2).addComponent(this.txtItemId, -2, -1, -2).addComponent(this.txtAmt, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layoutNotice.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField6, -2, -1, -2).addComponent(this.jTextField10, -2, -1, -2).addComponent(this.jTextField14, -2, -1, -2).addComponent(this.jTextField18, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layoutNotice.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField7, -2, -1, -2).addComponent(this.jTextField11, -2, -1, -2).addComponent(this.jTextField15, -2, -1, -2).addComponent(this.jTextField19, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layoutNotice.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField8, -2, -1, -2).addComponent(this.jTextField12, -2, -1, -2).addComponent(this.jTextField16, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layoutNotice.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField9, -2, -1, -2).addComponent(this.jTextField13, -2, -1, -2).addComponent(this.jTextField17, -2, -1, -2).addComponent(btnGiftItems)).addContainerGap(-1, 32767)));
        tPane.addTab("指令/公告", panelNotice);


        this.txtGiftAmt.setText("输入数量");
        this.txtGiftAmt.addActionListener(RoyMS.this::jTextField20ActionPerformed);
        this.txtGiftType.setText("1点卷/2抵用/3金币/4经验");
        btnGift.setText("发放全服点卷/抵用卷/金币/经验");
        btnGift.addActionListener(RoyMS.this::btnGiftActionPerformed);
        final GroupLayout layoutGift = new GroupLayout(panelRewards);
        panelRewards.setLayout(layoutGift);
        layoutGift.setHorizontalGroup(layoutGift.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layoutGift.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(this.txtGiftAmt, 88, 88, -2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(this.txtGiftType, 100, 200, -2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGift)
                        .addContainerGap(-1, 32767)
                )
        );
        layoutGift.setVerticalGroup(layoutGift.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layoutGift.createSequentialGroup().addContainerGap().addGroup(layoutGift.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.txtGiftAmt, -2, -1, -2).addComponent(this.txtGiftType, -2, -1, -2).addComponent(btnGift)).addContainerGap(146, 32767)));
        tPane.addTab("奖励系列", panelRewards);
        this.txtAccUpdated.setText("账号");
        this.txtPasswd.setText("新密码");
        btnUpdatePwd.setText("修改密码");
        btnUpdatePwd.addActionListener(RoyMS.this::btnUpdatePwdActionPerformed);
        this.jTextField26.setText("万能密码");
        this.checkbox1.setCursor(new Cursor(0));
        this.checkbox1.setName("123");
        this.checkbox1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                RoyMS.this.checkbox1MouseClicked(evt);
            }
        });
        jButton20.setText("设置可万能登录");
        jButton20.addActionListener(RoyMS.this::jButton20ActionPerformed);
        jButton21.setText("取消其万能登录权限");
        jButton21.addActionListener(RoyMS.this::jButton21ActionPerformed);
        final GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addComponent(this.txtAccUpdated, -2, 88, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.txtPasswd, -2, 88, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextField26, -2, 88, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.checkbox1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(btnUpdatePwd).addGap(181, 181, 181)).addGroup(jPanel3Layout.createSequentialGroup().addComponent(jButton20).addGap(18, 18, 18).addComponent(jButton21).addContainerGap(-1, 32767)))));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.txtAccUpdated, -2, -1, -2).addComponent(this.txtPasswd, -2, -1, -2).addComponent(this.jTextField26, -2, -1, -2).addComponent(btnUpdatePwd)).addComponent(this.checkbox1, -2, -1, -2)).addGap(18, 18, 18).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jButton20).addComponent(jButton21)).addContainerGap(105, 32767)));
        tPane.addTab("账号服务", jPanel3);

        jLabel3.setFont(new Font("宋体", Font.BOLD, 12));
        jLabel3.setText("   本程序来自互联网 仅供学习测试 禁止商业用途 否则本人不承担任何后果");
        jTabbedPane1.addTab("公告申明", jLabel3);
        jLabel4.setFont(new Font("宋体", Font.BOLD, 12));
        jLabel4.setText("       版本号：V079_MAX正式版 ");
        jTabbedPane1.addTab("版权说明", jLabel4);
        jLabel5.setFont(new Font("宋体", Font.BOLD, 12));
        jLabel5.setText("      修复大量BUG 修复全任务 全副本 全BOSS 全剧情完美 全职业完美 ");
        jTabbedPane1.addTab("更新内容A", jLabel5);
        jLabel6.setFont(new Font("宋体", Font.BOLD, 12));
        jLabel6.setText("      修复卡号    修复双登    修复复制     修复假死    修复掉线");
        jTabbedPane1.addTab("更新内容B", jLabel6);
        jLabel7.setFont(new Font("宋体", Font.BOLD, 12));
        jLabel7.setText("      修复多线程   修复炸线   增加全新的外挂检测   增加大量函数");
        jTabbedPane1.addTab("更新内容C", jLabel7);
        tPane.addTab("关于我们", jTabbedPane1);
        final GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap(600, 32767)
                        .addComponent(canvas1, -2, -1, -2)
                )
                .addComponent(tPane, -2, 0, 32767)
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(tPane)
                        .addGap(5, 5, 5)
                        .addComponent(canvas1, -2, -1, -2)
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1, -2, 93, -2)
                        .addContainerGap()
                )
        );
        this.pack();
    }

    private void btnReloadEventsActionPerformed(final ActionEvent evt) {
        for (final ChannelServer instance1 : ChannelServer.getAllInstances()) {
            if (instance1 != null) {
                instance1.reloadEvents();
            }
        }
        final String 输出 = "[重载系统] 副本重载成功。";
        JOptionPane.showMessageDialog(null, "副本重载成功。");
        this.printChatLog(输出);
    }

    private void btnReloadDropsActionPerformed(final ActionEvent evt) {
        MapleMonsterInformationProvider.getInstance().clearDrops();
        final String 输出 = "[重载系统] 爆率重载成功。";
        JOptionPane.showMessageDialog(null, "爆率重载成功。");
        this.printChatLog(输出);
    }

    private void btnReloadPacketOpcodeActionPerformed(final ActionEvent evt) {
        SendPacketOpcode.reloadValues();
        RecvPacketOpcode.reloadValues();
        final String 输出 = "[重载系统] 包头重载成功。";
        JOptionPane.showMessageDialog(null, "包头重载成功。");
        this.printChatLog(输出);
    }

    private void btnReloadPortalScriptsActionPerformed(final ActionEvent evt) {
        PortalScriptManager.getInstance().clearScripts();
        final String msg = "[重载系统] 传送门重载成功。";
        JOptionPane.showMessageDialog(null, "传送门重载成功。");
        this.printChatLog(msg);
    }

    private void btnReloadShopsActionPerformed(final ActionEvent evt) {
        MapleShopFactory.getInstance().clear();
        final String msg = "[重载系统] 商店重载成功。";
        JOptionPane.showMessageDialog(null, "商店重载成功。");
        this.printChatLog(msg);
    }

    private void btnReloadReactorScriptsActionPerformed(final ActionEvent evt) {
        ReactorScriptManager.getInstance().clearDrops();
        final String msg = "[重载系统] 反应堆重载成功。";
        JOptionPane.showMessageDialog(null, "反应堆重载成功。");
        this.printChatLog(msg);
    }

    private void btnReloadQuestsActionPerformed(final ActionEvent evt) {
        MapleQuest.clearQuests();
        final String msg = "[重载系统] 任务重载成功。";
        JOptionPane.showMessageDialog(null, "任务重载成功。");
        this.printChatLog(msg);
    }

    private void jButton8ActionPerformed(final ActionEvent evt) {
        int p = 0;
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            ++p;
            cserv.closeAllMerchant();
        }
        final String msg = "[保存雇佣商人系统] 雇佣商人保存" + p + "个频道成功。";
        JOptionPane.showMessageDialog(null, "雇佣商人保存" + p + "个频道成功。");
        this.printChatLog(msg);
    }

    private void btnSaveDataActionPerformed(final ActionEvent evt) {
        int p = 0;
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                ++p;
                chr.saveToDB(true, true);
            }
        }
        final String msg = "[保存数据系统] 保存" + p + "个成功。";
        JOptionPane.showMessageDialog(null, msg);
        this.printChatLog(msg);
    }

    private void btnStartServerActionPerformed(final ActionEvent evt) {
        try {
            Start.instance.startServer();
            final String msg = "[服务器] 服务器启动成功！";
            this.printChatLog(msg);
        } catch (Exception ex) {
            logger.error("start server failed!", ex);
        }
    }

    private void btnDisconnectCharActionPerformed(final ActionEvent evt) {
        this.sendNotice(0);
    }

    private void btnReloadCashShopsActionPerformed(final ActionEvent evt) {
        CashItemFactory.getInstance().clearCashShop();
        final String out = "[重载系统] 商城重载成功。";
        JOptionPane.showMessageDialog(null, "商城重载成功。");
        this.printChatLog(out);
    }

    private void btnReleaseNoticesActionPerformed(final ActionEvent evt) {
        this.sendNoticeGG();
    }

    private void btnGiftItemsActionPerformed(final ActionEvent evt) {
        this.刷物品();
    }

    private void jTextField20ActionPerformed(final ActionEvent evt) {
    }

    private void btnGiftActionPerformed(final ActionEvent evt) {
        this.给全服点卷();
    }

    private void btnShutdownServerActionPerformed(final ActionEvent evt) {
        this.shutdownServer();
    }

    private void btnDisconnectAccountActionPerformed(final ActionEvent evt) {
        this.FixAcLogged();
    }

    private void btnUpdatePwdActionPerformed(final ActionEvent evt) {
        this.ChangePassWord();
    }

    private void btnReloadDbConnectionsActionPerformed(final ActionEvent evt) {
        DatabaseConnection.closeTimeout();
    }

    private void checkbox1MouseClicked(final MouseEvent evt) {
        final boolean status = this.checkbox1.getState();
        if (!(ServerConstants.Super_password = status)) {
            ServerConstants.superpw = "";
        } else {
            ServerConstants.superpw = this.jTextField26.getText();
        }
    }

    private void jButton20ActionPerformed(final ActionEvent evt) {
        this.可以万能登录();
    }

    private void jButton21ActionPerformed(final ActionEvent evt) {
        this.不可以万能登录();
    }

    private void jButton22ActionPerformed(final ActionEvent evt) {
        int p = 0;
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                if (chr != null) {
                    ++p;
                }
            }
        }
        JOptionPane.showMessageDialog(this, "当前在线人数：" + p + "人");
    }

    private void btnDisconnectAllActionPerformed(final ActionEvent evt) {
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            cserv.getPlayerStorage().disconnectAll(true);
        }
        JOptionPane.showMessageDialog(null, "已断开全部频道玩家");
    }

    private void 不可以万能登录() {
        final String account = this.txtAccUpdated.getText();
        if (!AutoRegister.checkAccountExistsByName(account)) {
            JOptionPane.showMessageDialog(null, "账号不存在");
            return;
        }
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("Update accounts set handsome = ? Where name = ?");
            ps.setString(1, "1");
            ps.setString(2, account);
            ps.execute();
            ps.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
        JOptionPane.showMessageDialog(null, "成功取消其万能权限");
        this.printChatLog("更改账号: " + account + " .设置取消其万能登录权限。");
    }

    private void 可以万能登录() {
        final String account = this.txtAccUpdated.getText();
        if (!AutoRegister.checkAccountExistsByName(account)) {
            JOptionPane.showMessageDialog(null, "账号不存在");
            return;
        }
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("Update accounts set handsome = ? Where name = ?");
            ps.setString(1, "0");
            ps.setString(2, account);
            ps.execute();
            ps.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
        JOptionPane.showMessageDialog(null, "成功设置万能登录权限");
        this.printChatLog("更改账号: " + account + " .设置其可以万能登录游戏.");
    }

    private void ChangePassWord() {
        final String account = this.txtAccUpdated.getText();
        final String password = this.txtPasswd.getText();
        if (password.length() > 12) {
            JOptionPane.showMessageDialog(null, "密码过长");
            return;
        }
        if (!AutoRegister.checkAccountExistsByName(account)) {
            JOptionPane.showMessageDialog(null, "账号不存在");
            return;
        }
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("Update accounts set password = ? Where name = ?");
            ps.setString(1, LoginCrypto.hexSha1(password));
            ps.setString(2, account);
            ps.execute();
            ps.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
        this.printChatLog("更改账号: " + account + "的密码为 " + password);
    }

    private void shutdownServer() {
        try {
            this.minutesLeft = Integer.parseInt(this.txtShutdonwServerMinutes.getText());
            if (RoyMS.ts == null && (RoyMS.t == null || !RoyMS.t.isAlive())) {
                RoyMS.t = new Thread(ShutdownServer.getInstance());
                RoyMS.ts = Timer.EventTimer.getInstance().register(() -> {
                    if (RoyMS.this.minutesLeft == 0) {
                        RoyMS.t.start();
                        RoyMS.ts.cancel(false);
                        return;
                    }
                    World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, "服务器將在 " + RoyMS.this.minutesLeft + "分钟后关闭. 请尽快关闭雇佣商人安全下线.").getBytes());
                    logger.info("服务器將在 " + RoyMS.this.minutesLeft + "分钟后关闭.");
                    RoyMS.this.minutesLeft--;
                }, TimeUnit.MINUTES.toMillis(1));
            }
            this.txtShutdonwServerMinutes.setText("关闭服务器倒数时间");
            this.printChatLog("关闭服务器...");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + e);
        }
    }

    private void 给全服点卷() {
        try {
            int 数量;
            if ("输入数量".equals(this.txtGiftAmt.getText())) {
                数量 = 0;
            } else {
                数量 = Integer.parseInt(this.txtGiftAmt.getText());
            }
            int 类型;
            if ("1点卷/2抵用/3金币/4经验".equals(this.txtGiftType.getText())) {
                类型 = 0;
            } else {
                类型 = Integer.parseInt(this.txtGiftType.getText());
            }
            if (数量 <= 0 || 类型 <= 0) {
                return;
            }
            String msg = "";
            int ret = 0;
            if (类型 == 1 || 类型 == 2) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        mch.modifyCSPoints(类型, 数量);
                        String cash = null;
                        if (类型 == 1) {
                            cash = "点卷";
                        } else if (类型 == 2) {
                            cash = "抵用卷";
                        }
                        mch.startMapEffect("管理员发放" + 数量 + cash + "给在线的所有玩家！快感谢管理员吧！", 5121009);
                        ++ret;
                    }
                }
            } else if (类型 == 3) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        mch.gainMeso(数量, true);
                        mch.startMapEffect("管理员发放" + 数量 + "冒险币给在线的所有玩家！快感谢管理员吧！", 5121009);
                        ++ret;
                    }
                }
            } else if (类型 == 4) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        mch.gainExp(数量, true, false, true);
                        mch.startMapEffect("管理员发放" + 数量 + "经验给在线的所有玩家！快感谢管理员吧！", 5121009);
                        ++ret;
                    }
                }
            }
            String 类型A = "";
            if (类型 == 1) {
                类型A = "点卷";
            } else if (类型 == 2) {
                类型A = "抵用卷";
            } else if (类型 == 3) {
                类型A = "金币";
            } else if (类型 == 4) {
                类型A = "经验";
            }
            msg = "一个发放[" + 数量 * ret + "]." + 类型A + "!一共发放给了" + ret + "人！";
            this.txtGiftAmt.setText("输入数量");
            this.txtGiftType.setText("1点卷/2抵用/3金币/4经验");
            this.printChatLog(msg);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + e);
        }
    }

    private void 刷物品() {
        try {
            String 名字;
            if ("玩家名字".equals(this.txtAcc.getText())) {
                名字 = "";
            } else {
                名字 = this.txtAcc.getText();
            }
            int 物品ID;
            if ("物品ID".equals(this.txtItemId.getText())) {
                物品ID = 0;
            } else {
                物品ID = Integer.parseInt(this.txtItemId.getText());
            }
            int 数量;
            if ("数量".equals(this.txtAmt.getText())) {
                数量 = 0;
            } else {
                数量 = Integer.parseInt(this.txtAmt.getText());
            }
            int 力量;
            if ("力量".equals(this.jTextField6.getText())) {
                力量 = 0;
            } else {
                力量 = Integer.parseInt(this.jTextField6.getText());
            }
            int 敏捷;
            if ("敏捷".equals(this.jTextField7.getText())) {
                敏捷 = 0;
            } else {
                敏捷 = Integer.parseInt(this.jTextField7.getText());
            }
            int 智力;
            if ("智力".equals(this.jTextField8.getText())) {
                智力 = 0;
            } else {
                智力 = Integer.parseInt(this.jTextField8.getText());
            }
            int 运气;
            if ("运气".equals(this.jTextField9.getText())) {
                运气 = 0;
            } else {
                运气 = Integer.parseInt(this.jTextField9.getText());
            }
            int HP;
            if ("HP设置".equals(this.jTextField10.getText())) {
                HP = 0;
            } else {
                HP = Integer.parseInt(this.jTextField10.getText());
            }
            int MP;
            if ("MP设置".equals(this.jTextField11.getText())) {
                MP = 0;
            } else {
                MP = Integer.parseInt(this.jTextField11.getText());
            }
            int 可加卷次数;
            if ("加卷次数".equals(this.jTextField12.getText())) {
                可加卷次数 = 0;
            } else {
                可加卷次数 = Integer.parseInt(this.jTextField12.getText());
            }
            String 制作人名字;
            if ("制作人".equals(this.jTextField13.getText())) {
                制作人名字 = "";
            } else {
                制作人名字 = this.jTextField13.getText();
            }
            int 给予时间;
            if ("给予物品时间".equals(this.jTextField14.getText())) {
                给予时间 = 0;
            } else {
                给予时间 = Integer.parseInt(this.jTextField14.getText());
            }
            final String 是否可以交易 = this.jTextField15.getText();
            int 攻击力;
            if ("攻击力".equals(this.jTextField16.getText())) {
                攻击力 = 0;
            } else {
                攻击力 = Integer.parseInt(this.jTextField16.getText());
            }
            int 魔法力;
            if ("魔法力".equals(this.jTextField17.getText())) {
                魔法力 = 0;
            } else {
                魔法力 = Integer.parseInt(this.jTextField17.getText());
            }
            int 物理防御;
            if ("物理防御".equals(this.jTextField18.getText())) {
                物理防御 = 0;
            } else {
                物理防御 = Integer.parseInt(this.jTextField18.getText());
            }
            int 魔法防御;
            if ("魔法防御".equals(this.jTextField19.getText())) {
                魔法防御 = 0;
            } else {
                魔法防御 = Integer.parseInt(this.jTextField19.getText());
            }
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(物品ID);
            String 输出A = "";
            final String msg = "玩家名字：" + 名字 + " 物品ID：" + 物品ID + " 数量：" + 数量 + " 力量:" + 力量 + " 敏捷:" + 敏捷 + " 智力:" + 智力 + " 运气:" + 运气 + " HP:" + HP + " MP:" + MP + " 可加卷次数:" + 可加卷次数 + " 制作人名字:" + 制作人名字 + " 给予时间:" + 给予时间 + " 是否可以交易:" + 是否可以交易 + " 攻击力:" + 攻击力 + " 魔法力:" + 魔法力 + " 物理防御:" + 物理防御 + " 魔法防御:" + 魔法防御 + "\r\n";
            for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    if (mch.getName().equals(名字)) {
                        if (数量 >= 0) {
                            if (!MapleInventoryManipulator.checkSpace(mch.getClient(), 物品ID, 数量, "")) {
                                return;
                            }
                            if ((type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(物品ID) && !GameConstants.isBullet(物品ID)) || (type.equals(MapleInventoryType.CASH) && 物品ID >= 5000000 && 物品ID <= 5000100)) {
                                final Equip item = (Equip) ii.getEquipById(物品ID);
                                if (ii.isCash(物品ID)) {
                                    item.setUniqueId(1);
                                }
                                if (力量 > 0 && 力量 <= 32767) {
                                    item.setStr((short) 力量);
                                }
                                if (敏捷 > 0 && 敏捷 <= 32767) {
                                    item.setDex((short) 敏捷);
                                }
                                if (智力 > 0 && 智力 <= 32767) {
                                    item.setInt((short) 智力);
                                }
                                if (运气 > 0 && 运气 <= 32767) {
                                    item.setLuk((short) 运气);
                                }
                                if (攻击力 > 0 && 攻击力 <= 32767) {
                                    item.setWatk((short) 攻击力);
                                }
                                if (魔法力 > 0 && 魔法力 <= 32767) {
                                    item.setMatk((short) 魔法力);
                                }
                                if (物理防御 > 0 && 物理防御 <= 32767) {
                                    item.setWdef((short) 物理防御);
                                }
                                if (魔法防御 > 0 && 魔法防御 <= 32767) {
                                    item.setMdef((short) 魔法防御);
                                }
                                if (HP > 0 && HP <= 30000) {
                                    item.setHp((short) HP);
                                }
                                if (MP > 0 && MP <= 30000) {
                                    item.setMp((short) MP);
                                }
                                if ("可以交易".equals(是否可以交易)) {
                                    byte flag = item.getFlag();
                                    if (item.getType() == MapleInventoryType.EQUIP.getType()) {
                                        flag |= (byte) ItemFlag.KARMA_EQ.getValue();
                                    } else {
                                        flag |= (byte) ItemFlag.KARMA_USE.getValue();
                                    }
                                    item.setFlag(flag);
                                }
                                if (给予时间 > 0) {
                                    item.setExpiration(System.currentTimeMillis() + 给予时间 * 24 * 60 * 60 * 1000);
                                }
                                if (可加卷次数 > 0) {
                                    item.setUpgradeSlots((byte) 可加卷次数);
                                }
                                if (制作人名字 != null) {
                                    item.setOwner(制作人名字);
                                }
                                final String name = ii.getName(物品ID);
                                if (物品ID / 10000 == 114 && name != null && name.length() > 0) {
//                                    final String msg = "你已获得称号 <" + name + ">";
                                    mch.getClient().getPlayer().dropMessage(5, "你已获得称号 <" + name + ">");
//                                    mch.getClient().getPlayer().dropMessage(5, msg);
                                }
                                MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                            } else {
                                MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short) 数量, "", null, 给予时间, (byte) 0);
                            }
                        } else {
                            MapleInventoryManipulator.removeById(mch.getClient(), GameConstants.getInventoryType(物品ID), 物品ID, -数量, true, false);
                        }
                        mch.getClient().getSession().write((Object) MaplePacketCreator.getShowItemGain(物品ID, (short) 数量, true));
                        输出A = "[刷物品]:" + msg;
                    }
                }
            }
            this.txtAcc.setText("玩家名字");
            this.txtItemId.setText("物品ID");
            this.txtAmt.setText("数量");
            this.jTextField6.setText("力量");
            this.jTextField7.setText("敏捷");
            this.jTextField8.setText("智力");
            this.jTextField9.setText("运气");
            this.jTextField10.setText("HP设置");
            this.jTextField11.setText("MP设置");
            this.jTextField12.setText("加卷次数");
            this.jTextField13.setText("制作人");
            this.jTextField14.setText("给予物品时间");
            this.jTextField15.setText("可以交易");
            this.jTextField16.setText("攻击力");
            this.jTextField17.setText("魔法力");
            this.jTextField18.setText("物理防御");
            this.jTextField19.setText("魔法防御");
            this.printChatLog(输出A);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + e);
        }
    }

    private void printChatLog(final String str) {
        this.chatLog.setText(this.chatLog.getText() + str + "\r\n");
    }

    private void sendNoticeGG() {
        try {
            final String str = this.txtNotice.getText();
            String msg = "";
            for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    mch.startMapEffect(str, 5121009);
                    msg = "[公告]:" + str;
                }
            }
            this.txtNotice.setText("");
            this.printChatLog(msg);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + e);
        }
    }

    private void FixAcLogged() {
        try {
            final Connection dcon = DatabaseConnection.getConnection();
            try (final PreparedStatement ps = dcon.prepareStatement("UPDATE accounts SET loggedin = 0 WHERE name = " + this.txtAccToDisconnect.getText())) {
                ps.executeUpdate();
            }
            this.printChatLog("解除卡账号" + this.txtAccToDisconnect.getText());
            this.txtAccToDisconnect.setText("");
        } catch (SQLException ex) {
        }
    }

    private void sendNotice(final int type) {
        try {
            final String str = this.txtCharToDisconnect.getText();
            String msg = "";
            if (type == 0) {
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        try {
                            ChannelServer.forceRemovePlayerByCharName(str);
                            if (chr.getName().equals(str) && chr.getMapId() != 0) {
                                chr.getClient().getSession().close(true);
                                chr.getClient().disconnect(true, false);
                                msg = "[解卡系统] 成功断开" + str + "玩家！";
                            } else {
                                msg = "[解卡系统] 玩家名字输入错误或者该玩家没有在线！";
                            }
                        } catch (Exception ex) {
                        }
                    }
                }
            }
            this.txtCharToDisconnect.setText("");
            this.printChatLog(msg);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + e);
        }
    }

    public static void main(final String[] args) {
        try {
            for (final UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
                 InstantiationException ex) {
            logger.error(null, ex);
        }
        EventQueue.invokeLater(() -> new RoyMS().setVisible(true));
    }

    static {
        RoyMS.instance = new RoyMS();
        RoyMS.ts = null;
        RoyMS.t = null;
    }
}
