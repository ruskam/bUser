package com.example.esr14.signupplay;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MessageThread extends Thread {
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    Handler bluetoothIn;
    int handlerState;
    //creation of the connect thread
    public MessageThread(BluetoothSocket socket, Handler btIn, int state) {
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        bluetoothIn = btIn;
        handlerState = state;
        try {
            //Create I/O streams for connection
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[256];
        int bytes = 0;

        // Keep looping to listen for received messages
        while (true) {
            try {
                bytes = mmInStream.read(buffer);            //read bytes from input buffer
                String readMessage = new String(buffer, 0, bytes);
                // Send the obtained bytes to the UI Activity via handler
                bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
            } catch (IOException e) {
                break;
            }
        }
    }
    //write method
    public void write(String input) {
        byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
        try {
            mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
        } catch (IOException e) {

        }
    }
}
