/*
 * Copyright 2011 BTI360 Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bti360.hackathon.listview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class HackathonActivity extends Activity {
	
	protected static final int ADD_DIALOG = 0;
	
	List<String> mNames;
	NameAdapter mAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Let's create a String List and retrieve the String Array from our Resources
        mNames = new ArrayList<String>();
        mNames.addAll(Arrays.asList(getResources().getStringArray(R.array.names)));
        
        //grab the ListView
        ListView lv = (ListView) findViewById(R.id.listView1);
        
        //create a new ListAdapter and pass in our ArrayList of data
        mAdapter = new NameAdapter(this, R.layout.list_item, mNames);
        lv.setAdapter(mAdapter);
        
        //grab our button and set an onClickListener
        Button addButton = (Button) findViewById(R.id.button1);
        addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				/*when the add button is clicked we will show the Add Dialog.
				 * Opening a Dialog with showDialog causes the Activity to Manage the
				 * dialog meaning it will control the lifecycle - create, show, hide,
				 * destroy when Activity is killed, etc.
				 */			
				showDialog(ADD_DIALOG);
			}});
    }
    
    private class NameAdapter extends ArrayAdapter<String> {
    	
    	LayoutInflater mLayoutInflater;
    	
		public NameAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			mLayoutInflater = getLayoutInflater();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//check if our view is going to be recycled/reused
			if(convertView == null) {
				convertView = mLayoutInflater.inflate(R.layout.list_item, null);
			}
			//grab the TextView and set the text with the name at the position
			TextView textView = (TextView) convertView.findViewById(R.id.textView1);
			textView.setText(getItem(position));
			
			//grab the ImageView and see if we have a picture for the name
			ImageView iv = (ImageView) convertView.findViewById(R.id.imageView1);
			String url = null;
			switch(position) {
			case 0:
				url = "http://www.bti360.com/uploads/Tim_thumb.jpg";
				break;
			case 1:
				url = "http://www.bti360.com/uploads/Jonthan_thumb.jpg";
				break;
			case 2:
				url = "http://www.bti360.com/uploads/Clinton_thumb2.jpg";
				break;
			}
			if(url != null) {
				//if the ImageView is not null we create a new AsycTask and pass in our 
				//ImageView and the url to the image resource
				new FetchTask(iv).execute(url);
				
/*				Comment out our old fetch code
 * 				try {
					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpGet request = new HttpGet(url);
					HttpResponse response = httpClient.execute(request);
					Bitmap bm = BitmapFactory.decodeStream(response.getEntity().getContent());
					iv.setImageBitmap(bm);
				} catch(IOException e) {
					
				}*/
			} else { //if the url is null clear the ImageView
				iv.setImageBitmap(null);
			}
			return convertView;
		}
    	
    }


    /* This is called whenever showDialog is called with a dialog that has not
     * yet been created.
     */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id) {
		case ADD_DIALOG:
			// We are going to create an AlertDialog with a single text input and a button
			// first we create the EditText
			final EditText edit = new EditText(this);
			// Next we create an AlertDialog.Builder which creates a styled AlertDialog based
			// on our specifications;
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// set the title
			builder.setTitle("Add Person");
			// set the icon to a built-in, this one is a +
			builder.setIcon(android.R.drawable.ic_input_add);
			// set the text of the only button, and add a click listener
			builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// on the Ok Button we grab the text from the EditText,
					// clear it and then add the Name to our list
					String name = edit.getText().toString();
					edit.setText("");
					addName(name);
				}
			});
			// finally let's create the dialog
			final AlertDialog d = builder.create();
			// and set the view to our EditText
			d.setView(edit);
			
			// we'll set a special InputType since we are collecting a name
			// other's exist such as email, address, phone number, etc
			// this allows the IME (keyboard) to customize itself based on
			// expected input, e.g. show @ and .com when Email
			edit.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
			// Respond to the default action on the IME (keyboard) By default it is
			// "Done" but it can be changed with setImeActionLabel to be something
			// else like a search hourglass.
			// In our case we want a click on "Done" to do the same thing as a click
			// on the Ok button in the Dialog.
			edit.setOnEditorActionListener(new OnEditorActionListener() {

				@Override
				public boolean onEditorAction(TextView tv, int actionId,
						KeyEvent arg2) {
					// same as the DialogClick Handler except we also dismiss the dialog
					String name = edit.getText().toString();
					edit.setText("");
					addName(name);
					d.dismiss();
					return true;
				}});
			return d;
		}
		return super.onCreateDialog(id);
	}

	/*
	 * Add a name to our list.
	 * notifySetDataChanged tells the Adapter that it may need to refresh itself
	 */
	protected void addName(String name) {
		mNames.add(name);
		mAdapter.notifyDataSetChanged();
	}
	
	/*
	 * AsyncTask is a way to do Background Tasks. Async Tasks have a ThreadPool
	 * that they use.
	 */
	// The parameters are Params to execute(), Progress variable, Return Variable
	private class FetchTask extends AsyncTask<String, Void, Bitmap> {

		ImageView imageView;
		
		public FetchTask(ImageView imageView) {
			super();
			this.imageView = imageView;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			
		// Download the image using Http client
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
			    HttpGet request = new HttpGet(params[0]);
			    HttpResponse response = httpClient.execute(request);
			    // Use BitmapFactory to decode the bytes into a Bitmap and return it
				return BitmapFactory.decodeStream(response.getEntity().getContent());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// This runs on the UI/Main thread so its okay to update the ImageView
			if(result != null) {
				imageView.setImageBitmap(result);
			}
		}
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// this will run when the menu is first created to add our
		// menu from the xml file.
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// show the add dialog when the add menu item is selected
		switch(item.getItemId()) {
		case R.id.add:
			showDialog(ADD_DIALOG);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}