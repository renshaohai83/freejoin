package com.ui.freejion.common;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

public class JSonParse {
	private static final String TAG = "JSonParse";

	/**
	 * 解析活动列表
	 * 
	 * @param data
	 */
	public static List<ActivityData> parseList(String data) {

		CBXManageLog.D(TAG, "parseList");

		if (TextUtils.isEmpty(data)) {
			return null;
		}

		List<ActivityData> returnData = new ArrayList<ActivityData>();
		try {
			JSONObject result = new JSONObject(data);
			JSONArray array = result.getJSONArray("data");

			if (null != array) {
				for (int i = 0; i < array.length(); ++i) {
					JSONObject item = (JSONObject) array.get(i);

					if (null == item) {
						continue;
					}
					ActivityData activity = new ActivityData();
					activity.mId = item.optString("id");
					activity.mTitle = item.optString("title");
					activity.mStartTime = item.optString("startTime");
					activity.mEndTime = item.optString("endTime");
					activity.mContent = item.optString("content");
					activity.mCreatedBy = item.optString("createdBy");
					activity.mCreatedAt = item.optString("createdAt");
					activity.mJoinStatus = item.optString("joinStatus");

					returnData.add(activity);
				}
			}

		} catch (Exception e) {
			CBXManageLog.E(TAG, "Exception:" + e);
		}

		return returnData;
	}

	/**
	 * 解析活动Detail
	 * 
	 * @param data
	 */
	public static ActivityDetail parseDetail(String data) {

		CBXManageLog.D(TAG, "parseDetail");

		if (TextUtils.isEmpty(data)) {
			return null;
		}

		ActivityDetail returnData = new ActivityDetail();
		try {
			JSONObject result = new JSONObject(data);

			if (null != result) {
				returnData.mId = result.optString("id");
				returnData.mTitle = result.optString("title");
				returnData.mStartTime = result.optString("startTime");
				returnData.mEndTime = result.optString("endTime");
				returnData.mContent = result.optString("content");
				returnData.mCreatedBy = result.optString("createdBy");
				returnData.mCreatedAt = result.optString("createdAt");
				returnData.mJoinStatus = result.optString("joinStatus");
				returnData.mGroupId = result.optString("groupId");

				JSONArray users = result.getJSONArray("joinedUsers");

				if (null != users) {
					returnData.mJoinedUsers = new ArrayList<JoinedUserData>();

					for (int i = 0; i < users.length(); ++i) {
						JSONObject item = (JSONObject) users.get(i);

						if (null == item) {
							continue;
						}
						JoinedUserData user = new JoinedUserData();
						user.mId = item.optString("id");
						user.mUsername = item.optString("userName");
						user.mMobile = item.optString("mobile");
						user.mCreateAt = item.optString("createdAt");

						returnData.mJoinedUsers.add(user);
					}
				}

				JSONArray notUsers = result.getJSONArray("notJoinedUsers");

				if (null != notUsers) {
					returnData.mNotJoinedUsers = new ArrayList<JoinedUserData>();

					for (int i = 0; i < notUsers.length(); ++i) {
						JSONObject item = (JSONObject) notUsers.get(i);

						if (null == item) {
							continue;
						}
						JoinedUserData user = new JoinedUserData();
						user.mId = item.optString("id");
						user.mUsername = item.optString("userName");
						user.mMobile = item.optString("mobile");
						user.mCreateAt = item.optString("createdAt");

						returnData.mNotJoinedUsers.add(user);
					}
				}
			}

		} catch (Exception e) {
			CBXManageLog.E(TAG, "Exception:" + e);
		}

		return returnData;
	}

	/**
	 * 解析 我的活动列表
	 * 
	 * @param data
	 */
	public static MyActivitiesData parseMyActivities(String data) {

		CBXManageLog.D(TAG, "parseMyActivities");

		if (TextUtils.isEmpty(data)) {
			return null;
		}

		MyActivitiesData returnData = new MyActivitiesData();
		try {
			JSONObject result = new JSONObject(data);

			if (null != result) {
				returnData.mId = result.optString("id");
				returnData.mUsername = result.optString("userName");
				returnData.mMobile = result.optString("mobile");
				returnData.mCreateAt = result.optString("createdAt");

				JSONArray activities = result.getJSONArray("joinedActivities");

				if (null != activities) {
					returnData.mJoinedActivities = new ArrayList<ActivityData>();

					for (int i = 0; i < activities.length(); ++i) {
						JSONObject item = (JSONObject) activities.get(i);

						if (null == item) {
							continue;
						}
						ActivityData activity = new ActivityData();
						activity.mId = item.optString("id");
						activity.mTitle = item.optString("title");
						activity.mStartTime = item.optString("startTime");
						activity.mEndTime = item.optString("endTime");
						activity.mContent = item.optString("content");
						activity.mGroupId = item.optString("groupId");
						activity.mCreatedBy = item.optString("createdBy");
						activity.mCreatedAt = item.optString("createdAt");

						returnData.mJoinedActivities.add(activity);
					}
				}
			}

		} catch (Exception e) {
			CBXManageLog.E(TAG, "Exception:" + e);
		}

		return returnData;
	}

	/**
	 * 解析Group列表
	 * 
	 * @param data
	 */
	public static List<GroupData> parseGroup(String data) {

		CBXManageLog.D(TAG, "parseGroup");

		if (TextUtils.isEmpty(data)) {
			return null;
		}

		List<GroupData> returnData = new ArrayList<GroupData>();
		try {
			JSONObject result = new JSONObject(data);
			JSONArray array = result.getJSONArray("data");

			if (null != array) {
				for (int i = 0; i < array.length(); ++i) {
					JSONObject item = (JSONObject) array.get(i);

					if (null == item) {
						continue;
					}
					GroupData group = new GroupData();
					group.mId = item.optString("id");
					group.mName = item.optString("groupName");
					group.mCreatedAt = item.optString("createdAt");

					returnData.add(group);
				}
			}

		} catch (Exception e) {
			CBXManageLog.E(TAG, "Exception:" + e);
		}

		return returnData;
	}
}
