package com.chinare.rop.demo.service;

import java.util.Date;

import org.nutz.http.Header;
import org.nutz.http.Request.METHOD;
import org.nutz.http.Response;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.random.R;
import org.nutz.lang.util.NutMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinare.rop.client.ROPClient;
import com.chinare.rop.client.ROPRequest;
import com.chinare.rop.server.ROPException;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@Component
public class RopService {
	@Autowired
	ROPClient client;

	/**
	 * @return
	 */
	public NutMap file() {
		Response response = client
				.send(ROPRequest
						.create("/file", METHOD.POST,
								NutMap.NEW().addv("id", 10).addv("file",
										Lang.array(Files.checkFile("application.yml"),
												Files.checkFile("application.yml"))))
						.setHeader(Header.create().asFormContentType()));
		if (response.isOK()) {
			return Lang.map(response.getContent());
		}
		throw new ROPException("接口调用失败");
	}

	/**
	 * @return
	 */
	public NutMap get() {
		Response response = client.send(ROPRequest.create("/get", METHOD.GET,
				NutMap.NEW().addv("ids", Lang.array(10, 11)).addv("name", R.UU64())));
		if (response.isOK()) {
			return Lang.map(response.getContent());
		}
		throw new ROPException("接口调用失败");
	}

	/**
	 * @return
	 */
	public NutMap post() {
		Response response = client.send(ROPRequest.create("/post", METHOD.POST,
				NutMap.NEW().addv("ids", Lang.array(10, 11)).addv("name", R.UU64())));
		if (response.isOK()) {
			return Lang.map(response.getContent());
		}
		throw new ROPException("接口调用失败");
	}

	public NutMap test() {
		int i = R.random(0, 100);
		String s = R.sg(10).next() + "中文";
		Date d = Times.now();
		Response response = client.send(ROPRequest.create("/test", METHOD.POST).setData(
				Json.toJson(NutMap.NEW().addv("i", i).addv("s", s).addv("d", Times.format("yyyy-MM-dd HH:mm:ss", d)))));
		if (response.isOK()) {
			return Lang.map(response.getContent());
		}

		throw new ROPException("接口调用失败");
	}
}
