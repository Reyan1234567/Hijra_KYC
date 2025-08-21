package com.example.hijra_kyc.controller;


import com.example.hijra_kyc.dto.BackReasonDto.BackReasonInDto;
import com.example.hijra_kyc.dto.BackReasonDto.BackReasonOutDto;
import com.example.hijra_kyc.dto.FormDto.*;
import com.example.hijra_kyc.service.DistributorService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.hijra_kyc.service.BaseService;
import com.example.hijra_kyc.service.MakeFormService;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@Slf4j
@RestController
@RequestMapping("makeForm")
@RequiredArgsConstructor
public class MakeFormController {
    private final MakeFormService makeFormService;
    private final BaseService baseService;
    private final DistributorService distributorService;

    private Integer drafts = 0;
    private Integer pending = 1;
    private Integer approved = 2;
    private Integer rejected = 3;

    @PreAuthorize("hasRole('maker')")
    @GetMapping
    public ResponseEntity<?> get(@RequestParam("makerId") Long makerId, @RequestParam("date") Instant date, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNumber") Integer pageNumber) {
        MakeFormPage makes = makeFormService.getAll(makerId, date, pageSize, pageNumber);
        return ResponseEntity.ok(makes);
    }

    @PreAuthorize("hasRole('maker')")
    @GetMapping("/draft")
    public ResponseEntity<?> getDrafts(@RequestParam("makerId") Long makerId, @RequestParam("date") Instant date, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNumber") Integer pageNumber) {
        MakeFormPage makes = makeFormService.getAllDrafts(makerId, date, pageSize, pageNumber, drafts);
        return ResponseEntity.ok(makes);
    }

    @PreAuthorize("hasRole('maker')")
    @GetMapping("/pending")
    public ResponseEntity<?> getPending(@RequestParam("makerId") Long makerId, @RequestParam("date") Instant date, @RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber) {
        MakeFormPage makes = makeFormService.getAllDrafts(makerId, date, pageSize, pageNumber, pending);
        return ResponseEntity.ok(makes);
    }

    @PreAuthorize("hasRole('maker')")
    @GetMapping("/approved")
    public ResponseEntity<?> getAccepted(@RequestParam("makerId") Long makerId, @RequestParam("date") Instant date, @RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber) {
        MakeFormPage makes = makeFormService.getAllDrafts(makerId, date, pageSize, pageNumber, approved);
        return ResponseEntity.ok(makes);
    }

    @PreAuthorize("hasRole('maker')")
    @GetMapping("/rejected")
    public ResponseEntity<?> getRejected(@RequestParam("makerId") Long makerId, @RequestParam("date") Instant date, @RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber) {
        MakeFormPage makes = makeFormService.getAllDrafts(makerId, date, pageSize, pageNumber, rejected);
        return ResponseEntity.ok(makes);
    }

    @PreAuthorize("hasRole('HO_Manager')")
    @GetMapping("/manager")
    public ResponseEntity<?> getAll(@RequestParam("date") Instant date, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNumber") Integer pageNumber) {
        MakeFormPage makes = makeFormService.getManager(date, pageSize, pageNumber);
        return ResponseEntity.ok(makes);
    }

    @PreAuthorize("hasRole('HO_Manager')")
    @GetMapping("/manager/pending")
    public ResponseEntity<?> getAllPending(@RequestParam("date") Instant date, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNumber") Integer pageNumber) {
        MakeFormPage makes = makeFormService.getManagerStatus(date, pageSize, pageNumber, pending);
        return ResponseEntity.ok(makes);
    }

    @PreAuthorize("hasRole('HO_Manager')")
    @GetMapping("/manager/approved")
    public ResponseEntity<?> getAllApproved(@RequestParam("date") Instant date, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNumber") Integer pageNumber) {
        MakeFormPage makes = makeFormService.getManagerStatus(date, pageSize, pageNumber, approved);
        return ResponseEntity.ok(makes);
    }

    @PreAuthorize("hasRole('HO_Manager')")
    @GetMapping("/manager/rejected")
    public ResponseEntity<?> getAllRejected(@RequestParam("date") Instant date, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNumber") Integer pageNumber) {
        MakeFormPage makes = makeFormService.getManagerStatus(date, pageSize, pageNumber, rejected);
        return ResponseEntity.ok(makes);
    }

    @PreAuthorize("hasRole('HO_Checker')")
    @GetMapping("/getHo")
    public ResponseEntity<?> getWithHoUserId(@RequestParam("hoUserId") Long hoUserId, @RequestParam("date") Instant date, @RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        MakeFormPage makes = makeFormService.getWithHoUserId(hoUserId, date, pageNumber, pageSize);
        return ResponseEntity.ok(makes);
    }

    @PreAuthorize("hasRole('HO_Checker')")
    @GetMapping("/getHo/pending")
    public ResponseEntity<?> getWithHoUserIdPending(@RequestParam("hoUserId") Long hoUserId, @RequestParam("date") Instant date, @RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        MakeFormPage makes = makeFormService.getWithHoUserIdStatus(hoUserId, date, pending, pageNumber, pageSize);
        return ResponseEntity.ok(makes);
    }

