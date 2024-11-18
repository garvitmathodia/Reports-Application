package com.reports.app.service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.reports.app.entity.CitizenPlan;
import com.reports.app.repository.CitizenPlanRepo;
import com.reports.app.request.SearchRequest;
import com.reports.app.util.EmailUtils;
import com.reports.app.util.ExcelGenerator;
import com.reports.app.util.PdfGenerator;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private CitizenPlanRepo planRepo;

	@Autowired
	private ExcelGenerator excelGenerator;

	@Autowired
	private PdfGenerator pdfGenerator;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public List<String> getPlanNames() {

		return planRepo.getPlanNames();
	}

	@Override
	public List<String> getPlanStatus() {

		return planRepo.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> search(SearchRequest request) {

		CitizenPlan entity = new CitizenPlan();

		if (null != request.getPlanName() && !"".equals(request.getPlanName())) {
			entity.setPlanName(request.getPlanName());
		}

		if (null != request.getPlanStatus() && !"".equals(request.getPlanStatus())) {
			entity.setPlanStatus(request.getPlanStatus());
		}

		if (null != request.getGender() && !"".equals(request.getGender())) {
			entity.setGender(request.getGender());
		}

		if (null != request.getStartDate() && !"".equals(request.getStartDate())) {
			String startDate = request.getStartDate();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			LocalDate localDate = LocalDate.parse(startDate, formatter);

			entity.setPlanStartDate(localDate);

		}

		if (null != request.getEndDate() && !"".equals(request.getEndDate())) {
			String endDate = request.getEndDate();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			LocalDate localDate = LocalDate.parse(endDate, formatter);

			entity.setPlanEndDate(localDate);

		}

		System.out.println(entity);

		return planRepo.findAll(Example.of(entity));
	}

	@Override
	public boolean exportExcel(HttpServletResponse response) throws Exception {

		File f = new File("plans.xls");

		List<CitizenPlan> plans = planRepo.findAll();

		excelGenerator.generate(response, plans, f);

		String subject = "Test Mail";
		String body = "<h1>Test Mail Body</h1>";
		String to = "gmathodia@gmail.com";

		emailUtils.sendEmail(subject, body, to, f);

		f.delete();

		return true;
	}

	@Override
	public boolean exportPdf(HttpServletResponse response) throws Exception {

		File f = new File("plans.pdf");

		List<CitizenPlan> plans = planRepo.findAll();

		pdfGenerator.generate(response, plans, f);

		String subject = "Test Mail";
		String body = "<h1>Test Mail Body</h1>";
		String to = "gmathodia@gmail.com";

		emailUtils.sendEmail(subject, body, to, f);

		f.delete();

		return true;
	}

}
