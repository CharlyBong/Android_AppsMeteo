package com.example.appsmeteo;

import java.util.Locale;

import com.example.appsmeteo.bdd.City;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PrevjoursActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	City maville = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prev_jours);
		

		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		this.maville = getIntent().getExtras().getParcelable("Extra_city");
		
		Log.d("log.appsmeteo.previ", "Affichage de : "+maville.getName());	
		
		this.setTitle(this.getTitle()+" - "+maville.getName()+", "+maville.getPays());
		
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			((DummySectionFragment) fragment).setville(maville);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 8 total pages (résumé + 7 jours)
			return 7;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section_resume).toUpperCase(l);
			case 1:
				return maville.getPrevision(0).getDate();
			case 2:
				return maville.getPrevision(1).getDate();
			case 3:
				return maville.getPrevision(2).getDate();
			case 4:
				return maville.getPrevision(3).getDate();
			case 5:
				return maville.getPrevision(4).getDate();
			case 6:
				return maville.getPrevision(5).getDate();
			case 7:
				return maville.getPrevision(6).getDate();
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		private City maville;

		public DummySectionFragment() {}
		
		public void setville(City maville){
			this.maville  = maville;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = null;
			
			int id_page = getArguments().getInt(ARG_SECTION_NUMBER)-2;
			
			
			if(id_page == -1)
				rootView = this.view_resume(inflater, container);
			else 
				rootView = this.view_day(inflater, container, id_page);
			
			return rootView;
		}
		
		private View view_day(LayoutInflater inflater, ViewGroup container,int id_page){
			View rootView = inflater.inflate(R.layout.fragment_day_dummy,container, false);
			
			TextView temp = (TextView) rootView.findViewById(R.id.day_temp);
			temp.setText(Integer.toString(maville.getPrevision( id_page ).getTemperatureDay()));
			
			TextView temp_min = (TextView) rootView.findViewById(R.id.day_temp_min);
			temp_min.setText(Integer.toString(maville.getPrevision( id_page ).getTemperatureMin())+"°");
			
			TextView temp_max = (TextView) rootView.findViewById(R.id.day_temp_max);
			temp_max.setText(Integer.toString(maville.getPrevision( id_page ).getTemperatureMax())+"°");
			
			TextView symbol = (TextView) rootView.findViewById(R.id.day_symbol);
			symbol.setText(maville.getPrevision( id_page ).getSymbol());
			
						
			ImageView img = (ImageView) rootView.findViewById(R.id.day_img);			
			String id_img = "img_"+maville.getPrevision( id_page ).getSymbolVar();			
			int idResource = getResources().getIdentifier(id_img, "drawable", "com.example.appsmeteo");			
			img.setImageResource(idResource);
			
			TextView precip = (TextView) rootView.findViewById(R.id.day_precip);
			precip.setText(Integer.toString(maville.getPrevision( id_page ).getPrecipitation()));
			
			TextView windspeed = (TextView) rootView.findViewById(R.id.day_windspeed);
			windspeed.setText(Float.toString(maville.getPrevision( id_page ).getWindSpeed()));
			
			TextView winddirect = (TextView) rootView.findViewById(R.id.day_winddirect);
			winddirect.setText(maville.getPrevision( id_page ).getWindDirection());
			
			TextView humidity = (TextView) rootView.findViewById(R.id.day_humidity);
			humidity.setText(Integer.toString(maville.getPrevision( id_page ).getHumidity()));
			
			TextView pressure = (TextView) rootView.findViewById(R.id.day_pressure);
			pressure.setText(Integer.toString(maville.getPrevision( id_page ).getPressure()));
			
			return rootView;
		}
		
		private View view_resume(LayoutInflater inflater, ViewGroup container){
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,container, false);
			
			
			ImageView img = (ImageView) rootView.findViewById(R.id.res_img);
			String id_img = "img_"+maville.getPrevision( 0 ).getSymbolVar();			
			int idResource = getResources().getIdentifier(id_img, "drawable", "com.example.appsmeteo");			
			img.setImageResource(idResource);
			
			TextView temp = (TextView) rootView.findViewById(R.id.res_temp);
			temp.setText(Integer.toString(maville.getPrevision( 0 ).getTemperatureDay()));
			
			TextView temp_min = (TextView) rootView.findViewById(R.id.res_temp_min);
			temp_min.setText(Integer.toString(maville.getPrevision( 0 ).getTemperatureMin())+"°");
			
			TextView temp_max = (TextView) rootView.findViewById(R.id.res_temp_max);
			temp_max.setText(Integer.toString(maville.getPrevision( 0 ).getTemperatureMax())+"°");
			
			
			ImageView img1 = (ImageView) rootView.findViewById(R.id.res_img_1);
			id_img = "img_"+maville.getPrevision( 1 ).getSymbolVar();			
			idResource = getResources().getIdentifier(id_img, "drawable", "com.example.appsmeteo");			
			img1.setImageResource(idResource);
			
			TextView temp1 = (TextView) rootView.findViewById(R.id.res_temp_1);
			temp1.setText(Integer.toString(maville.getPrevision( 1 ).getTemperatureDay())+"°");
			
			ImageView img2 = (ImageView) rootView.findViewById(R.id.res_img_2);
			id_img = "img_"+maville.getPrevision( 2 ).getSymbolVar();			
			idResource = getResources().getIdentifier(id_img, "drawable", "com.example.appsmeteo");			
			img2.setImageResource(idResource);
			
			TextView temp2 = (TextView) rootView.findViewById(R.id.res_temp_2);
			temp2.setText(Integer.toString(maville.getPrevision( 2 ).getTemperatureDay())+"°");
			
			ImageView img3 = (ImageView) rootView.findViewById(R.id.res_img_3);
			id_img = "img_"+maville.getPrevision( 3 ).getSymbolVar();			
			idResource = getResources().getIdentifier(id_img, "drawable", "com.example.appsmeteo");			
			img3.setImageResource(idResource);
			
			TextView temp3 = (TextView) rootView.findViewById(R.id.res_temp_3);
			temp3.setText(Integer.toString(maville.getPrevision( 3 ).getTemperatureDay())+"°");
			
			ImageView img4 = (ImageView) rootView.findViewById(R.id.res_img_4);
			id_img = "img_"+maville.getPrevision( 4 ).getSymbolVar();			
			idResource = getResources().getIdentifier(id_img, "drawable", "com.example.appsmeteo");			
			img4.setImageResource(idResource);
			
			TextView temp4 = (TextView) rootView.findViewById(R.id.res_temp_4);
			temp4.setText(Integer.toString(maville.getPrevision( 4 ).getTemperatureDay())+"°");
			
			ImageView img5 = (ImageView) rootView.findViewById(R.id.res_img_5);
			id_img = "img_"+maville.getPrevision( 5 ).getSymbolVar();			
			idResource = getResources().getIdentifier(id_img, "drawable", "com.example.appsmeteo");			
			img5.setImageResource(idResource);
			
			TextView temp5 = (TextView) rootView.findViewById(R.id.res_temp_5);
			temp5.setText(Integer.toString(maville.getPrevision( 5 ).getTemperatureDay())+"°");
			
			ImageView img6 = (ImageView) rootView.findViewById(R.id.res_img_6);
			id_img = "img_"+maville.getPrevision( 6 ).getSymbolVar();			
			idResource = getResources().getIdentifier(id_img, "drawable", "com.example.appsmeteo");			
			img6.setImageResource(idResource);
			
			TextView temp6 = (TextView) rootView.findViewById(R.id.res_temp_6);
			temp6.setText(Integer.toString(maville.getPrevision( 6 ).getTemperatureDay())+"°");
			
			
			return rootView;
		}
	}

}
