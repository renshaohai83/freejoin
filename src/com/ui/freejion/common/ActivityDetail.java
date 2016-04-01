package com.ui.freejion.common;

import java.util.List;

public class ActivityDetail extends ActivityData {
	// Group Id
	public String mGroupId = "";
	// 参与人
	public List<JoinedUserData> mJoinedUsers = null;
	// 没有参与的人
	public List<JoinedUserData> mNotJoinedUsers = null;

}
