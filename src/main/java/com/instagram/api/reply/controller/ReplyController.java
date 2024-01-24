package com.instagram.api.reply.controller;

import com.instagram.api.reply.service.ReplyService;
import com.instagram.api.user.state.UserAuthorize;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reply")
@UserAuthorize
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
}
