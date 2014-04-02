package ntq.example.demoemoji;

import java.util.ArrayList;
import java.util.List;

import ntq.lbs.EmojiTextView;
import ntq.lbs.emoji.Emoji;
import ntq.lbs.preferences.EmojiPrefrence;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.demoemoji.R;

public class EmojiActivity extends FragmentActivity implements OnClickListener{
	private View viewEmojiPanel;
	private PopupWindow popupWindow;
	private LinearLayout lnlMain;
	private EditText mEdtMain;
	private View mTxtAds;
	private EmojiTextView mTxtChat;
	
	private final int EMOJI_VERSION = 1;
	private int mKeyboardHeight = 0;
	
	private boolean isShowPanelNext = false;
	
	private OnEmojiItemClicked mOnEmojiItemClicked = new OnEmojiItemClicked(){
		private final char SPACE = ' ';
		
		@Override
		public void onClick(String code){
			String emojiText = mEdtMain.getText().toString();
			emojiText = emojiText + SPACE + code + SPACE;
			mEdtMain.setText(emojiText);
			mEdtMain.setSelection(emojiText.length());
		}
	};
	
	public interface OnEmojiItemClicked{
		public void onClick(String code);
	}
	
	@Override
	protected void onCreate(Bundle arg0){
		super.onCreate(arg0);
		setContentView(R.layout.ac_emoji);
		initEmojiPanel();
		initView();
		initEmojiData();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		lnlMain.getViewTreeObserver().addOnGlobalLayoutListener(
				mOnGlobalLayoutListener);
	}
	
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	protected void onPause(){
		super.onPause();
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
			lnlMain.getViewTreeObserver().removeOnGlobalLayoutListener(
					mOnGlobalLayoutListener);
		}else{
			lnlMain.getViewTreeObserver().removeGlobalOnLayoutListener(
					mOnGlobalLayoutListener);
		}
	}
	
	private void initView(){
		mEdtMain = (EditText) findViewById(R.id.bcd);
		mEdtMain.setOnClickListener(this);
		mEdtMain.setOnEditorActionListener(new OnEditorActionListener() {
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        boolean handled = false;
		        if (actionId == EditorInfo.IME_ACTION_SEND) {
		            sendMessage();
		            handled = true;
		        }
		        return handled;
		    }
		});
		lnlMain = (LinearLayout) findViewById(R.id.efg);
		mTxtAds = findViewById(R.id.ads);
		mTxtChat = (EmojiTextView) findViewById(R.id.emoji_text_view);
		TextView txtMain = (TextView) findViewById(R.id.abc);
		txtMain.setOnClickListener(this);
		
	}
	
	private void sendMessage(){
		String msg = mEdtMain.getText().toString();
		if(msg.length() == 0) return;
		mTxtChat.setEmojiText(msg);
		mEdtMain.setText("");
	}
	
	private void initEmojiPanel(){
		viewEmojiPanel = getLayoutInflater()
				.inflate(R.layout.panel_emoji, null);
		GridView gridEmoji = (GridView) viewEmojiPanel
				.findViewById(R.id.grid_emoji);
		TextView txtClose = (TextView) viewEmojiPanel.findViewById(R.id.close);
		txtClose.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				hidePanle();
			}
		});
		TextView txtSend = (TextView) viewEmojiPanel.findViewById(R.id.send);
		txtSend.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
	            sendMessage();
			}
		});
		mEmojiAdapter.setOnEmojiItemClicked(mOnEmojiItemClicked);
		List<Emoji> listEmoji = EmojiPrefrence.getInstance().getListEmoji();
		for(Emoji emoji : listEmoji){
			mEmojiAdapter.setEmoji(emoji.getKey(), emoji.getValue());
		}
		gridEmoji.setAdapter(mEmojiAdapter);
		popupWindow = new PopupWindow(viewEmojiPanel,
				LayoutParams.MATCH_PARENT, mKeyboardHeight, false);
		popupWindow.setAnimationStyle(R.style.PanelAnimation);
	}
	
	private void initEmojiData(){
		EmojiPrefrence emojiPrefrence = EmojiPrefrence.getInstance();
		int currentVersion = emojiPrefrence.getVersion();
		if(currentVersion == EMOJI_VERSION){
			return;
		}else{
			emojiPrefrence.saveVersion(EMOJI_VERSION);
		}
		emojiPrefrence.saveEmoji(">-)", "emoji_041");
		emojiPrefrence.saveEmoji("o:)", "emoji_042");
		emojiPrefrence.saveEmoji("X(", "emoji_043");
		emojiPrefrence.saveEmoji("=D>", "emoji_044");
		emojiPrefrence.saveEmoji("0-+", "emoji_045");
		emojiPrefrence.saveEmoji("~X(", "emoji_046");
		emojiPrefrence.saveEmoji(";;)", "emoji_047");
		emojiPrefrence.saveEmoji("b-(", "emoji_048");
		emojiPrefrence.saveEmoji(":bz", "emoji_049");
		emojiPrefrence.saveEmoji(":d", "emoji_050");
		emojiPrefrence.saveEmoji(">:D<", "emoji_051");
		emojiPrefrence.saveEmoji("o=>", "emoji_052");
		emojiPrefrence.saveEmoji("\">=\">", "emoji_053");
		emojiPrefrence.saveEmoji(">:/", "emoji_054");
		emojiPrefrence.saveEmoji("=((", "emoji_055");
		emojiPrefrence.saveEmoji("=:)", "emoji_056");
		emojiPrefrence.saveEmoji(":-c", "emoji_057");
		emojiPrefrence.saveEmoji(":-@", "emoji_058");
		emojiPrefrence.saveEmoji("~:>", "emoji_059");
		emojiPrefrence.saveEmoji(":O)", "emoji_060");
		emojiPrefrence.saveEmoji(":-/", "emoji_061");
		emojiPrefrence.saveEmoji(":-??", "emoji_062");
		emojiPrefrence.saveEmoji("B-)", "emoji_063");
		emojiPrefrence.saveEmoji("3:-o", "emoji_064");
		emojiPrefrence.saveEmoji("<):)", "emoji_065");
		emojiPrefrence.saveEmoji(":((", "emoji_066");
		emojiPrefrence.saveEmoji("~o)", "emoji_067");
		emojiPrefrence.saveEmoji("\\:D/", "emoji_068");
		emojiPrefrence.saveEmoji("8->", "emoji_069");
		emojiPrefrence.saveEmoji(">:)", "emoji_070");
		emojiPrefrence.saveEmoji(":-$", "emoji_071");
		emojiPrefrence.saveEmoji(":o3", "emoji_072");
		emojiPrefrence.saveEmoji("#-o", "emoji_073");
		emojiPrefrence.saveEmoji("=P~", "emoji_074");
		emojiPrefrence.saveEmoji("%%-", "emoji_075");
		emojiPrefrence.saveEmoji(":-L", "emoji_076");
		emojiPrefrence.saveEmoji(";))", "emoji_077");
		emojiPrefrence.saveEmoji("o->", "emoji_078");
		emojiPrefrence.saveEmoji(":!!", "emoji_079");
		emojiPrefrence.saveEmoji("@-)", "emoji_080");
		// TODO:
	}
	
	private void onBtnEmojiClicked(){
		if(!popupWindow.isShowing()){
			if(mKeyboardHeight == 0){
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.showSoftInput(mEdtMain, 0);
				isShowPanelNext = true;
			}else{
				showPanel();
			}
		}else{
			hidePanle();
		}
	}
	
	private void showPanel(){
		popupWindow.setHeight(mKeyboardHeight);
		popupWindow.showAtLocation(lnlMain, Gravity.BOTTOM, 0, 0);
	}
	
	private void hidePanle(){
		popupWindow.dismiss();
	}
	
	private void showAds(){
		mTxtAds.setVisibility(View.VISIBLE);
	}
	
	private void hideAds(){
		mTxtAds.setVisibility(View.GONE);
	}
	
	@Override
	public void onClick(View v){
		int id = v.getId();
		switch(id){
			case R.id.abc:
				onBtnEmojiClicked();
				break;
			case R.id.bcd:
				hidePanle();
				break;
			default:
				break;
		}
	}
	
	private OnGlobalLayoutListener mOnGlobalLayoutListener = new OnGlobalLayoutListener(){
		@Override
		public void onGlobalLayout(){
			Rect r = new Rect();
			lnlMain.getWindowVisibleDisplayFrame(r);
			int screenHeight = lnlMain.getRootView().getHeight();
			mKeyboardHeight = screenHeight - r.bottom;
			if(mKeyboardHeight > 0){
				if(isShowPanelNext){
					isShowPanelNext = false;
					showPanel();
					hideAds();
				}
			}else{
				hidePanle();
				showAds();
			}
		}
	};
	
	public EmojiAdapter mEmojiAdapter = new EmojiAdapter();
	
	private class EmojiAdapter extends BaseAdapter{
		private List<Emoji> mEmoji = new ArrayList<Emoji>();
		private final String RESOURCE_TYPE = "drawable";
		private final float PERCENT_MEASURE = 0.8f;
		private OnEmojiItemClicked mOnEmojiItemClicked;
		
		public void setOnEmojiItemClicked(OnEmojiItemClicked onEmojiItemClicked){
			this.mOnEmojiItemClicked = onEmojiItemClicked;
		}
		
		public void setEmoji(String key, String value){
			mEmoji.add(new Emoji(key, value));
		}
		
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent){
			ViewHolder holder = null;
			if(convertView == null){
				convertView = View.inflate(getBaseContext(),
						R.layout.item_panel_emoji, null);
				holder = new ViewHolder();
				holder.imgEmoji = (ImageView) convertView
						.findViewById(R.id.emoji_item);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			String source = mEmoji.get(position).getValue();
			String packageName = getBaseContext().getPackageName();
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
			holder.imgEmoji.setImageDrawable(emoji);
			if(mOnEmojiItemClicked != null){
				holder.imgEmoji.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v){
						mOnEmojiItemClicked.onClick(mEmoji.get(position)
								.getKey());
					}
				});
			}
			return convertView;
		}
		
		class ViewHolder{
			public ImageView imgEmoji;
		}
		
		@Override
		public long getItemId(int position){
			return 0;
		}
		
		@Override
		public Emoji getItem(int position){
			return mEmoji.get(position);
		}
		
		@Override
		public int getCount(){
			return mEmoji.size();
		}
	};
}