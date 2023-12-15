package org.rmj.guanzongroup.gsecurity.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcF;
import android.os.Bundle;

import org.rmj.guanzongroup.gsecurity.databinding.ActivityReadNfcBinding;
import org.rmj.guanzongroup.gsecurity.ui.screens.settings.places.FragmentPlaces;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class ReadNfcActivity extends AppCompatActivity implements NfcAdapter.ReaderCallback {

    private ActivityReadNfcBinding binding;

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter intentFilter;
    private String[][] techListsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReadNfcBinding.inflate(getLayoutInflater());
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
        Ndef ndef = Ndef.get(tag);

        if(ndef == null) {
            return;
        }

        setResult(RESULT_OK);
        finish();
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
//                Log.d("ReadNfcActivity", record.toString());
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
//        }e
    }
}