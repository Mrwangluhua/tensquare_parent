package com.tensquare.user.service;

import com.tensquare.user.dao.AdminDao;
import com.tensquare.user.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public List<Admin> findAll() {
        return adminDao.findAll();
    }

    public Optional<Admin> findById(String adminId){
        return adminDao.findById(adminId);
    }

    public void update(Admin admin, String adminId) {
        admin.setId(adminId);
        adminDao.save(admin);
    }

    public void deleteById(String adminId) {
        adminDao.deleteById(adminId);
    }

    public void addAdmin(Admin admin) {
        admin.setId(idWorker.nextId()+"");
        String password = admin.getPassword();
        String nowPassword = encoder.encode(password);
        admin.setPassword(nowPassword);
        adminDao.save(admin);
    }

    public Admin login(String loginname, String password) {
        Admin loginAdmin = adminDao.findByLoginname(loginname);
        if(loginAdmin!=null &&encoder.matches(password,loginAdmin.getPassword())){
            return loginAdmin;
        }else {
            return null;
        }
    }
}
