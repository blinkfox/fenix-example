package com.blinkfox.fenix.example.controller;

import com.blinkfox.fenix.consts.Const;
import com.blinkfox.fenix.example.entity.Book;
import com.blinkfox.fenix.example.service.BookService;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Book Controller.
 *
 * @author blinkfox on 2020-12-07.
 * @since v2.4.0
 */
@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {

    @Resource
    private BookService bookService;

    /**
     * 使用 saveAll 新增保存指定数量的图书信息.
     *
     * @param count 数量
     * @return hello
     */
    @GetMapping("/save-all/{count}")
    public ResponseEntity<String> saveBooks(@PathVariable("count") int count) {
        if (count <= 0) {
            return ResponseEntity.ok("count 必须是大于等于 0 的数.");
        }

        // 保存并统计耗时.
        List<Book> books = bookService.buildBooks(count);
        long start = System.currentTimeMillis();
        bookService.saveAll(books);
        String result = "使用【saveAll】方法保存完成，耗时:【" + (System.currentTimeMillis() - start) + "】ms.";
        log.info(result);
        return ResponseEntity.ok(result);
    }

    /**
     * 使用 saveAll 新增保存指定数量的图书信息.
     *
     * @param count 数量
     * @return hello
     */
    @GetMapping("/save-update-all/{count}")
    public ResponseEntity<String> saveAndUpdateBooks(@PathVariable("count") int count) {
        // 保存并统计耗时.
        List<Book> books = bookService.buildBooks(count);
        long start = System.currentTimeMillis();
        bookService.saveAll(books);
        String result = "使用【saveAll】方法新增完成，耗时:【" + (System.currentTimeMillis() - start) + "】ms.";
        log.info(result);

        // 再更新保存并统计耗时.
        List<Book> updateBooks = bookService.buildUpdateBooks(books);
        long start2 = System.currentTimeMillis();
        bookService.saveAll(updateBooks);
        String result2 = "使用【saveAll】方法更新完成，耗时:【" + (System.currentTimeMillis() - start2) + "】ms.";
        log.info(result2);
        return ResponseEntity.ok(result2);
    }

    /**
     * 使用 saveBatch 新增保存指定数量的图书信息.
     *
     * @param count 数量
     * @param isRoll 是否回滚
     * @return hello
     */
    @GetMapping("/save-batch/roll/{count}")
    public ResponseEntity<String> saveBatchBooksWithRollback(
            @PathVariable("count") int count,
            @RequestParam(name = "isRoll", required = false, defaultValue = "true") Boolean isRoll) {
        bookService.saveBatchWithRollback(count, isRoll);
        return ResponseEntity.ok("执行完毕.");
    }

    /**
     * 使用 saveBatchBooksWithRollback 新增保存指定数量的图书信息.
     *
     * @param isRoll 是否回滚
     * @return hello
     */
    @GetMapping("/delete-batch/roll")
    public ResponseEntity<String> saveAndDeleteBatchBooksWithRollback(
            @RequestParam(name = "isRoll", required = false, defaultValue = "true") Boolean isRoll) {
        List<Book> books = bookService.buildBooks(5);
        bookService.saveBatch(books, Const.DEFAULT_BATCH_SIZE);
        log.info("保存数据成功.");

        // 构造需要删除的两条 ID.
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < 2; ++i) {
            ids.add(books.get(i).getId());
        }

        try {
            bookService.deleteBatchByIdsWithRollback(ids, isRoll);
        } catch (Exception e) {
            log.error("删除数据时发生了我们指定的异常.", e);
        }
        return ResponseEntity.ok("执行完毕.");
    }

    /**
     * 使用 saveBatch 和 updateBatch 来新增或更新保存指定数量的图书信息.
     *
     * @param count 数量
     * @param batchSize 批量大小
     * @return hello
     */
    @GetMapping("/save-batch/{count}")
    public ResponseEntity<String> saveBatchBooks(
            @PathVariable("count") int count,
            @RequestParam(name = "batchSize", required = false) Integer batchSize) {
        // 先新增保存并统计耗时.
        List<Book> books = bookService.buildBooks(count);
        long start = System.currentTimeMillis();
        bookService.saveBatch(books, batchSize);
        String result = "使用【saveBatch】方法保存完成，耗时:【" + (System.currentTimeMillis() - start) + "】ms.";
        log.info(result);
        return ResponseEntity.ok(result);
    }

    /**
     * 使用 saveBatch 和 updateBatch 来新增或更新保存指定数量的图书信息.
     *
     * @param count 数量
     * @param batchSize 批量大小
     * @return hello
     */
    @GetMapping("/save-update-batch/{count}")
    public ResponseEntity<String> saveAndUpdateBatchBooks(
            @PathVariable("count") int count,
            @RequestParam(name = "batchSize", required = false) Integer batchSize) {
        // 先新增保存并统计耗时.
        List<Book> books = bookService.buildBooks(count);
        long start = System.currentTimeMillis();
        bookService.saveBatch(books, batchSize);
        String result = "使用【saveBatch】方法保存完成，耗时:【" + (System.currentTimeMillis() - start) + "】ms.";
        log.info(result);

        // 再更新保存并统计耗时.
        List<Book> updateBooks = bookService.buildUpdateBooks(books);
        long start2 = System.currentTimeMillis();
        bookService.updateBatch(updateBooks, batchSize);
        String result2 = "使用【updateBatch】方法保存完成，耗时:【" + (System.currentTimeMillis() - start2) + "】ms.";
        log.info(result2);
        return ResponseEntity.ok(result2);
    }

    /**
     * 使用 updateBatchWithRollback 来更新保存指定数量的图书信息.
     *
     * @param isRoll 是否回滚
     * @return hello
     */
    @GetMapping("/update-batch/roll")
    public ResponseEntity<String> updateBatchWithRollback(
            @RequestParam(name = "isRoll", required = false, defaultValue = "true") Boolean isRoll) {
        // 先新增保存并统计耗时.
        List<Book> books = bookService.buildBooks(10);
        long start = System.currentTimeMillis();
        bookService.saveBatch(books, 3);
        String result = "使用【saveBatch】方法新增保存完成，耗时:【" + (System.currentTimeMillis() - start) + "】ms.";
        log.info(result);

        // 再更新并统计耗时.
        List<Book> updateBooks = bookService.buildUpdateBooks(books);
        long start2 = System.currentTimeMillis();
        bookService.updateBatchWithRollback(updateBooks, isRoll);
        String result2 = "使用【updateBatch】方法更新完成，耗时:【" + (System.currentTimeMillis() - start2) + "】ms.";
        log.info(result2);
        return ResponseEntity.ok(result2);
    }

    /**
     * 使用 saveOrUpdateBatch 来新增或更新保存指定数量的图书信息.
     *
     * @param count 数量
     * @param batchSize 批量大小
     * @return hello
     */
    @GetMapping("/save-or-update-all-notnull/{count}")
    public ResponseEntity<String> saveOrUpdateAllByNotNullProperties(
            @PathVariable("count") int count,
            @RequestParam(name = "batchSize", required = false) Integer batchSize) {
        // 先新增保存并统计耗时.
        List<Book> books = bookService.buildBooks(count);
        long start = System.currentTimeMillis();
        bookService.saveOrUpdateAllByNotNullProperties(books);
        String result = "使用【saveOrUpdateAllByNotNullProperties】方法新增保存完成，"
                + "耗时:【" + (System.currentTimeMillis() - start) + "】ms.";
        log.info(result);

        // 再更新保存并统计耗时.
        List<Book> updateBooks = bookService.buildUpdateNotNullProperties(books);
        long start2 = System.currentTimeMillis();
        bookService.saveOrUpdateAllByNotNullProperties(updateBooks);
        String result2 = "使用【saveOrUpdateAllByNotNullProperties】方法更新保存完成，"
                + "耗时:【" + (System.currentTimeMillis() - start2) + "】ms.";
        log.info(result2);
        return ResponseEntity.ok(result2);
    }

    /**
     * 使用 deleteByIds 来新增或更新保存指定数量的图书信息.
     *
     * @param count 数量
     * @param batchSize 批量大小
     * @return hello
     */
    @GetMapping("/delete/{count}")
    public ResponseEntity<String> deleteByIds(
            @PathVariable("count") int count,
            @RequestParam(name = "batchSize", required = false) Integer batchSize) {
        // 先新增保存并统计耗时.
        List<Book> books = bookService.buildBooks(count);
        long start = System.currentTimeMillis();
        bookService.saveAll(books);
        String result = "使用【saveAll】方法新增保存完成，耗时:【" + (System.currentTimeMillis() - start) + "】ms.";
        log.info(result);

        // 再删除并统计耗时.
        List<String> ids = bookService.getBookIds(books);
        long start2 = System.currentTimeMillis();
        bookService.deleteByIds(ids);
        String result2 = "使用【deleteByIds】方法删除完成，耗时:【" + (System.currentTimeMillis() - start2) + "】ms.";
        log.info(result2);
        return ResponseEntity.ok(result2);
    }

    /**
     * 使用 deleteByIds 来新增或更新保存指定数量的图书信息.
     *
     * @param count 数量
     * @param batchSize 批量大小
     * @return hello
     */
    @GetMapping("/delete-batch/{count}")
    public ResponseEntity<String> deleteBatchByIds(
            @PathVariable("count") int count,
            @RequestParam(name = "batchSize", required = false) Integer batchSize) {
        // 先新增保存并统计耗时.
        List<Book> books = bookService.buildBooks(count);
        long start = System.currentTimeMillis();
        bookService.saveAll(books);
        String result = "使用【saveAll】方法新增保存完成，耗时:【" + (System.currentTimeMillis() - start) + "】ms.";
        log.info(result);

        // 再删除并统计耗时.
        List<String> ids = bookService.getBookIds(books);
        long start2 = System.currentTimeMillis();
        bookService.deleteBatchByIds(ids, batchSize);
        String result2 = "使用【deleteByIds】方法删除完成，耗时:【" + (System.currentTimeMillis() - start2) + "】ms.";
        log.info(result2);
        return ResponseEntity.ok(result2);
    }

    /**
     * 使用 asyncGetBooks 来异步获取图书信息.
     *
     * @param count 数量
     * @return hello
     */
    @GetMapping("/async/{count}")
    public ResponseEntity<String> asyncSaveBooks(
            @PathVariable("count") int count) {
        this.bookService.asyncSaveBooks(count);
        return ResponseEntity.ok("执行完成.");
    }

}