    @PreAuthorize("hasRole('HO_Checker')")
    @GetMapping("/getHo/rejected")
    public ResponseEntity<?> getWithHoUserIdRejected(@RequestParam("hoUserId") Long hoUserId, @RequestParam("date") Instant date, @RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        MakeFormPage makes = makeFormService.getWithHoUserIdStatus(hoUserId, date, rejected, pageNumber, pageSize);
        return ResponseEntity.ok(makes);
    }

    @PreAuthorize("hasRole('HO_Checker')")
    @GetMapping("/getHo/approved")
    public ResponseEntity<?> getWithHoUserIdApproved(@RequestParam("hoUserId") Long hoUserId, @RequestParam("date") Instant date, @RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        MakeFormPage makes = makeFormService.getWithHoUserIdStatus(hoUserId, date, approved, pageNumber, pageSize);
        return ResponseEntity.ok(makes);
    }

    @PatchMapping("/updateStatus/{id}")
    public ResponseEntity<?> statusChange(@PathVariable Long id, @RequestParam("status") int statusNumber) {
        MakeFormDisplayDto approve = makeFormService.changeStatus(id, statusNumber);
        return ResponseEntity.ok(approve);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAll(@RequestParam("makerId") Long makerId, @RequestParam("status") Long status) {
        List<MakeFormOutDto> allMakes = makeFormService.get(makerId, status);
        return ResponseEntity.ok(allMakes);
    }

    @PostMapping("/status")
    public ResponseEntity<?> updateAssign(@RequestParam("id") Long id, @RequestParam("hoId") Long hoId) {
        MakeFormOutDto status = makeFormService.updateAssignTime(id, hoId);
        return ResponseEntity.ok(status);
    }

    @PreAuthorize("hasRole('maker')")
    @PatchMapping("/toDrafts/{id}")
    public ResponseEntity<?> toDraft(@PathVariable("id") Long id) {
        MakeFormDisplayDto makeForm = makeFormService.toDraft(id);
        return ResponseEntity.ok(makeForm);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody MakeFormDto makeFormDto) {
        MakeFormDisplayDto Id = makeFormService.saveForm(makeFormDto);
        return ResponseEntity.ok(Id);
    }

    @PreAuthorize("hasRole('HO_Manager')")
    @PatchMapping("/assignChecker")
    public ResponseEntity<?> AssignToChecker(@RequestParam("make") Long makeId, @RequestParam("checker") Long checkerId) {
        MakeFormDisplayDto make = makeFormService.assignToChecker(makeId, checkerId);
        return ResponseEntity.ok(make);
    }

    @GetMapping("accountCheck")
    public ResponseEntity<?> getAccountCheck(@RequestParam("accountNumber") String accountNumber) {
        MakeFormDisplayDto make = makeFormService.checkAccountPresence(accountNumber);
        return ResponseEntity.ok(make);
    }

//    @GetMapping("/distribute")
//    public ResponseEntity<?> distribute() {
//        String distribute = distributorService.Assign();
//        return ResponseEntity.ok(distribute);
//    }

    @GetMapping("/assignToNight")
    public ResponseEntity<?> assignToNight() {
        String makes = distributorService.AssignNight();
        return ResponseEntity.ok(makes);
    }

    @PatchMapping("/send-ToHo/{id}")
    public ResponseEntity<?> sendToHo(@PathVariable Long id) {
        MakeFormDisplayDto response = makeFormService.sendToHo(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('HO_Checker')")
    @PostMapping("/reject-request")
    public ResponseEntity<?> rejectRequest(@RequestBody BackReasonInDto comment) {
        BackReasonOutDto response = makeFormService.rejectResponse(comment);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dashboard/{makerId}")
    public ResponseEntity<?> getDashboardMaker(@PathVariable("makerId") Long makerId, @RequestParam("date") Instant date) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> roleList = authentication.getAuthorities().stream().filter((h) -> h.getAuthority().startsWith("ROLE")).collect(Collectors.toList());
        String role = roleList.get(0).getAuthority();
        log.error("Role {}", role);
        if (Objects.equals(role, "ROLE_maker")) {
            MakerDashboard makeForm = makeFormService.getMakerDashboard(makerId, date);
            return ResponseEntity.ok(makeForm);
        } else if (role.equals("ROLE_HO_Checker")) {
            CheckerDashboard makeForm = makeFormService.getChekcerDashboard(makerId, date);
            return ResponseEntity.ok(makeForm);
        } else if (role.equals("ROLE_HO_Manager")) {
            ManagerDashboard makeForm = makeFormService.getManagerDashboard(date);
            return ResponseEntity.ok(makeForm);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Access denied, your role isn't found");
    }

    @GetMapping("/getRejected")
    public ResponseEntity<?> getRejected(){
        Integer rejectedCount=makeFormService.getRejectedCount();
        return ResponseEntity.ok(rejectedCount);
    }

    @GetMapping("/getPending")
    public ResponseEntity<?> getPending(){
        Integer pendingCount=makeFormService.getPendingCount();
        return ResponseEntity.ok(pendingCount);
    }
}
