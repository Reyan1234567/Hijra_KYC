package com.example.hijra_kyc.repository;

import com.example.hijra_kyc.model.MakeForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<MakeForm, Long> {

    @Query(value = """
        SELECT f.id,
               f.customer_name,
               f.customer_account,
               f.customer_phone,
               CASE f.status
                    WHEN 0 THEN 'Pending'
                    WHEN 1 THEN 'Approved'
                    WHEN 2 THEN 'Rejected'
                    WHEN 3 THEN 'Saved'
               END as status,
               b.name as branchName,
               maker.user_name as user_name,
               checker.user_name as ho_user_name,
               f.make_time as uploaded_on,
               br.comment
        FROM kyc_make_forms f
        LEFT JOIN user_profile maker ON f.maker_user_id = maker.id
        LEFT JOIN branch b ON maker.branch_id = b.branch_id
        LEFT JOIN user_profile checker ON f.ho_user_id = checker.id
        LEFT JOIN back_reason br ON br.make_id_id = f.id
        WHERE (:status IS NULL OR f.status = :status)
          AND (:branchId IS NULL OR maker.branch_id = :branchId)
          AND (:fromDate IS NULL OR f.make_time >= :fromDate)
          AND (:toDate IS NULL OR f.make_time <= :toDate)
        ORDER BY f.make_time DESC
        """, nativeQuery = true)
    List<Object[]> getDetailReport(@Param("status") Integer status,
                                   @Param("branchId") Long branchId,
                                   @Param("fromDate") LocalDateTime fromDate,
                                   @Param("toDate") LocalDateTime toDate);

    @Query(value = """
        SELECT b.name,
               COUNT(*) as total,
               SUM(CASE WHEN f.status = 1 THEN 1 ELSE 0 END) as approved,
               SUM(CASE WHEN f.status = 0 THEN 1 ELSE 0 END) as pending,
               SUM(CASE WHEN f.status = 2 THEN 1 ELSE 0 END) as rejected,
               SUM(CASE WHEN f.status = 3 THEN 1 ELSE 0 END) as saved
        FROM kyc_make_forms f
        LEFT JOIN user_profile maker ON f.maker_user_id = maker.id
        LEFT JOIN branch b ON maker.branch_id = b.branch_id
        GROUP BY b.name
        ORDER BY b.name
        """, nativeQuery = true)
    List<Object[]> getSummaryReport();

    @Query(value = """
        SELECT checker.user_name as ho_user_name,
               COUNT(*) as total,
               SUM(CASE WHEN f.status = 1 THEN 1 ELSE 0 END) as approved,
               SUM(CASE WHEN f.status = 0 THEN 1 ELSE 0 END) as pending,
               SUM(CASE WHEN f.status = 2 THEN 1 ELSE 0 END) as rejected
        FROM kyc_make_forms f
        LEFT JOIN user_profile checker ON f.ho_user_id = checker.id
        WHERE f.ho_user_id IS NOT NULL
        GROUP BY checker.user_name
        ORDER BY total DESC
        """, nativeQuery = true)
    List<Object[]> getCheckerReport();
}