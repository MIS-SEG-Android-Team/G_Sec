package org.rmj.guanzongroup.gsecurity.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.TagLostException;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.data.repository.nfc.MifareUltralightTagTester;
import org.rmj.guanzongroup.gsecurity.databinding.ActivityPersonnelBinding;
import org.rmj.guanzongroup.gsecurity.ui.screens.places.FragmentPlaces;

import java.io.IOException;

public class PersonnelActivity extends AppCompatActivity implements NfcAdapter.ReaderCallback {
    private static final String TAG = PersonnelActivity.class.getSimpleName();

    private ActivityPersonnelBinding binding;

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter intentFilter;
    private String[][] techListsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPersonnelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_personnel);
        BottomNavigationView navView = binding.bottomNavBar;

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(nfcAdapter == null){
            Log.d(FragmentPlaces.class.getSimpleName(), "NFC is not supported on this device");
        }

        pendingIntent = PendingIntent.getActivity(
                this,
                0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                PendingIntent.FLAG_MUTABLE);

        intentFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            intentFilter.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException(e);
        }

        techListsArray = new String[][] { new String[] { NfcF.class.getName() } };

        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    protected void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, new IntentFilter[]{intentFilter}, techListsArray);
//        enableNfcForegroundDispatch();
        Bundle options = new Bundle();
        options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 250);
        nfcAdapter.enableReaderMode(this,
                this,
                NfcAdapter.FLAG_READER_NFC_A |
                        NfcAdapter.FLAG_READER_NFC_B |
                        NfcAdapter.FLAG_READER_NFC_F |
                        NfcAdapter.FLAG_READER_NFC_V |
                        NfcAdapter.FLAG_READER_NFC_BARCODE |
                        NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS,
                options);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(nfcAdapter!= null)
            nfcAdapter.disableReaderMode(this);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();

        if(itemID == R.id.actionChangePassword) {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu_settings_personnel, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String actionIntent = intent.getAction();
        String actionNDEF = NfcAdapter.ACTION_NDEF_DISCOVERED;
//        if (NfcAdapter.ACTION_TECH_DISCOVERED.equalsIgnoreCase(intent.getAction())) {
//            Parcelable[] rawMessages =
//                    intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
//            if (rawMessages != null) {
//                NdefMessage[] messages = new NdefMessage[rawMessages.length];
//                for (int i = 0; i < rawMessages.length; i++) {
//                    messages[i] = (NdefMessage) rawMessages[i];
//
//                    Log.d("PersonnelActivity", messages[i].toString());
//                }
//
//
//            }
            String message = "NFC, A new data written";
//            NdefMessage ndefMessage = createNdefMessage(message);
//        try {
//            writeDataToNFCTag(ndefMessage, intent);
//        } catch (IOException | FormatException e) {
//            throw new RuntimeException(e);
//        }
//        }
    }

    private NdefMessage createNdefMessage(String message) {
        NdefRecord ndefRecord = NdefRecord.createTextRecord("en", message);
        return new NdefMessage(ndefRecord);
    }

    private void writeDataToNFCTag(NdefMessage ndefMessage, Intent intent) throws IOException, FormatException {
        Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

//        NdefFormatable formatable = NdefFormatable.get(detectedTag);
//        if(formatable != null) {
//            try {
//                formatable.connect();
//                formatable.format(ndefMessage);
//                Log.d(TAG, "NFC TAG has been formatted.");
//            } catch (IOException | FormatException e) {
//                // Handle exceptions
//            } finally {
//                try {
//                    formatable.close();
//                } catch (IOException e) {
//                    // Handle exception
//                }
//            }
//        }

//        NdefFormatable ndefFormatable = NdefFormatable.get(detectedTag);
//        if (ndefFormatable != null) {
//            ndefFormatable.connect();
//            ndefFormatable.format(ndefMessage);
//            ndefFormatable.close();
//
//            Log.d("NFC", "Tag formatted as NDEF successfully");
//        }


//        if(MifareUltralightTagTester.writeTag(detectedTag, ndefMessage.toString())){
//            Toast.makeText(this, "Data written to NFC tag.", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Failed to write data on NFC tag", Toast.LENGTH_SHORT).show();
//        }

        // This portion of code here is use to write data on NFC TAG...
        if (detectedTag != null) {
            Ndef ndef = Ndef.get(detectedTag);
            if (ndef != null) {
                try {
                    ndef.connect();
                    ndef.writeNdefMessage(ndefMessage);
                    Toast.makeText(this, "NFC Tag written successfully!", Toast.LENGTH_SHORT).show();
                } catch (IOException | FormatException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        ndef.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Toast.makeText(this, "NFC Tag does not support NDEF", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        Ndef mNdef = Ndef.get(tag);

        // Check that it is an Ndef capable card
        if (mNdef == null) {
            return;
        }

        // Create a Ndef Record
        NdefRecord mRecord = NdefRecord.createTextRecord("en","English String");

        // Add to a NdefMessage
        NdefMessage mMsg = new NdefMessage(mRecord);

        // Catch errors
        try {
            mNdef.connect();
            mNdef.writeNdefMessage(mMsg);

            // Success if got to here
            runOnUiThread(() -> {
                Toast.makeText(getApplicationContext(),
                        "Write to NFC Success",
                        Toast.LENGTH_SHORT).show();
            });

            // Make a Sound
            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),
                        notification);
                r.play();
            } catch (Exception e) {
                // Some error playing sound
            }

        } catch (FormatException e) {
            // if the NDEF Message to write is malformed
        } catch (TagLostException e) {
            // Tag went out of range before operations were complete
        } catch (IOException e){
            // if there is an I/O failure, or the operation is cancelled
        } catch (SecurityException e){
            // The SecurityException is only for Android 12L and above
            // The Tag object might have gone stale by the time
            // the code gets to process it, with a new one been
            // delivered (for the same or different Tag)
            // The SecurityException is thrown if you are working on
            // a stale Tag
        } finally {
            // Be nice and try and close the tag to
            // Disable I/O operations to the tag from this TagTechnology object, and release resources.
            try {
                mNdef.close();
            } catch (IOException e) {
                // if there is an I/O failure, or the operation is cancelled
            }
        }
    }

//    private void enableNfcForegroundDispatch() {
//        if (!nfcAdapter.isEnabled()) {
//            Toast.makeText(this, "Please enable NFC in your settings", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
//
//        if (nfcAdapter != null) {
//
//            nfcAdapter.enableForegroundDispatch(this, pendingIntent, new IntentFilter[] {intentFilter}, null);

//            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
//        }
//    }

//    private void disableNfcForegroundDispatch() {
//        if (nfcAdapter != null) {
//            nfcAdapter.disableForegroundDispatch(this);
//        }
//    }
}