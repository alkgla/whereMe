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
			startLocation.setText("������λ");
		} else {
			isStart = true;
			mLocationClient.start();
			startLocation.setText("ֹͣ��λ");
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

		// ���ع���־�γ������ϵ coor=gcj02
		// ���ذٶ�ī��������ϵ coor=bd09
		// ���ذٶȾ�γ������ϵ coor=bd09ll
		/**
		 * �����������ֵ���ڵ���1000��ms��ʱ����λSDK�ڲ�ʹ�ö�ʱ��λģʽ������requestLocation()
		 * ��ÿ���趨��ʱ�䣬��λSDK�ͻ����һ�ζ�λ��
		 * �����λSDK���ݶ�λ���ݷ���λ��û�з����仯���Ͳ��ᷢ���������󣬷�����һ�ζ�λ�Ľ����
		 * �������λ�øı䣬�ͽ�������������ж�λ���õ��µĶ�λ�������ʱ��λʱ������һ��requestLocation���ᶨʱ��������λ�����
		 * ���������������������ֵС��1000��ms��ʱ������һ�ζ�λģʽ��ÿ����һ��requestLocation()��
		 * ��λSDK�ᷢ��һ�ζ�λ������λ��������һһ��Ӧ�� �趨�˶�ʱ��λ�󣬿������л���һ�ζ�λ����Ҫ��������ʱ����С��1000
		 * ��ms�����ɡ�locationClient����stop�󣬽����ٽ��ж�λ ��
		 * ����趨�˶�ʱ��λģʽ�󣬶�ε���requestLocation����������ÿ��һ��ʱ�����һ�ζ�λ��ͬʱ����Ķ�λ����Ҳ����ж�λ��
		 * ��Ƶ�ʲ��ᳬ��1��һ�Ρ�
		 */

		option.setCoorType("bd0911");
		option.setScanSpan(5000);// ���÷���λ����ļ��ʱ��Ϊ5000ms
		option.setOpenGps(true); // ��GPS
		// String ֵΪallʱ����ʾ���ص�ַ��Ϣ������ֵ����ʾ�����ص�ַ��Ϣ(�ٷ�ָ��˵�����������������ȴû�У���֪��Ϊʲô)
		// option.setAddrType("all");
		option.setProdName("com.example.textandroid"); // ���ò�Ʒ�����ƣ��ٶȽ���
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
			sb.append("��" + (++times) + "�λ�ȡ��λ");
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