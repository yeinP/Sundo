package com.example.demo.controller.notice;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.notice.NoticeDto;
import com.example.demo.dto.notice.PageHandler;
import com.example.demo.service.NoticeServiceImpl;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeServiceImpl noticeService;

    @Autowired
    public NoticeController(NoticeServiceImpl noticeService) {
        this.noticeService = noticeService;
    }
    
    @GetMapping("/write")
    public String writeForm(Model model) {
    	return "notice/noticeForm";
    }
    
    @PostMapping("/write")
    public String registerNotice(NoticeDto noticeDto) {
    	int rowCnt = this.noticeService.registerNotice(noticeDto);
    	if (rowCnt == 1) System.out.println("regist success");
    	return "redirect:/notice/list";
    }

    @GetMapping("/list")
    public String getList(Integer page, Integer pageSize, Model model) {
    	if (page == null) page = 1;
    	if (pageSize == null) pageSize = 10;
    	int totalNoticeCnt = this.noticeService.getNoticeCount();
    	PageHandler paging = new PageHandler(totalNoticeCnt, page, pageSize);

        List<NoticeDto> noticeList = this.noticeService.getNoticeList(pageSize, (page-1)*10);
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("paging", paging);
        return "notice/noticeList";
    }
    
    @GetMapping("/read")
    public String readNotice(Integer notice_no, Integer page, Integer pageSize, Model model) {
    	if (page == null) page = 1;
    	if (pageSize == null) pageSize = 10;
    	int totalNoticeCnt = this.noticeService.getNoticeCount();
    	PageHandler paging = new PageHandler(totalNoticeCnt, page, pageSize);
    	NoticeDto notice = this.noticeService.getNotice(notice_no);
    	boolean isEditing = false;
    	model.addAttribute("isEditing", isEditing);
    	model.addAttribute("notice", notice);
        model.addAttribute("paging", paging);
    	return "notice/notice";
    }
    
    @GetMapping("/modify")
    public String modifyNotice(Integer notice_no, Integer page, Integer pageSize, HttpServletRequest request, Model model) {
    	int totalNoticeCnt = this.noticeService.getNoticeCount();
    	PageHandler paging = new PageHandler(totalNoticeCnt, page, pageSize);
    	NoticeDto notice = this.noticeService.getNotice(notice_no);
    	boolean isEditing = true;
    	model.addAttribute("isEditing", isEditing);
    	model.addAttribute("notice", notice);
    	model.addAttribute("paging", paging);
    	return "notice/notice";
    }
    
    @PostMapping("/modify")
    public String reggisterNotice(Integer notice_no, NoticeDto noticeDto, Model model) {
    	int rowCnt = this.noticeService.modifyNotice(noticeDto, notice_no);
    	if (rowCnt == 1) System.out.println("modify success");
    	return "redirect:/notice/list";
    }
    
    @GetMapping("/delete")
    public String delete(Integer notice_no) {
    	int rowCnt = this.noticeService.removeNotice(notice_no);
    	if (rowCnt == 1) {
    		System.out.println(notice_no+" notice remove success");
    	} else {
    		System.out.println(notice_no+" notice remove fail");
    	}
    	return "redirect:/notice/list";
    }
    
    @GetMapping("/search")
    public String searchNotice(String searchType, String searchKeyword, Integer page, Integer pageSize,  Model model) {
    	System.out.println("searchType : "+searchType);
    	System.out.println("searchKeyword : "+searchKeyword);
    	if (page == null) page = 1;
    	if (pageSize == null) pageSize = 10;
    	int totalNoticeCnt = 1;
    	PageHandler paging = new PageHandler(totalNoticeCnt, page, pageSize);
    	if (searchType.equals("no")) {
    		NoticeDto result = this.noticeService.getSearchNotice(Integer.parseInt(searchKeyword));
    		System.out.println("no() executed");
    		System.out.println("result: "+result);
    		model.addAttribute("noticeList", result);
    		model.addAttribute("paging", paging);
    		return "notice/noticeSearchList";
    	}
    	if (searchType.equals("title")) {
    		System.out.println("title() executed");
    		int rowCnt = this.noticeService.getSimilarListCount(searchKeyword);
    		List<NoticeDto> result = this.noticeService.getSimilarList(searchKeyword, pageSize, (page-1)*10);
    		System.out.println("result : "+result);
    		paging = new PageHandler(rowCnt, page, pageSize);
    		model.addAttribute("noticeList", result);
    		model.addAttribute("paging", paging);
    		return "notice/noticeSearchList";
    	}
    	return "";
    }
}
