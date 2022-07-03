package zr.code.vuespringbootmybatis.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import zr.code.vuespringbootmybatis.common.Result;
import zr.code.vuespringbootmybatis.entity.News;
import zr.code.vuespringbootmybatis.mapper.NewsMapper;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Resource
    NewsMapper newsMapper;

    @PostMapping
    public Result<?> save(@RequestBody News news) {
        news.setTime(new Date());
        System.out.println(new Date());
        newsMapper.insert(news);
        return  Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody News news){
        news.setTime(new Date());
        newsMapper.updateById(news);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        newsMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(newsMapper.selectById(id));
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){

        LambdaQueryWrapper<News> wrapper = Wrappers.<News>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(News::getTitle, search);
        }
        Page<News> newsPage = newsMapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(newsPage);
    }
}
