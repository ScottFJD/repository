package net.fitrun.fitrungame.Bluetoothservice;

import java.util.HashMap;

/**
 * Created by 晁东洋 on 2016/12/18.
 */

public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();
    public static String HEART_NOTIF_MEASUREMENT = "0000fff1-0000-1000-8000-00805f9b34fb";
    public static String HEART_RATE_MEASUREMENT = "0000fff2-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_MY_CONFIG = "0000fff0-0000-1000-8000-00805f9b34fb";

    public static String NOCONTROL_CONFIG = "00001816-0000-1000-8000-00805f9b34fb";
    public static String NOCONTROL_HEART = "00002A5B-0000-1000-8000-00805f9b34fb";
    //手环的UUID
    public static String HEART_CLIENT_MY_CONFIG = "0000180D-0000-1000-8000-00805f9b34fb";
    public static String HEART_MY_NOTIF = "00002A37-0000-1000-8000-00805f9b34fb";

     static {
         attributes.put("00002001-0000-1000-8000-00805f9b34fb", "人体参数");
         attributes.put("00002002-0000-1000-8000-00805f9b34fb", "时间");
         // Sample Characteristics.
        // attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
         attributes.put("00002003-0000-1000-8000-00805f9b34fb", "闹钟");
         attributes.put("00002004-0000-1000-8000-00805f9b34fb", "睡眠设置");
         attributes.put("00002005-0000-1000-8000-00805f9b34fb", "震动");
         attributes.put("00002006-0000-1000-8000-00805f9b34fb", "来电");
         attributes.put("00002007-0000-1000-8000-00805f9b34fb", "短信");
         attributes.put("00002008-0000-1000-8000-00805f9b34fb", "配置");
         attributes.put("00002009-0000-1000-8000-00805f9b34fb", "绑定");
         attributes.put("0000200A-0000-1000-8000-00805f9b34fb", "登录");
         attributes.put("0000200B-0000-1000-8000-00805f9b34fb", "状态");
         attributes.put("0000200C-0000-1000-8000-00805f9b34fb", "运动30天数据");
         attributes.put("0000200D-0000-1000-8000-00805f9b34fb", "睡眠数据");
         attributes.put("0000200E-0000-1000-8000-00805f9b34fb", "运动24小时数据");
        }

     public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
        }
}
