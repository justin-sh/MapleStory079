package server;

import database.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ServerProperties {

    private static final Logger logger = LoggerFactory.getLogger(ServerProperties.class);

    public static boolean showPacket;
    private static Properties props;
    private static String[] toLoad;

    public static boolean ShowPacket() {
        return ServerProperties.showPacket;
    }

    public static String getProperty(final String s) {
        return ServerProperties.props.getProperty(s);
    }

    public static void setProperty(final String prop, final String newInf) {
        ServerProperties.props.setProperty(prop, newInf);
    }

    public static String getProperty(final String s, final String def) {
        return ServerProperties.props.getProperty(s, def);
    }

    private ServerProperties() {
    }

    static {
        ServerProperties.showPacket = true;
        ServerProperties.props = new Properties();
        try {
            String path = System.getProperty("server_property_file_path");
            final InputStreamReader fr = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8);
            ServerProperties.props.load(fr);
            fr.close();
        } catch (IOException ex) {
            logger.error("load server Settings failed.", ex);
        }
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM auth_server_channel_ip");
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                props.put(rs.getString("name") + rs.getInt("channelid"), rs.getString("value"));
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            logger.error("load auth server info from db failed!", ex);
            System.exit(0);
        }
    }
}
