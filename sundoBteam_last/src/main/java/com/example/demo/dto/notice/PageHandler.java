package com.example.demo.dto.notice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PageHandler {
	private int totalNoticeCnt;
	private int pageSize;
	private int naviSize = 10;
	private int totalPageCnt;
	private int page;
	private int beginPage;
	private int endPage;
	private boolean showPrev;
	private boolean showNext;
	
	public PageHandler(int totalNoticeCnt, int page) {
		this(totalNoticeCnt, page, 10);
	}
	public PageHandler(int totalNoticeCnt, int page, int pageSize) {
		super();
		this.totalNoticeCnt = totalNoticeCnt;
		this.pageSize = pageSize;
		this.page = page;
		
		totalPageCnt = (int)Math.ceil((double)totalNoticeCnt / pageSize);
		beginPage = (page-1) / 10 * 10 + 1;
		endPage = Math.min(beginPage + 10 - 1, totalPageCnt);
		
		showPrev = beginPage != 1;
		showNext = endPage != totalPageCnt;
	}
	
	void print() {
		System.out.println("Current page: "+page);
		System.out.print(showPrev ? "[Prev] " : "");
		for (int i=beginPage; i<=endPage; i++) {
			System.out.print(i+" ");
		}
		System.out.println(showNext ? "[Next]" : "");
	}
	
	
}
