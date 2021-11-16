/*
 * Copyright 2002-2010 the original author or authors.
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

package org.springframework.http.converter.feed;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Item;
import static org.custommonkey.xmlunit.XMLAssert.*;
import org.custommonkey.xmlunit.XMLUnit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import org.springframework.http.MediaType;
import org.springframework.http.MockHttpInputMessage;
import org.springframework.http.MockHttpOutputMessage;

/** @author Arjen Poutsma */
public class RssChannelHttpMessageConverterTests {

	private RssChannelHttpMessageConverter converter;

	private Charset utf8;

	@Before
	public void setUp() {
		utf8 = Charset.forName("UTF-8");
		converter = new RssChannelHttpMessageConverter();
		XMLUnit.setIgnoreWhitespace(true);
	}

	@Test
	public void canRead() {
		assertTrue(converter.canRead(Channel.class, new MediaType("application", "rss+xml")));
		assertTrue(converter.canRead(Channel.class, new MediaType("application", "rss+xml", utf8)));
	}

	@Test
	public void canWrite() {
		assertTrue(converter.canWrite(Channel.class, new MediaType("application", "rss+xml")));
		assertTrue(converter.canWrite(Channel.class, new MediaType("application", "rss+xml", Charset.forName("UTF-8"))));
	}

	@Test
	public void read() throws IOException {
		InputStream is = getClass().getResourceAsStream("rss.xml");
		MockHttpInputMessage inputMessage = new MockHttpInputMessage(is);
		inputMessage.getHeaders().setContentType(new MediaType("application", "rss+xml", utf8));
		Channel result = converter.read(Channel.class, inputMessage);
		assertEquals("title", result.getTitle());
		assertEquals("http://example.com", result.getLink());
		assertEquals("description", result.getDescription());

		List items = result.getItems();
		assertEquals(2, items.size());

		Item item1 = (Item) items.get(0);
		assertEquals("title1", item1.getTitle());

		Item item2 = (Item) items.get(1);
		assertEquals("title2", item2.getTitle());
	}

	@Test
	public void write() throws IOException, SAXException {
		Channel channel = new Channel("rss_2.0");
		channel.setTitle("title");
		channel.setLink("http://example.com");
		channel.setDescription("description");

		Item item1 = new Item();
		item1.setTitle("title1");

		Item item2 = new Item();
		item2.setTitle("title2");

		List items = new ArrayList(2);
		items.add(item1);
		items.add(item2);
		channel.setItems(items);

		MockHttpOutputMessage outputMessage = new MockHttpOutputMessage();
		converter.write(channel, null, outputMessage);

		assertEquals("Invalid content-type", new MediaType("application", "rss+xml", utf8),
				outputMessage.getHeaders().getContentType());
		String expected = "<rss version=\"2.0\">" +
				"<channel><title>title</title><link>http://example.com</link><description>description</description>" +
				"<item><title>title1</title></item>" +
				"<item><title>title2</title></item>" +
				"</channel></rss>";
		assertXMLEqual(expected, outputMessage.getBodyAsString(utf8));

	}

	@Test
	public void writeOtherCharset() throws IOException, SAXException {
		Channel channel = new Channel("rss_2.0");
		channel.setTitle("title");
		channel.setLink("http://example.com");
		channel.setDescription("description");

		String encoding = "ISO-8859-1";
		channel.setEncoding(encoding);

		Item item1 = new Item();
		item1.setTitle("title1");

		MockHttpOutputMessage outputMessage = new MockHttpOutputMessage();
		converter.write(channel, null, outputMessage);

		assertEquals("Invalid content-type", new MediaType("application", "rss+xml", Charset.forName(encoding)),
				outputMessage.getHeaders().getContentType());
	}


}