package org.apache.thrift.async;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

public class SaveBuffer {
    private volatile static Queue<ByteBuffer> framebufferQueue=new LinkedList<ByteBuffer>();

    public  static void pushBuffer(ByteBuffer buffer){
        framebufferQueue.add(buffer);
    }

    public  static ByteBuffer popBuffer(){
        return framebufferQueue.poll();
    }

}