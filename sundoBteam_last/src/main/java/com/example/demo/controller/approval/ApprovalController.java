package com.example.demo.controller.approval;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.example.demo.dto.approval.A_fileDto;
import com.example.demo.dto.approval.ApprovalDto;
import com.example.demo.dto.sms.CustomMessage;
import com.example.demo.dto.sms.MessageRes;
import com.example.demo.service.ApprovalService;
import com.example.demo.service.RiverCodeService;
import com.example.demo.service.SmsService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping("/approval")
public class ApprovalController {

	@Autowired
	SmsService smsService;

	@Autowired
	RiverCodeService riverCodeService;

	@Autowired
	ApprovalService approvalService;

	private final int pageRow = 5;
	private final int pagePerPage = 5;

	@GetMapping("/main")
	public String main(HttpSession session) {
		if (session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn")) {
			return "redirect:/approval/list";
		} else {
			return "/approval/main";
		}
	}

	@GetMapping("/list")
	public void list(Model model, @RequestParam(required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(required = false) Integer startPage) {
		List<ApprovalDto> list = new ArrayList<ApprovalDto>();
			int totalCount = approvalService.countAllApp();

			int endPage = ((int) Math.ceil((double) pageNum / (double) pagePerPage)) * pagePerPage;
			int totalPage = totalCount / pageRow + (totalCount % pageRow > 0 ? 1 : 0);
			if (totalPage < endPage) {
				endPage = totalPage;
			}
			if (startPage == null) {
				startPage = endPage - pagePerPage + 1;
			}
			if (startPage < 1) {
				startPage = 1;
			}

			model.addAttribute("total_count", totalCount);
			model.addAttribute("endPage", endPage);
			model.addAttribute("startPage", startPage);
			model.addAttribute("total_page", totalPage);
			model.addAttribute("pageNum", pageNum);
			int startRow = (pageNum - 1) * pageRow + 1;
			int endRow = startRow + pageRow - 1;

			list = approvalService.showList(startRow, endRow);

		model.addAttribute("riverList", riverCodeService.riverCode());
		model.addAttribute("list", list);
	}

	@GetMapping("/search")
	public void search(Model model, @RequestParam(required = false, defaultValue = "1") Integer pageNum,
					 @RequestParam(required = false) Integer startPage, String startDate, String endDate, Integer r_code) {
		List<ApprovalDto> list = new ArrayList<ApprovalDto>();
		if (startDate == null && endDate == null) {
			int totalCount = approvalService.countAllApp();

			int endPage = ((int) Math.ceil((double) pageNum / (double) pagePerPage)) * pagePerPage;
			int totalPage = totalCount / pageRow + (totalCount % pageRow > 0 ? 1 : 0);
			if (totalPage < endPage) {
				endPage = totalPage;
			}
			if (startPage == null) {
				startPage = endPage - pagePerPage + 1;
			}
			if (startPage < 1) {
				startPage = 1;
			}

			LocalDate currentDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate oneWeekAgoDate = currentDate.minusWeeks(1);
			startDate = oneWeekAgoDate.format(formatter);
			endDate = currentDate.format(formatter);

			model.addAttribute("total_count", totalCount);
			model.addAttribute("endPage", endPage);
			model.addAttribute("startPage", startPage);
			model.addAttribute("total_page", totalPage);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("pageNum", pageNum);
			int startRow = (pageNum - 1) * pageRow + 1;
			int endRow = startRow + pageRow - 1;

			list = approvalService.showList(startRow, endRow);
		} else {
			if (r_code == 999) {
				int totalCount = approvalService.countDateApp(startDate, endDate);

				int endPage = ((int) Math.ceil((double) pageNum / (double) pagePerPage)) * pagePerPage;
				int totalPage = totalCount / pageRow + (totalCount % pageRow > 0 ? 1 : 0);
				if (totalPage < endPage) {
					endPage = totalPage;
				}
				if (startPage == null) {
					startPage = endPage - pagePerPage + 1;
				}
				if (startPage < 1) {
					startPage = 1;
				}
				model.addAttribute("total_count", totalCount);
				model.addAttribute("endPage", endPage);
				model.addAttribute("startPage", startPage);
				model.addAttribute("total_page", totalPage);
				model.addAttribute("pageNum", pageNum);
				model.addAttribute("startDate", startDate);
				model.addAttribute("endDate", endDate);
				int startRow = (pageNum - 1) * pageRow + 1;
				int endRow = startRow + pageRow - 1;

				list = approvalService.searchDate(startRow, endRow, startDate, endDate);
			} else {
				int totalCount = approvalService.countDateR_codeApp(startDate, endDate, r_code);

				int endPage = ((int) Math.ceil((double) pageNum / (double) pagePerPage)) * pagePerPage;
				int totalPage = totalCount / pageRow + (totalCount % pageRow > 0 ? 1 : 0);
				if (totalPage < endPage) {
					endPage = totalPage;
				}
				if (startPage == null) {
					startPage = endPage - pagePerPage + 1;
				}
				if (startPage < 1) {
					startPage = 1;
				}
				model.addAttribute("total_count", totalCount);
				model.addAttribute("endPage", endPage);
				model.addAttribute("startPage", startPage);
				model.addAttribute("total_page", totalPage);
				model.addAttribute("pageNum", pageNum);
				model.addAttribute("startDate", startDate);
				model.addAttribute("endDate", endDate);
				int startRow = (pageNum - 1) * pageRow + 1;
				int endRow = startRow + pageRow - 1;

				list = approvalService.searchDateR_code(startRow, endRow, r_code, startDate, endDate);
			}
		}
		model.addAttribute("riverList", riverCodeService.riverCode());
		model.addAttribute("list", list);
		model.addAttribute("r_code", r_code);
	}

	@GetMapping("write/step1")
	public void step1() {
	}

	@PostMapping("/send")
	@ResponseBody
	public Map<String, Object> sendPost(String phone) throws InvalidKeyException, JsonProcessingException,
			RestClientException, NoSuchAlgorithmException, UnsupportedEncodingException, URISyntaxException {
		CustomMessage message = new CustomMessage(phone);
		MessageRes response = smsService.send_c(message);
		Map<String, Object> res = new HashMap<>();

		if (response.getStatusName().equals("success")) {
			res.put("success", true);
			res.put("signNum", message.getSignNum());
		} else {
			res.put("success", false);
			res.put("signNum", "0");
		}

		return res;
	}

	@PostMapping("/result")
	@ResponseBody
	public String getSignNum(String signNum, String signNumInput) {
		if (signNum.equals(signNumInput)) {
			return "true";
		} else {
			return "false";
		}
	}

	@GetMapping("/write/step2")
	public void writeStep2(String phone, Model model) {
		model.addAttribute("phone", phone);
		model.addAttribute("riverList", riverCodeService.riverCode());
	}

	@PostMapping("/write/step3")
	public String writeStep3(ApprovalDto approvalDto, @RequestParam(required = false) List<MultipartFile> files)
			throws IOException {
		int a_no = approvalService.InsertApp(approvalDto);

		if (files != null) {
			String tempDirectoryPath = "C:/Users/admin/Documents/files/approval/" + a_no + "/";
			File tempDirectory = new File(tempDirectoryPath);
			if (!tempDirectory.exists()) {
				tempDirectory.mkdirs();
			}

			List<MultipartFile> copiedFiles = new ArrayList<>(files);
			for (MultipartFile fl : copiedFiles) {
				if (!fl.isEmpty()) {
					String originalFileName = fl.getOriginalFilename();
					UUID uuid = UUID.randomUUID();
					String contType = originalFileName.substring(originalFileName.lastIndexOf("."));
					String savedFileName = uuid.toString() + contType;
					String savedPath = tempDirectoryPath + savedFileName;
					int fileSize = (int) fl.getSize();

					Path path = Paths.get(savedPath).toAbsolutePath();
					Files.copy(fl.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

					A_fileDto file = new A_fileDto(a_no, originalFileName, savedFileName, savedPath, fileSize);
					approvalService.uploadA_file(file);
				}
			}
		}

		return "redirect:/approval/main";
	}

	@GetMapping("/read")
	public void readCom(int a_no, Model model, HttpSession session) {
		Map<Integer, Object> map = approvalService.readApp(a_no);
		model.addAttribute("approval", map.get(1));
		model.addAttribute("fileList", map.get(2));
	}

	@GetMapping("/downloadFile_A")
	@ResponseBody
	public StreamingResponseBody downloadFile_A(@RequestParam int fno, HttpServletResponse response)
			throws IOException {
		A_fileDto file = approvalService.downloadA_file(fno);

		File newFile = new File(file.getFile_path());
		FileInputStream fileInputStream = new FileInputStream(newFile);
		String encodedFileName = URLEncoder.encode(file.getOgFile_name(), StandardCharsets.UTF_8);

		response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Type", "application/octet-stream");
		response.setHeader("Content-Length", String.valueOf(file.getFile_size()));
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");

		return outputStream -> {
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = fileInputStream.read(data, 0, data.length)) != -1) {
				outputStream.write(data, 0, nRead);
			}
			fileInputStream.close();
		};
	}

