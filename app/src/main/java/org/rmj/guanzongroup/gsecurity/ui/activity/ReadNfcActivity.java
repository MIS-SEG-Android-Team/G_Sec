package org.rmj.guanzongroup.gsecurity.ui.activity;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.READ_NFC_DATA_PAYLOAD;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;

import org.rmj.guanzongroup.gsecurity.databinding.ActivityReadNfcBinding;
import org.rmj.guanzongroup.gsecurity.ui.screens.settings.places.FragmentPlaces;

import java.nio.charset.StandardCharsets;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class ReadNfcActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter intentFilter;
    private String[][] techListsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityReadNfcBinding binding = ActivityReadNfcBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(nfcAdapter == null){
            Timber.tag(FragmentPlaces.class.getSimpleName()).d("NFC is not supported on this device");
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

        binding.cancelButton.setOnClickListener(view -> {
            finish();
            setResult(RESULT_CANCELED);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(nfcAdapter!= null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, new IntentFilter[]{intentFilter}, techListsArray);
            Bundle options = new Bundle();
            options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 250);
//            nfcAdapter.enableReaderMode(this,
//                    this,
//                    NfcAdapter.FLAG_READER_NFC_A |
//                            NfcAdapter.FLAG_READER_NFC_B |
//                            NfcAdapter.FLAG_READER_NFC_F |
//                            NfcAdapter.FLAG_READER_NFC_V |
//                            NfcAdapter.FLAG_READER_NFC_BARCODE |
//                            NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS,
//                    options);
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

//    @Override
//    public void onTagDiscovered(Tag tag) {
//        Ndef ndef = Ndef.get(tag);
//
//        if(ndef == null) {
//            return;
//        }
//
//        try {
//            ndef.connect();
//            NdefMessage ndefMessage = ndef.getNdefMessage();
//
//            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(),
//                    notification);
//            ringtone.play();
//
//            NdefRecord[] ndefRecords = ndefMessage.getRecords();
//            for (NdefRecord record: ndefRecords) {
//                Timber.tag("ReadNfcActivity").d(record.);
//                if (record.getTnf() == NdefRecord.TNF_WELL_KNOWN) {
//                    if (record.getType() == NdefRecord.RTD_TEXT) {
//                        // Read text payload
//
////                        Toast.makeText(this, "NFC Tag Text: " + text, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        } catch (FormatException | IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleNfcIntent(intent);
    }

    private void handleNfcIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            NdefMessage[] messages = getNdefMessages(intent);
            if (messages != null) {
                processNdefMessages(messages);
            }
        }
    }

    private NdefMessage[] getNdefMessages(Intent intent) {
        NdefMessage[] messages = null;
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages =
                    intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMessages != null) {
                messages = new NdefMessage[rawMessages.length];
                for (int i = 0; i < rawMessages.length; i++) {
                    messages[i] = (NdefMessage) rawMessages[i];
                }
            }
        }
        return messages;
    }

    private void processNdefMessages(NdefMessage[] messages) {
        for (NdefMessage message : messages) {
            for (NdefRecord record : message.getRecords()) {
                byte[] payload = record.getPayload();
                // Assuming the payload contains text data
                String textData = new String(payload, StandardCharsets.UTF_8);
                Timber.tag("NFC").d("Read NFC data: %s", textData);
                Intent intentResult = new Intent();
                intentResult.putExtra(READ_NFC_DATA_PAYLOAD, textData);
                setResult(RESULT_OK, intentResult);
                finish();
            }
        }
    }
}