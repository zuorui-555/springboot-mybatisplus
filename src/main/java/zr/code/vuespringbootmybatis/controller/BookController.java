package zr.code.vuespringbootmybatis.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import zr.code.vuespringbootmybatis.common.Result;
import zr.code.vuespringbootmybatis.entity.Book;
import zr.code.vuespringbootmybatis.mapper.BookMapper;

import javax.annotation.Resource;

@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    BookMapper bookMapper;

    @PostMapping
    public Result<?> save(@RequestBody Book book) {
        bookMapper.insert(book);
        return  Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Book book){
        bookMapper.updateById(book);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        bookMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(bookMapper.selectById(id));
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){

        LambdaQueryWrapper<Book> wrapper = Wrappers.<Book>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Book::getName, search);
        }
        Page<Book> bookPage = bookMapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(bookPage);
    }
}
