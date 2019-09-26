package com.chinare.rop.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.nutz.lang.Streams;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
public class ResettableStreamHttpServletRequest extends HttpServletRequestWrapper {

    public class ResettableServletInputStream extends ServletInputStream {

        private ByteArrayInputStream inputStream;

        public ResettableServletInputStream(byte[] buffer) {
            this.inputStream = new ByteArrayInputStream(buffer);
        }

        @Override
        public int available() throws IOException {
            return inputStream.available();
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            return inputStream.read(b, off, len);
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            // Do nothing
        }
    }

    private byte[] buffer;

    public ResettableStreamHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        this.buffer = Streams.readBytes(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ResettableServletInputStream(this.buffer);
    }
}
