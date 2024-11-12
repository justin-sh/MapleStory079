var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.dispose();
        }
        status--;
    }var MC = cm.getServerName();

    if (status == 0) {
        if (cm.getMap().getAllMonstersThreadsafe().size() <= 0) {
            cm.sendOk("当前地图没有怪物