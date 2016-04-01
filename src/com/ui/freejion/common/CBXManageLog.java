package com.ui.freejion.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.os.Environment;
import android.util.Log;

public class CBXManageLog {

	// Log关闭变量
	private static final boolean bOpenLog = true;

	private static final String LOG_FOLDER = Environment
			.getExternalStorageDirectory() + "/_Log/freejoin";

	public static String strFileName = null;
	private static Thread th = null;

	private static int pid = android.os.Process.myPid();

	public static void clearLog() {
		File file = new File(LOG_FOLDER + "/" + "log.txt");
		if (null != file && file.exists()) {
			file.delete();
		}
	}

	public static void E(String tag, String msg) {
		LOG_E(tag, msg);
	}

	public static void I(String tag, String msg) {
		LOG_I(tag, msg);
	}

	public static void D(String tag, String msg) {
		LOG_D(tag, msg);
	}

	public static void W(String tag, String msg) {
		LOG_W(tag, msg);
	}

	private static int LOG_D(String tag, String msg) {
		if (bOpenLog) {
			startLogThread();

			return Log.d(tag, msg);
		} else {
			return 0;
		}
	}

	private static int LOG_I(String tag, String msg) {
		if (bOpenLog) {
			startLogThread();

			return Log.i(tag, msg);
		} else {
			return 0;
		}
	}

	private static int LOG_W(String tag, String msg) {
		if (bOpenLog) {
			startLogThread();

			return Log.w(tag, msg);
		} else {
			return 0;
		}
	}

	private static int LOG_E(String tag, String msg) {
		if (bOpenLog) {
			startLogThread();

			return Log.e(tag, msg);
		} else {
			return 0;
		}
	}

	private static void startLogThread() {
		if (null != th) {
			return;
		}
		th = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					// 永真循环，一直去抓log。
					try {
						// 定义抓log的参数，-v就是抓所有的log，-e就是抓error的log。
						ArrayList<String> cmdLine = new ArrayList<String>();
						cmdLine.add("logcat");
						cmdLine.add("-v");
						cmdLine.add("time");

						BufferedReader bufferedReader;
						// Runtime用于抓log的类
						Process process = Runtime.getRuntime().exec(
								"logcat -v time");
						bufferedReader = new BufferedReader(
								new InputStreamReader(process.getInputStream()));

						String str = null;
						while ((str = bufferedReader.readLine()) != null) {
							// 将log写进文件。
							// 将log写进文件。
							if (str.contains("" + pid)
									&& !str.contains("ActivityThread")
									&& !str.contains("asset")
									&& !str.contains("NativeCrypto")) {
								saveLogToFile(str);
							}
						}
						if (str == null) {
							bufferedReader = null;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		});
		// 开启线程
		th.start();
	}

	private static void saveLogToFile(String strMessage) {
		writeToFile(strMessage);
	}

	public static void writeToFile(String content) {
		BufferedWriter out = null;
		try {
			File dir = new File(LOG_FOLDER);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File file = new File(LOG_FOLDER + "/" + "log.txt");

			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, true)));
			out.write("\n");
			out.write(content);
		} catch (Exception e) {
		} finally {
			try {
				if (null != out) {
					out.close();
					out = null;
				}
			} catch (IOException e) {
			}
		}
	}
}
