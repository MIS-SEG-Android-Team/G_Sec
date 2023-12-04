package org.rmj.guanzongroup.gsecurity.ui.activity;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.WRITE_NFC_PAYLOAD;

import androidx.appcompat.app.AppCompatActivity;

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
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.util.Log;

import org.rmj.guanzongroup.gsecurity.databinding.ActivityWriteNfcBinding;
import org.rmj.guanzongroup.gsecurity.ui.screens.places.FragmentPlaces;

import java.io.IOException;

public class WriteNfcActivity extends AppCompatActivity implements NfcAdapter.ReaderCallback {

    private ActivityWriteNfcBinding binding;

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter intentFilter;
    private String[][] techListsArray;

    private String record1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWriteNfcBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(getIntent().hasExtra(WRITE_NFC_PAYLOAD))
            record1 = getIntent().getStringExtra(WRITE_NFC_PAYLOAD);

        initNfcAdapter();

        binding.cancelButton.setOnClickListener(view -> {
            finish();
            setResult(RESULT_CANCELED);
        });
    }

    private void initNfcAdapter(){
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        // Validate if device has NFC Tag
        if(nfcAdapter == null){
            Log.d(FragmentPlaces.class.getSimpleName(), "NFC is not supported on this device");
        }

        // Initialization of actions upon tapping the NFC Tag on the device...
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, new IntentFilter[]{intentFilter}, techListsArray);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(nfcAdapter!= null) {
            nfcAdapter.disableReaderMode(this);
            nfcAdapter.disableForegroundDispatch(this);
        }
    }


    @Override
    public void onTagDiscovered(Tag tag) {
        Ndef mNdef = Ndef.get(tag);

        // Check that it is an Ndef capable card
        if (mNdef == null) {
            return;
        }

//        Log.d("WriteNfcActivity", record1);

        // Create a Ndef Record
        NdefRecord mRecord = NdefRecord.createTextRecord("en", record1);

        // Add to a NdefMessage
        NdefMessage mMsg = new NdefMessage(mRecord);

        // Catch errors
        try {
            mNdef.connect();
            mNdef.writeNdefMessage(mMsg);
            Log.d("WriteNfcActivity", "Nfc record has been written");

            // Success if got to here
            runOnUiThread(() -> {
//                Toast.makeText(getApplicationContext(),
//                        "NFC Write Successful",
//                        Toast.LENGTH_SHORT).show();
//                setResult(RESULT_OK);
//                finish();
            });

            // Make a Sound
            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),
                        notification);
                r.play();
                Log.d("WriteNfcActivity", "Notification tone played.");

            } catch (Exception e) {
                // Some error playing sound
            }

        } catch (FormatException e) {
            // if the NDEF Message to write is malformed
            e.printStackTrace();
        } catch (TagLostException e) {
            // Tag went out of range before operations were complete
            e.printStackTrace();
        } catch (IOException e){
            // if there is an I/O failure, or the operation is cancelled
            e.printStackTrace();
        } catch (SecurityException e){
            // The SecurityException is only for Android 12L and above
            // The Tag object might have gone stale by the time
            // the code gets to process it, with a new one been
            // delivered (for the same or different Tag)
            // The SecurityException is thrown if you are working on
            // a stale Tag
            e.printStackTrace();
        } finally {
            // Be nice and try and close the tag to
            // Disable I/O operations to the tag from this TagTechnology object, and release resources.
            try {
                mNdef.close();
                Log.d("WriteNfcActivity", "Ndef is closed.");
            } catch (IOException e) {
                // if there is an I/O failure, or the operation is cancelled
                e.printStackTrace();
            }
        }

        finish();
        Log.d("WriteNfcActivity", "Finish activity.");
        setResult(RESULT_OK);
        Log.d("WriteNfcActivity", "Writing nfc finished.");
    }
}