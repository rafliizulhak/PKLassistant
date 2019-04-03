package com.example.user.pklassistant;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.example.user.pklassistant.SessionManager.namauser;

public class pesan_admin extends AppCompatActivity {
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;

    public static final int RC_SIGN_IN = 1;
    private static final int RC_PHOTO_PICKER = 2;

    private ListView mMessageListView;
    private pesan_adapter_siswa mMessageAdapter;
    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton, mSendButton;
    private EditText mMessageEditText;

    /*private String mUsername;*/

    private TextView chat_conversation;
    private String user_name ,room_name;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mChatPhotosStorageReference;
    SessionManager sessionManager;
    private Context context;
    private pesan_adapter_admin.FirebaseDataListener Listener;
    private String temp_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_admin);

        sessionManager = new SessionManager(getApplicationContext());

        /*mUsername = sessionManager.pref.getString(namauser, "");*/

        user_name = getIntent().getExtras().get("user_name").toString();
        room_name = getIntent().getExtras().get("room_name").toString();

        setTitle(room_name);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        chat_conversation = (TextView)findViewById(R.id.textView);

        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("messages").child(room_name);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        /*mMessageListView = (ListView) findViewById(R.id.messageListView);*/
        mPhotoPickerButton = (ImageButton) findViewById(R.id.imv_insert);
        mMessageEditText = (EditText) findViewById(R.id.et_pesan);
        mSendButton = (ImageButton) findViewById(R.id.imv_pesan);

        /*final List<item_pesan_siswa> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new pesan_adapter_siswa(this, R.layout.item_message, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);*/

        /*mMessageListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final Dialog dialog = new Dialog(pesan_admin.this);
                dialog.setContentView(R.layout.dialog_view);
                dialog.setTitle("Pilih Aksi");
                dialog.show();

                Button delButton = (Button) dialog.findViewById(R.id.bt_delete_data);

                final int position = i;
                delButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        friendlyMessages.remove(position);

                    }
                });
                return true;
            }
        });*/

        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });

        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*// TODO: Send messages on click
                item_pesan_siswa friendlyMessage = new item_pesan_siswa(mMessageEditText.getText().toString(), mUsername, null);
                mMessagesDatabaseReference.push().setValue(friendlyMessage);
                // Clear input box
                mMessageEditText.setText("");*/

                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = mMessagesDatabaseReference.push().getKey();
                mMessagesDatabaseReference.updateChildren(map);

                DatabaseReference message_root = mMessagesDatabaseReference.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("msg",mMessageEditText.getText().toString());

                message_root.updateChildren(map2);

            }
        });

       /* mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                item_pesan_siswa friendlyMessage = dataSnapshot.getValue(item_pesan_siswa.class);
                mMessageAdapter.add(friendlyMessage);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mMessageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mMessagesDatabaseReference.addChildEventListener(mChildEventListener);*/

        mMessagesDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String chat_msg, chat_user_name;

    private void append_chat_conversation(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext())
        {
            chat_msg = (String) ((DataSnapshot)i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot)i.next()).getValue();

            chat_conversation.append(chat_user_name + " : "+chat_msg +"\n");


        }
    }



    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Signed In!", Toast.LENGTH_SHORT).show();
            }else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Signed In Canceled", Toast.LENGTH_SHORT).show();
                finish();
            }else if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK){
                Uri selectedImgUri = data.getData();
                StorageReference photoRef = mChatPhotosStorageReference.child(selectedImgUri.getLastPathSegment());

                photoRef.putFile(selectedImgUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        item_pesan_siswa friendlyMessage = new item_pesan_siswa(null, mUsername, downloadUrl.toString());
                        mMessagesDatabaseReference.push().setValue(friendlyMessage);
                    }
                });
            }
        }
    }*/
}
