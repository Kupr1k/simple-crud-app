package com.kupr1k.controller;

import com.kupr1k.service.ViewerService;
import com.kupr1k.service.ViewerServiceImpl;

public class UserController {
    private final ViewerService viewerService = new ViewerServiceImpl();

    public void run(){

        viewerService.load();

        int choise = viewerService.viewOptions();

        while (choise!=6){
            switch (choise){
                case 1:
                    viewerService.viewAll();
                    break;
                case 2:
                    viewerService.addUser();
                    break;
                case 3:
                    viewerService.editUser();
                    break;
                case 4:
                    viewerService.removeUser();
                    break;
                case 5:
                    viewerService.saveAndExit();
                    break;
            }
            choise = viewerService.viewOptions();
        }
        //Exit without save
        System.exit(0);
    }
}
