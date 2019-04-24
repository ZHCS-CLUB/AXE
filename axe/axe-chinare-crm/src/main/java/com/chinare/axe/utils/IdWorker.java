package com.chinare.axe.utils;

import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.log.Logs;

public class IdWorker {
	private final long workerId;
	private static final long TWE_POCH = 1288834974657L;
	private long sequence = 0L;
	private static final long WORKER_ID_BITS = 4L;
	public static final long MAX_WORKER_ID = -1L ^ -1L << WORKER_ID_BITS;
	private static final long SEQUENCE_BITS = 10L;

	private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
	private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
	public static final long SEQUENCE_MASK = -1L ^ -1L << SEQUENCE_BITS;

	private long lastTimestamp = -1L;

	public IdWorker(final long workerId) {
		super();
		if (workerId > IdWorker.MAX_WORKER_ID || workerId < 0) {
			throw new IllegalArgumentException(
					String.format("worker Id can't be greater than %d or less than 0", IdWorker.MAX_WORKER_ID));
		}
		this.workerId = workerId;
	}

	public synchronized long nextId() {
		long timestamp = this.timeGen();
		if (this.lastTimestamp == timestamp) {
			this.sequence = (this.sequence + 1) & IdWorker.SEQUENCE_MASK;
			if (this.sequence == 0) {
				Logs.get().debug("###########" + SEQUENCE_MASK);
				timestamp = this.tilNextMillis(this.lastTimestamp);
			}
		} else {
			this.sequence = 0;
		}
		if (timestamp < this.lastTimestamp) {
			throw Lang.makeThrow("Clock moved backwards.  Refusing to generate id for %d milliseconds",
					this.lastTimestamp - timestamp);
		}

		this.lastTimestamp = timestamp;
		long nextId = (timestamp - TWE_POCH << TIMESTAMP_LEFT_SHIFT) | (this.workerId << IdWorker.WORKER_ID_SHIFT)
				| (this.sequence);
		Logs.get().debug("timestamp:" + timestamp + ",timestampLeftShift:" + TIMESTAMP_LEFT_SHIFT + ",nextId:" + nextId
				+ ",workerId:" + workerId + ",sequence:" + sequence);
		return nextId;
	}

	public synchronized String nextIdForEc() {
		long timestamp = this.timeGen();
		if (this.lastTimestamp == timestamp) {
			this.sequence = (this.sequence + 1) & IdWorker.SEQUENCE_MASK;
			if (this.sequence == 0) {
				Logs.get().debug("###########" + SEQUENCE_MASK);
				timestamp = this.tilNextMillis(this.lastTimestamp);
			}
		} else {
			this.sequence = 0;
		}
		if (timestamp < this.lastTimestamp) {
			throw Lang.makeThrow("Clock moved backwards.  Refusing to generate id for %d milliseconds",
					this.lastTimestamp - timestamp);
		}

		this.lastTimestamp = timestamp;
		long nextId = (timestamp - TWE_POCH << TIMESTAMP_LEFT_SHIFT) | (this.workerId << IdWorker.WORKER_ID_SHIFT)
				| (this.sequence);
		Logs.get().debug("timestamp:" + timestamp + ",timestampLeftShift:" + TIMESTAMP_LEFT_SHIFT + ",nextId:" + nextId
				+ ",workerId:" + workerId + ",sequence:" + sequence);
		return DateUtils.format("yyyyMM", Times.now()) + nextId;
	}

	private long tilNextMillis(final long lastTimestamp) {
		long timestamp = this.timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = this.timeGen();
		}
		return timestamp;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}

}
