package com.finance.dashboard_backend.repository;
import com.finance.dashboard_backend.model.Transaction;
import com.finance.dashboard_backend.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Summary logic queries
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.transType = :type AND t.isDeleted = false")
    BigDecimal sumByType(@Param("type") TransactionType type);
    @Query("SELECT t.category as category, SUM(t.amount) as total FROM Transaction t " +
            "WHERE t.isDeleted = false GROUP BY t.category")
    List<Map<String, Object>> getCategoryWiseSum();
    // Filtering logic
    List<Transaction> findByTransTypeAndCategoryContainingIgnoreCase(TransactionType type, String category);
    // Recent activity: Last 10 transactions
    List<Transaction> findTop10ByOrderByTransDateDesc();
    // Weekly Trends: Last 7 days ka data (Oracle SQL specific)
    @Query(value = "SELECT TRUNC(trans_date) as day, SUM(amount) as total FROM transactions " +
            "WHERE trans_date >= SYSDATE - 7 AND is_deleted = 0 " +
            "GROUP BY TRUNC(trans_date) ORDER BY TRUNC(trans_date)", nativeQuery = true)
    List<Map<String, Object>> getWeeklyTrends();
}