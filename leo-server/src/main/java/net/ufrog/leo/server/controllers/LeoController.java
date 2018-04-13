package net.ufrog.leo.server.controllers;

import net.ufrog.leo.client.LeoClient;
import net.ufrog.leo.client.contract.AppUserResp;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-13
 * @since 3.0.0
 */
@RestController
@CrossOrigin
public class LeoController implements LeoClient {

    @Override
    public AppUserResp getUser(String appId, String token) {
        return null;
    }
}
