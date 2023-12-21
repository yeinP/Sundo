package com.example.demo.controller.complaint;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.example.demo.dto.complaint.C_userDto;
import com.example.demo.dto.complaint.ComplaintDto;
import com.example.demo.dto.complaint.R_fileDto;
import com.example.demo.dto.complaint.C_fileDto;
import com.example.demo.dto.complaint.Reply;
import com.example.demo.dto.sms.CustomMessage;
import com.example.demo.dto.sms.MessageRes;
import com.example.demo.service.ComplaintServiceImpl;
import com.example.demo.service.SmsService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping("/complaint")
public class ComplaintController {

	@Autowired
	ComplaintServiceImpl complaintServiceImpl;

	@Autowired
	SmsService smsService;

	private final int pageRow = 5;
	private final int pagePerPage = 5;

	@GetMapping("/main")
	public void list(Model model, @RequestParam(required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(required = false) Integer startPage) {
			int totalCount = complaintServiceImpl.countAllCom();
			
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

			List<ComplaintDto> list = complaintServiceImpl.showList(startRow, endRow);
			model.addAttribute("list", list);
	}

	@GetMapping("/search")
	public void search(Model model, @RequestParam(required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(required = false) Integer startPage, String keyword, String startDate, String endDate) {
		if (startDate == null && endDate == null) {
			int totalCount = complaintServiceImpl.countAllCom();
	        
			LocalDate currentDate = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        LocalDate oneWeekAgoDate = currentDate.minusWeeks(1);
	        startDate = oneWeekAgoDate.format(formatter);
			endDate = currentDate.format(formatter);
			
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
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("pageNum", pageNum);
			int startRow = (pageNum - 1) * pageRow + 1;
			int endRow = startRow + pageRow - 1;

			List<ComplaintDto> list = complaintServiceImpl.showList(startRow, endRow);
			model.addAttribute("list", list);
		} else {
			if (keyword.equals("")) {
				int totalCount = complaintServiceImpl.countDateCom(startDate, endDate);
				System.out.println(totalCount);
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

				List<ComplaintDto> list = complaintServiceImpl.searchDate(startRow, endRow, startDate, endDate);
				model.addAttribute("list", list);
			} else {
				int totalCount = complaintServiceImpl.countDateKeywordCom(startDate, endDate, keyword);
				System.out.println("------맨 아래");
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
				model.addAttribute("keyword", keyword);
				int startRow = (pageNum - 1) * pageRow + 1;
				int endRow = startRow + pageRow - 1;

				List<ComplaintDto> list = complaintServiceImpl.searchDateKeyword(startRow, endRow, startDate, endDate, keyword);
				model.addAttribute("list", list);
			}
		}
	}

	
	@GetMapping("/write/step1")
	public void writeStep1() {
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

	@PostMapping("/write/step2")
	public void writeStep2(String phone, Model model) {
		model.addAttribute("phone", phone);
	}

	@PostMapping("/write/step3")
	public void writeStep3(C_userDto c_userDto, Model model) {
		if(c_userDto.getCuGender() == 999) {
			c_userDto.setCuGender(null);
		}
		if(c_userDto.getCuBirth().equals("999")) {
			c_userDto.setCuBirth(null);
		}
		int cuno = complaintServiceImpl.InsertC_user(c_userDto);
		model.addAttribute("cuno", cuno);
	}

	@PostMapping("/write/step4")
	public void writeStep4(String ctitle, String c_content, int secret, @RequestParam(required = false) String pword,
			String c_loc, int cuno, @RequestParam(required = false) List<MultipartFile> files) throws IOException {
		ComplaintDto complaintDto = new ComplaintDto(ctitle, c_content, cuno, secret, pword, c_loc);
		int cno = complaintServiceImpl.InsertOneCom(complaintDto);

		if (files != null) {
			String tempDirectoryPath = "C:/Users/admin/Documents/files/complaint/" + cno + "/";
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

					C_fileDto file = new C_fileDto(cno, originalFileName, savedFileName, savedPath, fileSize);
					complaintServiceImpl.uploadC_file(file);
				}
			}
		}
	}

	@GetMapping("/read")
	public void readCom(int cno, Model model) {
		Map<Integer, Object> map = complaintServiceImpl.readCom(cno);
		model.addAttribute("complaint", map.get(1));
		model.addAttribute("fileList", map.get(2));
		model.addAttribute("reply", map.get(3));
		model.addAttribute("fileList_R", map.get(4));
	}

	@GetMapping("/downloadFile_C")
	@ResponseBody
	public StreamingResponseBody downloadFile_C(@RequestParam int fno, HttpServletResponse response) throws IOException {
		C_fileDto file = complaintServiceImpl.downloadC_file(fno);

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
	
	@GetMapping("/downloadFile_R")
	@ResponseBody
	public StreamingResponseBody downloadFile_R(@RequestParam int fno, HttpServletResponse response) throws IOException {
		R_fileDto file = complaintServiceImpl.downloadR_file(fno);

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

	@PostMapping("/check")
	@ResponseBody
	public Map<String, Object> revDelCheck(int cno) {
		Map<String, Object> map = new HashMap<>();
		String pword = complaintServiceImpl.revDelCheck(cno);
		if (!pword.equals("")) {
			map.put("success", true);
			map.put("pword", pword);
		} else {
			map.put("success", false);
		}
		return map;
	}

	@GetMapping("/revise")
	public void reviseCom(int cno, Model model) {
		Map<Integer, Object> map = complaintServiceImpl.readCom(cno);
		model.addAttribute("complaint", map.get(1));
		model.addAttribute("fileList", map.get(2));
	}

	@PostMapping("/revise")
	public String reviseComPost(ComplaintDto complaintDto, Model model) {
		complaintServiceImpl.reviseCom(complaintDto);
		return "redirect:/complaint/main";
	}

	@GetMapping("/delete")
	public String deleteCom(int cno) {
		complaintServiceImpl.deleteCom(cno);
		return "redirect:/complaint/main";
	}

	@GetMapping("/reply")
	public void replyCom(int cno, Model model) {
		Map<Integer, Object> map = complaintServiceImpl.readCom(cno);
		model.addAttribute("complaint", map.get(1));
		model.addAttribute("fileList", map.get(2));
	}

	@PostMapping("/reply")
	public String replyPost(Reply reply, @RequestParam(required = false) List<MultipartFile> files, int cuno)
			throws IOException, InvalidKeyException, RestClientException, NoSuchAlgorithmException, URISyntaxException {
		int rno = complaintServiceImpl.InsertOneRep(reply);
		complaintServiceImpl.updateStatus(1, reply.getCno());
		if (files != null) {
			String tempDirectoryPath = "C:/Users/admin/Documents/files/reply/" + rno + "/";
			File tempDirectory = new File(tempDirectoryPath);
			if (!tempDirectory.exists()) {
				tempDirectory.mkdirs();
			}

			List<MultipartFile> copiedFiles = new ArrayList<>(files);
			for (MultipartFile fl : copiedFiles) {
				// 파일이 비어있지 않은 경우에만 처리
				if (!fl.isEmpty()) {
					String originalFileName = fl.getOriginalFilename();
					UUID uuid = UUID.randomUUID();
					String contType = originalFileName.substring(originalFileName.lastIndexOf("."));
					String savedFileName = uuid.toString() + contType;
					String savedPath = tempDirectoryPath + savedFileName;
					int fileSize = (int) fl.getSize();

					Path path = Paths.get(savedPath).toAbsolutePath();
					Files.copy(fl.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

					R_fileDto file = new R_fileDto(rno, originalFileName, savedFileName, savedPath, fileSize);
					complaintServiceImpl.uploadR_file(file);
				}
			}
		}

		String phone = complaintServiceImpl.getPhoneNum(cuno);
		CustomMessage message = new CustomMessage(phone);
		MessageRes response = smsService.send_r(message);

		return "redirect:/complaint/main";
	}

	@GetMapping("/solved")
	public String solveCom(int cno, int cuno) throws InvalidKeyException, JsonProcessingException, RestClientException, NoSuchAlgorithmException, UnsupportedEncodingException, URISyntaxException {
		complaintServiceImpl.updateStatus(2, cno);
		String phone = complaintServiceImpl.getPhoneNum(cuno);
		CustomMessage message = new CustomMessage(phone);
		MessageRes response = smsService.send_s(message);
		return "redirect:/complaint/main";
	}
	
	@GetMapping("/myComplaint/step1")
	public void myComSt1() {}
	
	@RequestMapping("/myComplaint/step2")
	public void myComSt2(String cuname, String cuphone, Model model, @RequestParam(required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(required = false) Integer startPage) {
		
		int totalCount = complaintServiceImpl.countMyCom(cuname, cuphone);

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
		model.addAttribute("cuname", cuname);
		model.addAttribute("cuphone", cuphone);
		int startRow = (pageNum - 1) * pageRow + 1;
		int endRow = startRow + pageRow - 1;

		model.addAttribute("list", complaintServiceImpl.searchMyCom(cuname, cuphone, startRow, endRow));
	}
	
	@PostMapping("/myComplaint/check")
	@ResponseBody
	public String myAppCh(String cuname, String cuphone) {
		
		if (complaintServiceImpl.countMyCom(cuname, cuphone) > 0) {
			return "true";
		} else {
			return "false";
		}
	}
	
	@GetMapping("/testGraphOg")
	public void graph(){}
	
}