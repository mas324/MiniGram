package com.example.maste.minigram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.take_pic)
    Button snap_btn;
    @BindView(R.id.imageView)
    ImageView picture;
    @BindView(R.id.desc)
    EditText description;
    @BindView(R.id.submit)
    Button upload_btn;
    private File photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photo == null) {
                    Toast.makeText(MainActivity.this, "Must take a photo", Toast.LENGTH_SHORT).show();
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
                File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "MiniGram");
                if (!file.exists() && !file.mkdirs()) {
                    Log.e("GramPhoto", "Failed to create directory");
                }
                photo = new File(file.getPath() + File.separator + "pic.jpg");
                Uri uri = FileProvider.getUriForFile(MainActivity.this, "com.example.maste.minigram", photo);
                i.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                if (i.resolveActivity(getPackageManager()) != null)
                    startActivityForResult(i, 1001);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            ParseUser.logOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            Bitmap new_photo = BitmapFactory.decodeFile(photo.getAbsolutePath());
            picture.setImageBitmap(new_photo);
        } else {
            Toast.makeText(this, "Picture wasn't saved", Toast.LENGTH_SHORT).show();
        }
    }
}
