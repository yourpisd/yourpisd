//THIS WAS EDITED WITHIN ECLIPSE AS AN TEST
package com.sunstreaks.mypisd;

import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

import com.sunstreaks.mypisd.net.DataGrabber;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

	static int classCount;
	static Button[] buttons;
	static DataGrabber dg;
	static Bitmap proPic;
	
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Makes sure that there IS a datagrabber to insert. Now, what if there were multiple
		// versions of DataGrabber....? I wonder what would happen.
//		Intent intent = getIntent();
//		{
//			DataGrabber myDG = (DataGrabber) intent.getParcelableExtra("DataGrabber");
//			dg = myDG;
//		}
		dg = ( (YourPISDApplication) getApplication() ).getDataGrabber();
		
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		// Opens Grade Summary (index 0) on open.
		mViewPager.setCurrentItem(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new MainActivityFragment();
			Bundle args = new Bundle();
			args.putInt(MainActivityFragment.ARG_OBJECT, position);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 2 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section2).toUpperCase(l);
			case 1:
				return getString(R.string.title_section1).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class MainActivityFragment extends Fragment  {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_OBJECT = "object";
		private View rootView;
		private int position;
		
		public MainActivityFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Bundle args = getArguments();
		    position = args.getInt(ARG_OBJECT);

		    int tabLayout = 0;
		    switch (position) {
		    case 0:
		    	tabLayout = R.layout.tab_summary;
		    	break;
		    case 1:
		    	tabLayout = R.layout.tab_new;
		    	break;
		    }
		    

		    
		    rootView = inflater.inflate(tabLayout, container, false);
		    
		    if (position == 0)
		    	try {
		    	
		    		ScrollView sv = new ScrollView(getActivity());
		    		
		    		
		    		
		    		LinearLayout layout = new LinearLayout(getActivity());
		    		 
		            layout.setOrientation(LinearLayout.VERTICAL);
		            layout.setLayoutParams(new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
	
		     
		            TextView title = new TextView(getActivity());
		            LinearLayout.LayoutParams lll = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		            lll.gravity = Gravity.CENTER_HORIZONTAL;
		            title.setLayoutParams(lll);
		            title.setText("Grade Summary");
		            title.setId(900);
		            title.setTextSize(30);
		            layout.addView(title);
		            
		            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
		            		LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		            
		            lp.setMargins(30, 20, 30, 0);
		            
		            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
		            		LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		            lp1.setMargins(0, 0, 0, 20);
		            
		            
			    
		            
		            JSONArray gradeSummary = dg.getGradeSummary();
		            
			    	classCount = gradeSummary.length();
			    	buttons = new Button[classCount];
			    	
		            for (int i = 0; i < classCount ; i++) {
		            	
		            	JSONObject course = gradeSummary.getJSONObject(i);
		            	
		            	String name = " " + course.getString("title");
		            	
		            	LinearLayout innerLayout = new LinearLayout(getActivity());
			            innerLayout.setOrientation(LinearLayout.HORIZONTAL);
			   
			            TextView className = new TextView(getActivity());
			            className.setText(name);
			            //className.setBackgroundColor(Color.WHITE);
			            className.setTextSize(20);
			            className.setLayoutParams(
			            		new LinearLayout.LayoutParams(
			            				LinearLayout.LayoutParams.WRAP_CONTENT, 
			            				LinearLayout.LayoutParams.WRAP_CONTENT, 
			            				1f));
			            
			            innerLayout.addView(className);
			            
			            //Hard coded for first six weeks average.
			            JSONObject term = course.getJSONArray("terms").getJSONObject(0);
			            int average = term.optInt("average", -1);
			            
			            buttons[i] = new Button(getActivity());
			            buttons[i].setText(average + "");
			            buttons[i].setId(i);
			            buttons[i].setOnClickListener((View.OnClickListener)getActivity());
			            innerLayout.addView(buttons[i]);
//			            if (i % 2 == 0)
//			            	innerLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
//			            else
//			            	innerLayout.setBackgroundColor(Color.WHITE);
//			            innerLayout.setBackgroundDrawable(getResources().getDrawable(drawable.toast_frame));
			            innerLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.dropshadow));
			            layout.addView(innerLayout, lp);
			            
		            }
		            
		            sv.addView(layout, lp1);
		            return sv;
		    	} catch (JSONException e) {
		    		e.printStackTrace();
		    	}

		    
		    if (position == 1) {
		    	StudentPictureTask spTask = new StudentPictureTask();
			    spTask.execute();
		    }
		    
		    return rootView;
		    
		}

		public class StudentPictureTask extends AsyncTask<Void, Void, Bitmap> {

			@Override
			protected Bitmap doInBackground(Void... arg0) {	
				return dg.getStudentPicture();

			}
			
			@Override
			protected void onPostExecute (final Bitmap result) {
				LinearLayout lv = (LinearLayout)rootView.findViewById(R.id.overall);
		    	LinearLayout card = new LinearLayout(getActivity());
		    	ImageView profilePic = new ImageView(getActivity());

		    	Drawable picture = new BitmapDrawable(getResources(), result);
		    	profilePic.setImageDrawable(picture);
		    	card.addView(profilePic);
		    	card.setBackgroundDrawable(getResources().getDrawable(R.drawable.dropshadow));

		    	lv.addView(card);
			}
			
		}
		
	}


	
	@Override
	public void onClick(View v) {
		System.out.println(v.getId());
		Intent intent = new Intent (this, ClassSwipeActivity.class);
		System.out.println("classCount = " + classCount);
		System.out.println("classIndex = " + v.getId());
		intent.putExtra("classCount", classCount);
		intent.putExtra("classIndex", v.getId());
//		intent.putExtra("DataGrabber", dg);
		((YourPISDApplication) getApplication()).setDataGrabber(dg);
		startActivity(intent);
	}

}
