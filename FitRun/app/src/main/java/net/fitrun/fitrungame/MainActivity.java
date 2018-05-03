package net.fitrun.fitrungame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.inuker.bluetooth.library.BluetoothService;

import net.fitrun.fitrungame.Bluetoothservice.JobHandleService;
import net.fitrun.fitrungame.sportSelect.SportSelectActivity;
import net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo;

import static android.R.attr.id;
import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        initView();


    }

    public void initView() {
        this.startService(new Intent(this,JobHandleService.class));
        this.startService(new Intent(this,BluetoothService.class));
        webView = (WebView)findViewById(R.id.webView);
        //不使用缓存:
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //webView.loadUrl("http://resources.idxwrd.com/download/test.html");
        webView.loadUrl("http://screen.idxwrd.com/page/login.html");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成
                } else {
                    // 加载中
                }

            }
        });
        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.addJavascriptInterface(new InJavaScript(), "fitRun");
        /*startActivity(new Intent(getBaseContext(), SportSelectActivity.class));
        finish();*/
    }

    class InJavaScript {
        @JavascriptInterface
        public void queryUser(final String openid,final String nickname ,final int sex,final String province,final String city,final String country,final String headimgurl,final String unionid,final String systemUserId) {
            Log.e("拦截到js的信息","openid"+openid);
            Log.e("拦截到js的信息","nickname"+nickname);
            Log.e("拦截到js的信息","sex"+sex);
            Log.e("拦截到js的信息","province"+province);
            Log.e("拦截到js的信息","city"+city);
            Log.e("拦截到js的信息","country"+country);
            Log.e("拦截到js的信息","headimgurl"+headimgurl);
            Log.e("拦截到js的信息","unionid"+unionid);
            Log.e("拦截到js的信息",""+systemUserId);
            UserInfo userInfo = new UserInfo(openid,systemUserId,headimgurl,sex,nickname);
            startActivity(new Intent(getBaseContext(), SportSelectActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
