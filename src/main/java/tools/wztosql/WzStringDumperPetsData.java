package tools.wztosql;

import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;

import java.io.*;

public class WzStringDumperPetsData {
    public static void main(final String[] args) throws FileNotFoundException, IOException {
        final File stringFile = MapleDataProviderFactory.fileInwzPath("String.wz");
        final MapleDataProvider stringProvider = MapleDataProviderFactory.getDataProvider(stringFile);
        final MapleData pet = stringProvider.getData("Pet.img");
        final String output = args[0];
        final File outputDir = new File(output);
        final File petTxt = new File(output + "/Pet.txt");
        outputDir.mkdir();
        petTxt.createNewFile();
        try (final PrintWriter writer = new PrintWriter(new FileOutputStream(petTxt))) {
            for (final MapleData child : pet.getChildren()) {
                writer.println("INSERT INTO `cashshop_modified_items` VALUES ('600500', '8000', '0', '1', '" + child.getName() + "', '0', '0', '0', '2', '1', '0', '0', '0', '0', '0'");
            }
            writer.flush();
        }
    }
}
