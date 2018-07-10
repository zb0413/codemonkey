package com.codemonkey.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codemonkey.domain.GlobalConfig;
import com.codemonkey.domain.UploadEntry;
import com.codemonkey.error.SysError;
import com.codemonkey.utils.SysUtils;

@Service
public class UploadEntryServiceImpl extends PhysicalServiceImpl<UploadEntry>
		implements UploadEntryService {
	
	@Autowired
	private GlobalConfigService globalConfigService;

	@Override
	public UploadEntry createEntity() {
		return new UploadEntry();
	}
	
	@Override
	public UploadEntry doSaveFile(HttpSession session, MultipartFile file , String description) {
		
		String uploadPath = globalConfigService.stringValue(GlobalConfig.UPLOAD_PATH);
		
//		String uploadPath = session.getServletContext().getRealPath("")
//				+ "/upload";
		
		if(file != null){
			Double maxSize = globalConfigService.doubleValue(GlobalConfig.MAX_UPLOAD_FILE_SIZE);
			
			if(maxSize != null){
				if(file.getSize() > maxSize * 1024 * 1024){
					throw new SysError("超过最大上传文件限制：" + maxSize + "M");
				}
			}
		}
		
		if(uploadPath == null){
			throw new SysError("没有设置上传路径，请联系系统管理员！");
		}
		
		File uploadDir = new File(uploadPath);
		try {
			if (!uploadDir.exists()) {
				FileUtils.forceMkdir(uploadDir);
			}
			if (file != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss");
				String timestamp = sdf.format(new Date());
				String fileName = extractFileName(file.getOriginalFilename());
				String filePath = uploadPath + File.separator + timestamp + "-"
						+ fileName;
				File destFile = new File(filePath);
				FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

				UploadEntry entry = createEntity();
				entry.setFileName(fileName);
				entry.setName(timestamp + "-"+ fileName);
				entry.setDescription(description);
				entry.setFilePath(filePath);
				entry.setFileSize(file.getSize());
				//用于解决平板下载无法用中文名的问题
				String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
				String tempname = timestamp + "." + prefix;
				entry.setTempName(tempname);
				
				if (destFile.getName().endsWith(".jpg")
						|| destFile.getName().endsWith(".png")
						|| destFile.getName().endsWith(".bmp")
						|| destFile.getName().endsWith(".gif")
						|| destFile.getName().endsWith(".jpeg")
						|| destFile.getName().endsWith(".tif")) {
					entry.setFileType("图片");
				} else {
					entry.setFileType("文档");
				}

				save(entry);
				return entry;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String extractFileName(String originalFilename) {
		
		if(SysUtils.isEmpty(originalFilename)){
			return "";
		}
		
		if(originalFilename.contains("\\")){
			return originalFilename.substring(originalFilename.lastIndexOf('\\')+1);
		}else{
			return originalFilename;
		}
	}

	@Override
	public UploadEntry doSaveFile(HttpSession session, MultipartFile file) {
		return doSaveFile(session , file , null);
	}
}