	@PostMapping("/updateStatus")
	public String updateStat(Integer a_no, Integer a_status) {
		approvalService.updateStatus(a_no, a_status);
		return "redirect:/approval/read?a_no=" + a_no;
	}

	@GetMapping("/inbox")
	public void inbox(Model model, @RequestParam(required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(required = false) Integer startPage) {
		int totalCount = approvalService.countInbox();

		int endPage = ((int) Math.ceil((double) pageNum / (double) pagePerPage)) * pagePerPage;
		int totalPage = totalCount / pageRow + (totalCount % pageRow > 0 ? 1 : 0);
		if (totalPage < endPage) {
			endPage = totalPage;
		}
		if (startPage == null) {
			startPage = endPage - pagePerPage + 1;
		}
		if (startPage < 1) {
			startPage = 1;
		}

		model.addAttribute("total_count", totalCount);
		model.addAttribute("endPage", endPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("total_page", totalPage);
		model.addAttribute("pageNum", pageNum);
		int startRow = (pageNum - 1) * pageRow + 1;
		int endRow = startRow + pageRow - 1;

		model.addAttribute("list", approvalService.showInbox(startRow, endRow));
	}

	@PostMapping("/approve")
	public String approve(int a_no, int a_status, @RequestParam(required = false) String a_comment)
			throws InvalidKeyException, JsonProcessingException, RestClientException, NoSuchAlgorithmException,
			UnsupportedEncodingException, URISyntaxException {
		String phone = approvalService.getPhoneNum(a_no);
		CustomMessage message = new CustomMessage(phone);

		approvalService.updateStatus(a_no, a_status);
		if (a_comment != null) {
			approvalService.approve(a_no, a_status, a_comment);
		}
		if (a_status == 3) {
			MessageRes response = smsService.send_a(message, "승인");

		} else {
			MessageRes response = smsService.send_a(message, "반려");
		}

		return "redirect:/approval/read?a_no=" + a_no;
	}

	@GetMapping("/usage")
	public void usage(Model model) {
		model.addAttribute("riverList", riverCodeService.riverCode());
	}

	@PostMapping("/usage")
	@ResponseBody
	public Map<String, Object> usagePost(int r_code) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ApprovalDto> list = approvalService.usageList(r_code);
		if (list.size() > 0) {
			map.put("success", true);
			map.put("list", list);
		} else {
			map.put("success", false);
		}
		return map;
	}

	@GetMapping("/myApproval/step1")
	public void myAppSt1() {
	}

	@PostMapping("/myApproval/step2")
	public void myAppSt2(String a_name, String a_phone, Model model) {
		model.addAttribute("list", approvalService.searchMyApp(a_name, a_phone));
	}

	@PostMapping("/myApproval/check")
	@ResponseBody
	public String myAppCh(String a_name, String a_phone) {
		List<ApprovalDto> list = approvalService.searchMyApp(a_name, a_phone);
		if (list.size() > 0) {
			return "true";
		} else {
			return "false";
		}
	}
}
