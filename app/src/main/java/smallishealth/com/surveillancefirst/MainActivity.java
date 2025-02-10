package smallishealth.com.surveillancefirst;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.SystemClock;
//import android.support.v7.app.AppCompatActivity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import smallishealth.com.surveillancefirst.model.Maladie;

public class MainActivity extends AppCompatActivity implements MaladieAdapter.ListItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int NOTIFICATION_ID = 0;

    RecyclerView recyclerView;
    MaladieAdapter adapter;
    List<Maladie> maladies;

    String CHANNEL_ID = "channelId";

    AlarmManager alarmManager;
    private NotificationManager notificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setupSharedPreferences();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        maladies = new ArrayList<>();
        maladies.add(new Maladie("Choléra", "Cholera", "Déshydratation grave ou décès suite à une diarrhée acqueuse aigue chez un patient de plus de 5 ans","Severe dehydration or death from acute watery diarrhea in a patient older than 5 years", 5));
        maladies.add(new Maladie("Rougeole", "Measles", "Toute personne présentant de la fièvre, une éruption généralisée maculopapulaire (non vésiculaire) et de la toux ou un rhume ou une conjonctivite (yeux rouges), ou toute personne chez laquelle un médecin soupçonne une rougeole", "Anyone with fever, maculopapular (non-vesicular) generalized rash and cough or cold or conjunctivitis (red eyes), or anyone suspected of having measles by a physician", 6));
        maladies.add(new Maladie("Fièvre jaune", "Yellow fever", "Toute personne présentant une brutale montée de fièvre, avec apparition d’un ictère dans les 14 jours suivant l’apparition des premiers symptômes.", "Anyone with a sudden rise in fever with jaundice within 14 days of the onset of symptoms.", 7));
        maladies.add(new Maladie("PFA", "AFP", "Tout enfant de moins de 15 ans présentant une paralysie flasque aiguë ou toute personne souffrant de paralysie, quel que soit son âge, chez laquelle le médecin soupçonne une poliomyélite", "Any child under 15 years of age with acute flaccid paralysis or any person with paralysis, regardless of age, who is suspected of having poliomyelitis", 10));
        maladies.add(new Maladie("TNN", "NNT", "Tout nouveau-né capable de pleurer et de prendre le sein normalement pendant les 2 premiers jours de sa vie, et qui, entre le 3ième et 28ème jour, ne peut plus têter normalement, devient raide et/ou a des convulsions", "Any newborn capable of crying and breastfeeding normally during the first 2 days of life, and who, between day 3 and day 28, can no longer nurse normally, becomes stiff and / or has seizures", 2));
        maladies.add(new Maladie("Ver de Guinée", "Guinea worm", "Toute personne présentant une lésion cutanée accompagnée de démangeaisons ou de cloques, résidant dans une région d’endémie pour le ver de Guinée", "Anyone with an itchy or blistered skin lesion residing in an endemic area for guinea worm", 5));
        maladies.add(new Maladie("Ebola", "Ebola", "Toute personne souffrant d’une forte fièvre qui ne répond à aucun traitement des causes habituelles de fièvre dans la région, et qui présente au moins l’un des signes suivants : diarrhée sanglante, hémorragie gingivale, hémorragies cutanées (purpura), injection des conjonctives et présence de sang dans les urine", "Anyone with a high fever who does not respond to any of the usual causes of fever in the area, and who has at least one of the following signs: bloody diarrhea, gum bleeding, skin bleeding (purpura), conjunctival injection and the presence of blood in the urine", 4));
        maladies.add(new Maladie("Variole du singe", "Monkey pox", "Apparition brutale d’une forte fièvre > 38,3 degré, suivie d’une éruption caractérisée par des vésicules ou des pustules dures, au même stade de développement (c'est-à-dire qui sont toutes des vésicules ou des pustules), sans autre cause apparente.", "Sudden onset of high fever> 38.3 degrees, followed by an eruption characterized by hard vesicles or pustules, at the same stage of development (ie all vesicles or pustules), without any other apparent cause.",5 ));
        maladies.add(new Maladie("Méningite", "Meningitis", "Toute personne présentant une forte fièvre d’apparition brutale (température rectale >38,5ºC ou axillaire >38,0ºC) et l’un des signes suivants : raideur de la nuque, altérations de la conscience ou autres signes méningés.", "Anyone with a high fever of sudden onset (rectal temperature> 38.5ºC or axillary> 38.0ºC) and any of the following signs: stiff neck, changes in consciousness or other signs of meningitis.", 4));
        maladies.add(new Maladie("Diarrhée sanglante", "Bloody diarrhea", "Toute personne souffrant de diarrhée avec présence de sang visible dans les selles", "Anyone with diarrhea with visible blood in the stool", 9));
        maladies.add(new Maladie("COVID-19", "COVID-19", "Toute personne, symptomatique ou non, avec un résultat biologique confirmant l’infection par le SARS-CoV-2, par RT-PCR ou par sérologie dans le cadre d’un diagnostic de rattrapage, conformément aux recommandations de la HAS", "Anyone, symptomatic or not, with a biological result confirming infection with SARS-CoV-2, by RT-PCR or by serology as part of a catch-up diagnosis, in accordance with HAS recommendations", 5));

        adapter = new MaladieAdapter(maladies, this);
        recyclerView.setAdapter(adapter);

        createNotificationChannel();

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent alarmIntent = new Intent(MainActivity.this, SurveillanceReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, alarmIntent, getPendingFlags());


        alarmManager.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime(),
                AlarmManager.INTERVAL_HOUR,
                pendingIntent
        );


    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //String language = sharedPreferences.getString(getString(R.string.channel_name),getResources().getString(R.string.channel_name));
        //String freq_notif = sharedPreferences.getString(getString(R.string.channel_name),getResources().getString(R.string.channel_name));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private int getPendingFlags() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
        };
        return PendingIntent.FLAG_UPDATE_CURRENT;
    };

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Maladie maladie = maladies.get(clickedItemIndex);
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("name_fr", maladie.getName_fr());
        intent.putExtra("name_en", maladie.getName_en());
        intent.putExtra("definition_cas", maladie.getDefinitionDesCasFr());
        intent.putExtra("case_definition", maladie.getDefinitionDesCasEn());
        intent.putExtra("image_pathologie", maladie.getImagePathologie());
        intent.putExtra("movie_pathologie", maladie.getMoviePathologie());

        startActivity(intent);

        //Toast.makeText(this, "Coucou", Toast.LENGTH_SHORT).show();
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
            Context context = MainActivity.this;
            Intent intent = new Intent(context, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String s) {

    }
}
