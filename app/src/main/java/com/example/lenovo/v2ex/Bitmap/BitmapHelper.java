package com.example.lenovo.v2ex.Bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.example.lenovo.v2ex.Global.V2EX;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by lenovo on 2016/7/23.
 */
public class BitmapHelper {

    public static String  output(Bitmap bitmap){
        String name = Calendar.getInstance().getTimeInMillis() + ".jpg";
        return output(bitmap,name);
    }

    public static String output(Bitmap bitmap,String name){
        Log.d("holo","output" + name);
        FileOutputStream out = null;
        BufferedOutputStream bufferedOutputStream;
        try{
            Log.d("holo","output" + name);
            File outputImage = new File(Environment.getExternalStorageDirectory()+"/v2ex/" + name);
            Log.d("pic", "保存的地址" + outputImage.getAbsolutePath());

            out = new FileOutputStream(outputImage);
            bufferedOutputStream = new BufferedOutputStream(out);
            if(bitmap.getByteCount() < 1024 * 1024){
                bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG,100,bufferedOutputStream);
            }else{
                bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG,70,bufferedOutputStream);
            }
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return name;
    }

    public static Bitmap get(String name){
        Log.d("holo", "from the bitmapHelper get method");
        String path =Environment.getExternalStorageDirectory() + "/v2ex/" + name;
        Log.d("holo", "from the bitmapHelper get method");
        Log.d("pic", "读取的地址" + path);
        return BitmapFactory.decodeFile(path);
    }

    public static void delete(String picName){
        File file = new File(Environment.getExternalStorageDirectory() + "/v2ex/" + picName);
        if(file.exists()){
            file.delete();
        }
    }

    public static Bitmap getByUrl(String url){
        Log.d("holo", url);
        Bitmap bitmap = null;
        try{
            URL bitmapURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) bitmapURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5*1000);
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len = inputStream.read(buffer)) != -1){
                outputStream.write(buffer,0,len);
            }
            inputStream.close();
            byte[] btImg = outputStream.toByteArray();
            outputStream.close();
            bitmap = BitmapFactory.decodeByteArray(btImg,0,btImg.length);
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("holo", "return bitmap" + bitmap.toString());
      return bitmap;
    }
}
