/*
 * Copyright 2002-2013 the original author or authors.
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

package org.springframework.format.datetime;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.validation.DataBinder;

/**
 * @author Phillip Webb
 * @author Keith Donald
 * @author Juergen Hoeller
 */
public class DateFormattingTests {

	private FormattingConversionService conversionService = new FormattingConversionService();

	private DataBinder binder;

	@Before
	public void setUp() {
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		setUp(registrar);
	}

	private void setUp(DateFormatterRegistrar registrar) {
		DefaultConversionService.addDefaultConverters(conversionService);
		registrar.registerFormatters(conversionService);

		SimpleDateBean bean = new SimpleDateBean();
		bean.getChildren().add(new SimpleDateBean());
		binder = new DataBinder(bean);
		binder.setConversionService(conversionService);

		LocaleContextHolder.setLocale(Locale.US);
	}

	@After
	public void tearDown() {
		LocaleContextHolder.setLocale(null);
	}

	@Test
	public void testBindDate() {
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("date", "10/31/09 12:00 PM");
		binder.bind(propertyValues);
		assertEquals(0, binder.getBindingResult().getErrorCount());
		assertEquals("10/31/09 12:00 PM", binder.getBindingResult().getFieldValue("date"));
	}

	@Test
	public void testBindDateArray() {
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("date", new String[] {"10/31/09 12:00 PM"});
		binder.bind(propertyValues);
		assertEquals(0, binder.getBindingResult().getErrorCount());
	}

	@Test
	public void testBindDateAnnotated() {
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("dateAnnotated", "10/31/09");
		binder.bind(propertyValues);
		assertEquals(0, binder.getBindingResult().getErrorCount());
		assertEquals("10/31/09", binder.getBindingResult().getFieldValue("dateAnnotated"));
	}

	@Test
	public void testBindDateAnnotatedWithError() {
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("dateAnnotated", "Oct X31, 2009");
		binder.bind(propertyValues);
		assertEquals(1, binder.getBindingResult().getFieldErrorCount("dateAnnotated"));
		assertEquals("Oct X31, 2009", binder.getBindingResult().getFieldValue("dateAnnotated"));
	}

	@Test
	@Ignore
	public void testBindDateAnnotatedWithFallbackError() {
		// TODO This currently passes because of the Date(String) constructor fallback is used
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("dateAnnotated", "Oct 031, 2009");
		binder.bind(propertyValues);
		assertEquals(1, binder.getBindingResult().getFieldErrorCount("dateAnnotated"));
		assertEquals("Oct 031, 2009", binder.getBindingResult().getFieldValue("dateAnnotated"));
	}

	@Test
	public void testBindCalendar() {
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("calendar", "10/31/09 12:00 PM");
		binder.bind(propertyValues);
		assertEquals(0, binder.getBindingResult().getErrorCount());
		assertEquals("10/31/09 12:00 PM", binder.getBindingResult().getFieldValue("calendar"));
	}

	@Test
	public void testBindCalendarAnnotated() {
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("calendarAnnotated", "10/31/09");
		binder.bind(propertyValues);
		assertEquals(0, binder.getBindingResult().getErrorCount());
		assertEquals("10/31/09", binder.getBindingResult().getFieldValue("calendarAnnotated"));
	}

	@Test
	public void testBindLong() {
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("millis", "1256961600");
		binder.bind(propertyValues);
		assertEquals(0, binder.getBindingResult().getErrorCount());
		assertEquals("1256961600", binder.getBindingResult().getFieldValue("millis"));
	}

	@Test
	public void testBindLongAnnotated() {
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("millisAnnotated", "10/31/09");
		binder.bind(propertyValues);
		assertEquals(0, binder.getBindingResult().getErrorCount());
		assertEquals("10/31/09", binder.getBindingResult().getFieldValue("millisAnnotated"));
	}

	@Test
	public void testBindISODate() {
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("isoDate", "2009-10-31");
		binder.bind(propertyValues);
		assertEquals(0, binder.getBindingResult().getErrorCount());
		assertEquals("2009-10-31", binder.getBindingResult().getFieldValue("isoDate"));
	}

	@Test
	public void testBindISOTime() {
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("isoTime", "12:00:00.000-0500");
		binder.bind(propertyValues);
		assertEquals(0, binder.getBindingResult().getErrorCount());
		assertEquals("17:00:00.000+0000", binder.getBindingResult().getFieldValue("isoTime"));
	}

