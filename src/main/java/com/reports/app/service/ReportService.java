package com.reports.app.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.reports.app.entity.CitizenPlan;
import com.reports.app.request.SearchRequest;

public interface ReportService {

	public List<String> getPlanNames();
	
	public List<String> getPlanStatus();
	
	public List<CitizenPlan> search(SearchRequest searchRequest);
	
	public boolean exportExcel(HttpServletResponse response) throws Exception;
	
	public boolean exportPdf(HttpServletResponse response)throws Exception;
}
