package com.project.dnflol.util;

import lombok.Data;

@Data
public class BoardMinMax {
	int limit;
	int min;
	int max;
	String prev;
	String next;
	public BoardMinMax(int limit) {
		this.limit = limit;
		this.min = 0;
		this.max = 100;
		prev = "false";
		if (limit <= max)
			next = "false";
		else
			next = "true";
	}
	public void next() {
		if (next.equals("true")) {
			this.min = max;
			this.max += 100;
			prev = "true";
			if (min < limit && limit <= max)
				next = "false";
		}
	}
	public void prev() {
		if (prev.equals("true")) {
			this.max = min;
			this.min -= 100;
			next = "true";
			if (min == 0)
				prev = "false";
		}
	}
}
