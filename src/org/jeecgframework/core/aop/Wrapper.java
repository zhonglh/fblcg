//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.jeecgframework.core.aop;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class Wrapper extends HttpServletResponseWrapper {
    public static final int OT_NONE = 0;
    public static final int OT_WRITER = 1;
    public static final int OT_STREAM = 2;
    private int outputType = 0;
    private ServletOutputStream output = null;
    private PrintWriter writer = null;
    private ByteArrayOutputStream buffer = null;

    public Wrapper(HttpServletResponse resp) throws IOException {
        super(resp);
        this.buffer = new ByteArrayOutputStream();
    }

    public PrintWriter getWriter() throws IOException {
        if(this.outputType == 2) {
            throw new IllegalStateException();
        } else if(this.outputType == 1) {
            return this.writer;
        } else {
            this.outputType = 1;
            this.writer = new PrintWriter(new OutputStreamWriter(this.buffer, this.getCharacterEncoding()));
            return this.writer;
        }
    }

    public ServletOutputStream getOutputStream() throws IOException {
        if(this.outputType == 1) {
            throw new IllegalStateException();
        } else if(this.outputType == 2) {
            return this.output;
        } else {
            this.outputType = 2;
            this.output = new Wrapper.WrappedOutputStream(this.buffer);
            return this.output;
        }
    }

    public void flushBuffer() throws IOException {
        if(this.outputType == 1) {
            this.writer.flush();
        }

        if(this.outputType == 2) {
            this.output.flush();
        }

    }

    public void reset() {
        this.outputType = 0;
        this.buffer.reset();
    }

    public byte[] getResponseData() throws IOException {
        this.flushBuffer();
        return this.buffer.toByteArray();
    }

    class WrappedOutputStream extends ServletOutputStream {
        private ByteArrayOutputStream buffer;

        public WrappedOutputStream(ByteArrayOutputStream buffer) {
            this.buffer = buffer;
        }

        public void write(int b) throws IOException {
            this.buffer.write(b);
        }

        public byte[] toByteArray() {
            return this.buffer.toByteArray();
        }
    }
}
