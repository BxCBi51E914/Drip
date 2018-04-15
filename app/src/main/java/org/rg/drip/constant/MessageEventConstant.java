package org.rg.drip.constant;

import org.rg.drip.event.MessageEvent;

/**
 * Created by TankGq
 * on 2018/4/13.
 */
public class MessageEventConstant {
	
	/**
	 * 重启 activity
	 */
	public static final int RESTART_ACTIVITY = 1;
	public static final MessageEvent RESTART_ACTIVITY_EVENT = new MessageEvent(RESTART_ACTIVITY);

	/**
	 * 更新当前选择的单词本的名称
	 */
	public static final int UPDATE_SELECT_WORDBOOK_NAME = 2;
	public static final MessageEvent UPDATE_SELECT_WORDBOOK_NAME_EVENT = new MessageEvent(UPDATE_SELECT_WORDBOOK_NAME);

	/**
	 * 隐藏选择单词本的界面
	 */
	public static final int HIDE_CHOOSE_WORDBOOK = 3;
	public static final MessageEvent HIDE_CHOOSE_WORDBOOK_EVENT = new MessageEvent(HIDE_CHOOSE_WORDBOOK);

	/**
	 * 显示开始学习的界面
	 */
	public static final int SHOW_START_LEARN = 4;
	public static final MessageEvent SHOW_START_LEARN_EVENT = new MessageEvent(SHOW_START_LEARN);

	/**
	 * 隐藏开始学习的界面
	 */
	public static final int HIDE_START_LEARN = 5;
	public static final MessageEvent HIDE_START_LEARN_EVENT = new MessageEvent(HIDE_START_LEARN);

	/**
	 * 显示卡片浏览的界面
	 */
	public static final int SHOW_BROWSE_IN_CARD = 6;
	public static final MessageEvent SHOW_BROWSE_IN_CARD_EVENT = new MessageEvent(SHOW_BROWSE_IN_CARD);

	/**
	 * 隐藏卡片浏览的界面
	 */
	public static final int HIDE_BROWSE_IN_CARD = 7;
	public static final MessageEvent HIDE_BROWSE_IN_CARD_EVENT = new MessageEvent(HIDE_BROWSE_IN_CARD);
}
