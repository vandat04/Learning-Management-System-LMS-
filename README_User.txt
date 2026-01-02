1. Get UserProfile
    SQL: users, user_role, user_profile
    Luồng: 1. Lấy userId từ token
           2. Lấy user, user_role, user_profile
           3. Tổng hợp và UserProfileResponse

2. Update Become Instructor
    SQL: instructor_application, certifications
    Json: form-data (title, file)
    Luồng: 1. Lấy userId từ token, check xem có spam apply không
           2. Kiểm tra xem list Tile và File có match nhau về số lượng không
           3. Validate cho từng tile, file -> Add và list
           4. Nếu không lỗi thì tạo new instructor_application và add vào certifications

3. View subscription plan
    - View all
    - View details
    - Search plan by name
    - Day/Duration/Price Filter
    - Buy plan