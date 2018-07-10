package com.codemonkey.web.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.codemonkey.domain.GlobalConfig;
import com.codemonkey.domain.UploadEntry;
import com.codemonkey.error.SysError;
import com.codemonkey.service.GlobalConfigService;
import com.codemonkey.service.UploadEntryService;
import com.codemonkey.utils.SysUtils;

@Controller
@RequestMapping(value = "/*/uploadFile/**")
public class UploadFileController extends AbsExtController<UploadEntry>{

	@Autowired private UploadEntryService uploadEntryService;
	@Autowired private GlobalConfigService globalConfigService;
	
	@Override
	protected UploadEntryService service() {
		return uploadEntryService;
	}
	
	@RequestMapping("download")
	public void download(@RequestParam UploadEntry uploadEntry , HttpServletResponse response){
		File file = new File(uploadEntry.getFilePath());
		if(!file.exists()){
			throw new SysError(uploadEntry.getFilePath() + "不存在");
		}
		setDownloadAttribute(response, uploadEntry.getName());
		try {
			SysUtils.exportPathToFile(response.getOutputStream(), uploadEntry.getFilePath());
		} catch (IOException e) {
			throw new SysError("数据传输错误");
		}
	}
	
	@RequestMapping("checkout/{filePath:.+}")
	public void checkout(@PathVariable String filePath , HttpServletResponse response){
		
		if(SysUtils.isEmpty(filePath)){
			return;
		}
		
		String uploadPath = globalConfigService.stringValue(GlobalConfig.UPLOAD_PATH);
		File file = new File(uploadPath + File.separator + filePath);
		if(!file.exists()){
			throw new SysError(filePath + "不存在");
		}
		setDownloadAttribute(response, filePath);
		try {
			SysUtils.exportPathToFile(response.getOutputStream(), file.getAbsolutePath());
		} catch (IOException e) {
			throw new SysError("数据传输错误");
		}
	}
	
	private void setDownloadAttribute(HttpServletResponse response, String fileName) {
		try {
			fileName = new String(fileName.getBytes("utf-8"), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@ResponseBody
	@RequestMapping("create")
	public String upload(HttpSession session , @RequestParam("file") MultipartFile file){
		
		UploadEntry entity = uploadEntryService.doSaveFile(session, file);
		
		return result(entity.listJson());
	}
	
}
