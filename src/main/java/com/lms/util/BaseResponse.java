package com.lms.util;

import com.lms.dto.response.admin.user.InstructorApplicationResponse;
import com.lms.dto.response.auth.profile.UserProfileResponse;
import com.lms.dto.response.course.CategoryResponse;
import com.lms.dto.response.course.CourseDetailsResponse;
import com.lms.entity.auth.*;
import com.lms.entity.course.Category;
import com.lms.entity.course.Course;
import com.lms.entity.course.CourseCategory;
import com.lms.exception.AppException;
import com.lms.repository.auth.*;
import com.lms.repository.course.*;
import com.lms.repository.learning.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseResponse {

    private final Validate validate;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final InstructorApplicationRepository instructorApplicationRepository;
    private final CertificationRepository certificationRepository;
    private final CourseRepository courseRepository;
    private final CourseCategoryRepository courseCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final LessonRepository lessonRepository;
    private final ClassEntityRepository classEntityRepository;
    private final EnrollmentRepository enrollmentRepository;

    public UserProfileResponse getUserProfile(Integer userId) {
        validate.checkNull(userId);

        UserProfileResponse userProfileResponse = new UserProfileResponse();

        User user = userRepository.findById(userId);
        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        UserRole userRole = userRoleRepository.findByUserId(user.getId());
        Role role = roleRepository.findById(userRole.getRoleId())
                .orElseThrow(() -> new AppException("Role error", HttpStatus.BAD_REQUEST));

        userProfileResponse.setUserId(userId);
        userProfileResponse.setGmail(user.getEmail());
        userProfileResponse.setFullName(userProfile.getFullName());
        userProfileResponse.setPhone(userProfile.getPhone());
        userProfileResponse.setAvatarUrl(userProfile.getAvatarUrl());
        userProfileResponse.setBio(userProfile.getBio());
        userProfileResponse.setRatingAvg(userProfile.getRatingAvg());
        userProfileResponse.setNumberReview(userProfile.getNumberReview());
        userProfileResponse.setRole(role.getName());
        userProfileResponse.setActive(user.getIsActive());
        userProfileResponse.setCreatedAt(user.getCreatedAt());
        userProfileResponse.setUpdatedAt(user.getUpdatedAt());

        return userProfileResponse;
    }

    public List<InstructorApplicationResponse> getInstructorApply(Integer userId) {
        validate.checkNull(userId);

        List<InstructorApplicationResponse> list = new ArrayList<>();

        InstructorApplicationResponse profile;
        UserProfileResponse user = getUserProfile(userId);
        List<InstructorApplication> applyList = instructorApplicationRepository.findInstructorApplicationByUserId(userId);
        List<Certification> cerList;

        for (InstructorApplication item : applyList) {
            cerList = certificationRepository.findCertificationByApplicationId(item.getId());

            profile = new InstructorApplicationResponse();
            profile.setUserProfileResponse(user);
            profile.setInstructorApplication(item);
            profile.setCertification(cerList);

            list.add(profile);
        }

        return list;
    }

    public InstructorApplicationResponse getSingleInstructorApply(Integer applicationId) {
        //Get Instructor application by applicationId
        validate.checkNull(applicationId);
        InstructorApplication apply = instructorApplicationRepository.findById(applicationId).orElseThrow(()
                -> new AppException("Can not find apply by your id", HttpStatus.BAD_REQUEST));
        //Get user full infor
        Integer userId = apply.getUserId();
        UserProfileResponse user = getUserProfile(userId);
        //Get Certification by applicationId
        List<Certification> cerList = certificationRepository.findCertificationByApplicationId(applicationId);
        //Get Instructor application full
        InstructorApplicationResponse profile = new InstructorApplicationResponse();
        profile.setUserProfileResponse(user);
        profile.setInstructorApplication(apply);
        profile.setCertification(cerList);

        return profile;
    }

    public CourseDetailsResponse getFullInformationOfCourse(Integer courseId) {
        validate.checkNull(courseId);
        CourseDetailsResponse courseDetailsResponse = new CourseDetailsResponse();
        //Create course infor
        Course course = courseRepository.findById(courseId).orElseThrow(()
                -> new AppException("The course you choose not exist", HttpStatus.BAD_REQUEST));
        courseDetailsResponse.setId(courseId);
        courseDetailsResponse.setInstructorProfileResponse(getUserProfile(course.getInstructorId()));
        courseDetailsResponse.setTitle(course.getTitle());
        courseDetailsResponse.setDescription(course.getDescription());
        courseDetailsResponse.setPrice(course.getPrice());
        courseDetailsResponse.setPriceWithOnl(course.getPriceWithOnl());
        switch (course.getStatus()) {
            case 1:
                courseDetailsResponse.setStatus("DRAFT");
                break;
            case 2:
                courseDetailsResponse.setStatus("PENDING");
                break;
            case 3:
                courseDetailsResponse.setStatus("PUBLISHED");
                break;
            case 4:
                courseDetailsResponse.setStatus("ARCHIVED/INACTIVE");
                break;
            default:
                throw new AppException("Course status not match", HttpStatus.BAD_REQUEST);
        }
        courseDetailsResponse.setRatingAvg(course.getRatingAvg());
        courseDetailsResponse.setCreatedAt(course.getCreatedAt());
        courseDetailsResponse.setUpdatedAt(course.getUpdatedAt());
        courseDetailsResponse.setApproveBy(course.getApprovedBy());
        courseDetailsResponse.setApproveAt(course.getApprovedAt());
        //Get Category
        List<Category> listCourseCategory = new ArrayList<>();
        List<Integer> listCategories = courseCategoryRepository.findCategoryIdByCourseId(courseId);
        for (Integer item : listCategories) {
            Category category = categoryRepository.findById(item).orElseThrow(()
                    -> new AppException("Category not exist. Please choose again!", HttpStatus.BAD_REQUEST));
            listCourseCategory.add(category);
        }
        courseDetailsResponse.setListCourseCategory(listCourseCategory);
        courseDetailsResponse.setCountLesson(lessonRepository.countByCourseId(courseId));
        courseDetailsResponse.setCountClass(enrollmentRepository.countClassByCourseId(courseId));
        courseDetailsResponse.setCountStudent(enrollmentRepository.countStudentByCourseId(courseId));

        return courseDetailsResponse;
    }
}
