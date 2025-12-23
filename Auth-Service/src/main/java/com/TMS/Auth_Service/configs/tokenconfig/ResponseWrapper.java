package com.TMS.Auth_Service.configs.tokenconfig;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;

class ResponseWrapper extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream capture = new ByteArrayOutputStream();
    private ServletOutputStream output;
    public ResponseWrapper(HttpServletResponse response) {
        super(response);
    }
    @Override
    public ServletOutputStream getOutputStream() {
        if (output == null) {
            output = new ServletOutputStream() {
                @Override
                public void write(int b) { capture.write(b); }
                @Override
                public boolean isReady() { return true; }
                @Override
                public void setWriteListener(WriteListener listener) {}
            };
        }
        return output;
    }

    public String getCaptureAsString() {
        return capture.toString(java.nio.charset.StandardCharsets.UTF_8);
    }
}