/*
 * Copyright 2017 Bird Guo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gxw.wificonnhelperlib.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.gxw.wificonnhelperlib.utils.bean.WifiBeanConn;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by guoxw on 2017/8/16 0016.
 *
 * @auther guoxw
 * @createTime 2017 /8/16 0016 17:03
 * @packageName com.gxw.wificonnhelperlib.utils
 */
public class WifiConnector {

    private static WifiConnector wifiConnectorNew;
    private Context mContext;

    private WifiManager mWifiManager;
    private WifiAdmin wifiAdmin;

    private Lock mLock;
    private Condition mCondition;
    private WifiBeanConn wifiBeanConn;

    private WifiConfiguration wifiConfiguration;
    private volatile boolean mIsConnnected = false;

    private int reason = -1;
    private int mNetworkID = -1;

    private WiFiConnectReceiver mWifiConnectReceiver;

    private static boolean isConnecting = false;
    private static boolean isRegReceive = false;

    public WifiConnector(Context context) {
        this.mContext = context;
        mLock = new ReentrantLock();
        mCondition = mLock.newCondition();
        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);

        wifiAdmin = new WifiAdmin(context);
        mWifiConnectReceiver = new WiFiConnectReceiver();
        regReceive();
    }

    public boolean CanReConnect() {
        return isConnecting;
    }


    public static WifiConnector build(Context context) {
        wifiConnectorNew = new WifiConnector(context);
        return wifiConnectorNew;
    }

    /**
     * Add wifi bean conn wifi connector new.
     *
     * @param wifiBeanConn the wifi bean conn
     * @return the wifi connector new
     */
    public WifiConnector addWifiBeanConn(WifiBeanConn wifiBeanConn) {
        this.wifiBeanConn = wifiBeanConn;
        return this;
    }

    /**
     * Add wifi config wifi connector new.
     *
     * @param wifiConfiguration the wifi configuration
     * @return the wifi connector new
     */
    public WifiConnector addWifiConfig(WifiConfiguration wifiConfiguration) {
        this.wifiConfiguration = wifiConfiguration;
        return this;
    }


    public void notifyDisConnect() {
        if (isConnecting) {
            mCondition.signalAll();
        }
    }

    /**
     * 链接已连接过的WiFi
     *
     * @param wifiConnListener
     *         the wifi conn listener
     */
    public void connectWithConfig(WifiConnListener wifiConnListener) {
        if (!isConnecting) {
           // wifiAdmin.dissconnectAll();
            wifiConnListener.onWifiConnectStart(wifiConfiguration.SSID, wifiConfiguration.BSSID);
            isConnecting = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    wifiAdmin.openWifi();
//                    //注册连接结果监听对象
//                    mContext.registerReceiver(mWifiConnectReceiver, new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));

                 //   regReceive();

                    if (onConnectWithConfig()) {
                        wifiConnListener.onWifiConnectSuccess(wifiConfiguration.SSID, wifiConfiguration.BSSID);
                    } else {
                        wifiConnListener.onWifiConnnectFail(wifiConfiguration.SSID, wifiConfiguration.BSSID, reason);
                    }

                  //  removeReceive();

                    isConnecting = false;
                }
            }).start();
        } else {
            wifiConnListener.onWifiConnecting(wifiConfiguration.SSID);
        }
    }



    private boolean onConnectWithConfig() {

        //添加网络配置
        mNetworkID = wifiConfiguration.networkId;
       // mNetworkID = mWifiManager.addNetwork(wifiConfiguration);
      //  if (mNetworkID == -1) {
      //      mNetworkID = networkId1;
      //  }
        // Make it the highest priority.
        //int newPri = wifiAdmin.getMaxPriority() + 1;
      //  wifiConfiguration.networkId = mNetworkID;
       // wifiConfiguration.priority = newPri;//提高优先级
     //   mWifiManager.updateNetwork(wifiConfiguration);//更新
        mIsConnnected = false;
      //  mWifiManager.saveConfiguration();//保存
        mLock.lock();

        boolean b = mWifiManager.enableNetwork(mNetworkID, true);
        //连接该网络
        if (!b) {
            mLock.unlock();
            return false;
        }

//        boolean connect = mWifiManager.reassociate();
//        if (!connect) {
//            mLock.unlock();
//            return false;
//        }

        try {
            //等待连接结果
            mCondition.await(Constants.WIFI_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mLock.unlock();


        return mIsConnnected;
    }

    /**
     * 链接没有密码的wifi
     *
     * @param wifiConnListener the wifi conn listener
     */
    public void connectWithSSID(WifiConnListener wifiConnListener) {
       // System.out.println("leo1 isConnecting : " + isConnecting);
        if (!isConnecting) {
          //  wifiAdmin.dissconnectAll();
            wifiConnListener.onWifiConnectStart(wifiBeanConn.getScanResult().SSID, wifiBeanConn.getScanResult().BSSID);
            isConnecting = true;
            new Thread(() -> {

                wifiAdmin.openWifi();

                //                    //注册连接结果监听对象
                //                    mContext.registerReceiver(mWifiConnectReceiver, new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));
              //  System.out.println("leo onConnectWithSSID");
                if (onConnectWithSSID()) {//连接成功
                    wifiConnListener.onWifiConnectSuccess(wifiBeanConn.getScanResult().SSID, wifiBeanConn.getScanResult().BSSID);
                } else {//连接失败
                    wifiConnListener.onWifiConnnectFail(wifiBeanConn.getScanResult().SSID, wifiBeanConn.getScanResult().BSSID, reason);
                }

                isConnecting = false;
            }).start();
        } else {
            wifiConnListener.onWifiConnecting(wifiBeanConn.getScanResult().SSID);
        }

    }



    public void notifyCondition(){
       /* mLock.lock();
        mCondition.signalAll();
        mLock.unlock();*/
    }

    /**
     * 具体连接方法
     *
     * @return
     */
    private boolean onConnectWithSSID() {

        ////移除原有保存
      //  wifiAdmin.removeExitConfig(wifiBeanConn.getScanResult().SSID);

        //添加新的网络配置
        WifiConfiguration cfg = new WifiConfiguration();

       /* Random random = new Random();
        boolean b = random.nextBoolean();
        if (b) {
            cfg.SSID = "\"" + "Qpop_1" + "\"";
        } else {
            cfg.SSID = "\"" + "Qpop_2 "+ "\"";
        }*/

        cfg.SSID = "\"" + wifiBeanConn.getScanResult().SSID + "\"";
        WifiSecurityMode securityMode = wifiAdmin.secretMode(wifiBeanConn.getScanResult());
        if (securityMode == WifiSecurityMode.WEP) {
            cfg.wepKeys[0] = "\"" + wifiBeanConn.getPassword() + "\"";
            cfg.wepTxKeyIndex = 0;
            cfg.status = WifiConfiguration.Status.ENABLED;
        } else if (securityMode == WifiSecurityMode.OPEN) {

 /*           cfg.hiddenSSID = false;

            cfg.status =  WifiConfiguration.Status.ENABLED;

            cfg.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

            cfg.allowedProtocols.set(WifiConfiguration.Protocol.WPA);

            cfg.allowedProtocols.set(WifiConfiguration.Protocol.RSN);

            cfg.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);

            cfg.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);

            cfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);

            cfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);

            cfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);

            cfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);

            cfg.preSharedKey = null;
*/

          /*  cfg.allowedAuthAlgorithms.clear();
            cfg.allowedGroupCiphers.clear();
            cfg.allowedKeyManagement.clear();
            cfg.allowedPairwiseCiphers.clear();
            cfg.allowedProtocols.clear();*/
           // cfg.wepKeys[0] = "";
          //  cfg.hiddenSSID = false;

            cfg.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
           // cfg.wepTxKeyIndex = 0;
        } else {
            cfg.preSharedKey = "\"" + wifiBeanConn.getPassword() + "\"";
            cfg.status = WifiConfiguration.Status.ENABLED;
        }
        //添加网络配置
        mNetworkID = mWifiManager.addNetwork(cfg);
        cfg.networkId = mNetworkID;
        int newPri = wifiAdmin.getMaxPriority() + 1;
        cfg.priority = newPri;//提高优先级

        mWifiManager.updateNetwork(cfg);//更新
        mWifiManager.saveConfiguration();//保存
        //System.out.println("leo lock");
      //  System.out.println("leo syn1");
       // synchronized (this){
        //    System.out.println("leo syn2");
            mLock.lock();    // System.out.println("syn1");
           // System.out.println("leo locked");
            mIsConnnected = false;
            //连接该网络
            if (!mWifiManager.enableNetwork(mNetworkID, true)) {//enableNetwork(id,true)断开其他，false不断开
                //System.out.println("leo  mLock.unlock()");
                mLock.unlock();
               // System.out.println("leo  mLock.unlocked()");
                return false;
            }

            /**
             * 即使已经存在，重新连接到当前活动的接入点连接的。 这可能导致异步传送状态改变事件。
             */
            //        boolean reassociate = mWifiManager.reassociate();
            //
            //        if (!reassociate) {
            //            mLock.unlock();
            //            return false;
            //        }


       /*     try {
                System.out.println("leo wait 1");
                wait(10000);
                System.out.println("leo wait 2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                notifyAll();
            }*/




            try {
                //等待连接结果
                mCondition.await(Constants.WIFI_CONNECT_TIMEOUT, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                //System.out.println("leo  locked()");
                mLock.unlock();
               // System.out.println("leo  unlocked()");
            }

      //  }



        return mIsConnnected;
    }
    /**
     * 监听系统的WIFI连接广播
     */
    private class WiFiConnectReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (!WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                return;
            }
              //  System.out.println("leo  onReceive lock()");
              //  synchronized (WifiConnector.class){

                    mLock.lock();
                   // System.out.println("leo  onReceive locked()");
                    int authState = intent.getIntExtra(WifiManager.EXTRA_SUPPLICANT_ERROR, -1);
                    if (authState == WifiManager.ERROR_AUTHENTICATING) {//身份验证错误到这儿
                        //提示密码错误
                        System.out.println("leo1 WiFiConnectReceiver : 密码错误");
                        reason = 1;
                    }
                    NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                    if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {


                        System.out.println("leo NetworkInfo.State.DISCONNECTED");

                    } else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {
                        //获取当前wifi名称
                        WifiInfo info2 = mWifiManager.getConnectionInfo();

                        if (info2 != null && info2.getNetworkId() == mNetworkID && info2.getSupplicantState() == SupplicantState.COMPLETED) {//这儿可能报空指针
                            mIsConnnected = true;
                            //System.out.println("leo1 WiFiConnectReceiver : 连接成功");
                            mCondition.signalAll();
                           // WifiConnector.this.notifyAll();

                        }
                    }
                    //  System.out.println("leo  onReceive unlock()");
                    mLock.unlock();
              //  }

                //System.out.println("leo  onReceive unlocked()");
            }

    }

    private void regReceive() {

        //注册连接结果监听对象
        mContext.registerReceiver(mWifiConnectReceiver, new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));
        isRegReceive = true;

    }

    /**
     * 移除广播
     */
    public void removeReceive() {
        //删除注册的监听类对象
        //http://stackoverflow.com/questions/6165070/receiver-not-registered-exception-error
        try {
            if (mContext != null && mWifiConnectReceiver != null && isRegReceive) {
                //                mCondition.signalAll();
                //                mLock.unlock();
                mContext.unregisterReceiver(mWifiConnectReceiver);
                isRegReceive = false;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

}
