/*
 * Copyright 2010 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lcm.domain.opta.moduleplanner.domain;

import java.util.List;

public class Shift extends AbstractPersistable {

	private ShiftDate shiftDate;
	private ShiftType shiftType;
	private int index;
	private float startTimeIndex;
	private float endTimeIndex;
	private List<Shift> shiftList;

	public ShiftDate getShiftDate() {
		return shiftDate;
	}

	public void setShiftDate(ShiftDate shiftDate) {
		this.shiftDate = shiftDate;
	}

	public ShiftType getShiftType() {
		return shiftType;
	}

	public void setShiftType(ShiftType shiftType) {
		this.shiftType = shiftType;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public float getStartTimeIndex() {
		return startTimeIndex;
	}

	public void setStartTimeIndex(float startTimeIndex) {
		this.startTimeIndex = startTimeIndex;
	}

	public float getEndTimeIndex() {
		return endTimeIndex;
	}

	public void setEndTimeIndex(float endTimeIndex) {
		this.endTimeIndex = endTimeIndex;
	}

	public List<Shift> getShiftList() {
		return shiftList;
	}

	public void setShiftList(List<Shift> shiftList) {
		this.shiftList = shiftList;
	}

}
