package com.project.dnflol.util;

import lombok.Data;

@Data
public class BoardMinMax {
	int limit;
	int min;
	int max;
	int paging;
	String prev;
	String next;
	public BoardMinMax(int limit) {
		this.limit = limit;
		this.min = 0;
		this.max = 100;
		this.paging = 1;
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
			this.paging += 10;
			prev = "true";
			if (min < limit && limit <= max)
				next = "false";
		}
	}
	public void prev() {
		if (prev.equals("true")) {
			this.max = min;
			this.min -= 100;
			this.paging -= 10;
			next = "true";
			if (min == 0)
				prev = "false";
		}
	}
}
