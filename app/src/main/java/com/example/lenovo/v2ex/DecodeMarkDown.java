package com.example.lenovo.v2ex;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.example.lenovo.v2ex.Bitmap.BitmapHelper;
import com.example.lenovo.v2ex.Global.V2EX;
import com.squareup.picasso.Picasso;
import com.zzhoujay.richtext.RichText;

import org.markdown4j.Markdown4jProcessor;

/**
 * Created by lenovo on 2016/7/28.
 */
public class DecodeMarkDown {
    private static Handler handler;
    private static Spanned spanned;
    private static String html;
    private static Html.ImageGetter getter;

    public static void decode(final TextView textView, String content, final int width){

        Log.d("holo","content" + content);


        try{
            html = new Markdown4jProcessor().process(content);
        }catch (Exception e){
            e.printStackTrace();
        }
        //html = content;

//        TextView test = new TextView(V2EX.getInstance().getApplicationContext());
//        RichText.fromMarkdown(content).into(test);
//        html = test.getText().toString();

        handler = new Handler(){
            @Override
            public void handleMessage(Message message){
                spanned = Html.fromHtml(html,getter,null);
               // RichText.fromHtml(spanned.).into();
                spanned = trimTrailingWhitespace((SpannableStringBuilder) spanned);
                textView.setText(spanned);
            }
        };


        getter = new Html.ImageGetter(){
            @Override
            public Drawable getDrawable(final String source){
//                Bitmap bitmap = null;
//                try{
//                    Log.d("bitmap", "begin");
//                    bitmap= Picasso.with(V2EX.getInstance().getApplicationContext()).load(source).get();
//                    Log.d("bitmap",  bitmap.toString());
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
//                bitmapDrawable.setBounds(0,0,width,width * 3 / 5);
//                 return bitmapDrawable;
                Bitmap contentBitmap = BitmapHelper.get(source.hashCode() + "");
                Log.d("holo", "source" + source);
                if(contentBitmap == null){
                    loadImage(source);
                    return null;
                }else{
                    Log.d("holo", "width:" + contentBitmap.getWidth() + "height" + contentBitmap.getHeight());
                    Drawable drawable = new BitmapDrawable(contentBitmap);
                    drawable.setBounds(0, 0, contentBitmap.getWidth(),contentBitmap.getHeight());
                    return drawable;
                }
            }
        };
        spanned = Html.fromHtml(html,getter,null);
        spanned = trimTrailingWhitespace((SpannableStringBuilder) spanned);
        textView.setText(spanned);




    }

    private static SpannableStringBuilder trimTrailingWhitespace(
            SpannableStringBuilder spannableString) {

        if (spannableString == null)
            return new SpannableStringBuilder("");

        int i = spannableString.length();

        // loop back to the first non-whitespace character
        while (--i >= 0 && Character.isWhitespace(spannableString.charAt(i))) {
        }

        return new SpannableStringBuilder(spannableString.subSequence(0, i + 1));
    }

    private static void loadImage(final String source){
        new Thread(new Runnable() {
            @Override
            public void run() {
//                Bitmap bitmap = null;
//                try{
//                    bitmap = Picasso.with(V2EX.getInstance().getApplicationContext()).load(source).get();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
                Bitmap bitmap = BitmapHelper.getByUrl(source);
                Log.d("holo", (bitmap == null) + "555");
                BitmapHelper.output(bitmap,source.hashCode() + "");
                handler.sendEmptyMessage(1);
            }
        }).start();


//        Bitmap bitmap = null;
//        try{
//            bitmap = Picasso.with(V2EX.getInstance().getApplicationContext()).load(source).get();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        //Bitmap bitmap = BitmapHelper.getByUrl(source);
//        Log.d("holo", (bitmap == null) + "555");
//        BitmapHelper.output(bitmap,source.hashCode() + "");
//        handler.sendEmptyMessage(1);
    }
}
