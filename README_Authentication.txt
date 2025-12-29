                I.Authentication/
1. Register:
    SQL: users, user_roles , user_profiles
    Json:(email, password, fullName)
    Luồng: 1. Kiểm tra dữ liệu sau khi nhập
             + Check null cho tất cả
             + email: check exist trong DB, check form
             + password: check độ dài tối thiếu(8), check điều kiện pass
             + fullName: check độ dài(2-50), check kí tự đặc biệt, check thêm bằng AI cho chuẩn.
           2. Tạo dữ liệu cho bảng user:
             + hashPassword, active = true, AuthProvider = LOCAL
           3. Tạo role cho user mới
           4. Tạo profile với fullName
                    
2. Login:
    SQL: users
    Json: (gmail, password)
    Luồng: 1. Kiểm tra email có tồn tại trong DB hay chưa
           2. Lấy ra user với email đó
           3. Kiểm tra password != null
           4. Kiểm tra password có match với hashPassword
           5. Kiểm tra active = 1
           6. Lấy ra role của user
           7. Generate token

3. User Update Profile:
    SQL: users, user_profiles
    Json: (userId, fullName, phone, bio)
    Luồng: 1. Lấy ra user theo token đã login
           2. Lấy ra userProfile theo userId của user
           3. set Update trong user
           4. Kiểm tra thông tin fullName, phone, bio
           5. Cập nhật các thông tin vào userProfile nếu có thay đổi
           6. Trả về User FullInfor

4. User Update Image Profile:
    SQL: users, user_profiles
    Json: (file)
    Luồng: 1. Lấy ra user theo token đã login
           2. Lấy ra userProfile theo userId của user
           3. Set Update trong user
           3. Kiểm tra file đẩy lên cloud lấy Url
           4. Cập nhật Url vào userProfile
           5. Trả về User FullInfor

5. User Change Password:
    SQL: users
    Json: (oldPassword, newPassword, confirmPassword)
    Luồng: 1. Lấy ra user theo token đã login
           2. Check xem oldPassword có match với hashPassword
           3. Check điều kiện của newPassword, confirmPassword
           4. HashPassword cho newPassword
           5. Lưu lại newPassowrd


6. Login With Google
   Json: (googleToken)
---> FE lấy googleToken , Lấy các thông tin liên quan từ token như: email, fullname, avatarUrl, googleId, Kiểm tra xem user đã tồn tại hay chưa,
nếu rồi thì kiểm tra xem là dạng LOCAL hay GOOGLE, nếu LOCAL out, user = null thì tạo account mới, nếu là tài khoản GOOGLE thì kiểm tra googleId có trùng không,
nếu trùng thì lấy Role rồi tạo JWT
    SQL: users, user_roles , user_profiles

7. Quên password
    SQL: user, otp_reset_password
    Json: (email)
    Luồng: 1. Check xem email có tồn tại trong DB chưa, check active = 1
           2. Tạo OTP có 6 chữ bất kì. hash OTP.
           3. lưu vào bảng  otp_reset_password với hạn sử dụng 3p
           4. Gửi OTP đó đến email yêu cầu

    Đổi mật khẩu:
        Json: (email, OTP, newPassword, confirmPasswod)
        Luồng: 1. Kiểm tra OTP còn lượt nhập không
               2. Check newPassword, confirmPassword theo điều kiện
               3. Hash và lưu lại newPassword theo user của email
               4. Xoá OTP đã dùng

7. Log out:
    SQL: invalid_tokens
    Luồng: 1. Lưu vào invalid_token để không thể sử dụng ở mọi nơi nữa


------
Refresh:
Tự động xoá token logout mỗi ngày
