package com.dcl.blog.model.dto;

import java.util.Date;
import java.util.List;

public class VoteDTO {
	private long id;
	private boolean publics;
	private boolean ones;
	private List<VoteItemDTO> voteItem;
	private Date startDate;
	private Date endDate;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public boolean isPublics() {
		return publics;
	}
	public void setPublics(boolean publics) {
		this.publics = publics;
	}
	public boolean isOnes() {
		return ones;
	}
	public void setOnes(boolean ones) {
		this.ones = ones;
	}
	public List<VoteItemDTO> getVoteItem() {
		return voteItem;
	}
	public void setVoteItem(List<VoteItemDTO> voteItem) {
		this.voteItem = voteItem;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
