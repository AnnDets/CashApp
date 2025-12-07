package edu.bsu.cashstorage.controller.html;

import edu.bsu.cashstorage.api.APIs;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping(APIs.Client.AUTH_PATH)
    public String auth() {
        return APIs.Params.AUTH;
    }

    @GetMapping(APIs.Client.ACCOUNTS_PATH)
    public String accounts() {
        return APIs.Params.ACCOUNTS;
    }
}