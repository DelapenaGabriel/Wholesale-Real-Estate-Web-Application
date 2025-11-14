import axios from "axios";

export default {
  // 🟩 Get all inquiries
  getInquiries() {
    return axios.get("/api/inquiries");
  },

  // 🟦 Get a single inquiry by ID
  getInquiryById(id) {
    return axios.get(`/api/inquiries/${id}`);
  },

  // 🟨 Create a new inquiry
  createInquiry(inquiry) {
    return axios.post("/api/inquiries", inquiry);
  },

  // 🟧 Update an existing inquiry
  updateInquiry(id, inquiry) {
    return axios.put(`/api/inquiries/${id}`, inquiry);
  },

  // 🟥 Delete an inquiry
  deleteInquiry(id) {
    return axios.delete(`/api/inquiries/${id}`);
  },
};
