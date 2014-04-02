package ntq.lbs;

import java.util.List;

import ntq.lbs.emoji.Emoji;
import ntq.lbs.preferences.EmojiPrefrence;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.widget.TextView;

public class EmojiTextView extends TextView{
	private EmojiGetter mEmojiGetter = new EmojiGetter();
	private String rawText = "";
	private List<Emoji> mEmojiList;
	
	public EmojiTextView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		initView();
	}
	
	public EmojiTextView(Context context, AttributeSet attrs){
		super(context, attrs);
		initView();
	}
	
	public EmojiTextView(Context context){
		super(context);
		initView();
	}
	
	private void initView(){
		setCompoundDrawablePadding(5);
	}
	
	public void setEmojiText(String text){
		setEmojiText(text, false);
	}
	
	public void setEmojiText(String text, boolean isMutiline){
		if(rawText.length() > 0 && rawText.equals(text)) return;
		rawText = text;
		if(isMutiline){
			text = text.replace("\n", "<br/>");
		}
		text = parseMessage(text);
		setText(Html.fromHtml(text, mEmojiGetter, null));
		Linkify.addLinks(this, Linkify.WEB_URLS);
	}
	
	private String parseMessage(String text){
		if(text == null || text.length() < 1) return text;
		if(mEmojiList == null || mEmojiList.size() == 0){
			mEmojiList = EmojiPrefrence.getInstance().getListEmoji();
		}
		for(Emoji emoji : mEmojiList){
			StringBuilder buildKey = new StringBuilder();
			buildKey.append(" ").append(emoji.getKey()).append(" ");
			StringBuilder buildValue = new StringBuilder();
			buildValue.append(" <img src=\"").append(emoji.getValue())
					.append("\"/> ");
			text = text.replace(buildKey.toString(), buildValue.toString());
		}
		return text.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll("&lt;img", "<img").replaceAll("  ", " ").trim();
	}
	
	private class EmojiGetter implements ImageGetter{
		private final String RESOURCE_TYPE = "drawable";
		private final float PERCENT_MEASURE = 0.8f;
		
		@Override
		public Drawable getDrawable(String source){
			String packageName = getContext().getPackageName();
			Resources resources = getResources();
			int id = resources
					.getIdentifier(source, RESOURCE_TYPE, packageName);
			Drawable emoji = resources.getDrawable(id);
			int drawableWidth = emoji.getIntrinsicWidth();
			int drawableHeight = emoji.getIntrinsicHeight();
			// Get new measure to display. Its make distant with another.
			int w = (int) (drawableWidth * PERCENT_MEASURE);
			int h = (int) (drawableHeight * PERCENT_MEASURE);
			emoji.setBounds(0, 0, w, h);
			return emoji;
		}
	}
}