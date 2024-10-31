package provider;

import provider.WzXML.MapleDataType;

import java.util.List;


public interface MapleData extends MapleDataEntity, Iterable<MapleData> {
    String getName();

    MapleDataType getType();

    List<MapleData> getChildren();

    MapleData getChildByPath(final String p0);

    Object getData();
}
