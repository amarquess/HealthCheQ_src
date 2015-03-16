/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.sw551.fairfield.healthcheq.withings;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Java object for response of Withings API.
 * 
 * @see http://www.withings.com/de/api#bodymetrics
 * @author Dennis Nobel
 * @since 1.5.0
 */
public class MeasureGroup {

	@SerializedName("attrib")
	public Attribute attribute;

	public Category category;

	public int date;

	@SerializedName("grpid")
	public int groupId;

	public List<Measure> measures;

}