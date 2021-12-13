package com.gupaoedu.nio.channel;

import java.io.*;  
import java.nio.*;  
import java.nio.channels.*;  
  
public class FileOutputProgram {  
    static private final byte message[] = { 83, 111, 109, 101, 32,  
        98, 121, 116, 101, 115, 46 };  
  
    static public void main( String args[] ) throws Exception {  
        FileOutputStream fout = new FileOutputStream( "e:\\test.txt" );  
          
        FileChannel fc = fout.getChannel();  
          
        ByteBuffer buffer = ByteBuffer.allocate( 1024 );  
          
        for (int i=0; i<message.length; ++i) {  
            buffer.put( message[i] );  
        }  
          
        buffer.flip();   
          
        fc.write( buffer );  
          
        fout.close();  
    }  
}
