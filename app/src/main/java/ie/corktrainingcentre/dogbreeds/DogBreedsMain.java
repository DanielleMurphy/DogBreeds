package ie.corktrainingcentre.dogbreeds;

import android.content.res.Resources;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class DogBreedsMain extends ActionBarActivity {

    String[] dogBreeds;
    String[] fileNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_breeds_main);

        Resources res = getResources();
        dogBreeds = res.getStringArray(R.array.dogs);
        fileNames = res.getStringArray(R.array.files);

        // add all the dog breeds to the LinearLayout
        LinearLayout dogBreedsLayout = (LinearLayout) findViewById(R.id.dogBreeds);
        for(int bow_wow = 0; bow_wow < dogBreeds.length; bow_wow++) {
            dogBreedsLayout.addView( createDogItem( bow_wow ) );
        }

    }

    private LinearLayout createDogItem(int breed) {

        ImageView dogImage = getDogImage(breed);
        TextView dogBreed = getDogBreed(breed);

        TextView dogText = getDogText(breed);

        // layout parameters for the two LinearLayouts created in this method
        LayoutParams generalParams = new LayoutParams( LayoutParams.WRAP_CONTENT,
                                                       LayoutParams.MATCH_PARENT );

        // create the LinearLayout to contain the image and the dog breed TextView
        LinearLayout rowOne = new LinearLayout(this);
        rowOne.setLayoutParams(generalParams);
        rowOne.setOrientation(LinearLayout.HORIZONTAL);
        //rowOne.setBackgroundColor(Color.RED); // for debugging the layout
        rowOne.addView(dogImage);
        rowOne.addView(dogBreed);

        // create the LinearLayout to contain rowOne and the dog breed details TextView
        LinearLayout dogItem = new LinearLayout(this);
        dogItem.setLayoutParams(generalParams);
        dogItem.setOrientation(LinearLayout.VERTICAL);
        //dogItem.setBackgroundColor(Color.BLUE); // for debugging the layout
        dogItem.addView(rowOne);
        dogItem.addView(dogText);
        dogItem.setPadding(0, 0, 0, 10); // left, top, right, bottom

        return dogItem;
    }

    private ImageView getDogImage(int breed) {
        ImageView dogImage = new ImageView( getApplicationContext() );
        String fileName = fileNames[breed];
        String resourceType = "drawable";
        String packageName = getApplicationContext().getPackageName();
        Resources res = this.getResources();
        int identifier = res.getIdentifier( fileName, resourceType, packageName );
        dogImage.setImageResource(identifier);
        int imageWidth = 70;
        int imageHeight = 70;
        dogImage.setLayoutParams( new LinearLayout.LayoutParams(imageWidth, imageHeight) );
        dogImage.setPadding(0, 0, 5, 5); // left, top, right, bottom
        return dogImage;
    }

    private TextView getDogBreed(int breed) {
        TextView dogBreed = new TextView(getApplicationContext());
        dogBreed.setText(dogBreeds[breed]);
        dogBreed.setTextColor(Color.BLACK);
        dogBreed.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        return dogBreed;
    }

    private TextView getDogText(int breed) {
        LayoutParams generalParams = new LayoutParams( LayoutParams.WRAP_CONTENT,
                                                       LayoutParams.MATCH_PARENT );
        TextView dogText = new TextView(getApplicationContext());
        dogText.setLayoutParams(generalParams);
        dogText.setTextColor(Color.DKGRAY);
        dogText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

        // read the dog breed details
        String fileName = fileNames[breed];
        String resourceType = "raw";
        String packageName = getApplicationContext().getPackageName();
        Resources res = this.getResources();
        int identifier = res.getIdentifier( fileName, resourceType, packageName );
        InputStream file = null;
        try {
            file = res.openRawResource(identifier);
            byte[] buffer = new byte[file.available()];
            file.read(buffer, 0, buffer.length);
            // put the dog breed details into the TextView
            dogText.setText(new String(buffer, "UTF-8"));
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dogText;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dog_breeds_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
