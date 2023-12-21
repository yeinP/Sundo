package com.example.demo.service;

import com.example.demo.dto.notice.NoticeDto;
import com.example.demo.repository.NoticeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl {

    @Autowired
    private NoticeDao noticeDao;
    
    public int registerNotice(NoticeDto noticeDto) {
    	return this.noticeDao.insertOne(noticeDto);
    }
    
    public int getNoticeCount() {
    	return this.noticeDao.count();
    }

    public List<NoticeDto> getNoticeList(int pageSize, int offset) {
        return this.noticeDao.selectAll(pageSize, offset);
    }
    
    public NoticeDto getNotice(int notice_no) {
    	return this.noticeDao.selectOne(notice_no);
    }
    
    public int modifyNotice(NoticeDto noticeDto, int notice_no) {
    	return this.noticeDao.updateOne(noticeDto, notice_no);
    }
    
    public int removeNotice(int notice_no) {
    	return this.noticeDao.deleteOne(notice_no);
    }
    
    public int getSimilarListCount(String searchKeyword) {
    	return this.noticeDao.selectSimilarListCount(searchKeyword);
    }
    
    public List<NoticeDto> getSimilarList(String searchKeyword, int pageSize, int offset) {
    	return this.noticeDao.selectSimilarList(searchKeyword, pageSize, offset);
    }
    
    public NoticeDto getSearchNotice(int searchKeyword) {
    	return this.noticeDao.selectSearchNotice(searchKeyword);
    }
}
