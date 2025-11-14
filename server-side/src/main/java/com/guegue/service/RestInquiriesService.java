package com.guegue.service;

import com.guegue.dao.InquiriesDao;
import com.guegue.model.Inquiries;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestInquiriesService implements InquiriesService{

    private InquiriesDao inquiriesDao;

    public RestInquiriesService(InquiriesDao inquiriesDao){
        this.inquiriesDao = inquiriesDao;
    }

    @Override
    public List<Inquiries> getInquiries() {
        return inquiriesDao.getInquiries();
    }

    @Override
    public Inquiries getInquiryById(int id) {
        return inquiriesDao.getInquiryById(id);
    }

    @Override
    public Inquiries createInquiry(Inquiries newInquiry) {
        return inquiriesDao.createInquiry(newInquiry);
    }

    @Override
    public Inquiries updateInquiry(Inquiries updatedInquiry) {
        return inquiriesDao.updateInquiry(updatedInquiry);
    }

    @Override
    public int deleteInquiry(int id) {
        return inquiriesDao.deleteInquiry(id);
    }
}
