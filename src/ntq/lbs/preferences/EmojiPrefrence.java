package ntq.lbs.preferences;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ntq.lbs.emoji.Emoji;
import ntq.lbs.utils.App;
import ntq.lbs.utils.NLog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.ParcelFormatException;

/**
 * This class make the Emoji code never conflict. We using input Emoji code
 * between two space character so we do not need to using Trie to save data.
 */
public class EmojiPrefrence{
	private final String TAG = "emoji_prefrence";
	private static EmojiPrefrence mEmojiPrefrence = new EmojiPrefrence();
	private final int MODE = Context.MODE_PRIVATE;
	private final String VERSION_KEY = "key_version";
	
	private EmojiPrefrence(){
		// That using to lock constructor of this class. Read Singleton pattern.
	}
	
	public static EmojiPrefrence getInstance(){
		return mEmojiPrefrence;
	}
	
	public SharedPreferences getSharedPreferences(){
		return App.getContext().getSharedPreferences(TAG, MODE);
	}
	
	private SharedPreferences.Editor getEditor(){
		return getSharedPreferences().edit();
	}
	
	public boolean isExit(String key){
		return getSharedPreferences().contains(key);
	}
	
	public boolean saveVersion(int version){
		return getEditor().putInt(VERSION_KEY, version).commit();
	}
	
	public int getVersion(){
		return getSharedPreferences().getInt(VERSION_KEY, -1);
	}
	
	public boolean saveEmoji(String key, String source){
		key = key.trim();
		source = source.trim();
		if(isExit(key)){
			NLog.e(TAG, "Save false: " + key);
			return false;
		}
		return getEditor().putString(key, source).commit();
	}
	
	public String getEmoji(String key){
		return getSharedPreferences().getString(key, "");
	}
	
	public void removeEmoji(String key){
		getEditor().remove(key).commit();
	}
	
	public List<Emoji> getListEmoji(){
		List<Emoji> emojiList = new ArrayList<Emoji>();
		Map<String, ?> emojiMap = getSharedPreferences().getAll();
		Set<?> emojiSet = emojiMap.entrySet();
		Iterator<?> iterator = emojiSet.iterator();
		while(iterator.hasNext()){
			try{
				@SuppressWarnings("unchecked")
				Map.Entry<String, String> entry = (Entry<String, String>) iterator
						.next();
				String key = entry.getKey();
				if(key.equals(VERSION_KEY)) continue;
				String value = entry.getValue();
				Emoji emoji = new Emoji(key, value);
				emojiList.add(emoji);
			}catch(ParcelFormatException e){
				String msg = e.getMessage();
				if(msg != null){
					NLog.e(TAG, msg);
				}else{
					NLog.e(TAG, "Unknow error");
				}
			}
		}
		return emojiList;
	}
}