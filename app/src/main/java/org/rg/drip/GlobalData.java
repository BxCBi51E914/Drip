package org.rg.drip;

import android.util.SparseArray;
import android.util.SparseIntArray;

import org.rg.drip.data.model.cache.Lexical;
import org.rg.drip.data.model.cache.LexicalItem;
import org.rg.drip.utils.ConfigUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by TankGq
 * on 2018/4/10.
 */
public class GlobalData {

	public static Object nullObj = new Object();
	
	public static String config = ConfigUtil.getDefaultConfig();
	
	public static final Boolean isDebug = false;//BuildConfig.DEBUG;

	public static int currentLexicalId = 0;
	public static int currentLexicalItemId = 0;
	public static SparseArray<List<LexicalItem>> lexicalItemDic = new SparseArray<>();
	public static SparseArray<Lexical> lexicalDic = new SparseArray<>();
	public static SparseIntArray lexicalPositionDic = new SparseIntArray();     // tab 中的 position 映射成 lexicalId
	public static SparseIntArray lexicalPositionInvDic = new SparseIntArray();  // 反之
	public static List<LexicalItem> getCurrentLexicalItemList() {
		return lexicalItemDic.get(currentLexicalId);
	}
}
