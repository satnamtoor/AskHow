package satnam.valentinelove;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import java.io.InputStream;

/**
 * Created by ss22493 on 25-01-2017.
 */
public class FaceRecogination extends AppCompatActivity implements View.OnClickListener {
    public static final int GALLERY_PICTURE = 2;
    private static final int INITIAL_REQUEST = 1338;
    private static final int CAMERA_REQUEST = INITIAL_REQUEST + 2;
    private static final int GALLERY_REQUEST = INITIAL_REQUEST + 3;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static Intent pictureActionIntent = null;
    boolean isGalleryOpen;
    FaceDetector faceDetector;
    boolean isPartner = false;
    FloatingActionButton btnFloatNext;
    Bitmap mBitmap1, mBitmap;
    private ImageView your_img, part_img;
    private EditText editYourName, editPartnerName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_gender);
        initial();

    }

    private void initial() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        your_img = (ImageView) findViewById(R.id.your_img);
        your_img.setOnClickListener(this);
        part_img = (ImageView) findViewById(R.id.part_img);
        part_img.setOnClickListener(this);
        editYourName = (EditText) findViewById(R.id.editYourName);
        editPartnerName = (EditText) findViewById(R.id.editPartnerName);
        btnFloatNext = (FloatingActionButton) findViewById(R.id.btnFloatNext);
        btnFloatNext.setOnClickListener(this);
        faceDetector = new
                FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false)
                .build();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.your_img:

                isPartner = false;
                Image_Picker_Dialog();
                break;
            case R.id.part_img:
                isPartner = true;
                Image_Picker_Dialog();
                break;


            case R.id.btnFloatNext:
                if (mBitmap == null && mBitmap1 == null) {
                    Toast.makeText(FaceRecogination.this, "Your Image is empty", Toast.LENGTH_LONG);
                } else if (editYourName.getText().toString().equalsIgnoreCase("")) {

                    Toast.makeText(FaceRecogination.this, "Your Name is empty", Toast.LENGTH_LONG);
                } else if (editPartnerName.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(FaceRecogination.this, "Your Partner is empty", Toast.LENGTH_LONG);
                } else {
                    Intent mIntent = new Intent(FaceRecogination.this, Calculation.class);
                    mIntent.putExtra("YOUR_NAME", editYourName.getText().toString());
                    mIntent.putExtra("PARTNER_NAME", editPartnerName.getText().toString());
                    startActivity(mIntent);
                }
                break;

            default:
                break;
        }
    }

    public void Image_Picker_Dialog() {

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(FaceRecogination.this);
        myAlertDialog.setTitle("Pictures Option");
        myAlertDialog.setMessage("Select Picture Mode");
        myAlertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                isGalleryOpen = true;
                marshmallowGPSGalleryPremissionCheck();
            }
        });

        myAlertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                marshmallowGPSCameraPremissionCheck();
            }
        });
        myAlertDialog.show();

    }


    private void marshmallowGPSCameraPremissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && this.checkSelfPermission(
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && this.checkSelfPermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && this.checkSelfPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                            new String[]{Manifest.permission.CAMERA,
                                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            CAMERA_REQUEST);
                }
            }
            if (isGalleryOpen == true) {
                requestPermissions(
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_REQUEST);
                isGalleryOpen = false;
            }

        } else {

            Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(photoCaptureIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

        }
    }

    private void marshmallowGPSGalleryPremissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

                && this.checkSelfPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && this.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    GALLERY_REQUEST);


        } else {


            pictureActionIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
            pictureActionIntent.setType("image*//*");
            pictureActionIntent.putExtra("return-data", true);
            startActivityForResult(pictureActionIntent, GALLERY_PICTURE);

        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(photoCaptureIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
        if (requestCode == GALLERY_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            pictureActionIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
            pictureActionIntent.setType("image/*");
            pictureActionIntent.putExtra("return-data", true);
            startActivityForResult(pictureActionIntent, GALLERY_PICTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) // After the selection of image you will retun on the main activity with bitmap image
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            try {
                if (data != null) {
                    if (requestCode == GALLERY_PICTURE) {
                        try {
                            InputStream imInputStream = null;
                            try {
                                imInputStream = getContentResolver().openInputStream(data.getData());
                                mBitmap = BitmapFactory.decodeStream(imInputStream);
                                Frame frame = new Frame.Builder().setBitmap(mBitmap).build();
                                SparseArray<Face> faces = faceDetector.detect(frame);
                                if (faces.size() != 0) {
                                    for (int i = 0; i < faces.size(); i++) {
                                        Face thisFace = faces.valueAt(i);
                                        float x1 = thisFace.getPosition().x;
                                        float y1 = thisFace.getPosition().y;
                                        float x2 = x1 + thisFace.getWidth();
                                        float y2 = y1 + thisFace.getHeight();
                                        //tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 2, 2, myRectPaint);
                                        if (isPartner == true) {
                                            mBitmap1 = mBitmap;
                                            part_img.setImageBitmap(mBitmap);
                                        } else {
                                            your_img.setImageBitmap(mBitmap);
                                        }


                                    }
                                } else {
                                    Toast.makeText(FaceRecogination.this, "Face is not Detected", Toast.LENGTH_SHORT).show();

                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        } catch (Exception ex) {
                        }
                    } else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                        if (data != null) {
                            try {
                                mBitmap = (Bitmap) data.getExtras().get("data");
                                Frame frame = new Frame.Builder().setBitmap(mBitmap).build();
                                SparseArray<Face> faces = faceDetector.detect(frame);
                                if (faces.size() != 0) {
                                    for (int i = 0; i < faces.size(); i++) {
                                        Face thisFace = faces.valueAt(i);
                                        float x1 = thisFace.getPosition().x;
                                        float y1 = thisFace.getPosition().y;
                                        float x2 = x1 + thisFace.getWidth();
                                        float y2 = y1 + thisFace.getHeight();
                                        //tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 2, 2, myRectPaint);

                                        if (isPartner == true) {
                                            mBitmap1 = mBitmap;
                                            part_img.setImageBitmap(mBitmap);
                                        } else {
                                            your_img.setImageBitmap(mBitmap);
                                        }
                                    }
                                } else {
                                    Toast.makeText(FaceRecogination.this, "Face is not Detected", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception ex) {

                                ex.printStackTrace();
                            }
                        }
                    }
                }
            } catch (Exception ex) {

            }
        }
    }
}
