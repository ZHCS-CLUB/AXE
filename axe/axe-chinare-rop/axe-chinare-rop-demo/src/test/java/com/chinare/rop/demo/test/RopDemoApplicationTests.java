package com.chinare.rop.demo.test;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nutz.http.Request.METHOD;
import org.nutz.http.Response;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.random.R;
import org.nutz.lang.util.NutMap;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chinare.rop.client.ROPClient;
import com.chinare.rop.client.ROPRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RopDemoApplicationTests {

    protected ROPClient client;

    @Test
    public void args() {
        int i = R.random(0, 100);
        String s = R.sg(10).next() + "中文";
        Date d = Times.now();
        Response response = client.send(ROPRequest.create("/test",
                                                          METHOD.POST)
                                                  .setData(Json.toJson(NutMap.NEW()
                                                                             .addv("i", i)
                                                                             .addv("s", s)
                                                                             .addv("d", Times.format("yyyy-MM-dd HH:mm:ss", d)))));
        if (response.isOK()) {
            NutMap data = Lang.map(response.getContent());
            System.err.println(data);
        }
    }

    @Before
    public void init() {
        /**
         * 1.调用点本机 <br/>
         * 2.服务器端实现的appSecret仅仅是取appKey的md5,生成环境可能是从数据库获取的 <br/>
         * 3.签名算法使用的是SHA1 <br/>
         */
        client = ROPClient.create("emas", Lang.md5("emas"), "http://localhost:8080/nop.endpoint", "SHA1");
    }

}
