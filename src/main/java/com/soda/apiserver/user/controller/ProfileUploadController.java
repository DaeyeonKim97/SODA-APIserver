package com.soda.apiserver.user.controller;


import com.soda.apiserver.user.model.entity.User;
import com.soda.apiserver.user.repository.UserRepository;
import com.soda.apiserver.common.response.ResponseMessage;
import com.soda.apiserver.file.model.entity.Attach;
import com.soda.apiserver.file.repository.AttachRepository;
import com.soda.apiserver.file.util.OciUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/profile")
public class ProfileUploadController {
    private final OciUtil ociUtil = new OciUtil();
    private final UserRepository repository;
    private final AttachRepository attachRepository;
    @Value("${spring.servlet.multipart.location}")
    private String configPath;

    @Autowired
    public ProfileUploadController(UserRepository repository, AttachRepository attachRepository) throws IOException {
        this.repository = repository;
        this.attachRepository = attachRepository;
    }

    @PostMapping
    public ResponseEntity<?> uploadProfile(@RequestParam MultipartFile[] uploadFile) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        Map<String,Object> responseMap = new HashMap<>();
        String username = null;

        for (MultipartFile file : uploadFile){
            if(!file.isEmpty()){
                try{
                    username = SecurityContextHolder.getContext().getAuthentication().getName();
                } catch (Exception e){
                    return ResponseEntity
                            .badRequest()
                            .build();
                }
                String originalFileName = file.getOriginalFilename();
                String ext = originalFileName.substring(originalFileName.lastIndexOf(".")+1);
                System.out.println(username);
                System.out.println(originalFileName);
                System.out.println(ext);
                System.out.println(file.getContentType());

                File tempFile = new File("profile/"+UUID.randomUUID()+"_"+originalFileName);
                File tempFileConfig = new File(this.configPath+"/"+tempFile.getPath());
                file.transferTo(tempFile);

                String saveName = username+"."+ext;

                ociUtil.upload("soda-profile",saveName,file.getContentType(), this.configPath+"/"+tempFile.getPath());

                String savedPath = "https://objectstorage.ap-seoul-1.oraclecloud.com/n/cnzrptgs7qfv/b/soda-profile/o/"+saveName;

                User user = repository.findByUserName(username);
                Attach attach = new Attach(0,originalFileName,saveName,savedPath,ext,(new Date()).toString(),0,"Y");
                attachRepository.save(attach);
                user.setProfileImg(attach);
                repository.save(user);

                responseMap.put("profileImgSrc" , savedPath);

                tempFile.delete();
                tempFileConfig.delete();

                return ResponseEntity
                        .created(URI.create("/users/"+username))
                        .headers(headers)
                        .body(new ResponseMessage(201, "profileImg modified",responseMap));

            }
        }

        return ResponseEntity
                .badRequest()
                .build();
    }
}
