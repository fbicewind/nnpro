package cn.nnnight.controller;

import cn.nnnight.common.Result;
import cn.nnnight.enums.Status;
import cn.nnnight.service.EmotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/emotionDo")
public class EmotionDoController {

    @Autowired
    private EmotionService emotionService;

    @RequestMapping(value = "saveEmotion", method = RequestMethod.POST)
    @ResponseBody
    public Result saveEmotion(@RequestParam String text, @RequestParam String type) {
        Result result = new Result();
        boolean flag = emotionService.saveEmotion(text, type);
        result.setCode(flag ? Status.SUCCESS.getCode() : Status.FAILURE.getCode());
        return result;
    }

    @RequestMapping(value = "updateGoal", method = RequestMethod.POST)
    @ResponseBody
    public Result updateGoal(@RequestParam String id, @RequestParam String type) {
        Result result = new Result();
        boolean flag = emotionService.updateGoal(id, type);
        result.setCode(flag ? Status.SUCCESS.getCode() : Status.FAILURE.getCode());
        return result;
    }
}
