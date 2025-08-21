package com.example.hijra_kyc.service;


import com.example.hijra_kyc.dto.BackReasonDto.BackReasonInDto;
import com.example.hijra_kyc.dto.BackReasonDto.BackReasonOutDto;
import com.example.hijra_kyc.dto.FormDto.*;
import com.example.hijra_kyc.exception.AuthenticationException;
import com.example.hijra_kyc.mapper.BackReasonMapper;
import com.example.hijra_kyc.mapper.MakeFormMapper;
import com.example.hijra_kyc.mapper.UserPrincipal;
import com.example.hijra_kyc.model.*;
import com.example.hijra_kyc.repository.*;

import com.example.hijra_kyc.util.Pagination;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MakeFormService {

    private final MakeFormRepository makeFormRepository;
    private final ImageRepository imageRepository;
    private final MakeFormMapper makerFormMapper;
    private final BackReasonRepository backReasonRepository;
    private final BackReasonMapper backReasonMapper;
    private final BaseService baseService;
    private final UserProfileRepository userRepository;
    private final BranchRepository branchRepository;
    private final ImageService imageService;
    private final Pagination pagination;

    public MakeFormDisplayDto saveForm(MakeFormDto makeFormDto) {
        log.info("In the saveForm function");
        String account = makeFormDto.getCustomerAccount();
        log.info(account);
        MakeForm make = makeFormRepository.findMakeFormByAccount(account);
        if (make == null) {
            log.info("shit is really null");
        } else {
            log.info("shit ain't null");
        }
        if (make == null) {
            log.info("Make is null");
            MakeForm mappedMake = makerFormMapper.mapToMakeForm(makeFormDto);
            log.info("maker: {}", makeFormDto.getMakerId());
            UserProfile user = userRepository.findById(makeFormDto.getMakerId()).orElseThrow(() -> new EntityNotFoundException("Maker not found"));
            log.info("user: {}", user);
            mappedMake.setMaker(user);
            makeFormRepository.save(mappedMake);
            return makerFormMapper.makeFormDisplayDto(mappedMake);
        }
        return makerFormMapper.makeFormDisplayDto(make);
    }

    public MakeFormPage getAll(Long makerId, Instant date, Integer pageSize, Integer pageNumber) {
        System.out.println(makerId);
        System.out.println(pageSize);
        System.out.println(pageNumber);
        userRepository.findById(makerId).orElseThrow(() -> new EntityNotFoundException("Maker not found"));
        if (Instant.now().isBefore(date)) {
            throw new RuntimeException("Invalid date");
        }
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("makeTime"));
        Page<MakeForm> makes = makeFormRepository.getMakeFormById(makerId, pageable, date);
        log.info("THE TOTAL MAKES");
//        log.info(makes.getContent().toString());
        List<MakeFormDisplayDto> displayForm = makes.getContent().stream()
                .map(makerFormMapper::makeFormDisplayDto)
                .toList();
        System.out.println(displayForm.size());
        log.info("DISPLAY MAKES");
//        log.info(displayForm.toString());
        return MakeFormPage.builder()
                .makes(displayForm)
                .total(makes.getTotalElements())
                .build();
    }

    public List<MakeFormOutDto> get(Long makerId, Long status) {
        if (status < 0 || status > 4) {
            throw new IllegalArgumentException("invalid status");
        }
        List<MakeForm> makersForm = makeFormRepository.findByMakerAndStatus(makerId, status);
        if (makersForm == null || makersForm.isEmpty()) {
            throw new EntityNotFoundException("No list items found");
        }
        return makersForm
                .stream()
                .map(makerFormMapper::makeFormOutMapper).toList();
    }

    public MakeFormOutDto updateAssignTime(Long id, Long hoId) {
        MakeForm makeForm1 = makeFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Make not found"));
        makeForm1.setHoAssignTime(Instant.now());
        UserProfile ho = userRepository.findById(hoId)
                .orElseThrow(() -> new RuntimeException("HO not found"));
        makeForm1.setHo(ho);
        makeFormRepository.save(makeForm1);
        return makerFormMapper.makeFormOutMapper(makeForm1);
    }


    public MakeFormDisplayDto changeStatus(Long makeId, int statusNumber) {
        if (statusNumber < 0 || statusNumber > 4) {
            throw new IllegalArgumentException("invalid status");
        }
        MakeForm makeForm1 = makeFormRepository.findById(makeId)
                .orElseThrow(() -> new EntityNotFoundException("Make not found"));
        makeForm1.setStatus(statusNumber);
        if (statusNumber == 2 || statusNumber == 3) {
            makeForm1.setHoActionTime(Instant.now());
        }
        makeFormRepository.save(makeForm1);
        return makerFormMapper.makeFormDisplayDto(makeForm1);
    }

    @Transactional
    public MakeFormDisplayDto sendToHo(Long id) {
        MakeForm makeForm = makeFormRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Make not found"));
        BackReason backReason = backReasonRepository.findByMakeId(id) != null ? backReasonRepository.findByMakeId(id) : null;
        if (backReason != null) {
            backReason.setMakeId(null);
            backReasonRepository.save(backReason);
        }
        if (makeForm.getStatus() != 0) {
            throw new RuntimeException("can't send a make with this status");
        }
        return changeStatus(id, 1);
    }

    public MakeFormPage getWithHoUserId(Long hoUserId, Instant date, Integer pageNumber, Integer pageSize) {
        System.out.println(hoUserId);
        userRepository.findById(hoUserId).orElseThrow(() -> new RuntimeException("Make not found"));
        if (date.isAfter(Instant.now())) {
            throw new RuntimeException("Invalid date");
        }
        Pageable page = PageRequest.of(pageNumber - 1, pageSize, Sort.by("makeTime"));
        Page<MakeForm> pagedHoForms = makeFormRepository.getMakeFormByHoId(hoUserId, page, date);

        List<MakeFormDisplayDto> dtod = pagedHoForms.getContent().stream().map(makerFormMapper::makeFormDisplayDto).toList();
        return MakeFormPage.builder()
                .makes(dtod)
                .total(pagedHoForms.getTotalElements())
                .build();
    }

    public BackReasonOutDto rejectResponse(BackReasonInDto inDto) {
        MakeForm makeForm1 = makeFormRepository.findById(inDto.getMakeFormId()).orElseThrow(() -> new RuntimeException("can't find makeForm1"));
        changeStatus(inDto.getMakeFormId(), 3);
        BackReason backReason = backReasonMapper.toEntity(inDto);
        backReason.setMakeId(makeForm1);
        return backReasonMapper.toDto(backReasonRepository.save(backReason));
    }

    public MakeFormDisplayDto toDraft(Long id) {
        MakeForm makeForm = makeFormRepository.findById(id).orElseThrow(() -> new RuntimeException("Make not found"));
        makeForm.setHoActionTime(null);
        makeFormRepository.save(makeForm);
        changeStatus(id, 0);
        return makerFormMapper.makeFormDisplayDto(makeForm);
    }

    public MakeFormPage getManager(Instant date, Integer pageSize, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("makeTime"));
        Page<MakeForm> makes = makeFormRepository.getAllMakes(date, pageable);
        List<MakeFormDisplayDto> makeFormDisplayDtos = makes.stream().map(makerFormMapper::makeFormDisplayDto).toList();
        return MakeFormPage.builder()
                .total(makes.getTotalElements())
                .makes(makeFormDisplayDtos)
                .build();
    }

    public MakeFormPage getManagerStatus(Instant date, Integer pageSize, Integer pageNumber, Integer status) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("makeTime"));
        Page<MakeForm> makes = makeFormRepository.getAllMakes(date, status, pageable);
        List<MakeFormDisplayDto> makeFormDisplayDtos = makes.stream().map(makerFormMapper::makeFormDisplayDto).toList();
        return MakeFormPage.builder()
                .total(makes.getTotalElements())
                .makes(makeFormDisplayDtos)
                .build();
    }

    public MakeFormDisplayDto assignToChecker(Long makeId, Long checkerId) {
        MakeForm makeForm = makeFormRepository.findById(makeId).orElseThrow(() -> new RuntimeException("Make not found"));
        UserProfile checker = userRepository.findById(checkerId).orElseThrow(() -> new RuntimeException("User not found"));

        makeForm.setHo(checker);
        makeFormRepository.save(makeForm);
        return makerFormMapper.makeFormDisplayDto(makeForm);
    }

    public MakerDashboard getMakerDashboard(Long makerId, Instant date) {
        UserProfile maker = userRepository.findById(makerId).orElseThrow(() -> new RuntimeException("Maker not found"));
        List<MakeForm> makeForm = maker.getMakeFormList();
        makeForm = makeForm.stream().filter((make) -> make.getMakeTime().isAfter(date)).toList();
        int drafts = makeForm.stream().filter((make) -> make.getStatus() == 0).toList().size();
        int saved = makeForm.stream().filter((make) -> make.getSaved() == 1).toList().size();
        int pending = makeForm.stream().filter((make) -> make.getStatus() == 1).toList().size();
        int accepted = makeForm.stream().filter((make) -> make.getStatus() == 2).toList().size();
        int rejected = makeForm.stream().filter((make) -> make.getStatus() == 3).toList().size();
        return MakerDashboard.builder()
                .saved(saved)
                .drafts(drafts)
                .pending(pending)
                .accepted(accepted)
                .rejected(rejected)
                .total(makeForm.size())
                .build();
    }

    public CheckerDashboard getChekcerDashboard(Long checkerId, Instant date) {
        userRepository.findById(checkerId).orElseThrow(() -> new RuntimeException("Checker not found"));
        List<MakeForm> makeForm = makeFormRepository.getMakeFormByHoIdCount(checkerId, date);

        int pending = makeForm.stream().filter((make) -> make.getStatus() == 1).toList().size();
        int accepted = makeForm.stream().filter((make) -> make.getStatus() == 2).toList().size();
        int rejected = makeForm.stream().filter((make) -> make.getStatus() == 3).toList().size();
        return CheckerDashboard.builder()
                .pending(pending)
                .accepted(accepted)
                .rejected(rejected)
                .total(makeForm.size())
                .build();
    }

    public ManagerDashboard getManagerDashboard(Instant date) {
        List<MakeForm> makeForm = makeFormRepository.findAll();
        makeForm = makeForm.stream().filter((make) -> make.getMakeTime().isAfter(date)).toList();

        int pending = makeForm.stream().filter((make) -> make.getStatus() == 1).toList().size();
        int accepted = makeForm.stream().filter((make) -> make.getStatus() == 2).toList().size();
        int rejected = makeForm.stream().filter((make) -> make.getStatus() == 3).toList().size();
        return ManagerDashboard.builder()
                .accepted(accepted)
                .pending(pending)
                .rejected(rejected)
                .total(makeForm.size())
                .build();
    }

    public MakeFormDisplayDto checkAccountPresence(String accountNumber) {
        MakeForm make = makeFormRepository.findMakeFormByAccount(accountNumber);
        if (make == null) {
            throw new EntityNotFoundException("Make not found");
        }
        return makerFormMapper.makeFormDisplayDto(make);
    }

    public MakeFormPage getAllDrafts(Long makerId, Instant date, int pageSize, int pageNumber, int status) {
        userRepository.findById(makerId).orElseThrow(() -> new EntityNotFoundException("Maker not found"));
        Pageable page = PageRequest.of(pageNumber - 1, pageSize, Sort.by("makeTime"));
        Page<MakeForm> draftedForms = makeFormRepository.getByStatusMakeFormById(makerId, status, page, date);

        List<MakeFormDisplayDto> newDraftedForms = draftedForms.stream().map(makerFormMapper::makeFormDisplayDto).toList();
        return MakeFormPage.builder()
                .total(draftedForms.getTotalElements())
                .makes(newDraftedForms)
                .build();
    }

    public MakeFormPage getWithHoUserIdStatus(Long hoUserId, Instant date, Integer status, Integer pageNumber, Integer pageSize) {
        userRepository.findById(hoUserId).orElseThrow(() -> new EntityNotFoundException("Ho user not found"));
        Pageable page = PageRequest.of(pageNumber - 1, pageSize, Sort.by("makeTime"));
        Page<MakeForm> getAll = makeFormRepository.getMakeFormByHoIdStatus(hoUserId, page, date, status);

        List<MakeFormDisplayDto> newGetAll = getAll.getContent().stream().map(makerFormMapper::makeFormDisplayDto).toList();
        return MakeFormPage.builder()
                .total(getAll.getTotalElements())
                .makes(newGetAll)
                .build();
    }


    public Integer getRejectedCount() {
        log.info("THE GET REJECTED METHOD");
        log.info("THE GET REJECTED METHOD");
        log.error("THE GET REJECTED METHOD");
        UserPrincipal principal=(UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId=principal.getUserId();
        if(userId==null){
            throw new AuthenticationException("User not found");
        }
        log.info("THE GET REJECTED METHOD");
        log.info("THE GET REJECTED METHOD");
        log.error("THE GET REJECTED METHOD");
        log.info(userId.toString());
        return makeFormRepository.getRejected(userId).size();
    }

    public Integer getPendingCount() {
        UserPrincipal principal=(UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId=principal.getUserId();
        if(userId==null){
            throw new AuthenticationException("User not found");
        }
        return makeFormRepository.getPending(userId).size();
    }
}


