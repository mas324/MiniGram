package com.example.maste.minigram.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.maste.minigram.BitmapScaler;
import com.example.maste.minigram.Post;
import com.example.maste.minigram.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class ComposeFragment extends Fragment {

    @BindView(R.id.take_pic)
    Button snap_btn;
    @BindView(R.id.imageView)
    ImageView picture;
    @BindView(R.id.desc)
    EditText description;
    @BindView(R.id.submit)
    Button upload_btn;
    private File photo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ButterKnife.bind(this, view);

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photo == null) {
                    Toast.makeText(getContext(), "Must take a photo", Toast.LENGTH_SHORT).show();
                }
                Post post = new Post(description.getText().toString(), new ParseFile(photo), ParseUser.getCurrentUser());
                post.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.d("GramPosted", "Successfully posted");
                            description.setText("");
                            picture.setImageBitmap(null);
                        } else {
                            Log.e("GramPosted", "The gram didn't post");
                            Log.e("GramPosted", e.getLocalizedMessage());
                        }
                    }
                });
            }
        });

        snap_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                photo = getPhotoFileUri("pic.jpg");
                Uri uri = FileProvider.getUriForFile(getContext(), "com.example.maste.minigram", photo);
                i.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                if (i.resolveActivity(getContext().getPackageManager()) != null)
                    startActivityForResult(i, 1001);
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            Bitmap new_photo = BitmapFactory.decodeFile(photo.getAbsolutePath());
            Bitmap rescaled = BitmapScaler.scaleToFitWidth(new_photo, 300);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            rescaled.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
            try {
                File new_file = getPhotoFileUri("pic_resized");
                if (new_file.createNewFile()) {
                    FileOutputStream out = new FileOutputStream(new_file);
                    out.write(bytes.toByteArray());
                    out.close();
                }
            } catch (IOException e) {
                Log.e("GramError", e.getLocalizedMessage());
            }

            picture.setImageBitmap(new_photo);
        } else {
            Toast.makeText(getContext(), "Picture wasn't saved", Toast.LENGTH_SHORT).show();
        }
    }

    private File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "MiniGram");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d("GramPhoto", "failed to create directory");
        }

        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }
}
