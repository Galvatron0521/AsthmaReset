package com.shenkangyun.asthmaproject.Utils;


import com.shenkangyun.asthmaproject.BeanFolder.VoteSubmitItem;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class XMLContentHandler extends DefaultHandler {
	private ArrayList<VoteSubmitItem> questions = null;
	private ArrayList<String> options = null;
	private VoteSubmitItem currentQuestion;

	private String tagName = null;// 当前解析的元素标签

	public ArrayList<VoteSubmitItem> getQuestions() {
		return questions;
	}

	/* 接收文档的开始的通知。 */

	@Override
	public void startDocument() throws SAXException {
		questions = new ArrayList<VoteSubmitItem>();
	}

	/* 接收字符数据的通知。 */

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (tagName != null) {
			String data = new String(ch, start,length);
			if (tagName.equals("title")) {
				this.currentQuestion.setVoteQuestion(data);
			} else if (tagName.equals("option")) {
				options.add(data);
			}
		}
	}

	/*
	 * 接收元素开始的通知。 参数意义如下： namespaceURI：元
	 * 
	 * 素的命名空间 localName ：元素的本地名称（不带前缀） qName ：元素的限定名（带前缀） atts ：元素的属性集合
	 */

	@Override
	public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
		if (localName.equals("Question")) {
			currentQuestion = new VoteSubmitItem();
//			currentQuestion.setId(Integer.parseInt(atts.getValue("id")));
		}else if(localName.equals("Options")){
			options=new ArrayList<String>();
		}
		this.tagName = localName;
		
	}

	/*
	 * 接收文档的结尾的通知。 参数意义如下： uri ：元素的命
	 * 
	 * 名空间 localName ：元素的本地名称（不带前缀） name ：元素的限定名（带前缀）
	 */

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		if (localName.equals("Question")) {
			questions.add(currentQuestion);
			currentQuestion = null;
		} else if (localName.equals("Options")) {
			currentQuestion.setVoteAnswers(options);
		}
		this.tagName = null;
	}
} 
