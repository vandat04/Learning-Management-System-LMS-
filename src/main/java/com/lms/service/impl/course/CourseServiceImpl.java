package com.lms.service.impl.course;

import com.lms.dto.request.course.InstructorCreateCourseRequest;
import com.lms.entity.course.Category;
import com.lms.entity.course.Course;
import com.lms.entity.course.CourseCategory;
import com.lms.exception.AppException;
import com.lms.repository.course.CategoryRepository;
import com.lms.repository.course.CourseCategoryRepository;
import com.lms.repository.course.CourseRepository;
import com.lms.service.core.course.CourseService;
import com.lms.util.Util;
import com.lms.util.Validate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseCategoryRepository courseCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final Validate validate;
    private final Util util;

    @Override
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    @Override
    public void createNewCourse(InstructorCreateCourseRequest request) {
        Integer userId = util.getUserId();
        if (!util.checkInstructorRole()){
            throw new AppException("Please update account become instructor account!", HttpStatus.BAD_REQUEST);
        }

        String title = request.getTitle().trim();
        String description = request.getDescription().trim();
        BigDecimal price = request.getPrice();
        BigDecimal priceWithOnl = request.getPriceWithOnl();
        List<Integer> listCategory = request.getListCategory();
        Integer status = request.getStatus();

        validate.checkNull(title);
        validate.validateBio(title);
        validate.validateByAI(title);

        validate.checkNull(description);
        validate.validateBio(description);
        validate.validateByAI(description);

        validate.isPositiveInteger(price);
        if (priceWithOnl != null) {
            validate.isPositiveInteger(priceWithOnl);
        }

        if (listCategory != null && !listCategory.isEmpty()){
            for (Integer item : listCategory){
                if (!categoryRepository.existsById(item)){
                    throw new AppException("Category not exist. Please choose again!", HttpStatus.BAD_REQUEST);
                }
            }
        }
        //Save DRAFT or PENDING đợi admin approve
        if (status != 1 && status != 2){
            throw new AppException("Status not exist. Please choose again!", HttpStatus.BAD_REQUEST);
        }

        Course course = new Course();
        course.setInstructorId(userId);
        course.setTitle(title);
        course.setDescription(description);
        course.setPrice(price);
        course.setPriceWithOnl(priceWithOnl);
        course.setStatus(status);
        course.setRatingAvg(BigDecimal.valueOf(0.0));
        course.setCreatedAt(LocalDateTime.now());
        Integer courseId = courseRepository.save(course).getId();

        if (listCategory != null && !listCategory.isEmpty()){
            for (Integer item : listCategory){
                if (!courseCategoryRepository.existsByCourseIdAndCategoryId(courseId, item)) {
                    CourseCategory courseCategory = new CourseCategory();
                    courseCategory.setCourseId(courseId);
                    courseCategory.setCategoryId(item);
                    courseCategoryRepository.save(courseCategory);
                }
            }
        }
    }
}
