package com.nfc.electronicseal.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.nfc.electronicseal.MainActivity;
import com.nfc.electronicseal.base.BaseInfoUpdate;

public class BDLocationUtil {
    private Activity context;
    private LocationClient mLocationClient;
    private BaseInfoUpdate infoUpdate;

    public BDLocationUtil(Activity context, BaseInfoUpdate infoUpdate){
        this.context = context;
        this.infoUpdate = infoUpdate;
        onCreate();
    }

    public void startLocation(){
        mLocationClient.registerLocationListener(myListener);//注册监听函数
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            //未开启定位权限            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(context,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},200);
        }else{
//            startLocaion();//开始定位            Toast.makeText(MainActivity.this,"已开启定位权限",Toast.LENGTH_LONG).show();        }
            mLocationClient.start();//开始定位
        }
//        mLocationClient.start();//开始定位
    }

    public void stopLocation() {
        mLocationClient.unRegisterLocationListener(myListener);  //注销掉监听
        mLocationClient.stop(); //停止定位
    }

    private void onCreate(){
        mLocationClient = new LocationClient(context);//声明LocationClient类
        LocationClientOption mOption = new LocationClientOption();

        /**
         * 默认高精度，设置定位模式
         * LocationMode.Hight_Accuracy 高精度定位模式：这种定位模式下，会同时使用
         网络定位（Wi-Fi和基站定位）和GPS定位，优先返回最高精度的定位结果；
         但是在室内gps无信号，只会返回网络定位结果；
         室外如果gps收不到信号，也只会返回网络定位结果。
         * LocationMode.Battery_Saving 低功耗定位模式：这种定位模式下，不会使用GPS，只会使用网络定位。
         * LocationMode.Device_Sensors 仅用设备定位模式：这种定位模式下，
         不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位
         */
        mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        /**
         * 默认是true，设置是否使用gps定位
         * 如果设置为false，即使mOption.setLocationMode(LocationMode.Hight_Accuracy)也不会gps定位
         */
        mOption.setOpenGps(true);

        /**
         * 默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
         * 目前国内主要有以下三种坐标系：
         1. wgs84：目前广泛使用的GPS全球卫星定位系统使用的标准坐标系；
         2. gcj02：经过国测局加密的坐标；
         3. bd09：为百度坐标系，其中bd09ll表示百度经纬度坐标，bd09mc表示百度墨卡托米制坐标；
         * 在国内获得的坐标系类型可以是：国测局坐标、百度墨卡托坐标 和 百度经纬度坐标。
         在海外地区，只能获得WGS84坐标。请在使用过程中注意选择坐标。
         */
        mOption.setCoorType("bd09ll");

        /**
         * 默认0，即仅定位一次；设置间隔需大于等于1000ms，表示周期性定位
         * 如果不在AndroidManifest.xml声明百度指定的Service，周期性请求无法正常工作
         * 这里需要注意的是：如果是室外gps定位，不用访问服务器，设置的间隔是3秒，那么就是3秒返回一次位置
         如果是WiFi基站定位，需要访问服务器，这个时候每次网络请求时间差异很大，设置的间隔是3秒，
         只能大概保证3秒左右会返回就一次位置，有时某次定位可能会5秒才返回
         */
        mOption.setScanSpan(3000);

        /**
         * 默认false，设置是否需要地址信息
         * 返回省、市、区、街道等地址信息，这个api用处很大，
         很多新闻类app会根据定位返回的市区信息推送用户所在市的新闻
         */
        mOption.setIsNeedAddress(true);

        /**
         * 默认false，设置是否需要位置语义化结果
         * 可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
         */
        mOption.setIsNeedLocationDescribe(true);

        /**
         * 默认false,设置是否需要设备方向传感器的方向结果
         * 一般在室外gps定位时，返回的位置信息是带有方向的，但是有时候gps返回的位置也不带方向，
         这个时候可以获取设备方向传感器的方向
         * wifi基站定位的位置信息是不带方向的，如果需要可以获取设备方向传感器的方向
         */
        mOption.setNeedDeviceDirect(false);

        /**
         * 默认false，设置是否当gps有效时按照设定的周期频率输出GPS结果
         * 室外gps有效时，周期性1秒返回一次位置信息，其实就是设置了
         locationManager.requestLocationUpdates中的minTime参数为1000ms，1秒回调一个gps位置
         * 如果设置了mOption.setScanSpan(3000)，那minTime就是3000ms了，3秒回调一个gps位置
         */
        mOption.setLocationNotify(false);

        /**
         * 默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
         * 如果你已经拿到了你要的位置信息，不需要再定位了，不杀死留着干嘛
         */
        mOption.setIgnoreKillProcess(true);

        /**
         * 默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
         * POI就是获取到的位置附近的一些商场、饭店、银行等信息
         */
        mOption.setIsNeedLocationPoiList(true);

        /**
         * 默认false，设置是否收集CRASH信息，默认收集
         */
        mOption.SetIgnoreCacheException(false);

        /**
         * 默认false，设置定位时是否需要海拔高度信息，默认不需要，除基础定位版本都可用
         */
        mOption.setIsNeedAltitude(false);
        mLocationClient.setLocOption(mOption);//设置定位参数


    }

    private BDLocationListener myListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //定位sdk获取位置后回调
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {

                /**
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                location.getTime();

                /**
                 * 定位类型
                 BDLocation.TypeGpsLocation----gps定位
                 BDLocation.TypeNetWorkLocation----网络定位(wifi基站定位)
                 以及其他定位失败信息
                 */
                location.getLocType();

                /**
                 * 对应的定位类型说明
                 * 比如"NetWork location successful"之类的信息
                 */
                location.getLocTypeDescription();

                /**
                 * 纬度
                 */
                double latitude = location.getLatitude();

                /**
                 * 经度
                 */
                double longitude = location.getLongitude();

                TLog.log("The latitude:" + latitude + "  longitude:" + longitude);

                /**
                 * 误差半径，代表你的真实位置在这个圆的覆盖范围内，
                 * 半径越小代表定位精度越高，位置越真实
                 * 在同一个地点，可能每次返回的经纬度都有微小的变化，
                 * 是因为返回的位置点并不是你真实的位置，有误差造成的。
                 */
                location.getRadius();

                location.getCountryCode();//国家码，null代表没有信息
                location.getCountry();//国家名称
                location.getCityCode();//城市编码
                location.getCity();//城市
                location.getDistrict();//区
                location.getStreet();//街道
                location.getAddrStr();//地址信息
                location.getLocationDescribe();//位置描述信息

                /**
                 * 判断用户是在室内，还是在室外
                 * 1：室内，0：室外，这个判断不一定是100%准确的
                 */
                location.getUserIndoorState();

                /**
                 * 获取方向
                 */
                location.getDirection();

                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        poi.getName();//获取位置附近的一些商场、饭店、银行等信息
                    }
                }

                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS类型定位结果
                    location.getSpeed();//速度 单位：km/h，注意：网络定位结果是没有速度的
                    location.getSatelliteNumber();//卫星数目，gps定位成功最少需要4颗卫星
                    location.getAltitude();//海拔高度 单位：米
                    location.getGpsAccuracyStatus();//gps质量判断
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {//网络类型定位结果
                    if (location.hasAltitude()) {//如果有海拔高度
                        location.getAltitude();//单位：米
                    }
                    location.getOperators();//运营商信息
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    //离线定位成功，离线定位结果也是有效的;
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    //服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com;
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    //网络不同导致定位失败，请检查网络是否通畅;
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    //无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机;
                }

                if(infoUpdate!=null)
                    infoUpdate.update(location);
            }
        }

    };
}
