package com.project.dnflol.util;

import lombok.Getter;

@Getter
public class BoardMinMax {
	int limit;
	int min;
	int max;
	boolean prevValid;
	boolean nextValid;
	public BoardMinMax(int limit) {
		this.limit = limit;
		this.min = 0;
		this.max = 100;
		prevValid = false;
		if (min < limit && limit <= max)
			nextValid = false;
		else
			nextValid = true;
	}
	public void next() {
		if (nextValid) {
			this.min = max;
			this.max += 100;
			prevValid = true;
			if (min < limit && limit <= max)
				nextValid = false;
		}
	}
	public void prev() {
		if (prevValid) {
			this.max = min;
			this.min -= 100;
			nextValid = true;
			if (min == 0)
				prevValid = false;
		}
	}
}