	@Test
	public void testBindISODateTime() {
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("isoDateTime", "2009-10-31T12:00:00.000-0800");
		binder.bind(propertyValues);
		assertEquals(0, binder.getBindingResult().getErrorCount());
		assertEquals("2009-10-31T20:00:00.000+0000", binder.getBindingResult().getFieldValue("isoDateTime"));
	}

	@Test
	public void testBindNestedDateAnnotated() {
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("children[0].dateAnnotated", "10/31/09");
		binder.bind(propertyValues);
		assertEquals(0, binder.getBindingResult().getErrorCount());
		assertEquals("10/31/09", binder.getBindingResult().getFieldValue("children[0].dateAnnotated"));
	}

	@Test
	public void dateToStringWithoutGlobalFormat() throws Exception {
		Date date = new Date();
		Object actual = this.conversionService.convert(date, TypeDescriptor.valueOf(Date.class), TypeDescriptor.valueOf(String.class));
		String expected = date.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void dateToStringWithGlobalFormat() throws Exception {
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter());
		setUp(registrar);
		Date date = new Date();
		Object actual = this.conversionService.convert(date, TypeDescriptor.valueOf(Date.class), TypeDescriptor.valueOf(String.class));
		String expected = new DateFormatter().print(date, Locale.US);
		assertEquals(expected, actual);
	}

	@Test
	@SuppressWarnings("deprecation")
	public void stringToDateWithoutGlobalFormat() throws Exception {
		// SPR-10105
		String string = "Sat, 12 Aug 1995 13:30:00 GM";
		Date date = this.conversionService.convert(string, Date.class);
		assertThat(date, equalTo(new Date(string)));
	}

	@Test
	public void stringToDateWithGlobalFormat() throws Exception {
		// SPR-10105
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		DateFormatter dateFormatter = new DateFormatter();
		dateFormatter.setIso(ISO.DATE_TIME);
		registrar.setFormatter(dateFormatter);
		setUp(registrar);
		// This is a format that cannot be parsed by new Date(String)
		String string = "2009-06-01T14:23:05.003+0000";
		Date date = this.conversionService.convert(string, Date.class);
		assertNotNull(date);
	}


	@SuppressWarnings("unused")
	private static class SimpleDateBean {

		@DateTimeFormat
		private Date date;

		@DateTimeFormat(style="S-")
		private Date dateAnnotated;

		@DateTimeFormat
		private Calendar calendar;

		@DateTimeFormat(style="S-")
		private Calendar calendarAnnotated;

		private Long millis;

		private Long millisAnnotated;

		@DateTimeFormat(pattern="M/d/yy h:mm a")
		private Date dateAnnotatedPattern;

		@DateTimeFormat(iso=ISO.DATE)
		private Date isoDate;

		@DateTimeFormat(iso=ISO.TIME)
		private Date isoTime;

		@DateTimeFormat(iso=ISO.DATE_TIME)
		private Date isoDateTime;

		private final List<SimpleDateBean> children = new ArrayList<SimpleDateBean>();

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public Date getDateAnnotated() {
			return dateAnnotated;
		}

		public void setDateAnnotated(Date dateAnnotated) {
			this.dateAnnotated = dateAnnotated;
		}

		public Calendar getCalendar() {
			return calendar;
		}

		public void setCalendar(Calendar calendar) {
			this.calendar = calendar;
		}

		public Calendar getCalendarAnnotated() {
			return calendarAnnotated;
		}

		public void setCalendarAnnotated(Calendar calendarAnnotated) {
			this.calendarAnnotated = calendarAnnotated;
		}

		public Long getMillis() {
			return millis;
		}

		public void setMillis(Long millis) {
			this.millis = millis;
		}

		@DateTimeFormat(style="S-")
		public Long getMillisAnnotated() {
			return millisAnnotated;
		}

		public void setMillisAnnotated(@DateTimeFormat(style="S-") Long millisAnnotated) {
			this.millisAnnotated = millisAnnotated;
		}

		public Date getIsoDate() {
			return isoDate;
		}

		public void setIsoDate(Date isoDate) {
			this.isoDate = isoDate;
		}

		public Date getIsoTime() {
			return isoTime;
		}

		public void setIsoTime(Date isoTime) {
			this.isoTime = isoTime;
		}

		public Date getIsoDateTime() {
			return isoDateTime;
		}

		public void setIsoDateTime(Date isoDateTime) {
			this.isoDateTime = isoDateTime;
		}

		public List<SimpleDateBean> getChildren() {
			return children;
		}
	}

}
