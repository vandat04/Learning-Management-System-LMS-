            INSTRUCTOR APPLICATION

1. View Instructor Application
    SQL: user, instructor_application, certifications
    Response: UserProfileResponse + InstructorApplication + List<Certification>
    Luồng: 0. Tạo baseResponse gồm thông tin 3 bảng: user, instructor_application, certifications
           1. Láy ra danh sách userId trong Instructor Application
           2. Duyệt qua từng userId
           3. Lấy ra danh sách theo từng userId
*: Tại sao không truy ván trực tiếp: Sử dụng lại các phương thức bên dưới đã tạo sẵn.

2. View Single Apply By ID
    SQL: user, instructor_application, certifications
    Response: UserProfileResponse + InstructorApplication + List<Certification>
    Json: applicationId
    Luồng: 0. Tạo baseResponse gồm thông tin 3 bảng: user, instructor_application, certifications
           1. Lấy theo applicationId

3. Feedback Apply
    SQL:  user_role, instructor_application, certifications
    Link: applicationId --- Json: isApproved, adminFeedback
    Luồng: 1. Check isApproved, adminFeedback
           2. Láy ra instructor application
           3. Save thông tin vào isApproved, adminFeedback, reviewAt
           4. Nếu isApprove = 1: Cập nhật lại role cho user thông qua userId của instructor_application

4. Search Apply By email
    SQL:  users, instructor_application, certifications
    Link: email
    Luồng: 1. Lấy ra danh sách userId trong users
           2. Duyệt qua từng userId
           3. Lấy ra danh sách theo từng userId

5. Filter by isApproved
   SQL:  users, instructor_application, certifications
   Link: isApprove
   Luồng: 1. Lấy ra danh sách applicationId theo isApprove
          2. Lấy ra danh sách theo từng applicationId

6. Filrer by Between day
   SQL:  users, instructor_application, certifications
   Link: start, end
   Luồng: 1. Lấy ra danh sách applicationId theo start between end
          2. Lấy ra danh sách theo từng applicationId

----------
Phân trang