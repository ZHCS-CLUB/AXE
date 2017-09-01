package club.zhcs.apm;

import java.util.Arrays;
import java.util.Date;

/**
 * @author kerbores
 *
 */
public interface APMAppender {

	public static class APMLog {

		String url;

		String tag;

		String user;

		Date actionTime;

		long actionDuration;

		Object[] args;

		Object retuenObj;

		boolean exception;

		/**
		 * @return the url
		 */
		public String getUrl() {
			return url;
		}

		/**
		 * @param url
		 *            the url to set
		 */
		public void setUrl(String url) {
			this.url = url;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "APMLog [tag=" + tag + ", user=" + user + ", actionTime=" + actionTime + ", actionDuration=" + actionDuration + ", args=" + Arrays.toString(args)
					+ ", retuenObj=" + retuenObj + ", exception=" + exception + "]";
		}

		/**
		 * @return the exception
		 */
		public boolean isException() {
			return exception;
		}

		/**
		 * @param exception
		 *            the exception to set
		 */
		public void setException(boolean exception) {
			this.exception = exception;
		}

		/**
		 * @return the tag
		 */
		public String getTag() {
			return tag;
		}

		/**
		 * @param tag
		 *            the tag to set
		 */
		public void setTag(String tag) {
			this.tag = tag;
		}

		/**
		 * @return the user
		 */
		public String getUser() {
			return user;
		}

		/**
		 * @param user
		 *            the user to set
		 */
		public void setUser(String user) {
			this.user = user;
		}

		/**
		 * @return the actionTime
		 */
		public Date getActionTime() {
			return actionTime;
		}

		/**
		 * @param actionTime
		 *            the actionTime to set
		 */
		public void setActionTime(Date actionTime) {
			this.actionTime = actionTime;
		}

		/**
		 * @return the actionDuration
		 */
		public long getActionDuration() {
			return actionDuration;
		}

		/**
		 * @param actionDuration
		 *            the actionDuration to set
		 */
		public void setActionDuration(long actionDuration) {
			this.actionDuration = actionDuration;
		}

		/**
		 * @return the args
		 */
		public Object[] getArgs() {
			return args;
		}

		/**
		 * @param args
		 *            the args to set
		 */
		public void setArgs(Object[] args) {
			this.args = args;
		}

		/**
		 * @return the retuenObj
		 */
		public Object getRetuenObj() {
			return retuenObj;
		}

		/**
		 * @param retuenObj
		 *            the retuenObj to set
		 */
		public void setRetuenObj(Object retuenObj) {
			this.retuenObj = retuenObj;
		}

	}

	void append(APMLog log);

}
