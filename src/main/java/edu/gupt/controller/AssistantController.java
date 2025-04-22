package edu.gupt.controller;

import edu.gupt.domain.vo.ChatVO;
import edu.gupt.result.Result;
import edu.gupt.service.OllamaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assistant")
@Slf4j
public class AssistantController {
    @Autowired
    private OllamaService ollamaService;

    /**
     * 获取ollama的回答
     * @param prompt
     * @return
     */
    @RequestMapping("/query")
    public Result<ChatVO> query(@RequestParam("prompt") String prompt, @RequestParam("mode") boolean mode) {
        ChatVO chatVO = ChatVO.builder().reply(ollamaService.queryOllama(prompt, mode)).build();
        if (chatVO!=null) return Result.success(ChatVO.builder().reply(ollamaService.queryOllama(prompt, mode)).build());
        return Result.fail("服务器繁忙！请稍后重试......");
    }
}
