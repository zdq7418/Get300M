package com.zdq.get300m;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.adapter.Call;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText shouji,yzm;
    private ImageView yzjtp;
    private TextView rizhi;
    private Button linqu,zuozhe,gengduo;
    private String sj_text,yzm_text;
    private RadioGroup yys;
    private String url="https://appapns.www.gov.cn/govdata/2018lhs/lhsll2018.shtml?submitCallback=jQuery110206767296394020996_1523770833906&operator=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StyledDialog.buildIosAlert( "系统提示", "加入【凉皮】福利线报群获取更多资源",  new MyDialogListener() {
            @Override
            public void onFirst() {
                jump_qqGruop("ZDguJJGxPJaQ8pSU-a64QcLK_fN4w5Jb");
            }

            @Override
            public void onSecond() {

            }
        }).setBtnText("确认").setCancelable(false,false).show();
        shouji=this.findViewById(R.id.shoujihaoma);
        yzm=this.findViewById(R.id.yzm);
        yzjtp=this.findViewById(R.id.yzmtp);
        linqu=this.findViewById(R.id.linqu);
        rizhi=this.findViewById(R.id.rizhi);
        zuozhe=this.findViewById(R.id.zuozhe);
        gengduo=this.findViewById(R.id.gengduo);
        yys=this.findViewById(R.id.yys);
        yys.setOnClickListener(this);
        linqu.setOnClickListener(this);
        zuozhe.setOnClickListener(this);
        gengduo.setOnClickListener(this);
        yzjtp.setOnClickListener(this);
        getBitmap("https://appapns.www.gov.cn/govdata/2018lhs/cap2018.shtml?t=1523774376000");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.linqu:
                sj_text=shouji.getText().toString();
                yzm_text=yzm.getText().toString();
                int yyss = yys.getCheckedRadioButtonId();
                if (sj_text.equals("")){
                    Toast.makeText(this,"请输入手机号码",Toast.LENGTH_LONG).show();
                    return;
                }
                if (yzm_text.equals("")){
                    Toast.makeText(this,"请输入验证码",Toast.LENGTH_LONG).show();
                    return;
                }
                if (yyss==R.id.dx){
                    try {
                        url= url+"10000&phone="+sj_text+"&jsoncallback=submitCallback&authnum="+ URLEncoder.encode(yzm_text,"UTF-8")+"&_=1523770833908";
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }else if (yyss==R.id.yd){
                    try {
                        url= url+"10086&phone="+sj_text+"&jsoncallback=submitCallback&authnum="+ URLEncoder.encode(yzm_text,"UTF-8")+"&_=1523770833908";
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }else if (yyss==R.id.lt){
                    try {
                        url= url+"10010&phone="+sj_text+"&jsoncallback=submitCallback&authnum="+ URLEncoder.encode(yzm_text,"UTF-8")+"&_=1523770833908";
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                OkGo.<String>get(url)//
                        .tag(this)//
                        .cacheKey("300m")
                        .headers("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0")//
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                url="https://appapns.www.gov.cn/govdata/2018lhs/lhsll2018.shtml?submitCallback=jQuery110206767296394020996_1523770833906&operator=";
                                //注意这里已经是在主线程了
                                String data = response.body();//这个就是返回来的结果
                                String[] error=data.split("error");
                                if (error.length==2){
                                    String a=error[1];
                                    a=a.replace("\"","");
                                    a=a.replace(":","");
                                    a=a.replace(")","");
                                    a=a.replace("}","");
                                    if ("OK".equals(a)){
                                        rizhi.setText(sj_text+":流量领取成功！流量将在下个月到账,请注意查收！");
                                    }else if ("phone has already existed".equals(a)){
                                        rizhi.setText(sj_text+":你已经领取过了！流量已经在路上,将在下个月到账,请注意查收！");
                                    }else if ("Verification code error ".equals(a)){
                                        rizhi.setText(sj_text+":验证码错误,请重试填写验证码！");
                                    }else if ("phone number is incompatible ".equals(a)){
                                        rizhi.setText(sj_text+":领取失败,运营商不符,请重新领取选择正确的运营商！");
                                    }

                                }

                            }
                            @Override
                            public void onError(Response<String> response) {
                                url="https://appapns.www.gov.cn/govdata/2018lhs/lhsll2018.shtml?submitCallback=jQuery110206767296394020996_1523770833906&operator=";
                                rizhi.setText(response.body());
                                super.onError(response);
                            }
                        });
                rizhi.setText("若该处无反应，请检查验证码");
                break;
            case R.id.zuozhe:
                jump_qq("1119055131");
                break;
            case R.id.gengduo:
                jump_qqGruop("ZDguJJGxPJaQ8pSU-a64QcLK_fN4w5Jb");
                break;
            case R.id.yzmtp:
                getBitmap("https://appapns.www.gov.cn/govdata/2018lhs/cap2018.shtml?t=1523774376000");
                break;
            case R.id.yys:


        }

    }

    private boolean jump_qq(String str){
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+str));
        try {
            startActivity(intent);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private boolean jump_qqGruop(String str){
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D"+str));
        try {
            startActivity(intent);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private void getBitmap(String url) {
        OkGo.<Bitmap>get(url)//
                .tag(this)//
                .cacheKey("300m")
                .headers("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0")//
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Response<Bitmap> response) {
                        yzjtp.setImageBitmap(response.body());
                    }
                });
    }

    @Override
    public void onBackPressed() {
        StyledDialog.buildIosAlert( "提示", "确定退出吗？",  new MyDialogListener() {
            @Override
            public void onFirst() {
                finish();
            }

            @Override
            public void onSecond() {

            }
        }).setBtnText("确认","取消").show();
    }
}
