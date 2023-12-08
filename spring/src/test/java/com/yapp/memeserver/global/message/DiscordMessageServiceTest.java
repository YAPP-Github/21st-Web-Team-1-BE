package com.yapp.memeserver.global.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

class DiscordMessageServiceTest {

    @Autowired
    private DiscordMessageService msgService;

    @Test
    public void 디스코드_메시지_테스트() throws Exception{
        // given
        boolean result = msgService.sendMsg("디스코드 테스트 알람입니다.");
        // then
        Assertions.assertEquals(result,true);
    }

}