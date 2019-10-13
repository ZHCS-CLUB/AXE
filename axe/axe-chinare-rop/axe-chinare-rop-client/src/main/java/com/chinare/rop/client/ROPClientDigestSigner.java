package com.chinare.rop.client;

import java.io.IOException;
import java.io.InputStreamReader;

import org.nutz.http.Http;
import org.nutz.lang.Lang;
import org.nutz.lang.Streams;

import com.chinare.rop.core.signer.DigestSigner;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
public class ROPClientDigestSigner extends DigestSigner implements ClientSigner {

    public ROPClientDigestSigner(String name) {
        super(name);
    }

    protected String getDataMate(ROPRequest request) {
        if (request.isGet()) {
            String query = request.getURLEncodedParams();
            return Lang.md5(Http.encode(query, request.getEnc()));
        }
        StringBuilder info;
        try {
            info = Streams.read(new InputStreamReader(request.getInputStream()));
        }
        catch (IOException e) {
            throw Lang.wrapThrow(e);
        }
        return Lang.md5(info);
    }

    @Override
    public String sign(String appSecret, String timestamp, String gateway, String nonce, ROPRequest request) {
        return sign(appSecret, timestamp, gateway, nonce, getDataMate(request));
    }

}
