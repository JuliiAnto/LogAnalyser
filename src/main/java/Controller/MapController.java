package Controller;

import Config.GroupingConfig;

import java.util.concurrent.ConcurrentHashMap;

public class MapController {

    private static final MapController inst = new MapController();
    private static ConcurrentHashMap<String, Integer> groupMap;

    private MapController() {
        groupMap = new ConcurrentHashMap<String, Integer>();
    }

    public static MapController getInstance() {
        return inst;
    }

    public synchronized void addRecord(String key) {
        if (groupMap.containsKey(key)) {
            int value = groupMap.get(key);
            groupMap.put(key, value + 1);
        } else
            groupMap.put(key, 1);
    }

    public void printMap() {
        String header = ((GroupingConfig.getInstance().hasUserName())? "name \t" : "") +
                ((GroupingConfig.getInstance().getTimeUnit()!=null )? GroupingConfig.getInstance().getTimeUnit().getUnitName()+"\t" : "") +
                "count of records";
        System.out.println(header);
        for (String key : groupMap.keySet()) {
            System.out.println(key + "\t" + groupMap.get(key));
        }
    }
}
