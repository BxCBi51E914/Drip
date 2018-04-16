package org.rg.drip.constant;

import android.graphics.Color;

import java.util.Arrays;
import java.util.List;

/**
 * Created by TankGq
 * on 2018/3/21.
 */
public class UIConstant {

	/**
	 * 每个主页面的索引
	 */
	public static final int WORDBOOK = 0;
	public static final int CUSTOM = 1;
	public static final int READING = 2;
	public static final int SETTING = 3;

	/**
	 * 页面显示和关闭的动画的播放时间, 单位毫秒
	 */
	public static final int ANIMATOR_DURATION = 600;

	/**
	 * 表格动画的播放时间, 单位毫秒
	 */
	public static final int CHART_ANIMATOR_DURATION = 1500;

	/**
	 * StartLearnFragment 页面下的卡片最大可见个数
	 */
	public static final int SWIPE_VISIBLE_CARD_COUNT = 3;

	/**
	 * AvatarFragment 页面下的 popup 的宽和高
	 */
	public static final int AVATAR_POPUP_HEIGHT = 192;
	public static final int AVATAR_POPUP_WIDTH = 192;

	/**
	 * 界面 Fade 动画的时长
	 */
	public static final int FADE_ANIMATOR_DURATION = 600;
	
	/**
	 * 界面 Slide 动画的时长
	 */
	public static final int SLIDE_ANIMATOR_DURATION = 600;

	/**
	 * 一次加载多少张卡片, 开始学习界面中
	 */
	public static final int LOAD_DATA_CARD_NUMBER = 8;

	/**
	 * 自定义学习里面每个 tab 的 title 的最小宽度
	 */
	public static final int CUSTOM_TAB_MIN_SIZE = 16;

	private static int rgb(String hex) {
		int color = (int) Long.parseLong(hex.replace("#", ""), 16);
		int r = (color >> 16) & 0xFF;
		int g = (color >> 8) & 0xFF;
		int b = color & 0xFF;
		return Color.rgb(r, g, b);
	}

	public static final List<Integer> COLOR_LIST = Arrays.asList(rgb("#EF9A9A"),
	                                                             rgb("#E53935"),
	                                                             rgb("#F8BBD0"),
	                                                             rgb("#D81B60"),
	                                                             rgb("#880E4F"),
	                                                             rgb("#CE93D8"),
	                                                             rgb("#8E24AA"),
	                                                             rgb("#4A148C"),
	                                                             rgb("#B39DDB"),
	                                                             rgb("#673AB7"),
	                                                             rgb("#311B92"),
	                                                             rgb("#3D5AFE"),
	                                                             rgb("#29B6F6"),
	                                                             rgb("#26C6DA"),
	                                                             rgb("#26A69A"),
	                                                             rgb("#4CAF50"),
	                                                             rgb("#8BC34A"),
	                                                             rgb("#CDDC39"),
	                                                             rgb("#FFEB3B"),
	                                                             rgb("#FFC107"),
	                                                             rgb("#FF9800"),
	                                                             rgb("#FF5722"));
	public static final int getCardColor(int index) {
		index = Math.max(0, index);
		index = index % COLOR_LIST.size();
		return COLOR_LIST.get(index);
	}
	
	/**
	 * 预加载的距离
	 */
	public static final int SCROLL_VIEW_PRE_LOAD_DISTANCE = 512;
}
