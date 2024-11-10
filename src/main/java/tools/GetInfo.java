package tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Formatter;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class GetInfo {

    private static final Logger logger = LoggerFactory.getLogger(GetInfo.class);

    public static void main(String[] args) {
        Config();
        getConfig();
        all();
//        System.setProperty("server_property_file_path","E:/game/2020mxd079/079sever/HuaiMS_服务端配置.properties");
//        System.setProperty("server_property_db_path","E:/game/2020mxd079/079sever/HuaiMS_数据库配置.properties");
//        System.setProperty("server_property_shop_path","E:/game/2020mxd079/079sever/HuaiMS_封商城道具.properties");
//        System.setProperty("server_property_fish_path","E:/game/2020mxd079/079sever/HuaiMS_钓鱼设置.properties");


    }

    public static void getIpconfig() {
        Map<String, String> map = System.getenv();
        logger.info(map.toString());
        logger.info(map.get("USERNAME"));
        logger.info(map.get("COMPUTERNAME"));
        logger.info(map.get("USERDOMAIN"));
        logger.info(map.get("USER"));
    }

    public static void all() {
        Properties sysProps = System.getProperties();
        logger.info("Java的運行環境版本：" + sysProps.getProperty("java.version"));
        logger.info("Java的運行環境供應商：" + sysProps.getProperty("java.vendor"));
        logger.info("Java供應商的URL：" + sysProps.getProperty("java.vendor.url"));
        logger.info("Java的安裝路徑：" + sysProps.getProperty("java.home"));
        logger.info("Java的虛擬機規範版本：" + sysProps.getProperty("java.vm.specification.version"));
        logger.info("Java的虛擬機規範供應商：" + sysProps.getProperty("java.vm.specification.vendor"));
        logger.info("Java的虛擬機規範名稱：" + sysProps.getProperty("java.vm.specification.name"));
        logger.info("Java的虛擬機實現版本：" + sysProps.getProperty("java.vm.version"));
        logger.info("Java的虛擬機實現供應商：" + sysProps.getProperty("java.vm.vendor"));
        logger.info("Java的虛擬機實現名稱：" + sysProps.getProperty("java.vm.name"));
        logger.info("Java運行時環境規範版本：" + sysProps.getProperty("java.specification.version"));
        logger.info("Java運行時環境規範名稱：" + sysProps.getProperty("java.specification.name"));
        logger.info("Java的類格式版本號：" + sysProps.getProperty("java.class.version"));
        logger.info("Java的類路徑：" + sysProps.getProperty("java.class.path"));
        logger.info("加載庫時搜索的路徑列表：" + sysProps.getProperty("java.library.path"));
        logger.info("默認的臨時文件路徑：" + sysProps.getProperty("java.io.tmpdir"));
        logger.info("一個或多個擴展目錄的路徑：" + sysProps.getProperty("java.ext.dirs"));
        logger.info("操作系統的構架：" + sysProps.getProperty("os.arch"));
        logger.info("操作系統的版本：" + sysProps.getProperty("os.version"));
        logger.info("文件分隔符：" + sysProps.getProperty("file.separator"));
        logger.info("路徑分隔符：" + sysProps.getProperty("path.separator"));
        logger.info("行分隔符：" + sysProps.getProperty("line.separator"));
        logger.info("用戶的賬戶名稱：" + sysProps.getProperty("user.name"));
        logger.info("用戶的主目錄：" + sysProps.getProperty("user.home"));
        logger.info("用戶的當前工作目錄：" + sysProps.getProperty("user.dir"));
    }

    public static void Config() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress();
            String hostName = addr.getHostName();
            logger.info("本機IP：" + ip + ", 本機名稱:" + hostName);
            Properties sysProps = System.getProperties();
            logger.info("操作系統的名稱：" + sysProps.getProperty("os.name"));
            logger.info("操作系統的版本：" + sysProps.getProperty("os.version"));
        } catch (UnknownHostException e) {
            logger.error("unknown host", e);
        }
    }

    public static void getConfig() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            NetworkInterface ni = NetworkInterface.getByInetAddress(address);
            byte[] mac = ni.getHardwareAddress();
            if (mac == null)
                mac = (ni.getInetAddresses().nextElement()).getAddress();
            String sIP = address.getHostAddress();
            String sMAC = "";
            Formatter formatter = new Formatter();
            for (int i = 0; i < mac.length; i++) {
                sMAC = formatter.format(Locale.getDefault(), "%02X%s", new Object[]{Byte.valueOf(mac[i]), (i < mac.length - 1) ? "-" : ""}).toString();
            }
            logger.info("IP：" + sIP);
            logger.info("MAC：" + sMAC);
        } catch (SocketException | UnknownHostException e) {
            logger.error("socket|host exception", e);
        }
    }
}
