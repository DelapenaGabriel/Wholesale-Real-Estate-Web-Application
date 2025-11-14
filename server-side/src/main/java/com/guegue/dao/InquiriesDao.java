package com.guegue.dao;

import com.guegue.model.Inquiries;

import java.util.List;

public interface InquiriesDao {
    List<Inquiries> getInquiries();

    Inquiries getInquiryById(int id);

    Inquiries createInquiry(Inquiries newInquiry);

    Inquiries updateInquiry(Inquiries updatedInquiry);

    int deleteInquiry(int id);

}
