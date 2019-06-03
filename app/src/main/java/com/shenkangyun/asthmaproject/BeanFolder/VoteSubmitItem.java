package com.shenkangyun.asthmaproject.BeanFolder;

import java.util.ArrayList;

public class VoteSubmitItem {
	//题目id
	public int itemId;
	//调查问题
	public String voteQuestion;
	//问卷调查选项
	public ArrayList<String> voteAnswers=new ArrayList<String>();
	//问卷调查得分
	public int resultScore;
	
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getVoteQuestion() {
		return voteQuestion;
	}
	public void setVoteQuestion(String voteQuestion) {
		this.voteQuestion = voteQuestion;
	}
	public ArrayList<String> getVoteAnswers() {
		return voteAnswers;
	}
	public void setVoteAnswers(ArrayList<String> voteAnswers) {
		this.voteAnswers = voteAnswers;
	}
	public int getResultScore() {
		return resultScore;
	}
	public void setResultScore(int resultScore) {
		this.resultScore = resultScore;
	}
	@Override
	public String toString() {
		return "VoteSubmitItem [itemId=" + itemId + ", voteQuestion="
				+ voteQuestion + ", voteAnswers=" + voteAnswers
				+ ", resultScore=" + resultScore + "]";
	}	
	
}
