package org.rmj.guanzongroup.gsecurity.ui.activity;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.WRITE_NFC_DATA_PAYLOAD;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.rmj.guanzongroup.gsecurity.databinding.ActivityWriteNfcBinding;
import org.rmj.guanzongroup.gsecurity.ui.screens.settings.places.FragmentPlaces;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class WriteNfcActivity extends AppCompatActivity implements NfcAdapter.ReaderCallback {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter intentFilter;
    private String[][] techListsArray;

    private Tag nfcTag;

    private String record1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        org.rmj.guanzongroup.gsecurity.databinding.ActivityWriteNfcBinding binding = ActivityWriteNfcBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(getIntent().hasExtra(WRITE_NFC_DATA_PAYLOAD))
            record1 = getIntent().getStringExtra(WRITE_NFC_DATA_PAYLOAD);

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
            Timber.tag(FragmentPlaces.class.getSimpleName()).d("NFC is not supported on this device");
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

        Timber.tag("WriteNfcActivity").d("NFC Tag ID: %s", Arrays.toString(tag.getId()));

        // Catch errors
        try {

            // Create a Ndef Record
            NdefRecord mRecord = createRecord(record1);
            Timber.tag("WriteNfcActivity").d("Nfc record to print: %s", record1);

            // Add to a NdefMessage
            NdefMessage mMsg = new NdefMessage(mRecord);

            mNdef.connect();
            mNdef.writeNdefMessage(mMsg);
            Timber.tag("WriteNfcActivity").d("Nfc record has been written");

            // Success if got to here
            runOnUiThread(() -> {
                setResult(RESULT_OK);
                finish();
            });

            // Make a Sound
            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),
                        notification);
                r.play();
                Timber.tag("WriteNfcActivity").d("Notification tone played.");

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
                Timber.tag("WriteNfcActivity").d("Ndef is closed.");
            } catch (IOException e) {
                // if there is an I/O failure, or the operation is cancelled
                e.printStackTrace();
            }
        }
    }

    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes(StandardCharsets.US_ASCII);
        int langLength = langBytes.length;
        int textLength = textBytes.length;
        byte[] payload = new byte[1 + langLength + textLength];

        // set status byte (see NDEF spec for actual bits)
        payload[0] = (byte) langLength;

        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);
        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);
    }

    private void write(String text, Tag tag) throws IOException, FormatException {
        NdefRecord[] records = new NdefRecord[]{createRecord(text)};
        NdefMessage message = new NdefMessage(records);
        // Get an instance of Ndef for the tag.
        Ndef ndef = Ndef.get(tag);
        // Enable I/O
        ndef.connect();
        // Write the message
        ndef.writeNdefMessage(message);
        // Close the connection
        ndef.close();
    }

}