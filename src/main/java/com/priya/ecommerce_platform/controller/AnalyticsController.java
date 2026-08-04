package com.priya.ecommerce_platform.controller;

import com.priya.ecommerce_platform.dto.response.*;
import com.priya.ecommerce_platform.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    /*
     * ==========================================
     * Dashboard
     * ==========================================
     */

    @GetMapping("/dashboard")
    public ApiResponse<AdminDashboardResponse> getDashboard() {

        return ApiResponse.success(
                "Dashboard fetched successfully",
                analyticsService.getDashboard()
        );
    }

    /*
     * ==========================================
     * Monthly Revenue
     * ==========================================
     */

    @GetMapping("/monthly-revenue")
    public ApiResponse<List<MonthlyRevenueResponse>> getMonthlyRevenue(
            @RequestParam Integer year) {

        return ApiResponse.success(
                "Monthly revenue fetched successfully",
                analyticsService.getMonthlyRevenue(year)
        );
    }

    /*
     * ==========================================
     * Yearly Revenue
     * ==========================================
     */

    @GetMapping("/yearly-revenue")
    public ApiResponse<List<YearlyRevenueResponse>> getYearlyRevenue() {

        return ApiResponse.success(
                "Yearly revenue fetched successfully",
                analyticsService.getYearlyRevenue()
        );
    }

    /*
     * ==========================================
     * Top Selling Products
     * ==========================================
     */

    @GetMapping("/top-products")
    public ApiResponse<List<TopProductResponse>> getTopSellingProducts() {

        return ApiResponse.success(
                "Top selling products fetched successfully",
                analyticsService.getTopSellingProducts()
        );
    }

    /*
     * ==========================================
     * Vendor Earnings
     * ==========================================
     */

    @GetMapping("/vendor-earnings")
    public ApiResponse<List<VendorEarningResponse>> getVendorEarnings() {

        return ApiResponse.success(
                "Vendor earnings fetched successfully",
                analyticsService.getVendorEarnings()
        );
    }

    /*
     * ==========================================
     * Top Customers
     * ==========================================
     */

    @GetMapping("/top-customers")
    public ApiResponse<List<TopCustomerResponse>> getTopCustomers() {

        return ApiResponse.success(
                "Top customers fetched successfully",
                analyticsService.getTopCustomers()
        );
    }

    /*
     * ==========================================
     * Category Sales
     * ==========================================
     */

    @GetMapping("/category-sales")
    public ApiResponse<List<CategorySalesResponse>> getCategorySales() {

        return ApiResponse.success(
                "Category sales fetched successfully",
                analyticsService.getCategorySales()
        );
    }

    /*
     * ==========================================
     * Low Stock Products
     * ==========================================
     */

    @GetMapping("/low-stock")
    public ApiResponse<List<LowStockResponse>> getLowStockProducts() {

        return ApiResponse.success(
                "Low stock products fetched successfully",
                analyticsService.getLowStockProducts()
        );
    }

    /*
     * ==========================================
     * Out Of Stock Products
     * ==========================================
     */

    @GetMapping("/out-of-stock")
    public ApiResponse<List<LowStockResponse>> getOutOfStockProducts() {

        return ApiResponse.success(
                "Out of stock products fetched successfully",
                analyticsService.getOutOfStockProducts()
        );
    }

}