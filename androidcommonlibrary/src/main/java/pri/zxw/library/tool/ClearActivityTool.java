package pri.zxw.library.tool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import android.app.Activity;

/**
 * 清楚activity
 * @author 张相伟
 *
 */
public class ClearActivityTool {

		/**
		 * 清空所有activity
		 */
		public static void clearActityNotServer(List<Activity> activityList) {
			Iterator<Activity> iterator = activityList.iterator();
			while (iterator.hasNext()) {
				Activity curActivity = iterator.next();
				if (curActivity != null) {
					curActivity.finish();
					iterator.remove();
				}
			}
		}

		/**
		 * 关闭其他activity，留下一个特定的activity
		 * @param actClass activity的类型
		 */
		public  static  void ClearOtherActityLeavingAct(List<Activity> activityList,
				Class<?> actClass) {
			
			Iterator<Activity> iterator = activityList.iterator();
			while (iterator.hasNext()) {
				Activity curActivity = iterator.next();
				if (curActivity != null&&!curActivity.getClass().equals(actClass )  ) {
					curActivity.finish();
					iterator.remove();
				}
			}

		}
		/**
		 * 关闭特定的activity
		 * @param actClass activity的类型
		 */
		public  static  void removeActity(List<Activity> activityList,
				Class<?> actClass) {
			
			Iterator<Activity> iterator = activityList.iterator();
			while (iterator.hasNext()) {
				Activity curActivity = iterator.next();
				if ( curActivity != null && curActivity.getClass().equals(actClass ) ) {
					curActivity.finish();
					iterator.remove();
				}
			}

		}
	
		public static void ClearActity(List<Activity> activityList,Activity act) {
			Iterator<Activity> iterator = activityList.iterator();
			while (iterator.hasNext()) {
				Activity curActivity = iterator.next();
				if (curActivity.equals(act)) {
					curActivity.finish();
					iterator.remove();
				}
			}

		}

		// 添加Activity到容器中
		public static void addActivity(List<Activity> activityList,Activity activity) {
			if (activityList == null) {
				activityList = new ArrayList<Activity>();
			}
			activityList.add(activity);
		}

	
}
