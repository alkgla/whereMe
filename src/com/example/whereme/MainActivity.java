package com.example.whereme;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {
	TextView location_tv1;
	Button startLocation;
	boolean isStart = false;
	LocationClient mLocationClient;
	MyLocationListener myLocationListener;
	int times = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		init();
		ctlLocService();
	}

	private void initView() {
		location_tv1 = (TextView) findViewById(R.id.location_tv1);
		startLocation = (Button) findViewById(R.id.startLocation);
		startLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ctlLocService();
			}
		});
	}

	protected void ctlLocService() {
		// TODO Auto-generated method stub
		if (isStart) {
			isStart = false;
			mLocationClient.stop();
			startLocation.setText("启动定位");
		} else {
			isStart = true;
			mLocationClient.start();
			startLocation.setText("停止定位");
		}

	}

	private void init() {
		mLocationClient = new LocationClient(this.getApplicationContext());
		myLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(myLocationListener);
		InitLocation();
	}

	private void InitLocation() {
		// TODO Auto-generated method stub
		LocationClientOption option = new LocationClientOption();

		// 返回国测局经纬度坐标系 coor=gcj02
		// 返回百度墨卡托坐标系 coor=bd09
		// 返回百度经纬度坐标系 coor=bd09ll
		/**
		 * 当所设的整数值大于等于1000（ms）时，定位SDK内部使用定时定位模式。调用requestLocation()
		 * 后，每隔设定的时间，定位SDK就会进行一次定位。
		 * 如果定位SDK根据定位依据发现位置没有发生变化，就不会发起网络请求，返回上一次定位的结果；
		 * 如果发现位置改变，就进行网络请求进行定位，得到新的定位结果。定时定位时，调用一次requestLocation，会定时监听到定位结果。
		 * 当不设此项，或者所设的整数值小于1000（ms）时，采用一次定位模式。每调用一次requestLocation()，
		 * 定位SDK会发起一次定位。请求定位与监听结果一一对应。 设定了定时定位后，可以热切换成一次定位，需要重新设置时间间隔小于1000
		 * （ms）即可。locationClient对象stop后，将不再进行定位 。
		 * 如果设定了定时定位模式后，多次调用requestLocation（），则是每隔一段时间进行一次定位，同时额外的定位请求也会进行定位，
		 * 但频率不会超过1秒一次。
		 */

		option.setCoorType("bd0911");
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.setOpenGps(true); // 打开GPS
		// String 值为all时，表示返回地址信息，其他值都表示不返回地址信息(官方指南说有这个方法，但类中却没有，不知道为什么)
		// option.setAddrType("all");
		option.setProdName("com.example.textandroid"); // 设置产品线名称，百度建议
		option.setAddrType("all");
		mLocationClient.setLocOption(option);

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mLocationClient.stop();
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			StringBuilder sb = new StringBuilder();
			sb.append("第" + (++times) + "次获取定位");
			sb.append("\ntime : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			}
			location_tv1.setText(sb.toString());
		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
			// TODO Auto-generated method stub

		}

	}
}