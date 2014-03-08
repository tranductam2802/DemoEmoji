package com.example.demoemoji;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class EmojiActivity extends FragmentActivity implements OnClickListener {
	private View viewEmojiPanel;
	private PopupWindow popupWindow;
	private LinearLayout lnlMain;
	private EditText mEdtMain;
	private View mTxtAds;

	private int mKeyboardHeight = 0;

	private boolean isShowPanelNext = false;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.ac_emoji);
		initEmojiPanel();
		initView();
	}

	private void initView() {
		mEdtMain = (EditText) findViewById(R.id.bcd);
		mEdtMain.setOnClickListener(this);
		lnlMain = (LinearLayout) findViewById(R.id.efg);
		lnlMain.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
		mTxtAds = findViewById(R.id.ads);
		TextView txtMain = (TextView) findViewById(R.id.abc);
		txtMain.setOnClickListener(this);

	}

	private void initEmojiPanel() {
		viewEmojiPanel = getLayoutInflater().inflate(R.layout.panel_emoji, null);
		popupWindow = new PopupWindow(viewEmojiPanel, LayoutParams.MATCH_PARENT, mKeyboardHeight, false);
		popupWindow.setAnimationStyle(R.style.PanelAnimation);
	}

	private void onBtnEmojiClicked() {
		if (!popupWindow.isShowing()) {
			if (mKeyboardHeight == 0) {
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.showSoftInput(mEdtMain, 0);
				isShowPanelNext = true;
			} else {
				showPanel();
			}
		} else {
			hidePanle();
		}
	}

	private void showPanel() {
		popupWindow.setHeight(mKeyboardHeight);
		popupWindow.showAtLocation(lnlMain, Gravity.BOTTOM, 0, 0);
	}

	private void hidePanle() {
		popupWindow.dismiss();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
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

	private OnGlobalLayoutListener mOnGlobalLayoutListener = new OnGlobalLayoutListener() {
		@Override
		public void onGlobalLayout() {
			Rect r = new Rect();
			lnlMain.getWindowVisibleDisplayFrame(r);
			int screenHeight = lnlMain.getRootView().getHeight();
			mKeyboardHeight = screenHeight - r.bottom;
			if (mKeyboardHeight > 0) {
				if (isShowPanelNext) {
					isShowPanelNext = false;
					showPanel();
				}
			} else {
				hidePanle();
			}
		}
	};
}