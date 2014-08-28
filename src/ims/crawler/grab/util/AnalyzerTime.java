package ims.crawler.grab.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AnalyzerTime {

	// ����ȫ�Ǿֲ��������������̲߳���ȫ�����

	public static String repeat(String str, int times) {

		String strRepeat = "";
		for (int i = 0; i < times; i++) {
			strRepeat += str;
		}

		return strRepeat;
	}

	// �ж�һ���ַ����Ƿ�������Ϊ��β
	public static boolean endWithNum(String str) {
		for (int i = 0; i < 10; i++) {
			if (str.endsWith(Integer.toString(i))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * ��������ʽ��ʱ���ʾת���ɱ�׼��ʱ���ʽ
	 * 
	 * @param initTimeStr
	 * @return
	 */
	public static String transStandardTimeFormat(String initTimeStr) {

		String formatTimestr = "0000-00-00 00:00:00";

		try {
			String year = null;
			String month = null;
			String day = null;
			String hour = null;
			String minute = null;
			String second = null;

			// ��ý�������
			String todayStr = new SimpleDateFormat("yyyy-MM-dd")
					.format(new Date());
			// �����������
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			String yesterdayStr = new SimpleDateFormat("yyyy-MM-dd").format(cal
					.getTime());

			// ���������ʱ���ַ������磺"����10:37"
			if (initTimeStr.contains("����")) {
				initTimeStr.replaceAll("���� ", (yesterdayStr + " "));
				initTimeStr.replaceAll("����", (yesterdayStr + " "));

				if (!endWithNum(initTimeStr)) {
					initTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date());
				}
			} else if (initTimeStr.contains("����")) {
				initTimeStr.replaceAll("���� ", (todayStr + " "));
				initTimeStr.replaceAll("����", (todayStr + " "));

				if (!endWithNum(initTimeStr)) {
					initTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date());
				}
			} else if (initTimeStr.contains("ǰ") || initTimeStr.contains("Сʱ")
					|| initTimeStr.contains("����")) {
				initTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date());

				if (!endWithNum(initTimeStr)) {
					initTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date());
				}
			}

			// �����ꡱ�����¡������ա������
			if (initTimeStr.contains("��") || initTimeStr.contains("��")
					|| initTimeStr.contains("��")) {
				initTimeStr.replaceAll("��", "-");
				initTimeStr.replaceAll("��", "-");
				initTimeStr.replaceAll("��", "-");
			}

			int digitBegin = 0, digitEnd = initTimeStr.length() - 1;
			for (int i = 0; i < initTimeStr.length(); i++) {
				if ((initTimeStr.charAt(i) >= '0' && initTimeStr.charAt(i) <= '9')) {
					digitBegin = i;
					break;
				}
			}
			for (int i = initTimeStr.length() - 1; i >= 0; i--) {
				if ((initTimeStr.charAt(i) >= '0' && initTimeStr.charAt(i) <= '9')) {
					digitEnd = i + 1;
					break;
				}
			}
			initTimeStr = initTimeStr.substring(digitBegin, digitEnd);

			String[] digitParts = { "", "" };
			digitParts[0] = initTimeStr.substring(0,
					initTimeStr.indexOf(" ") == -1 ? initTimeStr.length()
							: initTimeStr.indexOf(" "));
			digitParts[1] = initTimeStr.indexOf(" ") == -1 ? "" : initTimeStr
					.substring(initTimeStr.indexOf(" ") + 1);

			int digitPartLength = digitParts[1].equals("") ? 1 : 2;

			if (digitPartLength < 2) {

				digitParts[0].replaceAll("[^x00-xff]", "-");

				if (digitParts[0].contains("-")) {
					String[] digitDayParts = digitParts[0].split("-");

					if (digitDayParts.length < 3) {
						year = new SimpleDateFormat("yyyy").format(new Date());
						month = repeat("0", 2 - digitDayParts[0].length())
								+ digitDayParts[0];
						day = repeat("0", 2 - digitDayParts[1].length())
								+ digitDayParts[1];
					} else {
						year = digitDayParts[0].length() == 2 ? ("20" + digitDayParts[0])
								: digitDayParts[0];
						month = repeat("0", 2 - digitDayParts[1].length())
								+ digitDayParts[1];
						day = repeat("0", 2 - digitDayParts[2].length())
								+ digitDayParts[2];
					}
				} else {
					String[] digitTimeParts = digitParts[0].split(":");

					hour = repeat("0", 2 - digitTimeParts[0].length())
							+ digitTimeParts[0];
					minute = repeat("0", 2 - digitTimeParts[1].length())
							+ digitTimeParts[1];
					if (digitTimeParts.length < 3) {
						second = "00";
					} else {
						second = repeat("0", 2 - digitTimeParts[2].length())
								+ digitTimeParts[2];
					}
				}

			} else {
				String digitDayStr = digitParts[0];
				String digitTimeStr = digitParts[1];

				digitDayStr = digitDayStr.replaceAll("[^x00-xff]", "-");

				String[] digitDayParts = digitDayStr.split("-");

				if (digitDayParts.length < 3) {
					year = new SimpleDateFormat("yyyy").format(new Date());
					month = repeat("0", 2 - digitDayParts[0].length())
							+ digitDayParts[0];
					day = repeat("0", 2 - digitDayParts[1].length())
							+ digitDayParts[1];
				} else {
					year = digitDayParts[0].length() == 2 ? ("20" + digitDayParts[0])
							: digitDayParts[0];
					month = repeat("0", 2 - digitDayParts[1].length())
							+ digitDayParts[1];
					day = repeat("0", 2 - digitDayParts[2].length())
							+ digitDayParts[2];
				}

				String[] digitTimeParts = digitTimeStr.split(":");

				hour = repeat("0", 2 - digitTimeParts[0].length())
						+ digitTimeParts[0];
				minute = repeat("0", 2 - digitTimeParts[1].length())
						+ digitTimeParts[1];
				if (digitTimeParts.length < 3) {
					second = "00";
				} else {
					second = repeat("0", 2 - digitTimeParts[2].length())
							+ digitTimeParts[2];
				}
			}

			if ((year == null && month == null && day == null)
					&& (hour != null && minute != null && second != null)) {
				formatTimestr = new SimpleDateFormat("yyyy-MM-dd")
						.format(new Date())
						+ " " + hour + ":" + minute + ":" + second;
			} else if ((year != null && month != null && day != null)
					&& (hour == null && minute == null && second == null)) {
				formatTimestr = year + "-" + month + "-" + day + " 00:00:00";
			} else {
				formatTimestr = year + "-" + month + "-" + day + " " + hour
						+ ":" + minute + ":" + second;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			formatTimestr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date());
		}

		// ������������㷨�޷���ʱ�仯Ϊ��׼��ʽ���Ͷ���Ϊ��ǰʱ��
		if (formatTimestr
				.matches("^\\d{4}-0[1-9]|1[1-2]-0[1-9]|[1-2]\\d|3[0-1] [0-2][0-4]:[0-6]\\d:[0-6]\\d$")) {
			formatTimestr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date());
		}

		return formatTimestr;
	}

	/**
	 * �Ƚϴ��Ƚ�ʱ���Ƿ���ʱ������֮�󣬲�����Ϊ��׼ʱ���ʽ
	 * 
	 * @param compTime
	 * @param limitTime
	 * @return
	 */
	public static boolean compTimeLimit(String compTime, String limitTime) {
		// ���û��ʱ�����ƻ�û�д��Ƚ�ʱ�����������ֵΪ��
		if (limitTime == null || compTime == null) {
			return true;
		}

		Date compDate = null;
		Date limitDate = null;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			compDate = df.parse(compTime);
			limitDate = df.parse(limitTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ((compDate == null || limitDate == null)
				|| (compDate.getTime() - limitDate.getTime() >= 0)) {
			return true;
		}

		return false;
	}

	/**
	 * ��"��-��-��"��ʽ��ʱ����ת��Ϊ������
	 * 
	 * @param timeRangeDay
	 * @return
	 */
	public static long formatToDayTimeMillis(String timeRangeDay) {

		String[] timeParts = timeRangeDay.split("-");
		long timeMillis = (Long.parseLong(timeParts[0]) * 365 * 24 * 60 * 60
				+ Long.parseLong(timeParts[1]) * 30 * 24 * 60 * 60 + Long
				.parseLong(timeParts[2]) * 24 * 60 * 60) * 1000;

		return timeMillis;
	}

	/**
	 * ����ʱ�����ӵ�ǰʱ��������˵�ĳ��ʱ���֮ǰ��ʱ���
	 * 
	 * @param timeRangeStr
	 * @return
	 */
	public static String backToGoalTime(String timeRangeStr) {

		Date nowTime = null;

		String goalTimeStr = "0000-00-00 00:00:00";
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			nowTime = df.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date()));

			long goalTimeInt = nowTime.getTime()
					- formatToDayTimeMillis(timeRangeStr);

			goalTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(goalTimeInt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return goalTimeStr;
	}

	/**
	 * ��������ת��Ϊ"Сʱ�����ӣ���"��ʽ���ַ���
	 * 
	 * @param Ҫת���ĺ�����
	 * @return �ú�����ת��Ϊ hours * minutes * seconds ��ĸ�ʽ
	 * @author superhy
	 */
	public static String formatDuring(long mss) {
		long hours = mss / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		return (hours / 10 == 0 ? "0" : "") + hours + ":"
				+ (minutes / 10 == 0 ? "0" : "") + minutes + ":"
				+ (seconds / 10 == 0 ? "0" : "") + seconds;
	}

	/**
	 * ��"Сʱ�����ӣ���"��ʽ���ַ���ת��Ϊ������
	 * 
	 * @param timeRangeSec
	 * @return
	 */
	public static long formatToSecTimeMillis(String timeRangeSec) {
		String[] timeParts = timeRangeSec.split(":");
		long timeMillis = (Long.parseLong(timeParts[0]) * 60 * 60
				+ Long.parseLong(timeParts[1]) * 60 + Long
				.parseLong(timeParts[2])) * 1000;

		return timeMillis;
	}

	/**
	 * ��"Сʱ�����ӣ���"��ʽ���ַ���ת��Ϊ����
	 * 
	 * @param timeRangeSec
	 * @return
	 */
	public static int formatToSecTimeSecs(String timeRangeSec) {
		String[] timeParts = timeRangeSec.split(":");
		int timeSecs = Integer.parseInt(timeParts[0]) * 60 * 60
				+ Integer.parseInt(timeParts[1]) * 60
				+ Integer.parseInt(timeParts[2]);

		return timeSecs;
	}

	/**
	 * ���������ʱ���֮��ļ��
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String anayTimeRange(Timestamp startTime, Timestamp endTime) {
		// ������ܵ�ʱ����������
		long timeRange = endTime.getTime() - startTime.getTime();
		// ������ܵ�ʱ������ʾ�ַ���
		String timeRangeStr = formatDuring(timeRange);

		return timeRangeStr;
	}
}
