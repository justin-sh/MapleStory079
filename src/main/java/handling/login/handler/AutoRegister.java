package handling.login.handler;

import client.LoginCrypto;
import constants.ServerConstants;
import database.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AutoRegister {

    private static final Logger logger = LoggerFactory.getLogger(AutoRegister.class);

    private static final int ACCOUNTS_PER_MAC = 100;
    public static boolean autoRegister;
    public static boolean success;
    public static boolean mac;

    public static boolean checkAccountExistsByName(final String login) {
        boolean accountExists = false;
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("SELECT name FROM accounts WHERE name = ?");
            ps.setString(1, login);
            final ResultSet rs = ps.executeQuery();
            accountExists = rs.next();
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            logger.warn("check account exist by login failed!", ex);
        }
        return accountExists;
    }

    public static boolean checkAccountExistsById(final int id) {
        boolean accountExists = false;
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("SELECT name FROM accounts WHERE id = ?");
            ps.setInt(1, id);
            final ResultSet rs = ps.executeQuery();
            accountExists = rs.next();

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            logger.warn("check account exist by id failed!", ex);
        }
        return accountExists;
    }

    public static void createAccount(final String login, final String pwd, final String eip, final String macs) {
        final String sockAddr = eip;
        Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ipc = con.prepareStatement("SELECT count(1) as cnt FROM accounts WHERE macs = ?");
            ipc.setString(1, macs);
            final ResultSet rs = ipc.executeQuery();
            int macAccCnt = rs.next() ? rs.getInt(1) : 0;
            if (macAccCnt < ACCOUNTS_PER_MAC) {
                final PreparedStatement ps = con.prepareStatement("INSERT INTO accounts (name, password, email, birthday, macs, SessionIP) VALUES (?, ?, ?, ?, ?, ?)");
                ps.setString(1, login);
                ps.setString(2, LoginCrypto.hexSha1(pwd));
                ps.setString(3, "autoregister@mail.com");
                ps.setString(4, "2008-04-07");
                ps.setString(5, macs);
                ps.setString(6, "/" + sockAddr.substring(1, sockAddr.lastIndexOf(58)));
                ps.executeUpdate();
                AutoRegister.success = true;
            }
            if (macAccCnt >= 100) {
                AutoRegister.mac = false;
            }
        } catch (SQLException ex2) {
            logger.error("auto register failed!", ex2);
        }
    }

    static {
        AutoRegister.autoRegister = ServerConstants.getAutoReg();
        AutoRegister.success = false;
        AutoRegister.mac = true;
    }
}
