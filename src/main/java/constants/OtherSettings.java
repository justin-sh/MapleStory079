package constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class OtherSettings {
    private static OtherSettings instance;
    private static final Logger logger = LoggerFactory.getLogger(OtherSettings.class);

    /**
     * 商城禁止购买的道具List
     */
    private String[] bannedItemIdsForCashShop;
    /**
     * 现金交易禁止的道具List
     */
    private String[] bannedItemIdsForCashTrade;
    /**
     * 雇佣商人禁止上架的道具List
     */
    private String[] bannedItemIdsForTrader;

    public static OtherSettings getInstance() {
        if (OtherSettings.instance == null) {
            OtherSettings.instance = new OtherSettings();
        }
        return OtherSettings.instance;
    }

    public OtherSettings() {
        Properties itemsConf = new Properties();
        try {
            String path = System.getProperty("server_property_file_path");

            try (InputStreamReader is = new FileReader(path)) {
                itemsConf.load(is);
            }

            this.bannedItemIdsForCashShop = itemsConf.getProperty("cashban", "0").split(",");
            this.bannedItemIdsForCashTrade = itemsConf.getProperty("cashjy", "0").split(",");
            this.bannedItemIdsForTrader = itemsConf.getProperty("gysj", "0").split(",");
        } catch (IOException e) {
            logger.error("Could not read configuration.", e);
        }
    }

    public String[] getBannedItemIdsForCashShop() {
        return this.bannedItemIdsForCashShop;
    }

    public String[] getBannedItemIdsForTrader() {
        return this.bannedItemIdsForTrader;
    }

    public String[] getBannedItemIdsForCashTrade() {
        return this.bannedItemIdsForCashTrade;
    }

}
