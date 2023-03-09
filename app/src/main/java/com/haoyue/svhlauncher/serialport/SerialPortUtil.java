package com.haoyue.svhlauncher.serialport;

import android.util.Log;

import com.haoyue.svhlauncher.eventtask.CountDownTask;
import com.haoyue.svhlauncher.eventtask.PressureTask;
import com.haoyue.svhlauncher.eventtask.S1Task;
import com.haoyue.svhlauncher.eventtask.S2Task;
import com.haoyue.svhlauncher.eventtask.StatureTask;
import com.haoyue.svhlauncher.eventtask.TempTask;
import com.haoyue.svhlauncher.eventtask.UpTask;
import com.haoyue.svhlauncher.utils.DataUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android_serialport_api.SerialPort;

/**
 * @author by AllenJ on 2018/4/20.
 * <p>
 * 通过串口用于接收或发送数据
 */

public class SerialPortUtil {

    private SerialPort serialPort = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private ReceiveThread mReceiveThread = null;
    private boolean isStart = false;

    /**
     * 打开串口，接收数据
     * 通过串口，接收单片机发送来的数据
     */
    public void openSerialPort() {
        // /dev/ttyS2 57600
        try {
            SerialPort serialPort = new SerialPort();
            while (true) {
                boolean b = false;
                String h = "";
                b = serialPort.SerialPort(new File("/dev/ttyS3"), 4800, 0);
                h = "/dev/ttyS3";
                if (!b){
                    b = serialPort.SerialPort(new File("/dev/ttyS1"), 4800, 0);
                    h = "/dev/ttyS1";
                }
                if (!b){
                    b = serialPort.SerialPort(new File("/dev/ttyS2"), 4800, 0);
                    h = "/dev/ttyS2";
                }
                if (!b){
                    b = serialPort.SerialPort(new File("/dev/ttyS4"), 4800, 0);
                    h = "/dev/ttyS4";
                }
                if (!b){
                    b = serialPort.SerialPort(new File("/dev/ttyS5"), 4800, 0);
                    h = "/dev/ttyS5";
                }
                if (b) {
                    this.serialPort = serialPort;
                    //调用对象SerialPort方法，获取串口中"读和写"的数据流
                    inputStream = serialPort.getInputStream();
                    outputStream = serialPort.getOutputStream();
                    isStart = true;
                    Log.e("XSerialPortService", ">>>> open: " + h);
                    break;
                }else {
                    Log.e("XSerialPortService", "XSerialPortService open error!");
                    throw new IOException();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        getSerialPort();
    }

    /**
     * 关闭串口
     * 关闭串口中的输入输出流
     */
    public void closeSerialPort() {
        Log.i("test", "关闭串口");
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            isStart = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送数据
     * 通过串口，发送数据到单片机
     *
     * @param data 要发送的数据
     */
    public void sendSerialPort(String data) {
        try {
            byte[] sendData = DataUtils.HexToByteArr(data);
            outputStream.write(sendData);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getSerialPort() {
        if (mReceiveThread == null) {
            mReceiveThread = new ReceiveThread();
        }
        mReceiveThread.start();
    }

    /**
     * 接收串口数据的线程
     */

    private class ReceiveThread extends Thread {
        @Override
        public void run() {
            super.run();
            //条件判断，只要条件为true，则一直执行这个线程
            while (isStart) {
                if (inputStream == null) {
                    return;
                }
                byte[] readData = new byte[512];
                try {
                    int size = inputStream.read(readData);
                    if (size > 0) {
                        String readString = DataUtils.ByteArrToHex(readData, 0, size);
                        String data = DataUtils.convertHexToString(readString).replaceAll("\r|\n", "");
                        Log.e("数据-", data);
                        //接收体温 T:0037.0C$
                        if (data.startsWith("T:") && data.endsWith("$")) {
                            String temp = data.substring(4, data.indexOf("C$"));
                            EventBus.getDefault().postSticky(new TempTask(Double.parseDouble(temp)));
                        }
                        //体重秤上秤
                        if ("P:1$".equals(data)) {
                            EventBus.getDefault().postSticky(new UpTask());
                        }
                        //开始测量体重-提示
                        if ("S1$".equals(data)) {
                            EventBus.getDefault().postSticky(new S1Task());
                        }
                        //体重数值-W:062.4 H:166.5V1$
                        //体重最终值-W:062.4 H:167.5
                        if (data.startsWith("W:")) {
                            String weight = data.substring(2, 7);
                            String height = data.substring(10, 15);
                            if (data.endsWith("V1$")) {
                                //发送动态数据
                                EventBus.getDefault().postSticky(new StatureTask(weight, height, false));
                            } else {
                                //发送最终数据
                                EventBus.getDefault().postSticky(new StatureTask(weight, height, true));
                            }
                        }
                        //开始测量血压-提示
                        if ("S2$".equals(data)) {
                            EventBus.getDefault().postSticky(new S2Task());
                        }
                        //血压数值-B102072076$
                        if (data.startsWith("B") && data.endsWith("$")) {
                            int high_pressure = Integer.parseInt(data.substring(1, 4));
                            int low_pressure = Integer.parseInt(data.substring(4, 7));
                            int heart_rate = Integer.parseInt(data.substring(7, 10));
                            if (high_pressure == 0 && low_pressure == 0 && heart_rate == 0) {
                                //测量失败
                                EventBus.getDefault().postSticky(new PressureTask(high_pressure, low_pressure, heart_rate, false));
                            } else {
                                //测量成功
                                EventBus.getDefault().postSticky(new PressureTask(high_pressure, low_pressure, heart_rate, true));
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}