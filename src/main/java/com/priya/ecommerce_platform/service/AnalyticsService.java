package com.priya.ecommerce_platform.service;

import com.priya.ecommerce_platform.dto.response.*;

import java.util.List;

public interface AnalyticsService {

    AdminDashboardResponse getDashboard();

    List<MonthlyRevenueResponse> getMonthlyRevenue(Integer year);

    List<YearlyRevenueResponse> getYearlyRevenue();

    List<TopProductResponse> getTopSellingProducts();

    List<VendorEarningResponse> getVendorEarnings();

    List<TopCustomerResponse> getTopCustomers();

    List<CategorySalesResponse> getCategorySales();

    List<LowStockResponse> getLowStockProducts();

    List<LowStockResponse> getOutOfStockProducts();

}