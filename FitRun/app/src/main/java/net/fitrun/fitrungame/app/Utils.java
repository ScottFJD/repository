package net.fitrun.fitrungame.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 晁东洋 on 2016/10/14.
 */

public class Utils {
    private static final String TAG = "日志";
    /**

     * @param str

     * @return

     * 检验是否为空

     */

    public static boolean isEmpty(CharSequence str) {

        if (str == null || str.length() == 0)

            return true;

        else

            return false;

    }

    /**
     * 判断邮箱是否合法
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isMobileNO(String mobiles){

        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,3-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }

    /**
     * 根据日期计算人的年龄
     */
    public static int getPersonAge(Date date ){
        if (date == null){
            return 0;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        String strBirthDate  = dateFormat.format(date);
        //读取当前日期
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DATE);
        //计算年龄
        int age = year - Integer.parseInt(strBirthDate.substring(0,4)) -1;
        if (Integer.parseInt(strBirthDate.substring(5,7)) <month){
            age++;
        }else if (Integer.parseInt(strBirthDate.substring(5,7)) ==month && Integer.parseInt(strBirthDate.substring(8,10))<=day){
            age++;
        }
        return age;
    }
    /**
     * 日期格式转换为年月日
     */
    public static String getPersonDat(Date date ){
        if (date == null){
            return "1990-00-0";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        String strBirthDate  = dateFormat.format(date);

        return strBirthDate;
    }
       /* 
        * 毫秒转化时分秒毫秒 
        */
    public static String formatTime(Long ms) {
         Integer ss = 1000;
         Integer mi = ss * 60;
         Integer hh = mi * 60;
         Long hour =(ms)/hh;
         Long minute =(ms-hour*hh)/mi;
         StringBuffer sb = new StringBuffer();
         if(hour > 0) {
            sb.append(hour+"小时");
          }else {
             sb.append("");
         }
         if(minute >0){
            sb.append(minute+"分钟");
         }else {
             sb.append(0+"分钟");
         }
        return sb.toString();
    }


    // 获取当前目录下所有的mp4文件  
    public static Vector<String> GetVideoFileName(String fileAbsolutePath){
        Vector<String> vecFile = new Vector<String>();
        File file = new File(fileAbsolutePath);
        File[] subFile= file.listFiles();
        for(int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是否为文件夹  
            if (!subFile[iFileLength].isDirectory()) {
                String filename = subFile[iFileLength].getName();
                // 判断是否为MP4结尾  
                if (filename.trim().toLowerCase().endsWith(".mp4")) {
                    vecFile.add(filename);
                    }
                }
            }
        return vecFile;
       }

    //设置视频第一帧画面为背景
    public static Bitmap createVideoThumbnail(String filePath) {
        // MediaMetadataRetriever is available on API Level 8
        // but is hidden until API Level 10
        Class<?> clazz = null;
        Object instance = null;
        try {
            clazz = Class.forName("android.media.MediaMetadataRetriever");
            instance = clazz.newInstance();

            Method method = clazz.getMethod("setDataSource", String.class);
            method.invoke(instance, filePath);

            // The method name changes between API Level 9 and 10.
            if (Build.VERSION.SDK_INT <= 9) {
                return (Bitmap) clazz.getMethod("captureFrame").invoke(instance);
            } else {
                byte[] data = (byte[]) clazz.getMethod("getEmbeddedPicture").invoke(instance);
                if (data != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    if (bitmap != null) return bitmap;
                }
                return (Bitmap) clazz.getMethod("getFrameAtTime").invoke(instance);
            }
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } catch (InstantiationException e) {
            Log.e(TAG, "createVideoThumbnail", e);
        } catch (InvocationTargetException e) {
            Log.e(TAG, "createVideoThumbnail", e);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "createVideoThumbnail", e);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "createVideoThumbnail", e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "createVideoThumbnail", e);
        } finally {
            try {
                if (instance != null) {
                    clazz.getMethod("release").invoke(instance);
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    /** 
          * 字符串转换为16进制字符串 
          *  
          * @param s 
          * @return 
          */
         public static String stringToHexString(String s) {
            String str = "";
             for (int i = 0; i < s.length(); i++) {
               int ch = (int) s.charAt(i);
               String s4 = Integer.toHexString(ch);
               str = str + s4;
             }
         return str;
        }


    /**
     * Detects and toggles immersive mode (also known as "hidey bar" mode).
     */
    public static void toggleHideyBar(Activity mActivity) {

        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = mActivity.getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i(TAG, "Turning immersive mode mode off. ");
        } else {
            Log.i(TAG, "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        mActivity.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }


    /**
     * 秒转换为时分秒的单位
     **/
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }
    //时间转换为字符串
    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

/** 
      * @param path 
      * @return 
      * @throws IOException 
      * 压缩图片 
      */
    public static Bitmap revitionImageSize(String path) throws IOException {
        //根据文件路径,创建一个字节缓冲输入流  
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //根据流返回一个位图也就是bitmap，当options.inJustDecodeBounds = true的时候不需要完全解码，  
        // 它仅仅会把它的宽，高取回来给你，这样就不会占用太多的内存，也就不会那么频繁的发生OOM了  
        BitmapFactory.decodeStream(in, null, options);
        //关闭流  
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true){
            // options.outWidth >> i 。右移运算符，num >> 1,相当于num除以2  
            if ((options.outWidth >> i <= 1000) && (options.outHeight >> i <= 1000)) {
                //得到一个输入流  
                in = new BufferedInputStream(new FileInputStream(new File(path)));
                //为了解决图片解码时候出现SanpleSize错误，设置恰当的inSampleSize可以使BitmapFactory分配更少的空间以消除该错误  
                //你将 inSampleSize 赋值为2,那就是每隔2行采1行,每隔2列采一列,那你解析出的图片就是原图大小的1/4.  
                // Math.pow(2.0D, i)次方运算，2的i次方是多少  
                //options.inSampleSize = (int) Math.pow(2.0D,i);
                options.inSampleSize = 4;
                // 这里之前设置为了true，所以要改为false，否则就创建不出图片  
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
                }
            i += 1;
            }
        return bitmap;

        }


       /** 
         * @param bitmap 
         * 保存图片到SD卡的方法 
         */
    public static void saveBitmapFile(Bitmap bitmap){
        //Environment.getExternalStorageDirectory() 获取Android外部存储的空间，当有外部SD卡就在外部SD卡上建立。  
        //没有外部SD卡就在内部SD卡的非data/data/目录建立目录。（data/data/目录才是真正的内存目录。）  
        //IMAGE_NAME文件的名字，随便起。比如（xxx.jpg）  
        String IMAGE_NAME = "fetpao_head.jpg";
        File tempFile = new File(Environment.getExternalStorageDirectory(),IMAGE_NAME);

        try {
            //创建一个输出流，将数据写入到创建的文件对象中。  
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile));
            ////30 是压缩率，表示压缩70%; 如果不压缩是100，  
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            /* 为什么要调用flush()方法？当FileOutputStream作为BufferedOutputStream构造函数的参数传入，然后对BufferedOutputStream进行写入操作，才能利用缓冲及flush()。 
           查看BufferedOutputStream的源代码，发现所谓的buffer其实就是一个byte[]。 
           BufferedOutputStream的每一次write其实是将内容写入byte[]，当buffer容量到达上限时，会触发真正的磁盘写入。 
           而另一种触发磁盘写入的办法就是调用flush()了。*/
            bos.flush();
            //关闭流对象  
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
