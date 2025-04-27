package com.example.mobileprogramming_termproject;

import static com.example.mobileprogramming_termproject.MainActivity.baseUrl;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.bumptech.glide.Glide;
import com.example.mobileprogramming_termproject.data.NaverSearchResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarcodeActicity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PERMISSION = 2;

    private Uri photoUri;
    private String barcode ="";

    Button btnCamera,btnSend;
    ImageView imageView, productImage;
    TextView barcodeText, productName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        btnCamera = findViewById(R.id.btnCamera);
        btnSend = findViewById(R.id.btnSend);
        imageView = findViewById(R.id.imageCamera);
        barcodeText = findViewById(R.id.productBarcode);
        productName = findViewById(R.id.productName);
        productImage = findViewById(R.id.productImage);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photoFile = new File(getExternalFilesDir(null), "image.png");
                photoUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".fileprovider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendImageToServer(photoUri);
            }
        });

        checkPermission();
    }

    private Bitmap rotateBitmap90(Bitmap source) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_IMAGE_CAPTURE){
                String path = new File(getExternalFilesDir(null), "image.png").getAbsolutePath();
                Bitmap bitmap = getResizedBitmap(path, 800, 800); // 원하는 크기 (800x800 예시)
                if (bitmap != null) {
                    imageView.setImageBitmap(rotateBitmap90(bitmap));
                } else {
                    Toast.makeText(this, "이미지를 오류", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }
    }

    private Bitmap getResizedBitmap(String filePath, int targetW, int targetH) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // 얼마나 줄일지 계산
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeFile(filePath, bmOptions);
    }


    public void sendImageToServer(Uri imageUri) {
        String filePath = new File(getExternalFilesDir(null), "image.png").getAbsolutePath();
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        BarcodeApi api = RetrofitClient.getClient(baseUrl)
                .create(BarcodeApi.class);

        Call<ResponseBody> call = api.uploadImage(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();

                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray barcodesArray = jsonObject.getJSONArray("barcodes");
                        if (barcodesArray.length() > 0) {
                            barcode = barcodesArray.getString(0);
                        } else {
                            Toast.makeText(BarcodeActicity.this, "바코드 인식 실패 또는 없음", Toast.LENGTH_SHORT).show();
                            barcode = "00000000";
                        }
                        barcodeText.setText(barcode);
                        Log.d("Barcode Reuslt", barcode);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("Barcode Error", "request failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.e("Barcode Error", "request failed: " + t.getMessage());
            }
        });
        searchNaver(); //API 테스ㅇ트용
    }

    public void searchNaver(){
        //NAVER API 테스트용
        Log.d("Barcode","search Naver Start");
        RetrofitClient.getApiService()
                .searchProduct("오뚜기진라면매운맛", 1)
                .enqueue(new retrofit2.Callback<NaverSearchResponse>() {
                    @Override
                    public void onResponse(Call<NaverSearchResponse> call, retrofit2.Response<NaverSearchResponse> response) {
                        Log.d("Response code", String.valueOf(response.code()));
                        if (response.isSuccessful() && response.body() != null) {
                            NaverSearchResponse.Item item = response.body().items.get(0);
                            String title = item.title;
                            String imageUrl = item.image;

                            productName.setText(title);
                            Glide.with(getApplicationContext())
                                    .load(imageUrl)
                                    .into(productImage);
                        }else {
                            Log.e("Response Error", "Error: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<NaverSearchResponse> call, Throwable t) {
                        t.printStackTrace();
                        Log.d("barcode search", t.getMessage());
                    }
                });
        Log.d("Barcode","search Naver End");
    }

}
