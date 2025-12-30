package com.lms.controller.admin;

import com.lms.dto.request.auth.profile.AdminFeedbackInstructorApplicationRequest;
import com.lms.dto.response.admin.user.InstructorApplicationResponse;
import com.lms.entity.common.PageResponse;
import com.lms.service.core.auth.admin.AdminManageUserService;
import com.lms.util.PageUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
public class ManagerUserController {

    private final AdminManageUserService adminManageUserService;

    @GetMapping("/view-application")
    public PageResponse<InstructorApplicationResponse> viewAllInstructorApplication(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10")  int size
    ) {
        List<InstructorApplicationResponse> list = adminManageUserService.getInstructorApplicationByAdmin();
        return PageUtil.paginate(list,page,size);
    }

    @GetMapping("/view-application/{applicationId}")
    public InstructorApplicationResponse viewSingleInstructorApplication(@PathVariable Integer applicationId) {
        return adminManageUserService.getSingleInstructorApplicationByAdmin(applicationId);
    }

    @PutMapping("/view-application/{applicationId}/feedback")
    public ResponseEntity<?> feedbackApplicationByAdmin(@PathVariable Integer applicationId, @Valid @RequestBody AdminFeedbackInstructorApplicationRequest request) {
        adminManageUserService.updateStatusInstructorApplicationByAdmin(applicationId,request);
        return ResponseEntity.ok("Update successfully");
    }

    @GetMapping("/view-application/search/{email}")
    public PageResponse<InstructorApplicationResponse> findApplicationByEmail(
            @PathVariable String email,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "2")  int size
    ) {
        List<InstructorApplicationResponse> list = adminManageUserService.findInstructorApplicationByAdmin(email);
        return PageUtil.paginate(list,page,size);
    }

    @GetMapping("/view-application/approve-filter/{isApproved}")
    public PageResponse<InstructorApplicationResponse> findApplicationByApprovedFilter(
            @PathVariable Integer isApproved,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10")  int size
    ) {
        List<InstructorApplicationResponse> list = adminManageUserService.findInstructorApplicationByApproveFilter(isApproved);
        return PageUtil.paginate(list,page,size);
    }

    @GetMapping("/view-application/day-filter/start={start}&end={end}")
    public PageResponse<InstructorApplicationResponse> findApplicationByBetweenDayFilter(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10")  int size
    ) {
        List<InstructorApplicationResponse> list = adminManageUserService.findInstructorApplicationByBetweenDayFilter(start, end);
        return PageUtil.paginate(list,page,size);
    }

}
