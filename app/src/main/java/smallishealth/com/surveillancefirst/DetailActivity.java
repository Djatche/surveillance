package smallishealth.com.surveillancefirst;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class DetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView nameFrView, nameEnView, definitionCasView, caseDefinitionView;
    VideoView videoView;

    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = (ImageView) findViewById(R.id.imageViewDetail);
        nameFrView = (TextView) findViewById(R.id.name_detail_fr);
        nameEnView = (TextView) findViewById(R.id.name_detail_en);
        definitionCasView = (TextView) findViewById(R.id.case_definition_fr);
        caseDefinitionView = (TextView) findViewById(R.id.case_definition_en);
        videoView = (VideoView)findViewById(R.id.videoview);

        Intent intent = getIntent();
        String name_fr = null, name_en = null, definitionCas = null, caseDefinition = null;
        if (intent.hasExtra("name_fr")&& intent.hasExtra("name_en") && intent.hasExtra("definition_cas") && intent.hasExtra("case_definition")){
            name_fr = intent.getStringExtra("name_fr");
            name_en = intent.getStringExtra("name_en");
            definitionCas = intent.getStringExtra("definition_cas");
            caseDefinition = intent.getStringExtra("case_definition");

        }

        nameFrView.setText(name_fr);
        nameEnView.setText(name_en);
        definitionCasView.setText(definitionCas);
        caseDefinitionView.setText(caseDefinition);

        String pathVideo = "android.resource://" + getPackageName() + "/" + R.raw.test1;
        Uri uri = Uri.parse(pathVideo);
        videoView.setVideoURI(uri);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuItemSelected = item.getItemId();
        if (menuItemSelected == R.id.action_setting){
            Context context = DetailActivity.this;
            Intent intent = new Intent(context, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
