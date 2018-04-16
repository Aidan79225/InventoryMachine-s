package com.aidan.inventoryworkplatform.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


// TODO: Auto-generated Javadoc

/**
 * The Class LocalCacheHelper.
 */
public class LocalCacheHelper {
	
	/** The preferences. */
	public static SharedPreferences preferences = null;
	
	/** The preference editor. */
	public static Editor preferenceEditor = null;
	
	/** The application context. */
	public static Context applicationContext = null;
	
	/** The singleton. */
	private static LocalCacheHelper singleton = null;

	/** constant name for recording not clear data hashset to pref */
	private static final String PREF_NOT_CLEAR_DATA = "PREF_NOT_CLEAR_DATA";

	/**
	 * Gets the single instance of LocalCacheHelper.
	 *
	 * @return single instance of LocalCacheHelper
	 */
	public static LocalCacheHelper getInstance() {
		return singleton;
	}


	public static void init(Context appContext){
		if (applicationContext == null && appContext != null) {
			applicationContext = appContext;

			preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
			preferenceEditor = preferences.edit();
		}

		if (singleton == null) {
			synchronized (LocalCacheHelper.class) {
				if (singleton == null) {
					singleton = new LocalCacheHelper(appContext);
				}
			}
		}
	}
	
	/**
	 * Instantiates a new local cache helper.
	 *
	 * @param context the context
	 */
	private LocalCacheHelper(Context context) {
	}
	
	/**
	 * Sets the string.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void setString(String key,String value){
		preferenceEditor.putString(key, value);
		preferenceEditor.commit();
	}
	
	/**
	 * Gets the string.
	 *
	 * @param key the key
	 * @return the string
	 */
	public String getString(String key){
		return preferences.getString(key, "");
	}
	
	/**
	 * Sets the boolean.
	 *
	 * @param mContext the m context
	 * @param key the key
	 * @param b the b
	 */
	public static void setBoolean(Context mContext,String key,boolean b){
		preferenceEditor.putBoolean(key, b);
		preferenceEditor.commit();
	}
	
	/**
	 * Gets the boolean.
	 *
	 * @param mContext the m context
	 * @param key the key
	 * @return the boolean
	 */
	public static boolean getBoolean(Context mContext,String key){
		return preferences.getBoolean(key, true);
	}

	public static boolean getBoolean(Context mContext,String key, boolean defaultValue){
		return preferences.getBoolean(key, defaultValue);
	}
	/**
	 * Sets the float.
	 *
	 * @param mContext the m context
	 * @param key the key
	 * @param b the b
	 */
	public static void setFloat(Context mContext,String key,float b){
		preferenceEditor.putFloat(key, b);
		preferenceEditor.commit();
	}
	
	/**
	 * Gets the float.
	 *
	 * @param mContext the m context
	 * @param key the key
	 * @return the float
	 */
	public static float getFloat(Context mContext,String key){
		return preferences.getFloat(key, 0L);
	}

	/**
	 * Sets the int.
	 *
	 * @param mContext the m context
	 * @param key the key
	 * @param b the b
	 */
	public static void setInt(Context mContext,String key,int b){
		preferenceEditor.putInt(key, b);
		preferenceEditor.commit();
	}

	/**
	 * Gets the int.
	 *
	 * @param mContext the m context
	 * @param key the key
	 * @return the int
	 */
	public static int getInt(Context mContext,String key){
		return preferences.getInt(key, 0);
	}
	/**
	 * Sets the int.
	 *
	 * @param mContext the m context
	 * @param key the key
	 * @param b the b
	 */
	public static void setLong(Context mContext,String key,long b){
		preferenceEditor.putLong(key, b);
		preferenceEditor.commit();
	}

	/**
	 * Gets the long.
	 *
	 * @param mContext the m context
	 * @param key the key
	 * @return the long
	 */
	public static long getLong(Context mContext,String key){
		return preferences.getLong(key, 0L);
	}


	/**
	 * Sets the JSONObject string.
	 *
	 * @param mContext the m context
	 * @param key the key
	 * @param jsonObject the value
	 */
	public static void setJSONObject(Context mContext,String key,JSONObject jsonObject){
		if (jsonObject==null) return;
		preferenceEditor.putString(key, jsonObject.toString());
		preferenceEditor.commit();
	}

	/**
	 * Gets the JSONObject string.
	 *
	 * @param key the key
	 * @return the jsonObject
	 */
	public JSONObject getJSONObject(String key){
		String jsonString = preferences.getString(key, "");
		JSONObject jsonObject = new JSONObject();
		if (!jsonString.equals("")){
			try {
				jsonObject = new JSONObject(jsonString);
			} catch (JSONException e) {}
		}
		return jsonObject;
	}

	public void setJSONArray(String key, JSONArray jsonArray){
		if (jsonArray==null) return;
		preferenceEditor.putString(key, jsonArray.toString());
		preferenceEditor.commit();
	}

	public JSONArray getJSONArray(String key){
		String jsonString = preferences.getString(key, "");
		JSONArray jsonArray = new JSONArray();
		if (!jsonString.equals("")){
			try {
				jsonArray = new JSONArray(jsonString);
			} catch (JSONException e) {}
		}
		return jsonArray;
	}


	public static Date getDate(Context mContext, String key) {
		long milliseconds = getLong(mContext, key);
		return new Date(milliseconds);
	}


	public static void setDate(Context mContext, String key, Date date) {
		if (date == null) return;
		setLong(mContext, key, date.getTime());

	}

	/**
	 * Clear cache.
	 *
	 * @param mContext the m context
	 */
	public static void clearCache(Context mContext){
		//get notclear set
		Set<String> notClearDataHashSet = getNotClearData(mContext);
		//拿到每一個key
		Map<String, ?> allData = preferences.getAll();
		for(Map.Entry<String, ?> entry : allData.entrySet()) {
			String key = entry.getKey();
			//將非設定為不刪除的資料都刪除
			if (!notClearDataHashSet.contains(key)){
				preferenceEditor.remove(key);
			}
		}
		preferenceEditor.commit();
	}

	public static void clearCache(Context mContext, String key) {
		preferenceEditor.remove(key);
		preferenceEditor.commit();
	}

	public static void addNotClear(Context mContext, String key){
		Set<String> notClearDataHashSet = getNotClearData(mContext);
		notClearDataHashSet.add(key);
		preferenceEditor.putStringSet(PREF_NOT_CLEAR_DATA, notClearDataHashSet);
		preferenceEditor.commit();
	}

	public static void removeNotClear(Context mContext, String key){
		Set<String> notClearDataHashSet = getNotClearData(mContext);
		notClearDataHashSet.remove(key);
		preferenceEditor.putStringSet(PREF_NOT_CLEAR_DATA, notClearDataHashSet);
		preferenceEditor.commit();
	}

	public static Set<String> getNotClearData(Context mContext){
		Set<String> notClearDataHashSet = preferences.getStringSet(PREF_NOT_CLEAR_DATA, new HashSet<String>());
		//add self to avoid to be cleared
		notClearDataHashSet.add(PREF_NOT_CLEAR_DATA);
		return notClearDataHashSet;
	}


}
