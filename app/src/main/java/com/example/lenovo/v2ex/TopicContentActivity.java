package com.example.lenovo.v2ex;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.v2ex.Bitmap.BitmapHelper;
import com.example.lenovo.v2ex.Interface.Update;
import com.example.lenovo.v2ex.ItemClasses.ReplyItem;
import com.example.lenovo.v2ex.ItemClasses.TopicItem;
import com.example.lenovo.v2ex.net.GetTopicContent;

import org.markdown4j.Markdown4jProcessor;

/**
 * Created by lenovo on 2016/7/22.
 */
public class TopicContentActivity extends AppCompatActivity implements Update {
    RecyclerView replyList;
    ImageView bitmap;
    TextView username;
    TextView time;
    TextView replyNumber;
    TextView nodeName;
    TextView title;
    TextView content;
    LinearLayout layout;
    Spanned spanned = null;
    Handler handler = null;
    String html = null;
    Html.ImageGetter getter;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_content);

        bitmap = (ImageView)findViewById(R.id.avatar);
        username =(TextView) findViewById(R.id.username);
        time = (TextView)findViewById(R.id.time);
        replyNumber = (TextView)findViewById(R.id.reply_number);
        nodeName = (TextView)findViewById(R.id.node_name);
        title = (TextView)findViewById(R.id.title);
        content = (TextView)findViewById(R.id.content);
        layout = (LinearLayout) findViewById(R.id.replyLayout);

        final TopicItem topicItem = getIntent().getParcelableExtra("topicItem");

        username.setText(topicItem.getUsername());
        time.setText(topicItem.getCreated().toString());
        replyNumber.setText(topicItem.getReplies().toString() + "个回复");
        nodeName.setText(topicItem.getNodeName());
        title.setText(topicItem.getTitle());

        bitmap.setImageBitmap(topicItem.getImage());

        try{
            html = new Markdown4jProcessor().process(topicItem.getContent());
        }catch (Exception e){
            e.printStackTrace();
        }

         handler = new Handler(){
            @Override
            public void handleMessage(Message message){
                spanned = Html.fromHtml(html,getter,null);
                spanned = trimTrailingWhitespace((SpannableStringBuilder) spanned);
                content.setText(spanned);
            }
        };

        getter = new Html.ImageGetter(){
            @Override
            public Drawable getDrawable(final String source){
                Bitmap contentBitmap = BitmapHelper.get(source);
                Log.d("holo", (contentBitmap == null) + "11111");
                if(contentBitmap == null){
                   loadImage(source);
                    return null;
                }else{
                    Drawable drawable = new BitmapDrawable(contentBitmap);
                    drawable.setBounds(0, 0,getResources().getDisplayMetrics().widthPixels, drawable
                            .getIntrinsicHeight()*8);
                    Log.d("holo", "drawable" + drawable.toString());
                //    TopicContentActivity.this.bitmap.setImageDrawable(drawable);
                    return drawable;
                }
            }
        };
        spanned = Html.fromHtml(html,getter,null);
        spanned = trimTrailingWhitespace((SpannableStringBuilder) spanned);
        content.setText(spanned);

        GetTopicContent.GetContent(topicItem.getUrl(),this);
    }

    private void loadImage(final String source){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapHelper.getByUrl(source);
                Log.d("holo", (bitmap == null) + "555");
                BitmapHelper.output(bitmap,source);
                handler.sendEmptyMessage(1);
            }
        }).start();
    }

    @Override
    public void update(Object it){
        ReplyItem item = (ReplyItem) it;
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(TopicContentActivity.this)
                .inflate(R.layout.reply, null);
        ImageView avatar = (ImageView) linearLayout.findViewById(R.id.avatar);
        TextView username = (TextView) linearLayout.findViewById(R.id.username);
        TextView time = (TextView) linearLayout.findViewById(R.id.time);
        TextView reply = (TextView) linearLayout.findViewById(R.id.reply);
        TextView floor = (TextView) linearLayout.findViewById(R.id.floor);
        avatar.setImageBitmap(item.getAvatar());
        username.setText(item.getUsername());
        time.setText(item.getTime());
        reply.setText(item.getContent());
        floor.setText(item.getFloor());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.addView(linearLayout, layoutParams);
    }

    private SpannableStringBuilder trimTrailingWhitespace(
            SpannableStringBuilder spannableString) {

        if (spannableString == null)
            return new SpannableStringBuilder("");

        int i = spannableString.length();

        // loop back to the first non-whitespace character
        while (--i >= 0 && Character.isWhitespace(spannableString.charAt(i))) {
        }

        return new SpannableStringBuilder(spannableString.subSequence(0, i + 1));
    }
}
