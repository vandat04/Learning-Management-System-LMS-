                I.Authentication/
1. Register:
    Json:(gmail, password, fullname)
    Check:
        -Gmail: + Không null
                + Đúng định dạng (@gmail.com)
                + Chưa tồn tại
        -Password: + Không null
                   + >= 8 kí tự
                   + Chữ hoa, thường, số, kí tự đặc biệt
        - FullName: + Dài hơn 2, ngắn hơn 50
                    + Không null
                    + Không có kí tự đặc biệt, số
                    
2. Login:
    Json: (gmail, password)
    Check:
        -Gmail: + Không null
                + Đúng định dạng (@gmail.com)
        -Password: + Không null
                   + >= 8 kí tự
                   + Chữ hoa, thường, số, kí tự đặc biệt

3. User Update Profile:
    Json: (userId, fullName, phone, avatar, bio)
    Check:
        - UserID: + Check null
        - FullName: + Dài hơn 2, ngắn hơn 50
                    + Không null
                    + Không có kí tự đặc biệt, số
        - Phone: + Không null
                 + = 10 số
                 + Bắt đầu bằng số 0
---> Kiểm tra userId , Lấy User thông qua userId (cập nhật updated_at) , Lấy UserProfile (cập nhật lại thông tin cần cập nhật)

4. User Change Password:
    Json: (oldPassword, newPassword, confirmPassword)
    Check: 
        - Như check pass bthuong
        - oldPassword tồn tại
---> Check oldPassword == userPassword, validate cho newPassword, newPassword == confirmPassword, update Password

5. Login With Google
   Json: (googleToken)
---> FE lấy googleToken , Lấy các thông tin liên quan từ token như: email, fullname, avatarUrl, googleId, Kiểm tra xem user đã tồn tại hay chưa,
nếu rồi thì kiểm tra xem là dạng LOCAL hay GOOGLE, nếu LOCAL out, user = null thì tạo account mới, nếu là tài khoản GOOGLE thì kiểm tra googleId có trùng không,
nếu trùng thì lấy Role rồi tạo JWT

6. Quên password
    - Sau khi nhập email và ấn gửi OTP
    - Check email có tồn tại user hay chưa, kiểm tra tài khoản lại gì, còn hoạt động không
    - Tạo 1 bảng trong database với OTP(id, email, code, start_at, end_at) - end_at sau 3p kể từ khi tạo
    - Lưu OTP vào bảng OTP --> Gửi mail với nội dung(Code:... + Link reset password)
    - Kiểm tra xem OTP còn hoạt động hay không thông qua end_at.
        + Nếu còn chuyển qua mục nhập newpass + confirmpass.
        + Nếu hết thì bắt gửi lại OTP mới
    - Sau khi kiểm tra xoá bỏ OTP đó

7. Đổi mật khẩu:
    - Nhập OTP, newpassword, confirmpasswod
    - Kiểm tra OTP còn hoạt động hay không
    - Kiêm tra newpassword, confirmpassword đủ điều kiện, và trùng nhau khoong


8. Log out:
    - Lưu token và thời gian hết hạn vào 1 bảng sau khi ấn logout, khi đã log out thì không thể sử dụng được token đó nữa
    - Sau 1 ngày server tự reset để xoá các token đã hết hạn.